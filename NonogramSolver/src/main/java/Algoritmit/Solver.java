package Algoritmit;

import Nonogram.Peli;
import Tietorakenteet.ArrayList;
import Algoritmit.Simppeli;
import Tietorakenteet.TyhjaListaException;

public class Solver {
    
    Peli p;
    Integer[][] Ratkaisu;
    Long kesto;
    
    /**
     * Ratkaisija metodi. tästä voidaan valita kutsutaanko peliin viisaampaa vai simppeliä ratkaisijaa.
     * @param up peli jossa tieto sarakkeista ja riveistä 
     */
    public Solver(Peli up) {
        this.p = up;
    }
    
    public void viisaampi() {
        LogicalSolver_retired v = new LogicalSolver_retired(p, true);
        Ratkaisu = v.getKentta();
    }
    
    public void simppeli() {
        Simppeli s = new Simppeli(p, true);
        Ratkaisu = s.getKentta();
    }
    
    public void permutaatio(boolean b) throws TyhjaListaException {
        Long aika = System.currentTimeMillis();
        PermutaatioSolver permut = new PermutaatioSolver(p, true, b);
        Ratkaisu = permut.getKentta();
        kesto = System.currentTimeMillis()-aika;
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
        palaute += ("\nRatkaisussa kesti: " + kesto + " ms");
        return palaute;
    }



    
    
}
