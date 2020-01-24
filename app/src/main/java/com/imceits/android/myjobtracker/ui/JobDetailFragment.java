package com.imceits.android.myjobtracker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.imceits.android.myjobtracker.R;
import com.imceits.android.myjobtracker.data.Jobs;
import com.imceits.android.myjobtracker.databinding.FragmentJobDetailBinding;

import java.util.Arrays;

public class JobDetailFragment extends Fragment implements  OnMapReadyCallback {

    private JobsViewModel mViewModel;
    private GoogleMap mMap;
    private Jobs jobs;
    private FragmentJobDetailBinding detailBinding;
    private SharedPreferences sharedPreferences;
    private BottomSheetBehavior sheetBehavior;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), getString(R.string.google_api_key));
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        detailBinding = FragmentJobDetailBinding.inflate(inflater, container, false);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.fragment_search);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        RectangularBounds bounds = RectangularBounds.newInstance(new LatLng(1.1304753, 103.6920359), new LatLng(1.4504753, 104.0120359));
        autocompleteFragment.setCountry("SG");
        autocompleteFragment.setLocationBias(bounds);
        autocompleteFragment.setLocationRestriction(bounds);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                addMarker(place);
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.d("Map places", "Place error occurred : " + status);
            }
        });
        assert getArguments() != null;
        jobs = JobDetailFragmentArgs.fromBundle(getArguments()).getJobs();
        detailBinding.setJob(jobs);
        saveCache(jobs);
        detailBinding.imgExpand.setOnClickListener(v -> {
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        sheetBehavior = BottomSheetBehavior.from(detailBinding.bottomSheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                    case BottomSheetBehavior.STATE_EXPANDED:
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float slideOffset) {
                animateSheetArrow(detailBinding.imgExpand, slideOffset);
            }
        });
        return detailBinding.getRoot();
    }

    private void animateSheetArrow(ImageView imageView, float slideOffset) {
        imageView.setRotation(slideOffset * 180);
    }

    private void saveCache(Jobs jobs) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String job = new Gson().toJson(jobs);
        editor.putString("job", job);
        editor.apply();

    }

    private void addJobPlace(Jobs jobs) {
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(jobs.getGeoLocation().getLatitude(), jobs.getGeoLocation().getLongitude());
        markerOptions.position(latLng);
        markerOptions.title(jobs.getCompany());
        markerOptions.snippet(jobs.getAddress());
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        mMap.addMarker(markerOptions).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }

    private void addMarker(Place place) {
        if (place.getLatLng() != null) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position( place.getLatLng());
            markerOptions.title(place.getName());
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        }else {
            Toast.makeText(requireContext(), getString(R.string.place_message), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(JobsViewModel.class);
        detailBinding.setViewmodel(mViewModel);
        detailBinding.executePendingBindings();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sgLatLng = new LatLng(1.290270, 103.851959);
        mMap.addMarker(new MarkerOptions().position(sgLatLng).title("Singapore"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sgLatLng));
        mMap.setMinZoomPreference(15);  // min zoom in
        mMap.setMaxZoomPreference(50);   // max zoom out
        mMap.setTrafficEnabled(true);
     //   mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
      //  mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.setBuildingsEnabled(true);
      //  mMap.setIndoorEnabled(true);
        addJobPlace(jobs);
    }
}
