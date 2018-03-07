package id.towercontroller.org.towercontroller.model;

import android.app.Activity;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Hafid on 12/1/2017.
 */

public class ItemMarker {
    private GoogleMap googleMap;
    private Marker myMarker;

    public ItemMarker(GoogleMap googleMap, MarkerOptions markerOptions, ItemExample item) {
        this.googleMap = googleMap;
        myMarker = this.googleMap.addMarker(markerOptions);
        myMarker.setTag(item);
    }

    public Marker getMarker() {
        return myMarker;
    }

    public ItemExample getItem() {
        return (ItemExample) myMarker.getTag();
    }

    public void setItem(ItemExample item) {
        myMarker.setTag(item);
    }

    public void loadMarkerIcon(Activity activity, String loadUri) {
        //"http://www.myiconfinder.com/uploads/iconsets/256-256-a5485b563efc4511e0cd8bd04ad0fe9e.png"
        Glide.with(activity).asBitmap().load(loadUri).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(resource, 50, 50, false);
                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(resizedBitmap);
                myMarker.setIcon(icon);
            }
        });
    }
}
