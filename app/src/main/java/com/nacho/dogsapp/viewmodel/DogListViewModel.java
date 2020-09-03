package com.nacho.dogsapp.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.nacho.dogsapp.model.DogBreed;
import com.nacho.dogsapp.model.DogDao;
import com.nacho.dogsapp.model.DogDatabase;
import com.nacho.dogsapp.model.DogsApiService;
import com.nacho.dogsapp.util.NotificationsHelper;
import com.nacho.dogsapp.util.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DogListViewModel extends AndroidViewModel {
    private MutableLiveData<List<DogBreed>> dogs = new MutableLiveData<>();
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private DogsApiService dogsApiService = new DogsApiService();
    private CompositeDisposable disposable = new CompositeDisposable();

    private AsyncTask<List<DogBreed>, Void, List<DogBreed>> insertTask;
    private AsyncTask<Void, Void, List<DogBreed>> retrieveTask;

    private SharedPreferencesHelper preferencesHelper = SharedPreferencesHelper.getInstance(getApplication());
    private long refreshTime = 5 * 60 * 1000 * 1000 * 1000L;

    public DogListViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<DogBreed>> getDogs() {
        return dogs;
    }

    public MutableLiveData<Boolean> IsDogLoadError() {
        return isError;
    }

    public MutableLiveData<Boolean> IsLoading() {
        return isLoading;
    }

    public void refresh() {
        long updateTime = preferencesHelper.getUpdateTime();
        long currentTime = System.nanoTime();
        if (updateTime != 0 && currentTime - updateTime < refreshTime) {
            fetchFromDatabase();
        } else {
            fetchFromDogsApi();
        }
    }

    public void refreshBypassCache(){
        fetchFromDogsApi();
    }

    private void fetchFromDatabase() {
        isLoading.setValue(true);
        retrieveTask = new RetrieveDogsTask();
        retrieveTask.execute();
    }

    private void fetchFromDogsApi() {
        isLoading.setValue(true);
        disposable.add(
                dogsApiService.getDogs()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                            @Override
                            public void onSuccess(List<DogBreed> dogBreeds) {
//                                Collections.reverse(dogBreeds);
                                // * Asynchronously save dog list to local DB
                                insertTask = new InsertDogsTask();
                                insertTask.execute(dogBreeds);
                                NotificationsHelper.getInstance(getApplication()).showNotification();
                            }

                            @Override
                            public void onError(Throwable e) {
                                isError.setValue(true);
                                isLoading.setValue(false);
                                e.printStackTrace();
                            }
                        }));
    }

    private void setDogListLiveDataValue(List<DogBreed> dogList) {
        dogs.setValue(dogList);
        isError.setValue(false);
        isLoading.setValue(false);
    }

    @Override
    protected void onCleared() {
        disposable.clear();
        //* Destroy async tasks when the app lifecycle is destroyed
        if (insertTask != null) {
            insertTask.cancel(true);
            insertTask = null;
        }

        if (retrieveTask != null) {
            retrieveTask.cancel(true);
            retrieveTask = null;
        }
    }

    private class InsertDogsTask extends AsyncTask<List<DogBreed>, Void, List<DogBreed>> {

        @SafeVarargs
        @Override
        protected final List<DogBreed> doInBackground(List<DogBreed>... lists) {
            List<DogBreed> list = lists[0];
            DogDao dao = DogDatabase
                    .getInstance(getApplication())
                    .getDogDao();

            dao.deleteAllDogs();
            ArrayList<DogBreed> newList = new ArrayList<>(list);
            List<Long> result = dao.insertAll(newList.toArray(new DogBreed[0]));

            int i = 0;
            while (i < list.size()) {
                list.get(i).setUuid(result.get(i).intValue());
                i++;
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<DogBreed> dogBreeds) {
            //* Set the live data list value once the async task is done
            setDogListLiveDataValue(dogBreeds);
            preferencesHelper.setUpdateTime(System.nanoTime());
        }
    }

    private class RetrieveDogsTask extends AsyncTask<Void, Void, List<DogBreed>> {

        @Override
        protected List<DogBreed> doInBackground(Void... voids) {
            return DogDatabase.getInstance(getApplication()).getDogDao().getAllDogs();
        }

        @Override
        protected void onPostExecute(List<DogBreed> dogBreeds) {
            setDogListLiveDataValue(dogBreeds);
            Toast.makeText(getApplication(), "Dogs retrieved from database", Toast.LENGTH_SHORT).show();
        }
    }
}
