package com.pokemongeo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.pokemongeo.databinding.ActivityMainBinding;
import com.pokemongeo.databinding.FightFragmentBinding;
import com.pokemongeo.fragment.InfoPokemonCapturedFragment;
import com.pokemongeo.fragment.InfoPokemonFragment;
import com.pokemongeo.fragment.InventoryFragment;
import com.pokemongeo.fragment.MapFragment;
import com.pokemongeo.fragment.PokedexFragment;
import com.pokemongeo.fragment.chooseStarterFragment;
import com.pokemongeo.fragment.fightFragment;
import com.pokemongeo.fragment.pokemonsCaptured;
import com.pokemongeo.interfaces.BackOnClickListener;
import com.pokemongeo.interfaces.OnClickOnNoteListener;
import com.pokemongeo.models.PokeStat;
import com.pokemongeo.models.Pokemon;

import org.osmdroid.config.Configuration;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;
        getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        BottomNavigationView bottomNav = binding.getRoot().findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        prefs = getSharedPreferences("com.pokemongeo", MODE_PRIVATE);
        prefs.edit().putBoolean("firstrun", true).commit();
        if (prefs.getBoolean("firstrun", true)) {
            chooseStarter();
            prefs.edit().putBoolean("firstrun", false).commit();
        }else{
            showStartup();
        }

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

    public void chooseStarter() {;

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        BackOnClickListener listener = this::showStartup;
        chooseStarterFragment fragment = new chooseStarterFragment();
        fragment.setOnClickOnNoteListener(listener);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }

    public void showFight(Pokemon p) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        BackOnClickListener listenerBack = this::showMap;
        OnClickOnNoteListener listenerNote = this::showNoteDetail;
        fightFragment fragment = new fightFragment(p);
        fragment.setOnClickBackListener(listenerBack);
        fragment.setOnClickOnNoteListener(listenerNote);
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

    public void showPokemonsCaptured() {;

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        OnClickOnNoteListener listener = this::showPokemonCaptured;
        pokemonsCaptured fragment = new pokemonsCaptured();
        fragment.setOnClickOnNoteListener(listener);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }

    public void showPokemonCaptured(Pokemon p) {;

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        BackOnClickListener listener = this::showPokemonsCaptured;
        InfoPokemonCapturedFragment fragment = new InfoPokemonCapturedFragment(p);
        fragment.setOnClickOnNoteListener(listener);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }

    public void showMap() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        OnClickOnNoteListener listener = this::showFight;
        MapFragment fragment = new MapFragment();
        fragment.setOnClickOnNoteListener(listener);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }
    public void showInventory() {;

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        OnClickOnNoteListener listener = this::showNoteDetail;
        InventoryFragment fragment = new InventoryFragment();
        fragment.setOnClickOnNoteListener(listener);
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
        if (itemId == R.id.captured) {
            showPokemonsCaptured();
        }
        if (itemId == R.id.inventory) {
            showInventory();
        }
        return true;
    };

}


