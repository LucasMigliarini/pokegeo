package com.pokemongeo;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.pokemongeo.databinding.ActivityMainBinding;
import com.pokemongeo.fragment.InfoPokemonFragment;
import com.pokemongeo.fragment.PokedexFragment;
import com.pokemongeo.interfaces.OnClickOnNoteListener;
import com.pokemongeo.models.Pokemon;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
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
        InfoPokemonFragment fragment = new InfoPokemonFragment(p);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }



}


