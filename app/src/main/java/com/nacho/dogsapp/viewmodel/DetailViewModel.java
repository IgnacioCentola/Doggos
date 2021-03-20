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
    private AsyncTask<SetIsFavoriteTaskParams, Void, DogBreed> setIsFavoriteTask;

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

    public void setIsFavorite(int uuid, boolean isFavorite) {
        setIsFavoriteTask = new SetIsFavoriteTask();
        SetIsFavoriteTaskParams params = new SetIsFavoriteTaskParams(uuid, isFavorite);
        setIsFavoriteTask.execute(params);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (getSingleDogTask != null) {
            getSingleDogTask.cancel(true);
            getSingleDogTask = null;
        }

        if (setIsFavoriteTask != null) {
            setIsFavoriteTask.cancel(true);
            setIsFavoriteTask = null;
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

    private class SetIsFavoriteTask extends AsyncTask<SetIsFavoriteTaskParams, Void, DogBreed> {

        @Override
        protected DogBreed doInBackground(SetIsFavoriteTaskParams... setIsFavoriteTaskParams) {
            int id = setIsFavoriteTaskParams[0].getId();
            boolean isFavorite = setIsFavoriteTaskParams[0].isFavorite();
            DogDatabase.getInstance(getApplication()).getDogDao().setFavorite(id, isFavorite);
            return DogDatabase.getInstance(getApplication()).getDogDao().getSingleDog(id);
        }

        @Override
        protected void onPostExecute(DogBreed dogBreed) {
            dogLiveData.setValue(dogBreed);
        }
    }

    private static class SetIsFavoriteTaskParams {
        private int id;
        private boolean isFavorite;

        public SetIsFavoriteTaskParams(int id, boolean isFavorite) {
            this.id = id;
            this.isFavorite = isFavorite;
        }

        public int getId() {
            return id;
        }

        public boolean isFavorite() {
            return isFavorite;
        }
    }
}
