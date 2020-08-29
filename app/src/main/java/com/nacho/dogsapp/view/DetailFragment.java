package com.nacho.dogsapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nacho.dogsapp.R;
import com.nacho.dogsapp.databinding.FragmentDetailBinding;
import com.nacho.dogsapp.model.DogBreed;
import com.nacho.dogsapp.util.Util;
import com.nacho.dogsapp.viewmodel.DetailViewModel;
import com.nacho.dogsapp.viewmodel.DogListViewModel;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding fragmentDetailBinding;
    private int dogUuid;
    private String TAG = "DetailFragment";

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        return fragment;
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

        detailViewModel.getDogLiveData().observe(getViewLifecycleOwner(), dogBreed -> {
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