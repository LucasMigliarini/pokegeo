package com.pokemongeo.models;

public class PokeStat extends Pokemon{
    int id;
    int pokemon_id;
    int hp;
    int atq;
    int def;
    int spd;
    int lvl;

    public PokeStat (int hp,int atq,int def,int spd,int lvl){
        this.hp = hp;
        this.atq = atq;
        this.def = def;
        this.spd = spd;
        this.lvl = lvl;
    }
    public  PokeStat() {}


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPokemon_id() {
        return pokemon_id;
    }
    public void setPokemon_id(int pokemon_id) {
        this.pokemon_id = pokemon_id;
    }

    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtq() {
        return atq;
    }
    public void setAtq(int atq) {
        this.atq = atq;
    }

    public int getDef() {
        return def;
    }
    public void setDef(int def) {
        this.def = def;
    }

    public int getSpd() {
        return def;
    }
    public void setSpd(int spd) {
        this.spd = spd;
    }

    public int getLvl() {
        return lvl;
    }
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }



}
