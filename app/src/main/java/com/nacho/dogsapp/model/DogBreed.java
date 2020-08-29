package com.nacho.dogsapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class DogBreed {
    @ColumnInfo(name = "breed_id")
    @SerializedName("id")
    private String breedId;

    @ColumnInfo(name = "dog_name")
    @SerializedName("name")
    private String dogBreed;

    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    private String lifeSpan;

    @ColumnInfo(name = "breed_group")
    @SerializedName("breed_group")
    private String breedGroup;

    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    private String bredFor;

    @ColumnInfo(name = "temperament")
    @SerializedName("temperament")
    private String temperament;

    @ColumnInfo(name = "image_url")
    @SerializedName("url")
    private String imageUrl;

    @PrimaryKey(autoGenerate = true)
    private int uuid;

    public DogBreed(int uuid, String breedId, String dogBreed, String lifeSpan, String breedGroup, String bredFor, String temperament, String imageUrl) {
        this.uuid = uuid;
        this.breedId = breedId;
        this.dogBreed = dogBreed;
        this.lifeSpan = lifeSpan;
        this.breedGroup = breedGroup;
        this.bredFor = bredFor;
        this.temperament = temperament;
        this.imageUrl = imageUrl;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getBreedId() {
        return breedId;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public String getBreedGroup() {
        return breedGroup;
    }

    public String getBredFor() {
        return bredFor;
    }

    public String getTemperament() {
        return temperament;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "DogBreed{" +
                "uuid=" + uuid +
                ", breedId='" + breedId + '\'' +
                ", dogBreed='" + dogBreed + '\'' +
                ", lifeSpan='" + lifeSpan + '\'' +
                ", breedGroup='" + breedGroup + '\'' +
                ", bredFor='" + bredFor + '\'' +
                ", temperament='" + temperament + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
