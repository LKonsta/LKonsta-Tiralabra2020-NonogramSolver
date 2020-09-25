
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
    Boolean[] valmiitRivit;
    static ArrayList<Integer>[] sarakkeet;
    Boolean[] valmiitSarakkeet;
    static Integer pisteet;
    static ArrayList<Integer> oikeatSaaret;
    
    public LogicalSolver(Peli peli) {
        
        kentta = new Kentta(peli.getRivienMaara(), peli.getSarakkeidenMaara());
        valmiitRivit = new Boolean[peli.getRivienMaara()];
        valmiitSarakkeet = new Boolean[peli.getSarakkeidenMaara()];
        rivit = peli.getRivit();
        sarakkeet = peli.getSarakkeet();
        Integer[] lista = {2, 6, 10, 14, 18, 22, 26, 30};
        oikeatSaaret.addAll(lista);
        for (int i = 0; i < kentta.getLeveys(); i++) {
            for (int j = 0; j < kentta.getKorkeus(); j++) {
                kentta.setKohta(j, i, 0);
            }
        }
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
            while (pisteet > 0) {
                for (int i = 0; i < kentta.getLeveys(); i++) {
                    tyhjaTestaus(i, true);
                    saariTestaus(i, true);
                }
                for (int i = 0; i < kentta.getKorkeus(); i++) {
                    tyhjaTestaus(i, false);
                    saariTestaus(i, false);
                }
                System.out.println(kentta); 
                System.out.println("--------------------");
            }
            
        }
        
    }

    private void saariTestaus(int i, boolean b) {
        ArrayList<Integer> saaret;
        int[] vali;
        int[] tamanhetki;
        if (b) {
            saaret = sarakkeet[i];
            vali = new int[kentta.getKorkeus()];
            tamanhetki = new int[kentta.getKorkeus()];
        } else {
            saaret = rivit[i];
            vali = new int[kentta.getLeveys()];
            tamanhetki = new int[kentta.getLeveys()];
        }
        int tilaa = 0;
        for (int j = 0; j < tamanhetki.length; j++) {
            if (b) {
                tamanhetki[j] = kentta.getKohta(j,i);
                if (tamanhetki[j] == 0 || tamanhetki[j] == 1) {
                    tilaa++;
                }
            } else {
                tamanhetki[j] = kentta.getKohta(i,j);
                if (tamanhetki[j] == 0 || tamanhetki[j] == 1) {
                    tilaa++;
                }
            }
        }
        int pisteetRivilla = 0;
        for (int j = 0; j < saaret.size(); j++) {
            pisteetRivilla += saaret.get(j);
        }
        if (tilaa == pisteetRivilla) {
            for (int j = 0; j < vali.length; j++) {
                if (tamanhetki[j] == 0) {
                    if (b) {
                        kentta.setKohta(j, i, 1);
                        if(kentta.getKohta(j, i) != 1) {
                            pisteet--;
                        }
                    } else {
                        kentta.setKohta(i, j, 1);
                        if(kentta.getKohta(i, j) != 1) {
                            pisteet--;
                        }
                    }
                }
            }
        } 
//        ArrayList<Integer[]> tyhjat_ja_ykkoset = new ArrayList<>();
//        Integer[] vali_ilman2;
//        ArrayList<Integer> kooot = new ArrayList<>();
//        int ind = 0;
//        for (int j = 0; j < vali.length; j++) {
//            if (tamanhetki[j] == 0 || tamanhetki[j] == 1) {
//                ind++;
//            } else {
//                kooot.add(ind);
//                ind = 0;
//            }
//            if (j == vali.length && ind > 0) {
//                kooot.add(ind);
//            }
//        }
//        for (int j = 0; j < 10; j++) {
//            
//        }
//        saaretAlueelta(tyhjat_ja_ykkoset, saaret);
        else {
            ArrayList<Integer> valisaaret = new ArrayList<Integer>();
            valisaaret.copy(saaret);
            int index = 0;
            int saari = 1;
            for (int j = 0; j < vali.length; j++) {
                if (valisaaret.size()>index) {
                    if (valisaaret.get(index) >= 1) {
                        vali[j] = vali[j]+saari;
                        valisaaret.set(index, valisaaret.get(index)-1);
                    } else {
                        index++;
                        saari += 2;
                    }
                }
            }
            valisaaret.copy(saaret);
            valisaaret.flip();
            index = 0;
            saari = (saaret.size()*2) - 1;
            for (int j = vali.length-1; j >= 0; j--) {
                if (valisaaret.size()>index) {
                    if (valisaaret.get(index) >= 1) {
                        vali[j] = vali[j]+saari;
                        valisaaret.set(index, valisaaret.get(index)-1);
                    } else {
                        index++;
                        saari -=2;
                    }
                }
            }
            boolean taysi = true;
            for (int j = 0; j < vali.length; j++) {
                if (!oikeatSaaret.contains(vali[j]) && vali[j] != 0) {
                    taysi = false;
                }
            }

            if (taysi) {
                for (int j = 0; j < vali.length; j++) {
                    if (vali[j] == 0) {
                        if (b) {
                            kentta.setKohta(j, i, 2);
                        } else {
                            kentta.setKohta(i, j, 2);
                        }
                    }
                }
            }
            for (int j = 0; j < vali.length; j++) {
                if (oikeatSaaret.contains(vali[j])) {
                    if (b) {
                        kentta.setKohta(j, i, 1);
                        if(kentta.getKohta(j, i) != 1) {
                            pisteet--;
                        }
                        
                    } else {
                        kentta.setKohta(i, j, 1);
                        if(kentta.getKohta(i, j) != 1) {
                            pisteet--;
                        }
                    }
                }
            }
        }
    }
    
    private void saaretAlueelta(ArrayList<Integer[]> listat, ArrayList<Integer> s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void tyhjaTestaus(int i, boolean b) {
        ArrayList<Integer> saaret;
        int[] vali;
        int pisteet = 0;
        if (b) {
            saaret = sarakkeet[i];
            vali = new int[kentta.getKorkeus()];
        } else {
            saaret = rivit[i];
            vali = new int[kentta.getLeveys()];
        }
        boolean tyhja = true;
        for (int j = 0; j < vali.length; j++) {
            if (b) {
                if (kentta.getKohta(j, i) == 1) {
                    vali[j] = 1;
                    tyhja = false;
                }
            } else {
                if (kentta.getKohta(i, j) == 1) {
                    vali[j] = 1;
                    tyhja = false;
                }
            }
        }
        for (int j = 0; j < saaret.size(); j++) {
            pisteet += saaret.get(j);
        }
        if (!tyhja) {
            if (saaret.size() == 1) {
                int koko = saaret.get(0);
                int piirretty = 0;
                int alku = 0;
                int loppu = 0;
                        
                for (int j = 0; j < vali.length; j++) {
                    if (vali[j] == 1) {
                        piirretty++;
                        if (alku == 0) {
                            alku = j;
                        }
                    }
                    if (piirretty > 0 && vali[j] == 0 && loppu == 0) {
                        loppu = j-1;
                    }
                }
                if (piirretty == koko) {
                    for (int j = 0; j < vali.length; j++) {
                        if (vali[j] != 1) {
                            vali[j] = 2;
                        }
                    }
                } else {
                    for (int j = alku + koko; j < vali.length; j++) {
                        vali[j] = 2;
                    }
                    for (int j = loppu - koko; j >= 0; j--) {
                        vali[j] = 2;
                    }
                }
                
            }
            else {
                ArrayList<Integer> tyhjat = new ArrayList<Integer>();
                int koko = 0;
                int tyhjat_koko = 0;
                for (int j = 0; j < vali.length; j++) {
                    if (vali[j] == 0) {
                        koko++;
                        tyhjat_koko++;
                    } else if (vali[j] == 1 && koko > 0) {
                        tyhjat.add(koko);
                        koko = 0;
                    }
                    if (j == vali.length-1) {
                        tyhjat.add(koko);
                    }
                }
                ArrayList<Integer[]> parit = new ArrayList<Integer[]>();
                for (int j = 0; j < saaret.size(); j++) {
                    if (saaret.size() > 1 && j != saaret.size()) {
                        Integer[] pari = new Integer[2];
                        pari[0] = saaret.get(j);
                        pari[1] = saaret.get(j+1);
                        parit.add(pari);
                    }
                }
//                Boolean[] mahtuuko = new Boolean[tyhjat.size()];
//                int index = 0;
//                for (int j = 0; j < tyhjat.size(); j++) {
//                    if (parit.get(index)[0] + parit.get(j)[1] + 1 > tyhjat.get(j)) {
//                        mahtuuko[j] = false;
//                    } else {
//                        mahtuuko[j] = true;
//                    }
//                }
                
//                int tilaa = vali.length - (pisteet + saaret.size()-1);
//                int montako = 1;
//                int kerroin = 1;
//                for (int j = 0; j < tilaa; j++) {
//                    for (int k = 0; k < montako; k++) {
//                        kerroin += tilaa+1;
//                        if (montako < saaret.size()) {
//                            montako++;
//                        }
//                    }
//                }
//                System.out.println(tilaa);
//                System.out.println(kerroin);
                    
                
                if (vali.length-tyhjat_koko == pisteet) {
                    for (int j = 0; j < 10; j++) {
                        
                    }
                }
            }
        }
        for (int j = 0; j < vali.length; j++) {
            if (vali[j] == 2) {
                if (b) {
                    kentta.setKohta(j, i, 2);
                } else {
                    kentta.setKohta(i, j, 2);
                }
            }
        }
    }

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
