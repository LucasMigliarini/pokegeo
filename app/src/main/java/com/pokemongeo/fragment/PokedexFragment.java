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

import com.pokemongeo.database.DatabaseHelper;
import com.pokemongeo.interfaces.OnClickOnNoteListener;
import com.pokemongeo.models.POKEMON_TYPE;
import com.pokemongeo.models.Pokemon;
import com.pokemongeo.R;
import com.pokemongeo.databinding.PokedexFragmentBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PokedexFragment extends Fragment {


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

        //Ouverture du fichier res/raw
        InputStreamReader isr = new InputStreamReader(getResources().openRawResource(R.raw.poke));
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String data = "";
        // lecture du fichier. data == null => EOF
        while( data != null) {
            try {
                data = reader.readLine();
                builder.append(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Traitement du fichier
        try {
            JSONArray array = new JSONArray(builder.toString());
            for (int i=0; i < array.length(); i++) {
                Pokemon pokemon = new Pokemon();
                JSONObject object = array.getJSONObject(i);
                pokemon.setOrder(object.getInt("id"));
                pokemon.setName(object.getString("name"));
                pokemon.setFrontResource(getResources().getIdentifier(object.getString("image"),"drawable", binding.getRoot().getContext().getPackageName()));
                pokemon.setType1(POKEMON_TYPE.valueOf(object.getString("type1")));
                pokemon.setFrontType1(getResources().getIdentifier(object.getString("imagetype1"),"drawable",binding.getRoot().getContext().getPackageName()));
                if (object.has("type2")) {
                    pokemon.setType2(POKEMON_TYPE.valueOf(object.getString("type2")));
                    pokemon.setFrontType2(getResources().getIdentifier(object.getString("imagetype2"),"drawable",binding.getRoot().getContext().getPackageName()));
                }else {
                    pokemon.setType2(POKEMON_TYPE.valueOf(object.getString("type1")));
                    pokemon.setFrontType2(getResources().getIdentifier(object.getString("imagetype1"),"drawable",binding.getRoot().getContext().getPackageName()));
                }
                pokemon.setHeight(object.getString("height"));
                pokemon.setWeight(object.getString("weight"));
                pokemon.setisDiscovered(1);

                pokemonList.add(pokemon);
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        dbHelper.insertAllPokemon(pokemonList);

        List<Pokemon> discoveredPokemon = new ArrayList<>(dbHelper.getAllPokemon());

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
