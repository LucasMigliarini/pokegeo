package com.pokemongeo.models;

public class ObjectPokemon {
    String name;
    int trainer_id;
    int price;
    int front;

    public ObjectPokemon(String name, int trainer_id, int price,int front){
        this.name = name;
        this.trainer_id = trainer_id;
        this.price = price;
        this.front = front;
    }

    public ObjectPokemon(){}

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getTrainer_id(){
        return this.trainer_id;
    }
    public void setTrainer_id(int trainer_id){
        this.trainer_id = trainer_id;
    }

    public int getPrice(){
        return this.trainer_id;
    }
    public void setPrice(int price){
        this.price = price;
    }

    public int getFront(){
        return this.front;
    }
    public void setFront(int front){
        this.front = front;
    }
}
