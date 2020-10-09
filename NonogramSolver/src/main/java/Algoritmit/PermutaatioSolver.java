
package Algoritmit;

import Nonogram.Kentta;
import Nonogram.Peli;
import Tietorakenteet.ArrayList;
import java.util.Arrays;

public class PermutaatioSolver {
    
    private ArrayList<Integer>[] rivit;
    private ArrayList<Integer>[] sarakkeet;
    private Kentta k;
    

    public PermutaatioSolver(Peli peli) {
        k = new Kentta(peli.getSarakkeidenMaara(),peli.getRivienMaara());
        rivit = peli.getRivit();
        sarakkeet = peli.getSarakkeet();
        
        ratko();
    }

    private void ratko() {
        boolean muutosta = true;
        while (muutosta) {
            muutosta = false;
            for (int i = 0; i < k.getKorkeus(); i++) {
//                System.out.println(Arrays.toString(rivit));
//                System.out.println(i + k.getKorkeus());
                Integer[] rivi = ratko_linja(i, rivit[i], k.getRivi(i));
                if (rivi != null) {
                    for (int j = 0; j < rivi.length; j++) {
                        System.out.println(rivi.length);
                        if (rivi[j] == 1 && k.getKohta(j, i) != 1) {
                            k.setKohta(j, i, 1);
                            muutosta = true;
                        }
                        if (rivi[j] == 2 && k.getKohta(j, i) != 2) {
                            k.setKohta(j, i, 2);
                            muutosta = true;
                        }
                    }
                }
            }
            for (int i = 0; i < k.getLeveys(); i++) {
                Integer[] sarake = ratko_linja(i, sarakkeet[i], k.getSarake(i));
                if (sarake != null) {
                    for (int j = 0; j < sarake.length; j++) {
                        if (sarake[j] == 1 && k.getKohta(i, j) != 1) {
                            k.setKohta(i, j, 1);
                            muutosta = true;
                        }
//                        System.out.println("(" + j + ", " + i + ")");
                        if (sarake[j] == 2 && k.getKohta(i, j) != 2) {
                            k.setKohta(i, j, 2);
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
        permutaatiot(saaret, linja);
        for (Integer[] per : lista_permuista) {
            boolean sama = true;
//            System.out.println(linja.length);
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

    public void permutaatiot(ArrayList<Integer> saaret, Integer[] linja) {
        lista_permuista = new ArrayList<>();
        lista_permuista.removeAll();
//        System.out.println(saaret);
        if (saaret.size() > 0 ) {
            Integer[] uusi_linja = new Integer[linja.length];
            int max = linja.length - (sum(saaret) + saaret.size()-1);
//            System.out.println(max);
            for (int i = 0; i <= max; i++) {
                lisaaSaaret(saaret, linja, uusi_linja, 0, true);
//                System.out.println("-------------------" + i);
//                for (int j = 0; j < lista_permuista.size(); j++) {
//                    System.out.println(Arrays.toString(lista_permuista.get(j)));
//                }
            }
        }
    }
    
    private void lisaaSaaret(ArrayList<Integer> saaret, Integer[] linja, Integer[] uusi, int index, boolean eka) {
        ArrayList<Integer> uudet_saaret = new ArrayList<>();
        uudet_saaret.copy(saaret);
        Integer[] tama = new Integer[uusi.length];
        System.arraycopy(uusi, 0, tama, 0, tama.length);
//        System.out.println(uudet_saaret + " , " + Arrays.toString(linja) + Arrays.toString(tama));
        if (eka && index > 0) {
//            System.out.println("ekaakaka");
            for (int i = 0; i <= index; i++) {
                if (linja[i] == 2) {
                    return;
                }
                tama[i] = 1;
                index++;
            }
        }
//        System.out.println(uudet_saaret);
        if (uudet_saaret.size() > 0) {
            if (index <= linja.length - sum(uudet_saaret) + uudet_saaret.size()-1) {
//                System.out.println(index + " , " + (index+(uudet_saaret.get(0)-1)));
//                System.out.println(uudet_saaret.size() + " , " + uudet_saaret.get(0));
                int vali = index;
                for (int i = vali; i <= vali+(uudet_saaret.get(0)-1); i++) {
//                    System.out.println("taaala: " + i);
                    if (linja[i] == 1) {
//                        System.out.println("eiku");
                        return;
                    }
                    
                    tama[i] = 2;
                    index++;
//                    System.out.println(Arrays.toString(tama));
                }
                
//                index += uudet_saaret.get(0);
                uudet_saaret.removeFirst();
//                System.out.println("taannaa");
                if (uudet_saaret.size() == 0 && index == linja.length) {
                    lisaaListaan(tama);
                    return;
                }
//                if (index < linja.length) {
//                    if (linja[index] == 2) {
//                        return;
//                    }
//                    tama[index] = 1;
//                    index++;
//                }
                for (int i = index; i < linja.length; i++) {
                    
                    if (linja[i] == 2) {
                        return;
                    }
                    tama[i] = 1;
                    lisaaSaaret(uudet_saaret, linja, tama, i, false);
                }
            }
        } else {
            if (index <= linja.length) {
                for (int i = index; i < linja.length; i++) {
                    if (linja[i] == 2) {
                        return;
                    }
                    tama[i] = 1;
                }
                if (uudet_saaret.size() == 0) {
                    lisaaListaan(tama);
                }
            }
        }
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

    private void lisaaListaan(Integer[] tama) {
        lista_permuista.add(tama);
    }


}
