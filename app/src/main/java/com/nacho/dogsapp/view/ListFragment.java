package com.nacho.dogsapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nacho.dogsapp.R;
import com.nacho.dogsapp.viewmodel.DogListViewModel;

import java.util.ArrayList;

public class ListFragment extends Fragment {


    private DogListViewModel viewModel;
    private DogListAdapter adapter = new DogListAdapter(new ArrayList<>());

    private RecyclerView dogsRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView isErrorTextView;
    private ProgressBar isLoadingProgressBar;

    public ListFragment() {
    }

    public static ListFragment newInstance() {
        return new ListFragment();
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

//        FakeDogsApi fakeDogsApi = new FakeDogsApi();
//        DogsRepository dogsRepository = new DogsRepository(fakeDogsApi);
        viewModel = new ViewModelProvider(this).get(DogListViewModel.class);
        viewModel.refresh();


        dogsRecyclerView = getView().findViewById(R.id.recycler_view_dogs_list);
        isLoadingProgressBar = getView().findViewById(R.id.loading_bar);
        isErrorTextView = getView().findViewById(R.id.textView_error_msg);
        swipeRefreshLayout = getView().findViewById(R.id.refresh_layout);

        dogsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dogsRecyclerView.setAdapter(adapter);

        swipeRefreshLayout.setColorSchemeResources(
                R.color.secondaryColor,
                R.color.primaryColor,
                R.color.secondaryDarkColor);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            dogsRecyclerView.setVisibility(View.GONE);
            isErrorTextView.setVisibility(View.GONE);
            isLoadingProgressBar.setVisibility(View.VISIBLE);
//            viewModel.refreshBypassCache();
            swipeRefreshLayout.setRefreshing(false);
        });

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getDogs().observe(getViewLifecycleOwner(), dogs -> {
            if (dogs != null) {
                dogsRecyclerView.setVisibility(View.VISIBLE);
                adapter.updateDogList(dogs);
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
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
//        switch (item.getItemId()) {
//            case R.id.actionSettings: {
//                if(isAdded()) {
//                    Navigation.findNavController(getView()).navigate(ListFragmentDirections.actionSettings());
//                }
//                break;
//            }
//        }
//        return super.onOptionsItemSelected(item);
    }
}