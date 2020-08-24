package com.nacho.dogsapp.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nacho.dogsapp.R;
import com.nacho.dogsapp.databinding.ItemDogBinding;
import com.nacho.dogsapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DogListAdapter extends RecyclerView.Adapter<DogListAdapter.DogViewHolder> {

    private ArrayList<DogBreed> dogList;

    public DogListAdapter(ArrayList<DogBreed> dogList) {
        this.dogList = dogList;
    }

    public void updateDogList(List<DogBreed> newList) {
        dogList.clear();
        dogList.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater
//                .from(parent.getContext())
//                .inflate(R.layout.item_dog, parent, false);
        ItemDogBinding itemDogBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.item_dog,
                        parent,
                        false);

        MyHandlers myHandlers = new MyHandlers();
        itemDogBinding.setHandlers(myHandlers);

        return new DogViewHolder(itemDogBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        holder.itemDogBinding.setDog(dogList.get(position));
//        holder.itemDogBinding.getRoot().setOnClickListener((view) ->{
//            //* Navigate to detail fragment and pass the appropriate parameters
//            MyHandlers handlers = new MyHandlers();
//            handlers.onGoToDetails(view);
//        });
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    class DogViewHolder extends RecyclerView.ViewHolder {

        //                public View itemView;
        public ItemDogBinding itemDogBinding;

        public DogViewHolder(ItemDogBinding binding) {
            super(binding.getRoot());
//            this.itemView = binding.getRoot();
            itemDogBinding = binding;
        }
    }
}
