
package Algoritmit;

import Nonogram.Kentta;
import Nonogram.Peli;
import Tietorakenteet.ArrayList;
import java.util.Arrays;

public class PermutaatioSolver {
    
    private ArrayList<Integer>[] rivit;
    private ArrayList<Integer>[] sarakkeet;
    private Kentta k;
    

    public PermutaatioSolver(Peli peli, boolean ratkooko) {
        k = new Kentta(peli.getSarakkeidenMaara(),peli.getRivienMaara());
        rivit = peli.getRivit();
        sarakkeet = peli.getSarakkeet();
        System.out.println(peli);
        
        if (ratkooko) {
            ratko();
        }
    }

    private void ratko() {
        boolean muutosta = true;
        while (muutosta) {
            muutosta = false;
            for (int i = 0; i < k.getKorkeus(); i++) {
//                System.out.println(Arrays.toString(rivit));
//                System.out.println(i + k.getKorkeus());
//                System.out.println(i + ", " + rivit[i] + ", " + Arrays.toString(k.getRivi(i)));
                Integer[] rivi = ratko_linja(i, rivit[i], k.getRivi(i));
//                System.out.println(Arrays.toString(rivi) + "rivi");
                if (rivi != null) {
                    for (int j = 0; j < rivi.length; j++) {
//                        System.out.println(rivi.length + ", " + k.getRivi(0));
                        if (rivi[j] == 1 && k.getKohta(i, j) != 1) {
                            k.setKohta(i, j, 1);
                            muutosta = true;
                        }
                        if (rivi[j] == 2 && k.getKohta(i, j) != 2) {
                            k.setKohta(i, j, 2);
                            muutosta = true;
                        }
                    }
                }
            }
            for (int i = 0; i < k.getLeveys(); i++) {
//                System.out.println(i + ", " + sarakkeet[i] + ", " + Arrays.toString(k.getSarake(i)));
                Integer[] sarake = ratko_linja(i, sarakkeet[i], k.getSarake(i));
//                System.out.println(Arrays.toString(sarake) + "sarake");
                if (sarake != null) {
                    for (int j = 0; j < sarake.length; j++) {
                        if (sarake[j] == 1 && k.getKohta(j, i) != 1) {
                            k.setKohta(j, i, 1);
                            muutosta = true;
                        }
//                        System.out.println("(" + j + ", " + i + ")");
                        if (sarake[j] == 2 && k.getKohta(j, i) != 2) {
                            k.setKohta(j, i, 2);
                            muutosta = true;
                        }
                    }
                }
            }
        }
    }

    private Integer[] ratko_linja(int i, ArrayList<Integer> saaret, Integer[] linja) {
        ArrayList<Integer[]> toimivat_rivit = new ArrayList<>();
//        System.out.println(k);
//        System.out.println("-----");
        permutaatiot(saaret, linja, linja, 0, true);
        for (Integer[] per : lista_permuista) {
            boolean sama = true;
//            System.out.println(lCinja.length);
            for (int j = 0; j < linja.length; j++) {
                if (!per[j].equals(linja[j])) {
                    sama = false;
                    break;
                }
            }
            if (!sama) {
                toimivat_rivit.add(per);
            }
            
        }
//        System.out.println(linja.length + "taala");
        Integer[] uusi_rivi = toimivat_rivit.get(0);
        if (toimivat_rivit.size() > 0) {
            toimivat_rivit.removeFirst();
            for (Integer[] per2 : toimivat_rivit) {
                for (int j = 0; j < uusi_rivi.length; j++) {
                    if (per2[j] != uusi_rivi[j]) {
                        uusi_rivi[j] = 0;
                    }
                }
            }
        }
//        System.out.println("mue ti e");
//        System.out.println(Arrays.toString(uusi_rivi));
        return uusi_rivi;
    }

    public ArrayList<Integer[]> lista_permuista;

    public void permutaatiot(ArrayList<Integer> saaret, Integer[] linja, Integer[] tama,  int index, boolean eka) {
        if (eka) {
            lista_permuista = new ArrayList<>();
            lista_permuista.removeAll();
        }
        
//        System.out.println(sum(saaret) + ", saaret atm");
        Integer max = linja.length - (sum(saaret) - saaret.get(0)) - saaret.size() + 1;
//        System.out.println(max + " = " + linja.length + " - (" + sum(saaret) + " - " + saaret.get(0) + ") - " + saaret.size() + " + " + 1 + ", " + eka);
        
//        System.out.println(Arrays.toString(tama) + ", " + Arrays.toString(linja));
        
        for (int i = index; i < max - saaret.get(0) + 1; i++) {
            Integer[] uusi_linja = new Integer[linja.length];
            if (eka) {
                System.arraycopy(linja, 0, uusi_linja, 0, linja.length);
            } else {
                System.arraycopy(tama, 0 ,uusi_linja, 0 ,linja.length);
            }
            int koko = saaret.get(0);
            int loppuun = index;
            for (int j = index; j < max; j++) {
                if (j >= i && koko > 0) {
                    if (uusi_linja[j] != null && uusi_linja[j] == 1) {
                        break;
                    }
                    uusi_linja[j] = 2;
                    koko--;
                    loppuun++;
                } else {
                    if (uusi_linja[j] != null && uusi_linja[j] == 2) {
                        break;
                    }
                    uusi_linja[j] = 1;
                    loppuun++;
                }
//                System.out.println("loopin lopussa: " + j + ", " + koko + ", " + saaret.size() + ", " + saaret.toString() + ", " + Arrays.toString(uusi_linja));
                if (koko == 0 && loppuun != linja.length && saaret.size() > 1) {
//                    System.out.println("taalaadad");
                    if (uusi_linja[j+1] != null && uusi_linja[j+1] == 2) {
                        break;
                    }
                    ArrayList<Integer> uudet_saaret = new ArrayList<>();
                    uudet_saaret.copy(saaret);
//                    System.out.println("tdsadsadsa");
                    uusi_linja[j+1] = 1;
                    uudet_saaret.removeFirst();
//                    System.out.println("eikai , " + Arrays.toString(uusi_linja));
                    permutaatiot(uudet_saaret, linja, uusi_linja, loppuun + 1, false);

                }
            }
            
//            System.out.println(Arrays.toString(uusi_linja) + ", " + loppuun + ", " + max);
            if (loppuun == linja.length) {
                
                lista_permuista.add(uusi_linja);
            }
        }

        
        
//        listaToString();
//        System.out.println("--------");
    }

    
    private int sum(ArrayList<Integer> t) {
        int palaute = 0;
        for (int i = 0; i < t.size(); i++) {
            palaute += t.get(i);
        }
        return palaute;
    }

    Integer[][] getKentta() {
        return k.getKentta();
    }
    
    public void listaToString() {
        System.out.print("[");
        for (int i = 0; i < lista_permuista.size(); i++) {
            System.out.print(Arrays.toString(lista_permuista.get(i)));
            if (i != lista_permuista.size()-1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

}
