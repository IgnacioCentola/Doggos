package com.nacho.dogsapp.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nacho.dogsapp.R;
import com.nacho.dogsapp.databinding.FragmentDetailBinding;
import com.nacho.dogsapp.model.DogBreed;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding fragmentDetailBinding;
    private int dogUuid;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailFragment.
     */
    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentDetailBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_detail,
                        container, false);
        MyHandlers myHandlers = new MyHandlers();
        if (getArguments() != null) {
            dogUuid = DetailFragmentArgs.fromBundle(getArguments()).getDogUuid();
        } else {
            dogUuid = 0;
        }
        DogBreed dogBreed = new DogBreed(dogUuid, "BreedID" + dogUuid,
                "Winchester","10 years","Group 3",
                "50 years","Kind","ImageUrl");
        fragmentDetailBinding.setDog(dogBreed);
        fragmentDetailBinding.setHandlers(myHandlers);
        return fragmentDetailBinding.getRoot();
    }
}