package com.pokemongeo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.pokemongeo.R;
import com.pokemongeo.database.DatabaseHelper;
import com.pokemongeo.databinding.PokedexFragmentBinding;
import com.pokemongeo.interfaces.OnClickOnNoteListener;
import com.pokemongeo.models.POKEMON_TYPE;
import com.pokemongeo.models.Pokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class pokemonsCaptured extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        PokedexFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.pokedex_fragment, container, false);
        binding.pokemonList.setLayoutManager(new GridLayoutManager(
                binding.getRoot().getContext(),2));

        List<Pokemon> pokemonList = new ArrayList<>();


        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        dbHelper.insertAllPokemon(pokemonList);

        List<Pokemon> discoveredPokemon = new ArrayList<>(dbHelper.getAllCapture());

        PokemonListAdapter adapter = new PokemonListAdapter(discoveredPokemon, listener);
        binding.pokemonList.setAdapter(adapter);
        return binding.getRoot();
    }
    private OnClickOnNoteListener listener;
    public void setOnClickOnNoteListener(OnClickOnNoteListener listener)
    {
        this.listener = listener;
    }
}
