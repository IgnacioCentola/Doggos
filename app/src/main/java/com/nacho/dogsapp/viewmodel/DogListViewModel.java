package com.nacho.dogsapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nacho.dogsapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;

public class DogListViewModel extends ViewModel {
    private MutableLiveData<List<DogBreed>> dogs = new MutableLiveData<>();
    private MutableLiveData<Boolean> isDogLoadError = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public DogListViewModel() {
    }

    public MutableLiveData<List<DogBreed>> getDogs() {
        return dogs;
    }

    public MutableLiveData<Boolean> IsDogLoadError() {
        return isDogLoadError;
    }

    public MutableLiveData<Boolean> IsLoading() {
        return isLoading;
    }

    public void refresh() {
//        List<DogBreed> dogList = repository.getDogs.get();
        ArrayList<DogBreed> dogList = new ArrayList<>();
        DogBreed dog1 = new DogBreed(1, "1", "Corgi", "12 years",
                "", "", "", "");
        DogBreed dog2 = new DogBreed(2, "2", "Bulldog", "10 years",
                "", "", "", "");
        DogBreed dog3 = new DogBreed(3, "3", "Chihuahua", "15 years",
                "", "", "", "");
        DogBreed dog4 = new DogBreed(4, "3", "Chihuahua", "15 years",
                "", "", "", "");
        DogBreed dog5 = new DogBreed(5, "3", "Chihuahua", "15 years",
                "", "", "", "");
        DogBreed dog6 = new DogBreed(6, "3", "Chihuahua", "15 years",
                "", "", "", "");

        dogList.add(dog1);
        dogList.add(dog2);
        dogList.add(dog3);
        dogList.add(dog4);
        dogList.add(dog5);
        dogList.add(dog6);
        dogs.setValue(dogList);
        isDogLoadError.setValue(false);
        isLoading.setValue(false);

    }
}
