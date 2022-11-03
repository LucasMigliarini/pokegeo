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
import com.pokemongeo.models.Inventory;
import com.pokemongeo.models.ObjectPokemon;
import com.pokemongeo.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class InventoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        PokedexFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.pokedex_fragment, container, false);
        binding.pokemonList.setLayoutManager(new GridLayoutManager(
                binding.getRoot().getContext(),2));

        List<ObjectPokemon> objectList = new ArrayList<>();

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        List<Inventory> inventoryList = dbHelper.getAllItemsInInventory();

        for (Inventory row:inventoryList) {
            objectList.add(dbHelper.getObject(row.getObject_id()));
        }

        ObjectsAdaptater adapter = new ObjectsAdaptater(objectList, listener, inventoryList);
        binding.pokemonList.setAdapter(adapter);
        return binding.getRoot();
    }
    private OnClickOnNoteListener listener;
    public void setOnClickOnNoteListener(OnClickOnNoteListener listener)
    {
        this.listener = listener;
    }
}
