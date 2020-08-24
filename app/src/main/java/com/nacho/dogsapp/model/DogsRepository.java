package com.nacho.dogsapp.model;

import java.util.List;
import java.util.function.Supplier;

public class DogsRepository {
    private FakeDogsApi fakeDogsApi;

    public DogsRepository(FakeDogsApi fakeDogsApi) {
        this.fakeDogsApi = fakeDogsApi;
    }

//    public List<DogBreed> getDogs = fakeDogsApi.getDogs();

    public Supplier<List<DogBreed>> getDogs = () -> fakeDogsApi.getDogs.get();
}
