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
    
    /**
     * Viisaampi peli. Tätä kutsutaan kun halutaan ratkaista ratkojalle annettu peli viisaammalla solverilla.
     * 
     * Viisaampi solveri on huono ja se ei toimi joten tätä ei kannata käyttää.
     */
    public void viisaampi() {
        LogicalSolver_retired v = new LogicalSolver_retired(p, true);
        Ratkaisu = v.getKentta();
    }
    
    /**
     * Simppeli solveri. Tätä kutsutaan kun halutaan ratkaista ratkojalle annettu peli simppelillä solverilla.
     * 
     * simppeli solveri on rekursiivinen solveri joka toimii erittäin hitaasti. Muitakin tapoja tehdä rekursiivinen solveri oli mutten tätä kirjoittaessani ajatellut niitä.
     * joten mitä lopputuloksena on, on rekursiivinen solveri joka ratkoo nonogrammit testaamalla kaikkia mahdollisia kohtia ja testaamalla täsmääkö se annettuihin saariin. 
     */
    public void simppeli() {
        Long aika = System.currentTimeMillis();
        Simppeli s = new Simppeli(p, true);
        Ratkaisu = s.getKentta();
        kesto = System.currentTimeMillis()-aika;
    }
    
    /**
     * Permutaatio solveri. Tällä hetkellä ja lopullisesti ainoa solveri joka toimii joten projektiin kuuluu aikalailla vain tämä osuus.
     * 
     * @param b Tämä boolean on vain sitä varten että piirretäänkö välivaiheet. 
     * @throws TyhjaListaException on exceptio jota kutsutaan bruteforce osiossa tätä ohjelmaa kun bruteforce on löytänyt haaran jota on mahdoton solvata niin heittää se tämän exception jolla palaa takaisin alkuun ja kokeilee seuraavaa mahdollista kohtaa.
     */
    
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
