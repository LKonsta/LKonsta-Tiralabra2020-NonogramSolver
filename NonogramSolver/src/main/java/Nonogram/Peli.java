package Nonogram;

import Tietorakenteet.ArrayList;
import java.util.Objects;

public class Peli {
    String nimi;
    ArrayList<Integer>[] Rivit;
    Integer RivienMaara;
    ArrayList<Integer>[] Sarakkeet;
    Integer SarakkeidenMaara;
    public Kentta kentta;
    
    /**
     * peliä kutsutaan Stringeillä muodossa (3,1/0/1,1/0/0).jossa 
 "," erotetaan jos rivillä on monia saaria ja "/" merkillä 
 erotetaan rivit. Setteri taas muodostaa luokat "Rivit" ja "Sarakkeet" 
 joita eri ratkojat käyttävät myöhemmin. 
     * @param sarakkeet
     * @param rivit 
     * @param nimi 
     */
    public Peli(String sarakkeet, String rivit, String nimi) {
        this.nimi = nimi;
        Setteri(rivit, true);
        Setteri(sarakkeet, false);
        kentta = new Kentta(SarakkeidenMaara, RivienMaara);
        
    }
    /**
     * Setteri muuttaa String muodossa olevan tiedon taulukko Arraylistiin josta sitä on helpompi käsitellä.
     * @param Numerot lista saarista String muodossa
     * @param index tieto onko kyseessä rivit vai sarakkeet
     */
    private void Setteri(String Numerot, Boolean index) {
        
        String[] valinumerot = Numerot.split("/");
        Integer Maara = valinumerot.length;
        
        if (index) {
            Rivit = new ArrayList[Maara];
            RivienMaara = Maara;
        } else {
            Sarakkeet = new ArrayList[Maara];
            SarakkeidenMaara = Maara;
        }
        
        for (int i = 0; i < Maara; i++) {
            if (index) {
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
                if (index) {
                    Rivit[i].add(0);
                } else {
                    Sarakkeet[i].add(0);
                }
            }

            if (index) {
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
    
    public Kentta getKentta() {
        return kentta;
    }
    
    public void setKentta(Kentta k) {
        kentta = k;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    /**
     * onkoMahdollinen metodi kutsutaan bruteforce kohdassa ja tarkistetaan onko tämän hetkinen nonogrammi oikea. Eli täsmääkö se siis alkuperäisesti annettuihin saariin.
     * Tämä metodi toimii vain jos kenttä on täysi eli jos siitä puuttuu pisteitä niin tämä metodi ei toimi.
     * @return palauttaa joko true tai falsen siitä täsmääkö tämänhetkinen kenttä annettuihin saariin. 
     */
    
    public boolean onkoMahdollinen() {
//        System.out.println(kentta);
        boolean mahdollinen = true;
        for (int i = 0; i < RivienMaara; i++) {
            Integer koko = 0;
            Integer index = 0;
            for (int j = 0; j < SarakkeidenMaara; j++) {
                if (kentta.getKohta(i, j) == 2) {
                    koko++;
                }
                if (kentta.getKohta(i, j) == 1) {
                    if (koko > 0) {
                        if (!Objects.equals(Rivit[i].get(index), koko)) {
                            mahdollinen = false;
                            break;
                        } else {
                            koko = 0;
                            index++;
                        }
                    }
                }
                if (koko > 0 && j == SarakkeidenMaara-1) {
                    if (!Objects.equals(Rivit[i].get(index), koko)) {
                        mahdollinen = false;
                        break;
                    } else {
                        koko = 0;
                        index++;
                    }
                }
            }
        }
        for (int i = 0; i < SarakkeidenMaara; i++) {
            Integer koko = 0;
            Integer index = 0;
            for (int j = 0; j < RivienMaara; j++) {
                if (kentta.getKohta(j, i) == 2) {
                    koko++;
                }
                if (kentta.getKohta(j, i) == 1) {
                    if (koko > 0) {
                        if (!Objects.equals(Sarakkeet[i].get(index), koko)) {
                            mahdollinen = false;
                            break;
                        } else {
                            koko = 0;
                            index++;
                        }
                    }
                }
                if (koko > 0 && j == RivienMaara-1) {
                    if (!Objects.equals(Sarakkeet[i].get(index), koko)) {
                        mahdollinen = false;
                        break;
                    } else {
                        koko = 0;
                        index++;
                    }
                }
            }
        }
        return mahdollinen;
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
