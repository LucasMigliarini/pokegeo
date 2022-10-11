package com.pokemongeo.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pokemongeo.R;
import com.pokemongeo.databinding.MapFragmentBinding;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {
    MapFragmentBinding binding;
    Marker myPosition;
    List<GeoPoint> circle;
    Polygon polygon;
    Location myLocation;
    ArrayList<Drawable> drawables = new ArrayList<>();
    ArrayList<Marker> markerTab = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false);
        Context context = binding.getRoot().getContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        binding.mapView.setTileSource(TileSourceFactory.MAPNIK);


        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context),binding.mapView);
        mLocationOverlay.enableMyLocation();
        binding.mapView.getOverlays().add(mLocationOverlay);
        mLocationOverlay.enableFollowLocation();

        if (ActivityCompat.checkSelfPermission( context,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            String[] permissions =
                    {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,1);
        }

        myPosition = new Marker(binding.mapView);
        myPosition.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_CENTER);
        myPosition.setIcon(getResources().getDrawable((R.drawable.ic_launcher_foreground)));
        myPosition.setTitle("You");

        populateDrawables();

        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120, 100, myLocationListener);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 120, 100, myLocationListener);

        myLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        GeoPoint point = new GeoPoint(myLocation.getLongitude(), myLocation.getLatitude());

        binding.mapView.getController().setCenter(point);
        binding.mapView.getController().animateTo(point);
        binding.mapView.getController().setZoom(18.0);



        return binding.getRoot();

    }

    LocationListener myLocationListener = new LocationListener(){
        @Override
        public void onLocationChanged(Location newLocation) {
            System.out.println("NEW LOCATION");
            if(markerTab != null) {
                circle.clear();
                binding.mapView.getOverlays().remove(polygon);
                markerTab.clear();
            }else {

                circle = Polygon.pointsAsCircle(new GeoPoint(newLocation.getLatitude(), newLocation.getLongitude()), 300);
                polygon = new Polygon(binding.mapView);
                
                polygon.setPoints(circle);
                polygon.setTitle("A sample polygon");
                binding.mapView.getOverlays().add(polygon);

                // Marker
                myPosition.setPosition(new GeoPoint(newLocation.getLatitude(), newLocation.getLongitude()));
                binding.mapView.getOverlays().add(myPosition);
                binding.mapView.getController().animateTo(myPosition.getPosition());

                markerTab = new ArrayList<>();

                for (int i = 0; i < 10; i++) {
                    int position = (int) ((Math.random() * (circle.size() - 0)) + 0);

                    int num = (int) ((Math.random() * (drawables.size() - 0)) + 0);

                    Marker pokemon = new Marker(binding.mapView);

                    pokemon.setPosition(getPokemonPosition(newLocation, circle.get(position)));

                    pokemon.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                    pokemon.setIcon(drawables.get(num));

                    markerTab.add(pokemon);
                    binding.mapView.getOverlays().add(pokemon);
                }
            }


        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        public GeoPoint getPokemonPosition(Location newLocation, GeoPoint circle){
            GeoPoint pokemonPoint = null;
            if ( circle.getLatitude() > newLocation.getLatitude() && circle.getLongitude() > newLocation.getLongitude()  ){
                pokemonPoint = new GeoPoint((double) ( Math.random() * (circle.getLatitude() - newLocation.getLatitude() ) + newLocation.getLatitude() ),
                        (double) ( Math.random() * (circle.getLongitude() - newLocation.getLongitude() ) + newLocation.getLongitude() ));
            }
            else if ( circle.getLatitude() > newLocation.getLatitude() && circle.getLongitude() < newLocation.getLongitude()  ){
                pokemonPoint = new GeoPoint((double) ( Math.random() * (circle.getLatitude() - newLocation.getLatitude() ) + newLocation.getLatitude() ),
                        (double) ( Math.random() * (newLocation.getLongitude() - circle.getLongitude()) + circle.getLongitude() ));
            }
            else if ( circle.getLatitude() < newLocation.getLatitude() && circle.getLongitude() < newLocation.getLongitude()  ){
                pokemonPoint = new GeoPoint((double) ( Math.random() * (newLocation.getLatitude() - circle.getLatitude() ) + circle.getLatitude() ),
                        (double) ( Math.random() * (newLocation.getLongitude() - circle.getLongitude()) + circle.getLongitude() ));
            }
            else {
                pokemonPoint = new GeoPoint((double) ( Math.random() * (newLocation.getLatitude() - circle.getLatitude() ) + circle.getLatitude() ),
                        (double) ( Math.random() * (circle.getLongitude() - newLocation.getLongitude() ) + newLocation.getLongitude()  ));
            }

            return pokemonPoint;
        }
        @Override
        public void onStatusChanged(String provider, int
                status, Bundle extras){
        }
        @Override
        public void onProviderEnabled(String provider){
        }

    };

    public ArrayList<Drawable> populateDrawables() {
        Field[] drawablesFields = com.pokemongeo.R.drawable.class.getFields();
        for (Field field : drawablesFields) {
            try {
                if (field.getName().matches("^p+[0-9]?[0-9]?[0-9]")){
                    drawables.add(getResources().getDrawable(field.getInt(null)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return drawables;
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }


}
