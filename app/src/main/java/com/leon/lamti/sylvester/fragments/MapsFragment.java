package com.leon.lamti.sylvester.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.leon.lamti.sylvester.R;
import com.leon.lamti.sylvester.searchRecyclerViewPackage.Searches;

import io.realm.Realm;
import io.realm.RealmResults;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private static GoogleMap mMap;
    private SupportMapFragment mapFragment;

    // R e a l m
    Realm realm;
    RealmResults<Searches> realmResults;

    private static  String mUserInput;

    public MapsFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();

        if ( true ) {

            mMap.clear();
        }
        SharedPreferences sp = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("flag1", false);
        editor.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        realm = Realm.getDefaultInstance();

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Intent intent = getActivity().getIntent();
        mUserInput = intent.getStringExtra("userText");

        realmResults = realm.where(Searches.class).contains("nameId", mUserInput).findAll();

        for (int i=0; i<realmResults.size(); i++) {
            for (int j=0; j<realmResults.get(i).getTweetList().size(); j++) {

                if ( !realmResults.get(i).getTweetList().get(j).getTweetGeo().equalsIgnoreCase("No geolocation") ) {

                    String geoString = realmResults.get(i).getTweetList().get(j).getTweetGeo();
                    String fgs = geoString.replace(", longitude", "").replace("}", "");
                    Log.e("Map", "geoString: " + fgs );
                    String splStr[] = fgs.split("=");
                    double x = Double.parseDouble(splStr[1]);
                    double y = Double.parseDouble(splStr[2]);
                    addMarker(x, y, realmResults.get(i).getTweetList().get(j).getTweetUser(), realmResults.get(i).getTweetList().get(j).getTweetText());
                }
            }

        }

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private void addMarker( double x, double y, String name, String tweet ) {

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_twitter_icon);

        LatLng position = new LatLng(x,y);
        mMap.addMarker(new MarkerOptions().position(position).title(name).icon(icon).snippet(tweet));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    public static void moveCameraTo( double x, double y) {

        LatLng pos = new LatLng(x,y);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
    }
}
