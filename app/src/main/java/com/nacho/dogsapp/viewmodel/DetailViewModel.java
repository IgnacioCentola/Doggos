package com.nacho.dogsapp.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nacho.dogsapp.model.DogBreed;
import com.nacho.dogsapp.model.DogDatabase;

public class DetailViewModel extends AndroidViewModel {

    private MutableLiveData<DogBreed> dogLiveData = new MutableLiveData<>();
    private AsyncTask<Integer, Void, DogBreed> getSingleDogTask;

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<DogBreed> getDogLiveData() {
        return dogLiveData;
    }

    public void fetch(int uuid) {
        getSingleDogTask = new GetSingleDogTask();
        getSingleDogTask.execute(uuid);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (getSingleDogTask != null) {
            getSingleDogTask.cancel(true);
            getSingleDogTask = null;
        }
    }

    private class GetSingleDogTask extends AsyncTask<Integer, Void, DogBreed> {

        @Override
        protected DogBreed doInBackground(Integer... integers) {
            return DogDatabase.getInstance(getApplication()).getDogDao().getSingleDog(integers[0]);
        }

        @Override
        protected void onPostExecute(DogBreed dogBreed) {
            dogLiveData.setValue(dogBreed);
        }
    }
}
