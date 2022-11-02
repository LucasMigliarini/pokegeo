package com.pokemongeo.views;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.pokemongeo.models.PokeStat;
import com.pokemongeo.models.Pokemon;

public class FightViewModel extends BaseObservable {

    private PokeStat pokemonEnemy = new PokeStat();
    private PokeStat pokemonMe = new PokeStat();
    private String nameEnemy;
    private String nameMe;

    public void setPokemonEnemy(PokeStat pokemon) {
        this.pokemonEnemy = pokemon;
        notifyChange();
    }
    public void setPokemonMe(PokeStat pokemon) {
        this.pokemonMe = pokemon;
        notifyChange();
    }

    public void setNameEnemy(String name){
        this.nameEnemy = name;
    }

    public void setNameMe(String name){
        this.nameMe = name;
    }
    @Bindable
    public int getHpEnemy() {
        return pokemonEnemy.getHp();
    }
    @Bindable
    public int getHpMe() {
        return pokemonMe.getHp();
    }

    @Bindable
    public String getNameEnemy() {
        return nameEnemy;
    }
    @Bindable
    public String getNameMe() {
        return nameMe;
    }

    @Bindable
    public int getLvlEnemy() {
        return pokemonEnemy.getLvl();
    }
    @Bindable
    public int getLvlMe() {
        return pokemonMe.getLvl();
    }

    @Bindable
    public int getImageEnemy() {
        return pokemonEnemy.getFrontResource();
    }
    @Bindable
    public int getImageMe() {
        return pokemonMe.getFrontResource();
    }

    public Drawable getImage(Context context, int res) {
        if(res != -1)
            return ResourcesCompat.getDrawable(context.getResources(),
                    res, context.getTheme());
        else
            return null;
    }
}
