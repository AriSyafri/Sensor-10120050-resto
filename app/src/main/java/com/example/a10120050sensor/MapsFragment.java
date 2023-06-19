package com.example.a10120050sensor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    //NIM : 10120050
    //Nama : Ari Syafri
    //Kelas : IF-2

    private GoogleMap googleMap;
    private LocationManager locationManager;
    private LocationListener locationListener;


    //baru ditambahkan
    private PlacesClient placesClient;

    //penutup

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {

        //tambahan

        // Inisialisasi PlacesClient
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyAXt_Y6fp4tWobUbmjRYb9PrGxqeCcuKjI");
        }
        placesClient = Places.createClient(requireContext());

//        penutup tambahan

        googleMap = map;
        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                locationManager.removeUpdates(this); // Stop listening for location updates

                cariRestoranTerdekat(); // Memanggil metode untuk mencari restoran terdekat
            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && requireActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    private void cariRestoranTerdekat() {
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Tambahkan permintaan izin lokasi di sini jika diperlukan
            return;
        }
        // Dapatkan lokasi pengguna saat ini
        locationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                // Membuat permintaan Places API untuk mencari restoran terdekat
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                String placeType = "restaurant";
                String radius = "800"; // Jarak dalam meter
                String apiKey = "AIzaSyAXt_Y6fp4tWobUbmjRYb9PrGxqeCcuKjI"; // Ganti dengan kunci API Anda
                int count = 5; // Jumlah restoran yang ingin ditampilkan

                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                        "location=" + currentLocation.latitude + "," + currentLocation.longitude +
                        "&radius=" + radius +
                        "&type=" + placeType +
                        "&key=" + apiKey;

                RequestQueue queue = Volley.newRequestQueue(requireContext());
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        response -> {
                            try {
                                JSONArray results = response.getJSONArray("results");
                                for (int i = 0; i < Math.min(results.length(), count); i++) {
                                    JSONObject place = results.getJSONObject(i);
                                    JSONObject locationObj = place.getJSONObject("geometry").getJSONObject("location");
                                    double lat = locationObj.getDouble("lat");
                                    double lng = locationObj.getDouble("lng");
                                    String name = place.getString("name");
                                    LatLng restaurantLocation = new LatLng(lat, lng);
                                    googleMap.addMarker(new MarkerOptions().position(restaurantLocation).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            // Error handling
                        });

                queue.add(request);
            }
        });
    }



}