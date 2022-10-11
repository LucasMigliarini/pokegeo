package com.pokemongeo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.pokemongeo.databinding.ActivityMainBinding;
import com.pokemongeo.fragment.InfoPokemonFragment;
import com.pokemongeo.fragment.MapFragment;
import com.pokemongeo.fragment.PokedexFragment;
import com.pokemongeo.interfaces.BackOnClickListener;
import com.pokemongeo.interfaces.OnClickOnNoteListener;
import com.pokemongeo.models.Pokemon;

import org.osmdroid.config.Configuration;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;
        getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        BottomNavigationView bottomNav = binding.getRoot().findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        showStartup();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions, grantResults);
        if(grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
        //on a la permission
        } else {
        //afficher un message d’erreur
        }
    }



    public void showStartup() {;

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        OnClickOnNoteListener listener = this::showNoteDetail;
        PokedexFragment fragment = new PokedexFragment();
        fragment.setOnClickOnNoteListener(listener);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }

    public void showNoteDetail(Pokemon p) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        BackOnClickListener listener = this::showStartup;
        InfoPokemonFragment fragment = new InfoPokemonFragment(p);
        fragment.setOnClickOnNoteListener(listener);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }

    public void showMap() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MapFragment fragment = new MapFragment();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }

    private final NavigationBarView.OnItemSelectedListener navListener = item -> {
        int itemId = item.getItemId();
        if (itemId == R.id.poke) {
            showStartup();
        }
        if (itemId == R.id.map) {
            showMap();
        }
        return true;
    };

}


