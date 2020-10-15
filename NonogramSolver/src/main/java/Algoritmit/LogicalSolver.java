
package Algoritmit;

import Nonogram.Kentta;
import Nonogram.Peli;
import Tietorakenteet.ArrayList;

public class LogicalSolver {
    
    private Kentta kentta;
    static ArrayList<Integer>[] rivit;
    static ArrayList<Integer>[] sarakkeet;
    static Integer pisteet;
    private ArrayList<Integer>[][][] mahdolliset;
    /**
     * Looginen solveri. 
     * Yrittää ratkoa nonogrammin etsimällä vuorotellen varmat tyhjät ja varmat täydet kohdat sarakkeista ja riveistä.
     * 
     * @param peli Nonogram peli joka on annettu Logical Solverille
     */
    public LogicalSolver(Peli peli, boolean ratkooko) {
        kentta = new Kentta(peli.getSarakkeidenMaara(), peli.getRivienMaara());
        
        rivit = peli.getRivit();
        sarakkeet = peli.getSarakkeet();
        
        if (ratkooko) {
            setupMahdolliset();

            Integer SarakePisteet = 0;
            Integer RiviPisteet = 0;

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
            pisteet = SarakePisteet;
            if (!(SarakePisteet.equals(RiviPisteet))) {
                System.out.println("mahdoton: " + SarakePisteet + "/" + RiviPisteet);
            } else {
                while (pisteet > 0) {
                    for (int i = 0; i < kentta.getKorkeus(); i++) {
                        tyhjaTestaus(i, true);
                        saariTestaus(i, true);
                        mahdottomienPoisto(i, true);
                    }
                    for (int i = 0; i < kentta.getLeveys(); i++) {
                        tyhjaTestaus(i, false);
                        saariTestaus(i, false);
                        mahdottomienPoisto(i, false);
                    }
                    mahdollisetToString();
    //                
                    System.out.println(kentta); 
                    System.out.println("--------------------");
                }

            }
        }
        
    }
    /**
     * alustaa "mahdolliset" kentän. mahdolliset kenttä on muotoa [[[<>,<>]]] jossa sisimmässä taulukossa pitää kaksi arraylisitä.
     * ensimmäisessä näistä on tieto mahdollisista saarista sarakkeiden kautta ja toisessa tieto mahdollisista saarista rivien kautta.
     * 
     *  
     */
    private void setupMahdolliset() {
        mahdolliset = new ArrayList[kentta.getLeveys()][kentta.getKorkeus()][2];
        for (int i = 0; i < kentta.getLeveys(); i++) {
            for (int j = 0; j < kentta.getKorkeus(); j++) {
                kentta.setKohta(i, j, 0);
                mahdolliset[i][j][0] = new ArrayList<>();
                mahdolliset[i][j][1] = new ArrayList<>();
            }
        }
        for (int i = 0; i < kentta.getLeveys(); i++) {
            for (int j = 0; j < sarakkeet[i].size(); j++) {
                int min = 0;
                for (int k = 0; k < j; k++) {
                    min += sarakkeet[i].get(k);
                    min++;
                }
                int max = rivit.length;
                for (int k = sarakkeet[i].size()-1; k > j; k--) {
                    max -= sarakkeet[i].get(k);
                    max--;
                }
                for (int k = min; k < max; k++) {
                    mahdolliset[i][k][1].add(j+1);
                    
                }
            }
        }
        for (int i = 0; i < kentta.getKorkeus(); i++) {
            for (int j = 0; j < rivit[i].size(); j++) {
                int min = 0;
                for (int k = 0; k < j; k++) {
                    min += rivit[i].get(k);
                    min++;
                }
                int max = sarakkeet.length;
                for (int k = rivit[i].size()-1; k > j; k--) {
                    max -= rivit[i].get(k);
                    max--;
                }
                
                for (int k = min; k < max; k++) {
                    mahdolliset[k][i][0].add(j+1);
                }
            }
        }
    }

    /**
     * Tarkistaa onko mahdollisia tyhjiä tiloja.
     * @param i monesko rivi tai sarake.
     * @param b tieto onko kyseessä rivit vai sarakkeet. jos true niin rivit, jos false niin sarakkeet.
     */
    private void tyhjaTestaus(int i, boolean b) {
        ArrayList<Integer> saaret;
        if (b) {
            saaret = rivit[i];
            int koko = 0;
            int index = 0;
            for (int j = 0; j < kentta.getLeveys(); j++) {
                if (kentta.getKohta(j, i) == 1) {
                    if (mahdolliset[j][i][0].size() == 1 && index == 0) {
                        if (mahdolliset[j][i][0].get(0) != null) {
                            index = mahdolliset[j][i][0].get(0);
                        } else {
                            continue;
                        }
                    }
                    koko++;
                } else if (koko >= 1 && kentta.getKohta(j, i) != 1 && index >= 1){
                    poisto(j-1, i, b, koko, index, saaret.get(index-1));
                    koko = 0;
                    index = 0;
                } else {
                    koko = 0;
                }
            }
        } else {
            saaret = sarakkeet[i];
            int koko = 0;
            int index = 0;
            for (int j = 0; j < kentta.getKorkeus(); j++) {
                if (kentta.getKohta(i, j) == 1) {
                    if (mahdolliset[i][j][1].size() == 1 && index == 0) {
                        if (mahdolliset[i][j][1].get(0) != null) {
                            index = mahdolliset[i][j][1].get(0);
                        } else {
                            continue;
                        }
                    }
                    koko++;
                } else if (koko >= 1 && kentta.getKohta(i, j) != 1 && index >= 1){
//                    System.out.println(koko + ", " + saaret.get(index-1) + "  (" + (i-(koko-1)) + ", " + (j-1) + ") (" + i + ", " + (j-1) + ")");
                    poisto(i, j-1, b, koko, index, saaret.get(index-1));
                    koko = 0;
                    index = 0;
                } else {
                    koko = 0;
                }
            }
        }
    }
    
