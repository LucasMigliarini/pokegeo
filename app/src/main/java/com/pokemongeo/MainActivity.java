package com.pokemongeo;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.pokemongeo.databinding.ActivityMainBinding;
import com.pokemongeo.fragment.InfoPokemonFragment;
import com.pokemongeo.fragment.PokedexFragment;
import com.pokemongeo.interfaces.BackOnClickListener;
import com.pokemongeo.interfaces.OnClickOnNoteListener;
import com.pokemongeo.models.POKEMON_TYPE;
import com.pokemongeo.models.Pokemon;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        BottomNavigationView bottomNav = binding.getRoot().findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemReselectedListener(navListener);
        showStartup();
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

    private final NavigationBarView.OnItemReselectedListener navListener = item -> {
        int itemId = item.getItemId();
        if (itemId == R.id.poke) {
            showStartup();
        }
    };



}


