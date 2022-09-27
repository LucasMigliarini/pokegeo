package com.pokemongeo.models;


import com.pokemongeo.R;

public class Pokemon {
    private int order;
    private String name;
    private String height;
    private String weight;
    private int frontResource;
    private int frontType1;
    private int frontType2;
    private POKEMON_TYPE type1;
    private POKEMON_TYPE type2;
    public Pokemon() {
        order = 1;
        name = "Unknown";
        frontResource = R.drawable.p1;
        type1 = POKEMON_TYPE.Plante;
        frontType1 = -1;
        frontType2 = -1;
    }
    public Pokemon(int order, String name, int frontResource, int frontType1, int frontType2,
                   POKEMON_TYPE type1, POKEMON_TYPE type2, String weight, String height) {
        this.order = order;
        this.name = name;
        this.frontResource = frontResource;
        this.frontType1 = frontType1;
        this.frontType2 = frontType2;
        this.type1 = type1;
        this.type2 = type2;
        this.weight = weight;
        this.height = height;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getWeight() {
        return weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    public int getFrontResource() {
        return frontResource;
    }
    public void setFrontResource(int frontResource) {
        this.frontResource = frontResource;
    }
    public int getFrontType1() {return frontType1;}
    public void setFrontType1(int frontType1){
        this.frontType1 = frontType1;
    }
    public int getFrontType2() {return frontType2;}
    public void setFrontType2(int frontType2){
        this.frontType2 = frontType2;
    }


    public POKEMON_TYPE getType1() {
        return type1;
    }
    public void setType1(POKEMON_TYPE type1) {
        this.type1 = type1;
    }

    public POKEMON_TYPE getType2() {
        return type2;
    }
    public void setType2(POKEMON_TYPE type2) {
        this.type2 = type2;
    }
    public String getType1String() {
        return type1.name();
    }

    public String getType2String() {
        return type2.name();
    }
}
