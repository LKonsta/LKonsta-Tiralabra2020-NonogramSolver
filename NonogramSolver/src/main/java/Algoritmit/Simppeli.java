package Algoritmit;

import Nonogram.Kentta;
import Nonogram.Peli;
import Tietorakenteet.ArrayList;
import java.util.Objects;
/**
 * Simppeli nonogram solveri. Tämä ratkaisee nonogrammit rekursiolla joten se on todella hidas.
 * pystyy juuri ja juuri normaaliin aikaan ratkoa 5x5 kokoiset nonogrammit.
 *
 */
public class Simppeli {

    private Kentta kentta;
    static ArrayList<Integer>[] rivit;
    static ArrayList<Integer>[] sarakkeet;
    static Integer pisteet;
    
    /**
     * Ennen rekursion aloittamista peli tekee itselleen tarpeelliset setupit.
     * -------------------------------------------------------------------------
     * VAROITUS: tämä koodi toimii mutta on erittäin hidas
     * -------------------------------------------------------------------------
     * Projektin alkuun korjoitin tämän koodin kun se oli ensimmäinen joka tuli 
     * mieleen.
     * 
     * lopputulos olikin sen verran hidas että sitä oli turha verrata 
     * muihin algoritmeihin.
     *
     * 
     * @param peli 
     * @param ratkooko 
     */
    public Simppeli(Peli peli, boolean ratkooko) {
        Kentta valikentta = new Kentta(peli.getRivienMaara(), peli.getSarakkeidenMaara());
        rivit = peli.getRivit();
        sarakkeet = peli.getSarakkeet();

        if (ratkooko) {
            for (int i = 0; i < valikentta.getLeveys(); i++) {
                for (int j = 0; j < valikentta.getKorkeus(); j++) {
                    valikentta.setKohta(j, i, 0);
                }
            }

            int SarakePisteet = 0;
            int RiviPisteet = 0;

            for (ArrayList<Integer> sarakkeet1 : sarakkeet) {
                for (int j = 0; j < sarakkeet1.size(); j++) {
                    SarakePisteet += sarakkeet1.get(j);
                }
            }
            for (ArrayList<Integer> rivit1 : rivit) {
                for (int j = 0; j < rivit1.size(); j++) {
                    RiviPisteet += rivit1.get(j);
                }
            }
            if (!(SarakePisteet == RiviPisteet)) {
                System.out.println("mahdoton");
            } else {
                pisteet = RiviPisteet;
                SimppeliRekursio(valikentta, -1, 0);
            }
        }
    }
    
    /**
     * Rekursiivinen käsky.
     * 
     * @param kentta tieto tämän hetkisestä kentästä
     * @param x
     * @param y 
     */
    private void SimppeliRekursio(Kentta kentta, Integer x, Integer y) {
        if (this.kentta != null) {
            return;
        }
        if (Objects.equals(kentta.getYkkoset(), pisteet)) {
            boolean[] rivitOikein = new boolean[kentta.getKorkeus()];
            boolean[] saratOikein = new boolean[kentta.getLeveys()];

            for (int i = 0; i < rivit.length; i++) {
                ArrayList<Integer> Saaret = new ArrayList<Integer>();
                Integer koko = 0;
                for (int k = 0; k < kentta.getLeveys(); k++) {
                    if (kentta.getKohta(i, k) == 0 && koko > 0) {
                        Saaret.add(koko);
                        koko = 0;
                    }
                    if (kentta.getKohta(i, k) == 1) {
                        koko++;
                    }
                }
                if (koko > 0) {
                    Saaret.add(koko);
                }
                if (Saaret.isEmpty()) {
                    Saaret.add(0);
                }
                rivitOikein[i] = rivit[i].sama(Saaret);
            }

            for (int i = 0; i < sarakkeet.length; i++) {
                ArrayList<Integer> Saaret = new ArrayList<Integer>();
                Integer koko = 0;
                for (int k = 0; k < kentta.getKorkeus(); k++) {
                    if (kentta.getKohta(k, i) == 0 && koko > 0) {
                        Saaret.add(koko);
                        koko = 0;
                    }
                    if (kentta.getKohta(k, i) == 1) {
                        koko++;
                    }
                }
                if (koko > 0) {
                    Saaret.add(koko);
                }
                if (Saaret.isEmpty()) {
                    Saaret.add(0);
                }
                saratOikein[i] = sarakkeet[i].sama(Saaret);
            }

            if (peliloppui(rivitOikein, saratOikein)) {
                ArrayList<Integer> koordinaatit = new ArrayList<Integer>();
                for (int i = 0; i < kentta.getLeveys(); i++) {
                    for (int j = 0; j < kentta.getKorkeus(); j++) {
                        if (kentta.getKohta(j, i) == 1) {
                            koordinaatit.add(i);
                            koordinaatit.add(j);
                        }
                    }

                }
                setKentta(koordinaatit);
                return;
            }

        }
        x++;
        if (x > kentta.getKorkeus() - 1) {
            y++;
            x = 0;
        }

        if (y < kentta.getLeveys()) {
            if (kentta.getYkkoset() < pisteet) {
                kentta.setKohta(x, y, 1);
                SimppeliRekursio(kentta, x, y);
                kentta.setKohta(x, y, 0);
                SimppeliRekursio(kentta, x, y);
            }
        }
    }

    /**
     * Peli loppui kun rekursiosta löydetään yksi ratkaisu joka täsmää nonogrammiin.
     * tätä testataan katsomalla jokainen rivi ja sarake läpi ja katsotaan onko siinnä saman verran ja oikean kokoisia saaria.
     * @param rivit lista riveistä ja siitä onke ne täsmääviä
     * @param sarat lista sarakkeista ja siitä onko ne täsmääviä
     * @return 
     */
    private boolean peliloppui(boolean[] rivit, boolean[] sarat) {
        for (int i = 0; i < rivit.length; i++) {
            if (!(rivit[i])) {
                return false;
            }
        }
        for (int i = 0; i < sarat.length; i++) {
            if (!(sarat[i])) {
                return false;
            }
        }
        return true;
    }

    private void setKentta(ArrayList<Integer> k) {
        Kentta valikentta = new Kentta(sarakkeet.length, rivit.length);
        for (int i = 0; i < k.size(); i += 2) {
            valikentta.setKohta(k.get(i), k.get(i + 1), 1);
        }
        this.kentta = valikentta;
    }

    public Integer[][] getKentta() {
        Integer[][] palaute = new Integer[kentta.getLeveys()][kentta.getKorkeus()];
        for (int i = 0; i < kentta.getKorkeus(); i++) {
            for (int j = 0; j < kentta.getLeveys(); j++) {
                palaute[j][i] = kentta.getKohta(j,i);
            }
        }
        return palaute;
    }

}
