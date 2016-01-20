package com.example.method.worksurge.View;

import android.content.Context;
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
        Toast.makeText(getContext(), "Info window clicked", Toast.LENGTH_SHORT).show();
    }

    // Initialize
    public void init()
    {
        if(((FoundVacanciesActivity) getActivity()).checkConnectivity())
        {
            wc = new WebsiteConnector();
            new ReadWebsiteAsync(view.getContext().getApplicationContext()).execute();
        }
        else
        {
            Toast.makeText(getContext(), getResources().getString(R.string.no_connection), Toast.LENGTH_LONG).show();
        }

    }

    private void addMarkers(GoogleMap googleMap)
    {
        init();

        // Add Markers
        if(mapList != null)
        {
            for(VacancyMapDetail item : mapList)
            {
                LatLng latLng = convertAddressToLongAndLat(item.getAddress());
                googleMap.addMarker(new MarkerOptions()
                                .title(item.getVacancyModel().getTitle())
                                .snippet(item.getVacancyModel().getUndertitle())
                                .position(latLng)
                );

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
            }
        }

        googleMap.addMarker(new MarkerOptions()
                .title("Test")
                .snippet("Testtest g rTest")
                .position(new LatLng(52.067075, 4.32)));
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

    private class ReadWebsiteAsync extends AsyncTask<String, Void, Boolean> {
        private Context context;
        private List<VacancyMapDetail> model = new ArrayList<VacancyMapDetail>();

        private ReadWebsiteAsync(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            VacancyDetailModel test = new VacancyDetailModel();
            mapList = wc.readWebsiteMap(((FoundVacanciesActivity) getActivity()).getVacancyList());
            return true; // Return false if reading is unsuccesful
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(!result)
            {
                Toast.makeText(context, getResources().getString(R.string.no_connection), Toast.LENGTH_LONG).show();
            }
            else
            {
                // Add Markers
                // addMarkers(googleMap);
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
