package com.nacho.dogsapp.model;

import java.util.ArrayList;
import java.util.function.Supplier;

public class FakeDogsApi {
    private ArrayList<DogBreed> dogList;

    public FakeDogsApi() {
        dogList = new ArrayList<>();        DogBreed dog1 = new DogBreed(1, "1", "Corgi", "12 years",
                "", "", "", "");
        DogBreed dog2 = new DogBreed(2, "2", "Bulldog", "10 years",
                "", "", "", "");
        DogBreed dog3 = new DogBreed(3, "3", "Chihuahua", "15 years",
                "", "", "", "");

        dogList.add(dog1);
        dogList.add(dog2);
        dogList.add(dog3);
    }

    public Supplier<ArrayList<DogBreed>> getDogs =  () -> dogList;

}
