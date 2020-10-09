package Algoritmit;

import Nonogram.Peli;
import Tietorakenteet.ArrayList;
import Algoritmit.Simppeli;

public class Solver {
    
    Peli p;
    Integer[][] Ratkaisu;
    
    /**
     * Ratkaisija metodi. tästä voidaan valita kutsutaanko peliin viisaampaa vai simppeliä ratkaisijaa.
     * @param up peli jossa tieto sarakkeista ja riveistä 
     */
    public Solver(Peli up) {
        this.p = up;
    }
    
    public void viisaampi() {
        LogicalSolver v = new LogicalSolver(p);
        Ratkaisu = v.getKentta();
    }
    
    public void simppeli() {
        Simppeli s = new Simppeli(p);
        Ratkaisu = s.getKentta();
    }
    
    public void permutaatio() {
        PermutaatioSolver permut = new PermutaatioSolver(p);
        Ratkaisu = permut.getKentta();
    }
    
    /**
     * Muuttaa pelistä saadun kentän String muotoon
     * @return valmis kenttä
     */
    public String getRatkaisu() {
        String palaute = "";
        for (int i = 0; i < Ratkaisu.length; i++) {
            palaute += "[";
            for (int j = 0; j < Ratkaisu[i].length; j++) {
                palaute = palaute + Ratkaisu[i][j];
            }
            if (i+1==Ratkaisu.length) {
                palaute += "]";
            } else {
                palaute += "]\n";
            }
            
        }
        return palaute;
    }

    
    
}
