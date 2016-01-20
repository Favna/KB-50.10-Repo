package com.example.method.worksurge.View;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.method.worksurge.Enum.IntentEnum;
import com.example.method.worksurge.Model.VacancyDetailModel;
import com.example.method.worksurge.Model.VacancyMapDetail;
import com.example.method.worksurge.Model.VacancyModel;
import com.example.method.worksurge.R;
import com.example.method.worksurge.WebsiteConnector.WebsiteConnector;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    private WebsiteConnector wc;
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

        if(mapFragment != null)
            mapFragment.getMapAsync(this);
        else
            Toast.makeText(getContext(), "Map was not properly initialized", Toast.LENGTH_SHORT).show();

        init();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Set google functions
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

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

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(52.067075, 4.32), 13));
    }

    public void init()
    {
        wc = new WebsiteConnector();
        new ReadWebsiteAsync(view.getContext().getApplicationContext()).execute();
    }

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
            model = wc.readWebsiteMap(((FoundVacanciesActivity) getActivity()).getVacancyList());
            return true; // Return false if reading is unsuccesful
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(!result)
            {
                Toast.makeText(context, "Couldn't connect to the online Database", Toast.LENGTH_LONG).show();
            }
            else
            {
                mapList = model; // unnecessary
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
