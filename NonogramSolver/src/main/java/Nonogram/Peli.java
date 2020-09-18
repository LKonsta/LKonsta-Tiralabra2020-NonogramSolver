package Nonogram;

import Tietorakenteet.ArrayList;

public class Peli {
    
    ArrayList<Integer>[] Rivit;
    Integer RivienMaara;
    ArrayList<Integer>[] Sarakkeet;
    Integer SarakkeidenMaara;
    
    
    public Peli(String sarakkeet, String rivit) {
        // peliä kutsutaan Stringeillä muodossa (3,1/0/1,1/0/0) jossa 
        // "," erotetaan jos rivillä on monia saaria ja "/" merkillä 
        // erotetaan rivit. Setteri taas muodostaa luokat "Rivit" ja "Sarakkeet" 
        // joita eri ratkojat käyttävät myöhemmin. 

        Setteri(rivit, 1);
        Setteri(sarakkeet, 0);
        
    }
    
    private void Setteri(String Numerot, Integer index) {
        
        String[] valinumerot = Numerot.split("/");
        Integer Maara = valinumerot.length;
        
        if (index == 1) {
            Rivit = new ArrayList[Maara];
            RivienMaara = Maara;
        } else {
            Sarakkeet = new ArrayList[Maara];
            SarakkeidenMaara = Maara;
        }
        
        for (int i = 0; i < Maara; i++) {
            if (index == 1) {
                Rivit[i] = new ArrayList<>();
            } else {
                Sarakkeet[i] = new ArrayList<>();
            }
            String[] valinumero = valinumerot[i].split(",");
            Integer[] numero = new Integer[valinumero.length];
            

            for (int j = 0; j < numero.length; j++) {
                numero[j] = Integer.valueOf(valinumero[j]);
            }
//            System.out.println(numero.length + " " + numero[0] + " " + valinumero[0] + " " + Numerot);
            if (numero.length == 0) {
                if (index == 1) {
                    Rivit[i].add(0);
                } else {
                    Sarakkeet[i].add(0);
                }
            }

            if (index == 1) {
                Rivit[i].addAll(numero);
            } else {
                Sarakkeet[i].addAll(numero);
            }
        }
        
    }
    
    public ArrayList<Integer>[] getRivit() {
        if (Rivit != null) {
            return Rivit;
        }
        return null;
    }
    
    public ArrayList<Integer>[] getSarakkeet() {
        if (Sarakkeet != null) {
            return Sarakkeet;
        }
        return null;
    }
    
    public Integer getRivienMaara() {
        return RivienMaara;
    }
    
    public Integer getSarakkeidenMaara() {
        return SarakkeidenMaara;
    }
    
    @Override
    public String toString() {
        String palaute = "";
        palaute = palaute + "Rivit: \n";
        for (ArrayList<Integer> Rivit1 : Rivit) {
            palaute = palaute + Rivit1.toString() + "\n";
        }
        palaute = palaute + "Sarakkeet: \n";
        for (ArrayList<Integer> Sarakkeet1 : Sarakkeet) {
            palaute = palaute + Sarakkeet1.toString() + "\n";
        }
        return palaute;
    }
}
