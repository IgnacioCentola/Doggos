package com.nacho.dogsapp.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

@Dao
public interface DogDao {
    @Insert
    List<Long> insertAll(DogBreed... dogs);

    @Query("SELECT * FROM dogbreed")
    List<DogBreed> getAllDogs();

    @Query("SELECT * FROM dogbreed WHERE uuid = :dogId")
    DogBreed getSingleDog(int dogId);

    @Query("DELETE FROM dogbreed")
    void deleteAllDogs();

    @Query("UPDATE dogbreed SET isFavorite = :isFavorite WHERE uuid = :id")
    void setFavorite(int id, boolean isFavorite);
}
