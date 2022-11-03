package com.pokemongeo.models;

public class FightSystem {
    private PokeStat statEnemy;
    private PokeStat statMe;
    private int pvMaxEnemy;
    private int pvMaxMe;
    private int pvEnemy;
    private int pvMe;

    public FightSystem (PokeStat statEnemy,PokeStat statMe){
        this.statEnemy = statEnemy;
        this.statMe = statMe;
        this.pvMaxEnemy = statEnemy.getHp();
        this.pvMaxMe = statMe.getHp();
        this.pvEnemy = statEnemy.getHp();
        this.pvMe = statMe.getHp();
    }

    public void setPVEnemy(PokeStat statEnemy){ this.statEnemy.setHp(statEnemy.getHp());}
    public int getPvEnemy(){
        return this.statEnemy.getHp();
    }

    public void setPvMe(PokeStat statMe){ this.statMe.setHp(statMe.getHp());}
    public int getPvMe(){
        return this.statMe.getHp();
    }

    public void attack(){
        pvEnemy = pvEnemy-2;
        pvMe = pvMe -1;
        statEnemy.setHp(pvEnemy);
        statMe.setHp(pvMe);
    }

    public void enemyAttackWhenYoutryToCapture(){
        pvMe = pvMe -1;
        statMe.setHp(pvMe);
    }

    public int capture(){
        int pvActual = pvMaxEnemy-pvEnemy;
        if(pvActual == 0){
            pvActual =pvMaxEnemy;
        }
        if(pvActual > 0) {
            if (pvActual > (pvMaxEnemy / 0.66)) {
                return 1 + (int) (Math.random() * ((4 - 1) + 1));
            }
            if (pvActual > (pvMaxEnemy / 0.33)) {
                return 1 + (int) (Math.random() * ((3 - 1) + 1));
            }
            if (pvActual < (pvMaxEnemy / 0.33)) {
                return 1 + (int) (Math.random() * ((2 - 1) + 1));
            }
        }
        return 0;
    }
}
