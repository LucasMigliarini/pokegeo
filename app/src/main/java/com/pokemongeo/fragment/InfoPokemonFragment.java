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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pokemongeo.R;
import com.pokemongeo.databinding.InfopokemonFragmentBinding;
import com.pokemongeo.models.Pokemon;
import com.pokemongeo.views.PokemonViewModel;

public class InfoPokemonFragment extends Fragment {
    Pokemon pokemon;
    public InfoPokemonFragment(Pokemon pokemon){
        this.pokemon = pokemon;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        InfopokemonFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.infopokemon_fragment, container, false);
        PokemonViewModel viewModel = new PokemonViewModel();
        binding.setPokemonInfo(viewModel);
        binding.getPokemonInfo().setPokemon(pokemon);
        return binding.getRoot();
    }
}
