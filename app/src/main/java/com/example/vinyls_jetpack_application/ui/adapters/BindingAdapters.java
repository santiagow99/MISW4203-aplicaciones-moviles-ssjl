package com.example.vinyls_jetpack_application.ui.adapters;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;

public class BindingAdapters {

    @BindingAdapter("app:imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        // Use Glide to load the image from the provided URL and set it to the ImageView
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }
}