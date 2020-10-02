
package Algoritmit;

import static Algoritmit.Simppeli.rivit;
import static Algoritmit.Simppeli.sarakkeet;
import Nonogram.Kentta;
import Nonogram.Peli;
import Tietorakenteet.ArrayList;
import java.util.Arrays;

public class LogicalSolver {
    
    private Kentta kentta;
    static ArrayList<Integer>[] rivit;
    static ArrayList<Integer>[] sarakkeet;
    static Integer pisteet;
    Integer[] taulukko;
    static ArrayList<Integer> oikeatSaaret;
    private ArrayList<Integer>[][][] mahdolliset;
    
    public LogicalSolver(Peli peli) {
        
        kentta = new Kentta(peli.getSarakkeidenMaara(), peli.getRivienMaara());
        
        rivit = peli.getRivit();
        sarakkeet = peli.getSarakkeet();
        setupMahdolliset(peli);
        
        
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
        if (!(SarakePisteet == RiviPisteet)) {
            System.out.println("mahdoton");
        } else {
//            while (pisteet > 0) {
                for (int i = 0; i < kentta.getKorkeus(); i++) {
//                    tyhjaTestaus(i, true);
                    saariTestaus(i, true);
                    
                }
                for (int i = 0; i < kentta.getLeveys(); i++) {
                    tyhjaTestaus(i, false);
                    saariTestaus(i, false);
                }
                System.out.println(kentta); 
                System.out.println("--------------------");
                mahdollisetToString();
//            }
            
        }
        
    }
    
    private void setupMahdolliset(Peli peli) {
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
    
    void mahdollisetToString() {
        System.out.println("sarakkeet:");
        for (int i = 0; i < kentta.getKorkeus(); i++) {
            System.out.print("[");
            for (int j = 0; j < kentta.getLeveys(); j++) {
                System.out.print(mahdolliset[j][i][0]);
            }
            System.out.println("]");
            
        }
        System.out.println("rivit:");
        for (int i = 0; i < kentta.getLeveys(); i++) {
//            System.out.print(sarakkeet[i]);
            System.out.print("  [");
            for (int j = 0; j < kentta.getKorkeus(); j++) {
                System.out.print(mahdolliset[i][j][1]);
            }
            System.out.println("]");
        }
        System.out.println("");
    }
    
    private void tyhjaTestaus(int i, boolean b) {
        ArrayList<Integer> saaret;
        if (b) {
            saaret = rivit[i];
            int koko = 0;
            int index = 0;
            for (int j = 0; j < sarakkeet.length; j++) {
                if (kentta.getKohta(j, i) == 1) {
                    if (mahdolliset[j][i][0].size() == 1 && index == 0) {
                        index = mahdolliset[j][i][0].get(0);
//                        System.out.println(mahdolliset[j][i][0].get(0) + "    taala");
                    }
                    koko++;
                } else if (koko > 1 && kentta.getKohta(j, i) != 1){
//                    System.out.println("poisto:" + (j-1) + ", " + i + ", " + koko + ", " + b + ", " + index + ", " + saaret.get(index-1));
                    poisto(j-1, i, b, koko, index, saaret.get(index-1));
                    koko = 0;
                    index = 0;
                }
            }
        } else {
            saaret = sarakkeet[i];
            int koko = 0;
            int index = 0;
            for (int j = 0; j < rivit.length; j++) {
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
                    poisto(i, j-1, b, koko, index, saaret.get(index-1));
                    koko = 0;
                    index = 0;
                }
            }
        }
    }
    
