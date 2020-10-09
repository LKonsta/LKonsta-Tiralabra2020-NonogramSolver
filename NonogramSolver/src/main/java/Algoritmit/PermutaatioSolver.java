
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
            for (int i = 0; i < k.getLeveys(); i++) {
                Integer[] rivi = ratko_linja(i, sarakkeet[i], k.getRivi(i));
                if (rivi != null) {
                    for (int j = 0; j < rivi.length; j++) {
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
            for (int i = 0; i < k.getKorkeus(); i++) {
                Integer[] sarake = ratko_linja(i, rivit[i], k.getSarake(i));
                if (sarake != null) {
                    for (int j = 0; j < sarake.length; j++) {
                        if (sarake[j] == 1 && k.getKohta(j, i) != 1) {
                            k.setKohta(j, i, 1);
                            muutosta = true;
                        }
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
        permutaatiot(saaret, linja);
        for (Integer[] per : lista_permuista) {
            boolean sama = true;
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
        return uusi_rivi;
    }

    public ArrayList<Integer[]> lista_permuista;

    public void permutaatiot(ArrayList<Integer> saaret, Integer[] linja) {
        lista_permuista = new ArrayList<>();
        lista_permuista.removeAll();
        if (saaret.size() > 0 ) {
            Integer[] uusi_linja = new Integer[linja.length];
            for (int i = 0; i < linja.length; i++) {
                lisaaSaaret(saaret, linja, uusi_linja, 0, true);
                System.out.println("-------------------" + i);
//                for (int j = 0; j < lista_permuista.size(); j++) {
//                    System.out.println(Arrays.toString(lista_permuista.get(j)));
//                }
            }
        }
    }
    
    private void lisaaSaaret2(ArrayList<Integer> saaret, Integer[] linja, Integer[] uusi, int index, int tyhjia) {
        ArrayList<Integer> uudet_saaret = new ArrayList<>();
        uudet_saaret.copy(saaret);
        Integer[] tama = new Integer[uusi.length];
        System.arraycopy(uusi, 0, tama, 0, tama.length);
        int in = index;
        System.out.println(Arrays.toString(tama) + "ennen");
        if (in+tyhjia >= linja.length-1) {
            return;
        }
        for (int i = 0; i < tyhjia; i++) {
            if (linja[in+i] == 2) {
                return;
            }
            tama[in+i] = 1;
            in++;
        }
        for (int i = in; i < uudet_saaret.get(0); i++) {
            if (linja[i] == 1) {
                return;
            }
            tama[i] = 2;
            in++;
        }
        uudet_saaret.removeFirst();
        if (uudet_saaret.size() == 0) {
            for (int i = in; i < linja.length; i++) {
                tama[i] = 1;
            }
            lisaaListaan(tama);
            System.out.println(Arrays.toString(tama));
            return;
        }
        for (int i = 1; i < 10; i++) {
            System.out.println("taala");
            lisaaSaaret2(uudet_saaret, linja, tama, in, i);
        }
    }
    
    private void lisaaSaaret(ArrayList<Integer> saaret, Integer[] linja, Integer[] uusi, int index, boolean eka) {
        ArrayList<Integer> uudet_saaret = new ArrayList<>();
        uudet_saaret.copy(saaret);
        Integer[] tama = new Integer[uusi.length];
        System.arraycopy(uusi, 0, tama, 0, tama.length);
        if (eka && index > 0) {
            for (int i = 0; i <= index; i++) {
                if (linja[i] == 2) {
                    return;
                }
                tama[i] = 1;
            }
        }
        if (uudet_saaret.size() > 0) {
            System.out.println(linja.length - sum(uudet_saaret) + uudet_saaret.size()-1);
            if (index <= linja.length - sum(uudet_saaret) + uudet_saaret.size()-1) {
                for (int i = index; i <= index+(uudet_saaret.get(0)-1); i++) {
                    if (linja[i] == 1) {
                        return;
                    }
                    tama[i] = 2;
                }
                if (index < linja.length) {
                    if (linja[index] == 2) {
                        return;
                    }
                    tama[index] = 1;
                }
                index += uudet_saaret.get(0);
                uudet_saaret.removeFirst();
                if (uudet_saaret.size() == 0 && index == linja.length) {
                    lisaaListaan(tama);
                    return;
                }
                
                for (int i = index+1; i < linja.length; i++) {
                    lisaaSaaret(uudet_saaret, linja, tama, i, false);
                    if (linja[i] == 2) {
                        return;
                    }
                    tama[i] = 1;
                }
                if (uudet_saaret.size() == 0) {
                    lisaaSaaret(uudet_saaret, linja, tama, index, false);
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
//                    System.out.println(Arrays.toString(tama) + ", " + Arrays.toString(linja));
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
//        System.out.println(Arrays.toString(tama));
        lista_permuista.add(tama);
    }


}
