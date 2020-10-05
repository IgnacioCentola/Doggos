package com.nacho.dogsapp.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nacho.dogsapp.R;
import com.nacho.dogsapp.databinding.ItemDogBinding;
import com.nacho.dogsapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DogListAdapter extends RecyclerView.Adapter<DogListAdapter.DogViewHolder> implements Filterable {

    private List<DogBreed> dogList;
    private List<DogBreed> dogListAll;

    public DogListAdapter(ArrayList<DogBreed> dogList) {
        this.dogList = dogList;
        this.dogListAll = dogList;
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
        //* Replaced by data binding adapter method ///////////////////////////////////
        // ImageView dogImageView = holder.itemDogBinding.getRoot().findViewById(R.id.imageView_dog_image);

        // Util.loadImage(
        //         dogImageView,
        //         dogList.get(position).getImageUrl(),
        //         Util.getProgressDrawable(dogImageView.getContext()));
        ////////////////////////////////////////////////////*
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            // * run on background thread
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<DogBreed> filteredList = new ArrayList<>();
                if (charSequence.toString().isEmpty()) {
                    filteredList.addAll(dogListAll);
                } else {
                    filteredList = dogListAll.stream()
                            .filter(dogBreed -> dogBreed.getDogBreed()
                                    .toLowerCase()
                                    .trim()
                                    .contains(charSequence
                                            .toString()
                                            .toLowerCase()
                                            .trim()))
                            .collect(Collectors.toList());
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                filterResults.count = filteredList.size();
                return filterResults;
            }

            // * run on UI thread
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dogList.clear();
                dogList.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
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