    /**
     * jos tyhäntestaus löysi mahdollisia tyhjiä tämä poistaa ne.
     * @param j koordinaatti x tai y riippuu onko kyseessä rivit vai sarakkeet
     * @param i koordinaatti x tai y riippuu onko kyseessä rivit vai sarakkeet
     * @param b kertoo onko kyseessä rivit vai sarakkeet
     * @param k kuinka monta jo merkattua kohtaa saaresta löydetty, eli koko
     * @param index monesko saari on kyseessä
     * @param ok saaren koko
     */
    private void poisto(int j, int i, boolean b, int k, int index, int ok) {
        int reunat = ok-k;
        
        if (b) {
            int alku = j - (k - 1);
            for (int l = 0; l < mahdolliset.length; l++) {
                if (l < alku-reunat || l > j+reunat) {
                    mahdolliset[l][i][0].remove(index);
                    if (mahdolliset[l][i][0].size() == 0 && kentta.getKohta(l, i) != 1) {
                        mahdolliset[l][i][1].removeAll();
                        kentta.setKohta(l, i, 8);
                    }
                }
            }
        } else {
            int alku = i - (k - 1);
            for (int l = 0; l < mahdolliset[1].length; l++) {
                if (l < alku-reunat || l > i+reunat) {
                    mahdolliset[j][l][1].remove(index);
                    if (mahdolliset[j][l][1].size() == 0 && kentta.getKohta(j, l) != 1) {
                        mahdolliset[j][l][0].removeAll();
                        kentta.setKohta(j, l, 8);
                    }
                }
            }
        }
    }
    /**
     * Etsii saaria tai toisin sanoen merkattavia kohtia.
     * @param i monesko rivi tai sarake
     * @param b tieto onko kyseessä rivit vai sarakkeet. jos true niin rivit, jos false niin sarakkeet.
     */
    private void saariTestaus(int i, boolean b) {
        ArrayList<Integer> saaret;
        if (b) {
            saaret = rivit[i];
        } else {
            saaret = sarakkeet[i];
        }
        for (int j = 0; j < saaret.size(); j++) {
            int mahdollinenKoko = 0;
            if (b) {
                for (int k = 0; k < mahdolliset.length; k++) {
                    if (mahdolliset[k][i][0].contains(j+1)) {
                        mahdollinenKoko++;
                    }
                }
            } else {
                for (int k = 0; k < mahdolliset[1].length; k++) {
                    if (mahdolliset[i][k][1].contains(j + 1)) {
                        mahdollinenKoko++;
                    }
                }
            }
            if (saaret.get(j)*2 >mahdollinenKoko) {
                piirto(i, b, (j+1), mahdollinenKoko, saaret.get(j));
            }
        }
    }
    
