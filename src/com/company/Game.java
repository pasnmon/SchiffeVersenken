package com.company;

import javax.swing.*;
import java.util.Random;

public class Game {

    protected int schiffAnz;                   //anzahl der schiffe
    protected Schiff[][] schiffe;  //arrays für die schiffe
    protected Gamefield[] gameFields;      //array für die Spielfelder
    protected int groesse;                    //spielfeldgröße
    protected boolean cpuTurn = false;        //wer dran ist (cpu, oder player)
    protected final int FALSCH = 99999;       //falls vom player falsche koordinaten eingegeben wurden


    public Game(int groesse1, int sAnz){      //um das spiel zu starten
        schiffAnz = sAnz;
        groesse = groesse1;
    }
    public void startGame (){
        schiffe = new Schiff[2][schiffAnz];
        gameFields= new Gamefield[2];
        initGF();
        initShips();
        addShipsToField();
        System.out.println((gameLoop()? "Player won" : "CPU won"));
    }

    protected void initShips (){              //füllt beide ship arrays mit schiffen (schifflänge von 1 - anzSchiffe)
        for (int i = 0; i < schiffe.length;i++){
            for (int y = 0; y< schiffe[i].length;y++){
                schiffe[i][y] = new Schiff(y+1,(y%2==0));
            }
        }
    }

    protected void initGF(){                 //füllt das array mit 2 spielfelder
        for (int i = 0;i< 2;i++){
            gameFields[i] = new Gamefield(groesse,(i%2==0));
        }
    }

    protected void addShipsToField(){         //fügt die schiffe dem spielfeld hinzu
        for (int i = 0; i<schiffe.length;i++){
            for (int y = 0;y<schiffe[i].length;y++){
                do{
                    schiffe[i][y].changePosition(getRndPos(schiffe[i][y].getAusrichtung()?groesse-schiffe[i][y].getLaenge():groesse),
                            getRndPos(!schiffe[i][y].getAusrichtung()?groesse-schiffe[i][y].getLaenge():groesse));
                    //wenn ausrichtung horizontal dann bekomme nur koordinaten die das schiff in das spielfeld passen lassen
                }while (!gameFields[i].addShip(schiffe[i][y])); //erstelle so lange zufällige x/y koordinaten bis das schiff nicht mehr durch ein anderes
                                                                //blockiert wird (addShip returnt false falls blockiert)
            }
        }
    }
    protected boolean gameLoop(){         //Spielschleife


        do {
            printGameFields();
            cpuOrPlayer();
        }while (!gameFields[0].checkWin() && !gameFields[1].checkWin());    //solange keiner gewonnen hat
        return gameFields[1].checkWin();
    }

    protected int getRndPos(int max){                         //erstellt zufällige werte
        return new Random().nextInt(((max) - 0))+0;
    }
    protected void printGameFields (){                        //gibt beide spielfelder aus
        gameFields[0].ausgabeGF();
        gameFields[1].ausgabeGF();
        System.out.println();
    }
    protected void cpuOrPlayer(){                             //ändert wer dran ist
        if (!turn(cpuTurn)){                            //wenn nicht getroffen -> ändere wer dran ist
            cpuTurn = !cpuTurn;
        }
    }

    protected boolean turn (boolean cpu){                 //gibt true zurück , wenn getroffen, false wenn nicht
        if (cpu) {
            return turnCpu();
        }
        else {
            return turnPlayer();
        }
    }

    protected boolean turnCpu(){                          //cpu zug
        int posX;
        int posY;
        do {
            posX = getRndPos(groesse);
            posY = getRndPos(groesse);
        } while (gameFields[1].alrdHit(posY,posX));
        return gameFields[1].hit(posY,posX);        //return true bei treffer / false  wenn nicht

    }
    protected boolean turnPlayer(){               //player zug
        int posX;
        int posY;
        do {
            try{
                posY = Integer.parseInt(JOptionPane.showInputDialog("Y-Wert"))-1;
                posX = Integer.parseInt(JOptionPane.showInputDialog("X-Wert"))-1;
                if (posY > this.groesse-1 || posX > this.groesse-1){
                    throw new Exception();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Fehler in der Eingabe");
                posX = FALSCH;
                posY = FALSCH;
                continue;
            }
        }while (posX == FALSCH || gameFields[0].alrdHit(posY,posX));
        return gameFields[0].hit(posY,posX);    //return true bei treffer / false wenn nicht
    }
}
