package com.company;

import javax.swing.*;

public class Gamefield {
    protected String[][] field;       //das feld
    protected boolean isCpu;          //ob es das CPU Spielfeld ist
    protected int groesse;            //größe des Spielfelds (größe * größe)
    protected String wasserZeichen = String.format("%-5s","~");   //darstellung vom feld typ wasser
    protected String schiffZeichen = String.format("%-5s","0");   //darsellung vom feld typ schiff
    protected String schiffHit = String.format("%-5s","#");       //darstellung vom feld typ getroffenes schiffteil
    protected String noSchiffHit = String.format("%-5s","X");     //darstellung vom feld typ beschossen aber leer


    public Gamefield (int groesse,boolean cpu){        //konstruktor übergabe der größe und ob das feld der cpu ist
        this.groesse = groesse;
        this.isCpu = cpu;
        this.field = new String[groesse][groesse];
        initGF();
    }
    protected void initGF(){                              //allen feldern werden das wasserzeichen zugewiesen
        for (int i = 0; i< field.length;i++){
            for (int y = 0; y< field[i].length;y++){
                field[i][y] = wasserZeichen;
            }
        }
    }
    public boolean addShip(Schiff s1){                  //versucht das übergebenne objekt dem spielfeld hinzuzufügen

        for (int i = 0; i < s1.getLaenge(); i++) {      // falls der bereich des schiffes von einem anderen blockiert wird -> false
            int addX = (s1.getAusrichtung()?i:0);
            int addY = (s1.getAusrichtung()?0:i);
            if (field[s1.getPosY()+addY][s1.getPosX()+addX] == schiffZeichen){
                return false;
            }
        }

        for (int i = 0; i< s1.getLaenge();i++){     //ansonsten setze alle felder auf das schiffZeichen und return true
            int addX = (s1.getAusrichtung()?i:0);
            int addY = (s1.getAusrichtung()?0:i);
            field[s1.getPosY()+addY][s1.getPosX()+addX] = schiffZeichen;
        }
        return true;

    }

    public boolean hit (int y, int x){      //überprüft ob die übergebenen koordinaten ein schiff getroffen haben (true == getroffen/false== nicht getroffen)
        if (field[y][x] == schiffZeichen){
            field [y][x] = schiffHit;
            JOptionPane.showMessageDialog(null, ((isCpu)?"Schiff getroffen": "Cpu hat ein Schiff getroffen"));

            return true;
        }
        JOptionPane.showMessageDialog(null, ((isCpu)?"Kein Schiff getroffen" : "Cpu hat kein Schiff getroffen"));
        field[y][x] = noSchiffHit;
        return false;
    }
    public boolean alrdHit (int y, int x){  //überprüft ob die koordinaten schon mal beschossen wurden
        if (field[y][x] == noSchiffHit || field[y][x] == schiffHit){
            if (isCpu){
                JOptionPane.showMessageDialog(null,"Schon beschossen wähle ein anderes Ziel");
            }

            return true;
        }
        return false;
    }
    public void ausgabeGF(){                //gibt das spielfeld aus -> wenn es das cpu spielfeld ist gib für die schiffe das wasserzeichen aus
        System.out.println((isCpu)?"CPU field:":"Player field:");
        for (String[] x : field){
            for (String y : x){
                if (isCpu && y == schiffZeichen){
                    System.out.print(wasserZeichen);
                }
                else {
                    System.out.print(y);
                }
            }
            System.out.println();
        }
    }

    public boolean checkWin(){          //überprüft ob es keine schiffZeichen mehr gibt -> keine vorhanden == win
        for (String[] x: field){
            for (String y : x){
                if (y == schiffZeichen){
                    return false;
                }
            }
        }
        return true;
    }
}