    private void poisto(int j, int i, boolean b, int k, int index, int ok) {
        int reunat = ok-k;
        
        if (b) {
            int alku = j - (k - 1);
            for (int l = 0; l < mahdolliset.length; l++) {
                if (l < alku-reunat || l > j+reunat) {
//                    System.out.println(mahdolliset[l][i][1] + ", " + index + ", (" + l + ", " + i + ") " + kentta.getKohta(l, i));
                    mahdolliset[l][i][0].remove(index);
//                    System.out.println(mahdolliset[l][i][1] + ", " + index + ", (" + l + ", " + i + ")");
                }
            }
        } else {
            int alku = i - (k - 1);
//            System.out.println((alku - reunat) + ", " + (i + reunat) + "   min max  (" + reunat);
            for (int l = 0; l < mahdolliset[1].length; l++) {
                if (l < alku-reunat || l > i+reunat) {
//                    System.out.println(mahdolliset[j][l][1] + ", " + index + "  poisto"+ ", (" + j + ", " + l + ") " + kentta.getKohta(j, l));
                    mahdolliset[j][l][1].remove(index);
//                    System.out.println(mahdolliset[j][l][1] + ", " + index + "  poistettu"+ ", (" + j + ", " + l + ") " + kentta.getKohta(j, l));
                }
            }
        }
    }
    
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
                System.out.println("piirto " + (j+1) + ", " + i);
                piirto(i, b, (j+1), mahdollinenKoko, saaret.get(j));
                mahdottomienPoisto(i, b, (j+1), mahdollinenKoko, saaret.get(j));
            }
        }
    }
    
    private void piirto(int i, boolean b, int j, int m, int k) {
        int piirronmaara = k*2-m;
        int tyhjaa = k-piirronmaara;
        if (b) {
            for (int l = 0; l < mahdolliset.length; l++) {
                if (mahdolliset[l][i][0].contains(j)) {
                     if (tyhjaa > 0) {
                        tyhjaa--;
                    } else {
                        if (mahdolliset[l][i][1].size() == 1) {
                            if (i > 0) {
                                if (!mahdolliset[l][i - 1][1].contains(mahdolliset[l][i][1].get(0))) {
                                    mahdolliset[l][i - 1][1].removeAll();
                                }  
                            }
                           
                            if (i < kentta.getLeveys()) {
                                if (!mahdolliset[l][i + 1][1].contains(mahdolliset[l][i][1].get(0))) {
                                    mahdolliset[l][i + 1][1].removeAll();
                                }
                            }
                            
                        }
                        if (piirronmaara > 0){
                            kentta.setKohta(l, i, 1);
                            if (l > 0) {
                                if (mahdolliset[l-1][i][0]. size() > 1) {
                                    mahdolliset[l-1][i][0].removeAll();
                                    mahdolliset[l-1][i][0].add(j);
                                }
                            }
                            if (l < kentta.getKorkeus()) {
                                if (mahdolliset[l+1][i][0].size() > 1) {
                                    mahdolliset[l+1][i][0].removeAll();
                                    mahdolliset[l+1][i][0].add(j);
                                }
                            }
                            piirronmaara--;
                        }
                        
                    }
                }
            }
        } else {
            for (int l = 0; l < mahdolliset[1].length; l++) {
                if (mahdolliset[i][l][1].contains(j)) {
                     if (tyhjaa > 0) {
                        tyhjaa--;
                    } else {
//                         if (mahdolliset[i][l][0].size() == 1) {
//                             if (i > 0) {
//                                if (!mahdolliset[i - 1][l][0].contains(mahdolliset[i][l][0].get(0))) {
//                                    mahdolliset[i - 1][l][0].removeAll();
//                                }
//                             }
//                             if (i < kentta.getKorkeus()) {
//                                 if (!mahdolliset[i + 1][l][0].contains(mahdolliset[i][l][0].get(0))) {
//                                     mahdolliset[i + 1][l][0].removeAll();
//                                 }
//                             }
//                             
//                         }
                        if (piirronmaara > 0){
                            kentta.setKohta(i, l, 1);
                            if (l > 0) {
                                if (mahdolliset[i][l-1][0].size() > 1) {
                                    mahdolliset[i][l-1][0].removeAll();
                                    mahdolliset[i][l-1][0].add(j);
                                }
                            }
                            if (l < kentta.getLeveys()) {
                                if (mahdolliset[i][l+1][0].size() > 1) {
                                    mahdolliset[i][l+1][0].removeAll();
                                    mahdolliset[i][l+1][0].add(j);
                                }
                            }
                            piirronmaara--;
                        }
                    }
                }
            }
        }
    }
    
    private void mahdottomienPoisto(int i, boolean b, int i0, int mk, int j) {
        
    }

    
//    
//    private void saaretAlueelta(ArrayList<Integer[]> listat, ArrayList<Integer> s) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//


//    private void varmatTyhjat(int i, boolean b) {
//        
//    }

//    private void varmatTaydet(int i, boolean b) {
//        ArrayList<Integer> saaret;
//        int[] vali;
//        int[] tamanhetki;
//        if (b) {
//            saaret = sarakkeet[i];
//            vali = new int[kentta.getKorkeus()];
//            tamanhetki = new int[kentta.getKorkeus()];
//        } else {
//            saaret = rivit[i];
//            vali = new int[kentta.getLeveys()];
//            tamanhetki = new int[kentta.getLeveys()];
//        }
//        for (int j = 0; j < tamanhetki.length; j++) {
//            if (b) {
//                tamanhetki[j] = kentta.getKohta(j,i);
//            } else {
//                tamanhetki[j] = kentta.getKohta(i,j);
//            }
//        }
//        ArrayList<Integer> valisaaret = new ArrayList<>();
//        valisaaret.copy(saaret);
//        int index = 0;
//        int saari = 1;
//        for (int j = 0; j < tamanhetki.length; j++) {
//            if (tamanhetki[j] != 2) {
//                int koko = valisaaret.get(index);
//                for (int k = index; k < koko; k++) {
//                    if (tamanhetki[k] == 2) {
//                        break;
//                    } else {
//                        vali[k] = saari;
//                        valisaaret.set(index, valisaaret.get(index)-1);
//                    }
//                    if (valisaaret.get(index) == 0) {
//                        index++;
//                    }
//                }
//                    
//                
//            }
//        }
//        System.out.println(vali);
//        for (int j = 0; j < vali.length; j++) {
//            if (oikeatSaaret.contains(vali[j])) {
//                if (b) {
//                    kentta.setKohta(j, i, 1);
//                    if (kentta.getKohta(j, i) != 1) {
//                        pisteet--;
//                    }
//
//                } else {
//                    kentta.setKohta(i, j, 1);
//                    if (kentta.getKohta(i, j) != 1) {
//                        pisteet--;
//                    }
//                }
//            }
//        }
//
//    }
//
//    private void vierekkaiset(int i, boolean b) {
//        
//    }



    

    

    
}
