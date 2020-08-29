package com.nacho.dogsapp.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

interface DogsApi {
    @GET("DevTides/DogsApi/master/dogs.json")
    Single<List<DogBreed>> getDogs();
}
