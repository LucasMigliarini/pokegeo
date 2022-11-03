package com.pokemongeo.models;

public class Inventory {
    int nombre;
    int trainer_id;
    int object_id;

    public Inventory(int nombre, int trainer_id, int object_id){
        this.nombre = nombre;
        this.trainer_id = trainer_id;
        this.object_id = object_id;
    }

    public Inventory(){}

    public int getNombre(){
        return this.nombre;
    }
    public void setNombre(int nombre){
        this.nombre = nombre;
    }

    public int getTrainer_id(){
        return this.trainer_id;
    }
    public void setTrainer_id(int trainer_id){
        this.trainer_id = trainer_id;
    }

    public int getObject_id(){
        return this.object_id;
    }
    public void setObject_id(int object_id){
        this.object_id = object_id;
    }
}