    /**
     * Täytettäviä kohti on löydetty saariTestauksesta.
     * @param i koordinaatti x tai y riippuu onko kyseessä rivit vai sarakkeet
     * @param b tieto on kyseessä rivit vai sarakkeet
     * @param j koordinaatti x tai y riippuu onko kyseessä rivit vai sarakkeet
     * @param m mahdollinen koko eli "mahdolliset" kentästä haettu tieto että kuinka monessa eri pisteessä saari voi olla
     * @param k saaren koko
     */
    private void piirto(int i, boolean b, int j, int m, int k) {
        int piirronmaara = k*2-m;
        int tyhjaa = k-piirronmaara;
        if (b) {
            for (int l = 0; l < kentta.getLeveys(); l++) {
                if (mahdolliset[l][i][0].contains(j)) {
                     if (tyhjaa > 0) {
                        tyhjaa--;
                    } else {
                        if (mahdolliset[l][i][1].size() == 1) {
                            if (i > 0) {
                                if (mahdolliset[l][i - 1][1].size() != 0) {
                                    mahdolliset[l][i - 1][1].removeAll();
                                    mahdolliset[l][i - 1][1].add(mahdolliset[l][i][1].get(0));
                                }
                            }
                            if (i < kentta.getKorkeus()-1) {
                                if (mahdolliset[l][i + 1][1].size() != 0) {
                                    mahdolliset[l][i + 1][1].removeAll();
                                    mahdolliset[l][i + 1][1].add(mahdolliset[l][i][1].get(0));
                                }
                            }
                        }
                        if (piirronmaara > 0) {
                            if (kentta.getKohta(l, i) != 1) {
                                pisteet--;
                            }
                            kentta.setKohta(l, i, 1);
                            piirronmaara--;
                        }
                    } 
                } else if (kentta.getKohta(l, i) == 1 && mahdolliset[l][i][0].size() == 0) {
                    piirronmaara--;
                }
            }
        } else {
            for (int l = 0; l < kentta.getKorkeus(); l++) {
                if (mahdolliset[i][l][1].contains(j)) {
                    if (tyhjaa > 0) {
                        tyhjaa--;
                    } else {
                        if (mahdolliset[i][l][0].size() == 1) {
                            if (i > 0) {
                                if (mahdolliset[i - 1][l][1].size() != 0) {
                                    mahdolliset[i - 1][l][0].removeAll();
                                    mahdolliset[i - 1][l][0].add(mahdolliset[i][l][0].get(0));
                                }
                            }
                            if (i < kentta.getLeveys()-1) {
                                if (mahdolliset[i + 1][l][1].size() != 0) {
                                    mahdolliset[i + 1][l][0].removeAll();
                                    mahdolliset[i + 1][l][0].add(mahdolliset[i][l][0].get(0));
                                }
                            }
                        }
                        if (piirronmaara > 0){
                            if (kentta.getKohta(i, l) != 1) {
                                pisteet--;
                            }
                            kentta.setKohta(i, l, 1);
                            piirronmaara--;
                        }
                    }
                }
                else if (kentta.getKohta(i, l) == 1 && mahdolliset[i][l][0].size() == 0) {
                    piirronmaara--;
                }
            }
        }
    }
    
    /**
     * Tarkistus jossa poistetaan mahdottomat pisteet joita mahdolliset kentässä vielä on.
     * @param i monesko rivi tai sarake
     * @param b tieto onko kyseessä rivit vai sarakkeet. jos true niin rivit, jos false niin sarakkeet.
     */
    private void mahdottomienPoisto(int i, boolean b) {
        if (b) {
            ArrayList<Integer> saaret = rivit[i];
            for (int j = 0; j < saaret.size(); j++) {
                for (int t = 1; t < mahdolliset.length-1; t++) {
                    if (kentta.getKohta(t, i) == 1 && mahdolliset[t][i][0].size() == 1 && mahdolliset[t][i][0].contains(j+1)) {
                        for (int k = t-1; k <= t+1; k++) {
                            if (mahdolliset[k][i][0].size() != 0) {
                                mahdolliset[k][i][0].removeAll();
                                mahdolliset[k][i][0].add(j + 1);
                            }
                        }
                    }   
                }
            }
        } else {
            ArrayList<Integer> saaret = sarakkeet[i];
            for (int j = 0; j < saaret.size(); j++) {
                for (int t = 1; t < mahdolliset[1].length-1; t++) {
                    if (kentta.getKohta(i, t) == 1 && mahdolliset[i][t][1].size() == 1 && mahdolliset[i][t][1].contains(j+1)) {
                        for (int k = t-1; k <= t+1; k++) {
                            if (mahdolliset[i][k][1].size() != 0) {
                                mahdolliset[i][k][1].removeAll();
                                mahdolliset[i][k][1].add(j + 1);
                            }
                        }
                    }
                }  
            }
        }
    }
    
    /**
     * Palauttaa tämänhetkisen tiedon mitä "mahdolliset" kentässä on. Luota vain bugifiksausta varten.
     */
    void mahdollisetToString() {
        System.out.println("sarakkeet:");
        for (int i = 0; i < kentta.getLeveys(); i++) {
            System.out.print("[");
            for (int j = 0; j < kentta.getKorkeus(); j++) {
                System.out.print("(");
                if (mahdolliset[i][j][0].size() == 0) {
                    System.out.print("  ");
                }
                if (mahdolliset[i][j][0].size() == 1) {
                    System.out.print(" ");
                }
                for (int k = 0; k < mahdolliset[i][j][0].size(); k++) {
                    System.out.print(mahdolliset[i][j][0].get(k));
                }
                System.out.print(") ");
                
            }
            System.out.println("]");
            
        }
        System.out.println("rivit:");
        for (int i = 0; i < kentta.getLeveys(); i++) {
            System.out.print(sarakkeet[i]);
            if (sarakkeet[i].size() == 1) {
                System.out.print("   ");
            }
            System.out.print("  [");
            for (int j = 0; j < kentta.getKorkeus(); j++) {
                System.out.print(mahdolliset[i][j][1]);
            }
            System.out.println("]");
        }
        System.out.println("");
    }
    
    /**
     * 
     * @return palauttaa Integer[][] jossa on kentän pisteet 
     */
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
