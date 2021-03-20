package com.nacho.dogsapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nacho.dogsapp.R;
import com.nacho.dogsapp.model.DogBreed;
import com.nacho.dogsapp.viewmodel.DogListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListFragment extends Fragment {

    private DogListViewModel viewModel;
    private DogListAdapter adapter = new DogListAdapter(new ArrayList<>());

    private SearchView searchView = null;

    private RecyclerView dogsRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView isErrorTextView, noResultsTextView;
    private ProgressBar isLoadingProgressBar;
    private boolean favorites = false;

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
//        return inflater.inflate(R.layout.fragment_list, container, false);
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(viewModel == null) {
            viewModel = new ViewModelProvider(this).get(DogListViewModel.class);

            if(adapter.getItemCount() == 0) {
                viewModel.refresh();
            }
        }

        dogsRecyclerView = getView().findViewById(R.id.recycler_view_dogs_list);
        isLoadingProgressBar = getView().findViewById(R.id.loading_bar);
        isErrorTextView = getView().findViewById(R.id.textView_error_msg);
        noResultsTextView = getView().findViewById(R.id.textView_no_results);
        swipeRefreshLayout = getView().findViewById(R.id.refresh_layout);


        dogsRecyclerView.setLayoutManager(
                getActivity().getResources().getConfiguration().orientation == 1
                        ? new LinearLayoutManager(getContext())
                        : new GridLayoutManager(getContext(), 2));

        dogsRecyclerView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(
                R.color.secondaryColor,
                R.color.primaryColor,
                R.color.secondaryDarkColor);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            favorites = false;
            dogsRecyclerView.setVisibility(View.GONE);
            isErrorTextView.setVisibility(View.GONE);
            noResultsTextView.setVisibility(View.GONE);
            isLoadingProgressBar.setVisibility(View.VISIBLE);
            viewModel.refreshBypassCache();
            swipeRefreshLayout.setRefreshing(false);
        });

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getDogs().observe(getViewLifecycleOwner(), dogs -> {
            if (dogs != null) {
                dogsRecyclerView.setVisibility(View.VISIBLE);
                noResultsTextView.setVisibility(View.GONE);
                if (favorites) {
                    List<DogBreed> favoritesList = dogs.stream()
                            .filter(DogBreed::isFavorite)
                            .collect(Collectors.toList());

                    adapter.updateDogList(favoritesList);
                } else {
                    adapter.updateDogList(dogs);
                }
            }
        });

        viewModel.IsDogLoadError().observe(getViewLifecycleOwner(), isError -> {
            if (isError != null) {
                isErrorTextView.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.IsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                isLoadingProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    isErrorTextView.setVisibility(View.GONE);
                    dogsRecyclerView.setVisibility(View.GONE);
                    noResultsTextView.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.list_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);

//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null) {
            // Assumes current activity is the searchable activity
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint("Enter dog name");

            searchView.setInputType(1);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    Log.d("Jesus", newText);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
//                    adapter.getFilter().filter(query);
                    Log.d("Jesus2", query);
                    return false;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                if (isAdded()) {
                    Navigation.findNavController(getView()).navigate(ListFragmentDirections.actionSettings());
                }
                return true;
            }
            case R.id.favorites_filter: {
                favorites = !favorites;
                observeViewModel();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}