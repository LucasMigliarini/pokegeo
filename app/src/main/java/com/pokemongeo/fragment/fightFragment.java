package com.pokemongeo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.pokemongeo.R;
import com.pokemongeo.database.DatabaseHelper;
import com.pokemongeo.databinding.FightFragmentBinding;
import com.pokemongeo.databinding.InfopokemonFragmentBinding;
import com.pokemongeo.databinding.PokedexFragmentBinding;
import com.pokemongeo.interfaces.BackOnClickListener;
import com.pokemongeo.models.POKEMON_TYPE;
import com.pokemongeo.models.PokeStat;
import com.pokemongeo.models.Pokemon;
import com.pokemongeo.views.FightViewModel;
import com.pokemongeo.views.PokemonViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class fightFragment extends Fragment {

    Pokemon pokemonEnemy;
    public fightFragment(Pokemon pokemonEnemy){
        this.pokemonEnemy = pokemonEnemy;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FightFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.fight_fragment, container, false);

        Log.d("Pokemon",String.valueOf(pokemonEnemy.getOrder()));
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        PokeStat statEnemy = new PokeStat(10,5,2,1,1);
        statEnemy.setFrontResource(pokemonEnemy.getFrontResource());

        Pokemon pokemonMe = dbHelper.getFirstPokemonInTeam();
        PokeStat statMe = dbHelper.getPokemonCapture(pokemonMe.getOrder());
        statMe.setFrontResource(pokemonMe.getFrontResource());

        FightViewModel viewModel = new FightViewModel();
        binding.setFight(viewModel);

        binding.getFight().setNameEnemy(pokemonEnemy.getName());
        binding.getFight().setNameMe(pokemonMe.getName());
        binding.getFight().setPokemonEnemy(statEnemy);
        binding.getFight().setPokemonMe(statMe);


        return binding.getRoot();
    }



    private BackOnClickListener listener;
    public void setOnClickOnNoteListener(BackOnClickListener listener)
    {
        this.listener = listener;
    }
}
