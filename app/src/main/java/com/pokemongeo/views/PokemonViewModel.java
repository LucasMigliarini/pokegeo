package com.pokemongeo.views;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.pokemongeo.models.Pokemon;

public class PokemonViewModel extends BaseObservable {
    private Pokemon pokemon = new Pokemon();
    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
        notifyChange();
    }
    @Bindable
    public int getFront() {
        return pokemon.getFrontResource();
    }
    @Bindable
    public int getFrontType1() {
        return pokemon.getFrontType1();
    }
    @Bindable
    public int getFrontType2() {
        return pokemon.getFrontType2();
    }
    @Bindable
    public String getName() {
        return pokemon.getName();
    }
    @Bindable
    public String getType1() {
        return pokemon.getType1String();
    }
    @Bindable
    public String getType2() {
        if (pokemon.getType2() != null)
            return pokemon.getType2String();
        return "";
    }
    @Bindable
    public String getWeight() {
        return pokemon.getWeight();
    }
    @Bindable
    public String getHeight() {
        return pokemon.getHeight();
    }
    @Bindable
    public String getNumber() {
        return "n°"+pokemon.getOrder();
    }
    public Drawable getImage(Context context, int res) {
        if(res != -1)
            return ResourcesCompat.getDrawable(context.getResources(),
                    res, context.getTheme());
        else
            return null;
    }
}

