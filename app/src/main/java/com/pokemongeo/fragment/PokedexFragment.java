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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pokemongeo.interfaces.OnClickOnNoteListener;
import com.pokemongeo.models.POKEMON_TYPE;
import com.pokemongeo.models.Pokemon;
import com.pokemongeo.models.PokemonListAdapter;
import com.pokemongeo.R;
import com.pokemongeo.databinding.PokedexFragmentBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        //ouverture du json
        InputStreamReader isr = new InputStreamReader(getResources().openRawResource(R.raw.pokemons));
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String data = "";
        //lecture du fichier. data == null => EOF
        while(data != null) {
            try {
                data = reader.readLine();
                builder.append(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Traitement du fichier
        try {
            JSONArray array = new JSONArray(builder.toString());

            for (int i = 0; i < array.length(); i++) {
                Pokemon datapoke = new Pokemon();
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("name");
                String image = object.getString("image");
                String type1 = object.getString("type1");
                String type2 = null;
                if (object.has("type2")) {
                    type2 = object.getString("type2");
                    datapoke.setType2(POKEMON_TYPE.valueOf(type2));
                }

                datapoke.setOrder(i);
                datapoke.setName(name);
                datapoke.setFrontResource(getResources().getIdentifier(image,"drawable",binding.getRoot().getContext().getPackageName()));
                datapoke.setType1(POKEMON_TYPE.valueOf(type1));
                pokemonList.add(datapoke);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PokemonListAdapter adapter = new PokemonListAdapter(pokemonList,listener);
        binding.pokemonList.setAdapter(adapter);
        return binding.getRoot();
    }
    private OnClickOnNoteListener listener;
    public void setOnClickOnNoteListener(OnClickOnNoteListener listener)
    {
        this.listener = listener;
    }


}
