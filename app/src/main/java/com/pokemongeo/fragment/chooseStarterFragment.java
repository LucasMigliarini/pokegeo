package com.pokemongeo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.pokemongeo.R;
import com.pokemongeo.database.DatabaseHelper;
import com.pokemongeo.databinding.ChoosestarterFragmentBinding;
import com.pokemongeo.interfaces.BackOnClickListener;
import com.pokemongeo.models.Inventory;
import com.pokemongeo.models.ObjectPokemon;
import com.pokemongeo.models.POKEMON_TYPE;
import com.pokemongeo.models.PokeStat;
import com.pokemongeo.models.Pokemon;
import com.pokemongeo.views.PokemonViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class chooseStarterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ChoosestarterFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.choosestarter_fragment, container, false);

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
                pokemon.setisDiscovered(0);

                pokemonList.add(pokemon);
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        dbHelper.insertAllPokemon(pokemonList);
        ObjectPokemon object = new ObjectPokemon("Potion Max",1,0,getResources().getIdentifier("p1","drawable", binding.getRoot().getContext().getPackageName()));
        dbHelper.insertObject(object);
        Inventory inventory = new Inventory(10,1,1);
        dbHelper.insertInInventory(inventory);

        Button mButton1 = (Button) binding.getRoot().findViewById(R.id.button_1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.BackOnClickListener();
                }
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                Pokemon pokemon = dbHelper.getPokemon(7);
                pokemon.setisDiscovered(1);
                PokeStat statPokemon = new PokeStat();
                statPokemon.setHp(10);
                statPokemon.setAtq(2);
                statPokemon.setDef(5);
                statPokemon.setSpd(5);
                statPokemon.setLvl(2);
                statPokemon.setPokemon_id(7);
                dbHelper.insertRowCapture(statPokemon);
                dbHelper.upatePokemon(pokemon);
                dbHelper.insertTeam(pokemon,1);
            }
        });

        Button mButton2 = (Button) binding.getRoot().findViewById(R.id.button_2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.BackOnClickListener();
                }
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                Pokemon pokemon = dbHelper.getPokemon(1);
                pokemon.setisDiscovered(1);
                PokeStat statPokemon = new PokeStat();
                statPokemon.setHp(10);
                statPokemon.setAtq(2);
                statPokemon.setDef(5);
                statPokemon.setSpd(5);
                statPokemon.setLvl(2);
                statPokemon.setPokemon_id(1);
                dbHelper.insertRowCapture(statPokemon);
                dbHelper.upatePokemon(pokemon);
                dbHelper.insertTeam(pokemon,1);

            }
        });

        Button mButton3 = (Button) binding.getRoot().findViewById(R.id.button_3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.BackOnClickListener();
                }
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                Pokemon pokemon = dbHelper.getPokemon(4);
                pokemon.setisDiscovered(1);
                PokeStat statPokemon = new PokeStat();
                statPokemon.setHp(10);
                statPokemon.setAtq(2);
                statPokemon.setDef(5);
                statPokemon.setSpd(5);
                statPokemon.setLvl(2);
                statPokemon.setPokemon_id(4);
                dbHelper.insertRowCapture(statPokemon);
                dbHelper.upatePokemon(pokemon);
                dbHelper.insertTeam(pokemon,1);

            }
        });

        return binding.getRoot();
    }
    private BackOnClickListener listener;
    public void setOnClickOnNoteListener(BackOnClickListener listener)
    {
        this.listener = listener;
    }

}
