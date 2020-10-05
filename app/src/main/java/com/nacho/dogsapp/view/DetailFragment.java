package com.nacho.dogsapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nacho.dogsapp.R;
import com.nacho.dogsapp.databinding.FragmentDetailBinding;
import com.nacho.dogsapp.model.DogBreed;
import com.nacho.dogsapp.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding fragmentDetailBinding;
    private FloatingActionButton floatingActionButton;
    private DogBreed dog;
    private int dogUuid;
    private String TAG = "DetailFragment";

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDetailBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_detail,
                        container, false);

        return fragmentDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            dogUuid = DetailFragmentArgs.fromBundle(getArguments()).getDogUuid();
        }

        DetailViewModel detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        detailViewModel.fetch(dogUuid);

        floatingActionButton = getView().findViewById(R.id.favorite_button);
        floatingActionButton.setOnClickListener(button -> {
            detailViewModel.setIsFavorite(dog.getUuid(), dog.isFavorite());

        });

        detailViewModel.getDogLiveData().observe(getViewLifecycleOwner(), dogBreed -> {
            dog = dogBreed;
            fragmentDetailBinding.setDog(dogBreed);
            //* Replaced by data binding adapter method ///////////////////////////////////
            //  ImageView dogImageView = fragmentDetailBinding.getRoot().findViewById(R.id.imageView_dog_image_detail);

            // Util.loadImage(dogImageView,
            //         dogBreed.getImageUrl(),
            //         Util.getProgressDrawable(dogImageView.getContext()));
            ////////////////////////////////////////////////////*
            Log.d(TAG, dogBreed.toString());
        });


    }
}