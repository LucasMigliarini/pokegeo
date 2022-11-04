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

import com.pokemongeo.R;
import com.pokemongeo.database.DatabaseHelper;
import com.pokemongeo.databinding.InfopokemonFragmentBinding;
import com.pokemongeo.databinding.WinfightFragmentBinding;
import com.pokemongeo.interfaces.BackOnClickListener;
import com.pokemongeo.models.Inventory;
import com.pokemongeo.models.Pokemon;
import com.pokemongeo.views.PokemonViewModel;

public class WinFightFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        WinfightFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.winfight_fragment, container, false);
        PokemonViewModel viewModel = new PokemonViewModel();

        Button mButton = (Button) binding.getRoot().findViewById(R.id.button_confirm);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                    Inventory inventory = new Inventory(1,1,1);
                    dbHelper.insertInInventory(inventory);
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
