package com.pokemongeo.database;

import com.pokemongeo.R;
import com.pokemongeo.models.POKEMON_TYPE;
import com.pokemongeo.models.PokeStat;
import com.pokemongeo.models.Pokemon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.fragment.app.Fragment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "pokemons";
    SQLiteDatabase db;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {

        db.execSQL(
                "create table pokemon " +
                        "(id integer primary key, name text,weight int,height int, type1 text, type2 text, image int, discovered integer, imagetype1 int, imagetype2 int)"
        );
        db.execSQL(
                "create table trainer " +
                        "(id integer primary key, name text, sexe text, image int)"
        );
        db.execSQL(
                "create table object " +
                        "(id integer primary key, name text, trainer_id integer, price int, foreign key(trainer_id) references trainer(id))"
        );
        db.execSQL(
                "create table inventory " +
                        "(id integer primary key, nombre int, trainer_id integer, object_id , foreign key(trainer_id) references trainer(id), foreign key(object_id) references object(id))"
        );
        db.execSQL(
                "create table capture " +
                        "(id integer primary key AUTOINCREMENT,hp integer, atq integer, def integer, spd int, lvl int, trainer_id integer, pokemon_id , foreign key(trainer_id) references trainer(id), foreign key(pokemon_id) references pokemon(id))"
        );
        db.execSQL(
                "create table team " +
                        "(pokemonid int,position integer,foreign key(pokemonid) references capture(id))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }


    //Insert all pokemon in the database
    public void insertAllPokemon(List<Pokemon> pokemonList) {
        db = this.getWritableDatabase();
        if (getAllPokemon().size() == 0) {
            for (Pokemon pokemon : pokemonList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", pokemon.getOrder());
                contentValues.put("name", pokemon.getName());
                contentValues.put("weight", pokemon.getWeight());
                contentValues.put("height", pokemon.getHeight());
                contentValues.put("type1", String.valueOf(pokemon.getType1()));
                contentValues.put("type2", String.valueOf(pokemon.getType2()));
                contentValues.put("image", pokemon.getFrontResource());
                contentValues.put("discovered", pokemon.getisDiscovered());
                contentValues.put("imagetype1", pokemon.getFrontType1());
                contentValues.put("imagetype2", pokemon.getFrontType2());
                db.insert("pokemon", null, contentValues);
            }

        }
    }

    //Get all pokemon from database
    public List<Pokemon> getAllPokemon() {
        db = this.getReadableDatabase();
        List<Pokemon> pokemonList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM pokemon", null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            Pokemon pokemon = new Pokemon();
            pokemon.setOrder(cursor.getInt(0));
            pokemon.setName(cursor.getString(1));
            pokemon.setWeight(cursor.getString(2));
            pokemon.setHeight(cursor.getString(3));
            pokemon.setType1(POKEMON_TYPE.valueOf(cursor.getString(4)));
            pokemon.setType2(POKEMON_TYPE.valueOf(cursor.getString(5)));
            pokemon.setFrontResource(cursor.getInt(6));
            pokemon.setisDiscovered(cursor.getInt(7));
            pokemon.setFrontType1(cursor.getInt(8));
            pokemon.setFrontType2(cursor.getInt(9));
            pokemonList.add(pokemon);
        }
        cursor.close();

        return pokemonList;
    }

    // Get a pokemon from his id in the database
    public Pokemon getPokemon(int id) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pokemon WHERE id = " + id, null);
        cursor.moveToNext();
        Pokemon pokemon = new Pokemon();
        pokemon.setOrder(cursor.getInt(0));
        pokemon.setName(cursor.getString(1));
        pokemon.setWeight(cursor.getString(2));
        pokemon.setHeight(cursor.getString(3));
        pokemon.setType1(POKEMON_TYPE.valueOf(cursor.getString(4)));
        pokemon.setType2(POKEMON_TYPE.valueOf(cursor.getString(5)));
        pokemon.setFrontResource(cursor.getInt(6));
        pokemon.setisDiscovered(cursor.getInt(7));
        pokemon.setFrontType1(cursor.getInt(8));
        pokemon.setFrontType2(cursor.getInt(9));
        cursor.close();
        return pokemon;
    }

    public PokeStat getPokemonCapture(int id) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM capture WHERE pokemon_id = " + id, null);
        cursor.moveToNext();
        PokeStat capture = new PokeStat();
        capture.setId(cursor.getInt(0));
        capture.setHp(cursor.getInt(1));
        capture.setAtq(cursor.getInt(2));
        capture.setDef(cursor.getInt(3));
        capture.setSpd(cursor.getInt(4));
        capture.setLvl(cursor.getInt(5));
        capture.setPokemon_id(cursor.getInt(7));
        cursor.close();

        return capture;
    }

    public void upatePokemon(Pokemon pokemon){
        db = this.getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", pokemon.getOrder());
        contentValues.put("name", pokemon.getName());
        contentValues.put("weight", pokemon.getWeight());
        contentValues.put("height", pokemon.getHeight());
        contentValues.put("type1", String.valueOf(pokemon.getType1()));
        contentValues.put("type2", String.valueOf(pokemon.getType2()));
        contentValues.put("image", pokemon.getFrontResource());
        contentValues.put("discovered", 1);
        contentValues.put("imagetype1", pokemon.getFrontType1());
        contentValues.put("imagetype2", pokemon.getFrontType2());
        db.update("pokemon", contentValues, "id=?",new String[]{String.valueOf(pokemon.getOrder())});
    }
    //Get all capture from database
   public List<PokeStat> getAllCapture() {
        db = this.getReadableDatabase();
        List<PokeStat> captureList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM capture", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            PokeStat capture = new PokeStat();
            capture.setId(cursor.getInt(0));
            capture.setHp(cursor.getInt(1));
            capture.setAtq(cursor.getInt(2));
            capture.setDef(cursor.getInt(3));
            capture.setSpd(cursor.getInt(4));
            capture.setLvl(cursor.getInt(5));
            capture.setPokemon_id(cursor.getInt(7));
            captureList.add(capture);
        }
        cursor.close();
        return captureList;
    }
    // Insert a row in the capture table
    public void insertRowCapture(PokeStat capture) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hp", capture.getHp());
        contentValues.put("atq", capture.getAtq());
        contentValues.put("def", capture.getDef());
        contentValues.put("spd", capture.getSpd());
        contentValues.put("lvl", capture.getLvl());
        contentValues.put("pokemon_id", capture.getPokemon_id());
        db.insert("capture", null, contentValues);
    }

    //Get all pokemon from database where discovered = true
    public List<Pokemon> getDiscoveredPokemon() {
        db = this.getReadableDatabase();
        List<Pokemon> pokemonList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM pokemon WHERE discovered == 1", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            Pokemon pokemon = new Pokemon();
            pokemon.setOrder(cursor.getInt(0));
            pokemon.setName(cursor.getString(1));
            pokemon.setWeight(cursor.getString(2));
            pokemon.setHeight(cursor.getString(3));
            pokemon.setType1(POKEMON_TYPE.valueOf(cursor.getString(4)));
            pokemon.setType2(POKEMON_TYPE.valueOf(cursor.getString(5)));
            pokemon.setFrontResource(cursor.getInt(6));
            pokemon.setFrontType1(cursor.getInt(8));
            pokemon.setFrontType2(cursor.getInt(9));
            pokemonList.add(pokemon);
        }
        cursor.close();

        return pokemonList;
    }

    public void insertTeam(Pokemon pokemon,int position){
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pokemonid", pokemon.getOrder());
        contentValues.put("position", position);
        db.insert("team", null, contentValues);
    }

    public int getFirstPokemonInTeam(){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT pokemonid FROM team WHERE position == 1", null);
        cursor.moveToNext();
        int id = cursor.getInt(0);
        cursor.close();
        return id;
    }
}