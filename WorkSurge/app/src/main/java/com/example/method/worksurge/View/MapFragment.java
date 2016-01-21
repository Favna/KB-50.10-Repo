package com.example.method.worksurge.View;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.example.method.worksurge.Enum.FragmentEnum;
import com.example.method.worksurge.Enum.IntentEnum;
import com.example.method.worksurge.Model.VacancyDetailModel;
import com.example.method.worksurge.Model.VacancyMapDetail;
import com.example.method.worksurge.R;
import com.example.method.worksurge.WebsiteConnector.WebsiteConnector;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements
        OnMapReadyCallback,
        OnInfoWindowClickListener {

    private View view;
    private WebsiteConnector wc;
    private GoogleMap googleMap;
    private List<VacancyMapDetail> mapList = new ArrayList<>();
    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.workSurgeGoogleMap);

        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.map_not_initialized), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        // Set google functions
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setOnInfoWindowClickListener(this);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Position Camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.067075, 4.32), 13));

        addMarkers(googleMap);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        for(int i = 0; i < mapList.size(); i++)
        {
            if(mapList.get(i).getVacancyModel().getUndertitle().equals(marker.getSnippet()) && mapList.get(i).getVacancyModel().getTitle().equals(marker.getTitle())
                    && !mapList.get(i).getVacancyModel().getURL().isEmpty())
            {
                ((FoundVacanciesActivity) getActivity()).ReadWebsiteAsync(i, FragmentEnum.MAP);
            }
        }

    }

    private void addMarkers(GoogleMap googleMap)
    {
        mapList = ((FoundVacanciesActivity) getActivity()).getVacancyMapList();

        // Add Markers
        if(mapList != null)
        {
            for(VacancyMapDetail item : mapList)
            {
                LatLng latLng = convertAddressToLongAndLat(item.getAddress());
                if(latLng != null)
                {
                    googleMap.addMarker(new MarkerOptions()
                                    .title(item.getVacancyModel().getTitle().isEmpty() ? "No Title" : item.getVacancyModel().getTitle())
                                    .snippet(item.getVacancyModel().getUndertitle().isEmpty() ? " " : item.getVacancyModel().getUndertitle())
                                    .position(latLng)
                    );
                }
            }
        }
    }

    // Convert Address to long and lat(Google: LatLng)
    private LatLng convertAddressToLongAndLat(String strAddress)
    {
        Geocoder geoCoder = new Geocoder(getContext());
        List<Address> address = null;
        LatLng latLng = null;

        try {
            address = geoCoder.getFromLocationName(strAddress, 1);

            if(address == null)
                return null;

            Address loc = address.get(0);
            latLng = new LatLng(loc.getLatitude(), loc.getLongitude());

        } catch (Exception ex)
        {
            return null;
        }

        return latLng;
    }

}
