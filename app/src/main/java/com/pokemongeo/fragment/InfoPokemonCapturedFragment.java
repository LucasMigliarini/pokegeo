package com.pokemongeo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.pokemongeo.R;
import com.pokemongeo.database.DatabaseHelper;
import com.pokemongeo.databinding.InfopokemonFragmentBinding;
import com.pokemongeo.databinding.InfopokemonFragmentBindingImpl;
import com.pokemongeo.databinding.PokemonscapturedFragmentBinding;
import com.pokemongeo.interfaces.BackOnClickListener;
import com.pokemongeo.models.PokeStat;
import com.pokemongeo.models.Pokemon;
import com.pokemongeo.views.FightViewModel;
import com.pokemongeo.views.PokemonViewModel;

public class InfoPokemonCapturedFragment extends Fragment {

    Pokemon pokemon;
    public InfoPokemonCapturedFragment(Pokemon pokemon){
        this.pokemon = pokemon;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PokemonscapturedFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.pokemonscaptured_fragment, container, false);
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        PokeStat pokeStat = dbHelper.getPokemonCapture(pokemon.getOrder());

        TextView attack = (TextView) binding.getRoot().findViewById(R.id.info_atq);
        attack.setText(String.valueOf(pokeStat.getAtq()));
        TextView defense = (TextView) binding.getRoot().findViewById(R.id.info_def);
        defense.setText(String.valueOf(pokeStat.getDef()));
        TextView speed = (TextView) binding.getRoot().findViewById(R.id.info_spd);
        speed.setText(String.valueOf(pokeStat.getSpd()));
        TextView level = (TextView) binding.getRoot().findViewById(R.id.info_lvl);
        level.setText(String.valueOf("LVL:" + pokeStat.getLvl()));

        TextView type1 = (TextView) binding.getRoot().findViewById(R.id.info_type1);
        type1.setText(String.valueOf(pokemon.getType1()));
        if(pokemon.getType1() != pokemon.getType2()) {
            TextView type2 = (TextView) binding.getRoot().findViewById(R.id.info_type2);
            type2.setText(String.valueOf(pokemon.getType2()));
            ImageView imageType2 = (ImageView) binding.getRoot().findViewById(R.id.frontType2);
            imageType2.setImageResource(pokemon.getFrontType2());
        }

        ImageView imageFront = (ImageView) binding.getRoot().findViewById(R.id.front);
        imageFront.setImageResource(pokemon.getFrontResource());
        ImageView imageType1 = (ImageView) binding.getRoot().findViewById(R.id.frontType1);
        imageType1.setImageResource(pokemon.getFrontType1());


        Button mButton = (Button) binding.getRoot().findViewById(R.id.button_return);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.BackOnClickListener();
                }

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
