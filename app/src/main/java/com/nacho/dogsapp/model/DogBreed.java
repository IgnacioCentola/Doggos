package com.nacho.dogsapp.model;

public class DogBreed {
    private int uuid;
    private String breedId;
    private String dogBreed;
    private String lifeSpan;
    private String breedGroup;
    private String bredFor;
    private String temperament;
    private String imageUrl;

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
}
