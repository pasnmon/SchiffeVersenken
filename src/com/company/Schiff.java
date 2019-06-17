package com.company;

public class Schiff {
    protected int laenge;
    protected int posX = 0;
    protected int posY = 0;
    protected boolean hor;

    public Schiff (int l , boolean ausr){
        laenge = l;
        hor = ausr;
    }
    public void changePosX(int x){
        posX = x;
    }
    public void changePosY(int y){
        posY = y;
    }
    public void changePosition(int x, int y){
        changePosX(x);
        changePosY(y);
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public int[] getPosition(){
        return new int[]{this.getPosX(),this.getPosY()};
    }
    public boolean getAusrichtung(){
        return hor;
    }
    public int getLaenge(){
        return laenge;
    }

}
