package com.imceits.android.myjobtracker.binding;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class BindingAdapters {

    @BindingAdapter("img_uri")
    public static void setCurrentUserPhotoUri(ImageView imageView, Uri uri) {
        if (uri != null) {
            Glide.with(imageView).load(uri).fitCenter().centerCrop().into(imageView);
        }
    }

    @BindingAdapter("visible")
    public static void setVisibility(View view, boolean visible){
        view.setVisibility(visible? View.VISIBLE : View.GONE);
    }
}
