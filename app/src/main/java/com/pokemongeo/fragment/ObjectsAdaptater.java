package com.pokemongeo.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.pokemongeo.R;
import com.pokemongeo.databinding.InventoryFragmentBinding;
import com.pokemongeo.databinding.PokemonItemBinding;
import com.pokemongeo.interfaces.OnClickOnNoteListener;
import com.pokemongeo.models.Inventory;
import com.pokemongeo.models.ObjectPokemon;
import com.pokemongeo.models.Pokemon;
import com.pokemongeo.views.PokemonViewModel;

import java.util.List;

public class ObjectsAdaptater extends RecyclerView.Adapter<ObjectsAdaptater.ViewHolder>{

    List<ObjectPokemon> objectList;
    List<Inventory> inventoryList;
    private OnClickOnNoteListener listener;

    public ObjectsAdaptater(List<ObjectPokemon> objectList, OnClickOnNoteListener listener, List<Inventory> inventoryList) {
        assert objectList != null;
        this.objectList = objectList;
        this.inventoryList = inventoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ObjectsAdaptater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InventoryFragmentBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.inventory_fragment, parent, false);
        return new ObjectsAdaptater.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjectPokemon objectPokemon = objectList.get(position);
        Inventory inventory = inventoryList.get(position);


        TextView nameItem= (TextView) holder.binding.getRoot().findViewById(R.id.nameItem);
        nameItem.setText(objectPokemon.getName());
        TextView quanity= (TextView) holder.binding.getRoot().findViewById(R.id.quanity);
        quanity.setText("X" + inventory.getNombre());

        ImageView front = (ImageView) holder.binding.getRoot().findViewById(R.id.front);
        front.setImageResource(objectPokemon.getFront());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private InventoryFragmentBinding binding;
        ViewHolder(InventoryFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }
}
