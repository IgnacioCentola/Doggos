package com.nacho.dogsapp.view;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.nacho.dogsapp.R;

public class MyHandlers {
    public void onGoToDetails(View view, int uuid) {
        NavController controller =  Navigation.findNavController(view);
        //* Pass args to fragment through the action that works like a channel
        ListFragmentDirections.ActionDetail action =
                ListFragmentDirections.actionDetail();
        action.setDogUuid(uuid);
        controller.navigate(action);
    }

//    public void onGoToList(View view) {
//        NavController controller =  Navigation.findNavController(view);
//        NavDirections action = DetailFragmentDirections.actionList();
//        controller.navigate(action);
//    }
}
