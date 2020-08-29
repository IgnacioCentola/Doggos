package com.nacho.dogsapp.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nacho.dogsapp.R;

public class Util {
    public static void loadImage(ImageView imageView, String url,
                                 CircularProgressDrawable progressDrawable) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(progressDrawable)
                .error(R.mipmap.ic_launcher);

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .transition(withCrossFade())
                .into(imageView);

    }

    public static CircularProgressDrawable getProgressDrawable(Context context) {
        CircularProgressDrawable cpd = new CircularProgressDrawable(context);
        cpd.setStrokeWidth(10f);
        cpd.setCenterRadius(50f);
        cpd.setColorSchemeColors(R.color.secondaryLightColor, R.color.secondaryColor, R.color.secondaryDarkColor);
//        cpd.setBackgroundColor(R.color.primaryLightColor);
        cpd.start();
        return cpd;
    }

    //* Substitutes the need to manually load the image from the fragments with Glide into the ImageViews
    //* by making a custom XML prop that can be used as data binding
    @BindingAdapter("android:imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        loadImage(imageView, url, getProgressDrawable(imageView.getContext()));
    }
}
