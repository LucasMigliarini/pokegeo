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
import com.pokemongeo.databinding.FightFragmentBinding;
import com.pokemongeo.interfaces.BackOnClickListener;
import com.pokemongeo.interfaces.OnClickOnNoteListener;
import com.pokemongeo.models.FightSystem;
import com.pokemongeo.models.PokeStat;
import com.pokemongeo.models.Pokemon;
import com.pokemongeo.views.FightViewModel;

import org.w3c.dom.Text;


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

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        PokeStat statEnemy = new PokeStat(10,5,2,1,1);
        statEnemy.setFrontResource(pokemonEnemy.getFrontResource());
        statEnemy.setPokemon_id(pokemonEnemy.getOrder());

        int pokemonMeId = dbHelper.getFirstPokemonInTeam();
        Pokemon pokemonMe = dbHelper.getPokemon(pokemonMeId);
        PokeStat statMe = dbHelper.getPokemonCapture(pokemonMeId);
        statMe.setFrontResource(pokemonMe.getFrontResource());

        //TODO le view model ne veut pas fonctionner, regarder pourquoi à la fin
        TextView pvEnemy = (TextView) binding.getRoot().findViewById(R.id.pvEnemy);
        pvEnemy.setText(String.valueOf(statEnemy.getHp()));

        TextView nameEnemy = (TextView) binding.getRoot().findViewById(R.id.nameEnemy);
        nameEnemy.setText(String.valueOf(pokemonEnemy.getName()));

        TextView lvlEnemy = (TextView) binding.getRoot().findViewById(R.id.lvlEnemy);
        lvlEnemy.setText("lvl: " + String.valueOf(statEnemy.getLvl()));

        ImageView imageEnemy = (ImageView) binding.getRoot().findViewById(R.id.imageEnemy);
        imageEnemy.setImageResource(pokemonEnemy.getFrontResource());

        TextView pvMe = (TextView) binding.getRoot().findViewById(R.id.pvMe);
        pvMe.setText(String.valueOf(statMe.getHp()));

        TextView nameMe = (TextView) binding.getRoot().findViewById(R.id.nameMe);
        nameMe.setText(String.valueOf(pokemonMe.getName()));

        TextView lvlMe= (TextView) binding.getRoot().findViewById(R.id.lvlMe);
        lvlMe.setText("lvl: " + String.valueOf(statMe.getLvl()));

        ImageView imageMe = (ImageView) binding.getRoot().findViewById(R.id.imageMe);
        imageMe.setImageResource(pokemonMe.getFrontResource());

        Button button_fuite = (Button) binding.getRoot().findViewById(R.id.button_fuite);
        button_fuite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listenerBack != null) {
                    listenerBack.BackOnClickListener();
                }

            }
        });

        FightSystem fightSystem = new FightSystem(statEnemy,statMe);
        Button attack = (Button) binding.getRoot().findViewById(R.id.button_atq);
        attack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fightSystem.attack();
                if(fightSystem.getPvEnemy() <= 0 || fightSystem.getPvMe() <= 0){
                    listenerNote.onClickOnNote(pokemonEnemy);
                }else{
                    pvEnemy.setText(String.valueOf(fightSystem.getPvEnemy()));
                    pvMe.setText(String.valueOf(fightSystem.getPvMe()));
                }

            }
        });
        Button button_capture = (Button) binding.getRoot().findViewById(R.id.button_capture);
        button_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int capture = fightSystem.capture();
                if(capture == 1){
                    DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                    dbHelper.insertRowCapture(statEnemy);
                    listenerBack.BackOnClickListener();
                }else{
                    fightSystem.enemyAttackWhenYoutryToCapture();
                    pvMe.setText(String.valueOf(fightSystem.getPvMe()));
                }

            }
        });
        return binding.getRoot();
    }

    private BackOnClickListener listenerBack;
    private OnClickOnNoteListener listenerNote;
    public void setOnClickBackListener(BackOnClickListener listener)
    {
        this.listenerBack = listener;
    }
    public void setOnClickOnNoteListener(OnClickOnNoteListener listener)
    {
        this.listenerNote = listener;
    }
}
