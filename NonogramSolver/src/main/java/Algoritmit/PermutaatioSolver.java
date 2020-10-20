
package Algoritmit;

import Nonogram.Kentta;
import Nonogram.Peli;
import Tietorakenteet.ArrayList;
import Tietorakenteet.TyhjaListaException;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Objects;

public class PermutaatioSolver {
    
    private ArrayList<Integer>[] rivit;
    private Integer[] rivit_pituus;
    private ArrayList<Integer>[] sarakkeet;
    private Integer[] sarakkeet_pituus;
    private Kentta k;
    private Peli peli;
    private Integer taydet;
    private Integer tyhjat;
    private Integer max_pisteet;
    
    /**
     *
     * @param peli peli jonka permutaatio solveri ratkoo
     * @param ratkooko boolean siitä että ratkotaanko peliä vai ei (tehty testejä varten)
     * @param piirto piirretäänkö välissä
     * @throws TyhjaListaException Exception joka toimii yhteydessä bruteforce osuuden kanssa.
     */
    public PermutaatioSolver(Peli peli, boolean ratkooko, boolean piirto) throws TyhjaListaException {
        this.peli = peli;
        k = peli.kentta;
        rivit = peli.getRivit();
        sarakkeet = peli.getSarakkeet();
//        System.out.println(peli);
        taydet = 0;
        tyhjat = 0;
        Integer pisteetS = 0;
        for (ArrayList<Integer> sarakkeet1 : sarakkeet) {
            for (int j = 0; j < sarakkeet1.size(); j++) {
                pisteetS += sarakkeet1.get(j);
            }
        }
        Integer pisteetR = 0;
        for (ArrayList<Integer> rivit1 : rivit) {
            for (int j = 0; j < rivit1.size(); j++) {
                pisteetR += rivit1.get(j);
            }
        }
        boolean mahdoton = false;
        max_pisteet = pisteetS;
        if (!(pisteetS.equals(pisteetR))) {
            System.out.println("mahdoton: " + pisteetS + "/" + pisteetR);
            mahdoton = true;
        }
        if (ratkooko && !mahdoton) {
            ratko(piirto);
            if (taydet < max_pisteet) {
                Kentta atm = new Kentta(0, 0);
                atm.copy(k);
    //            System.out.println("bruteeminen alkakoot: " + taydet + ", " + max_pisteet);
    //            System.out.println(k);
                BruteForce(atm, 0, piirto);
            }
        }
        
    }


    
    private void BruteForce(Kentta vali, Integer muutosta, boolean piirto) {
        System.out.println("taala");
        Kentta atm = new Kentta(0, 0);
        atm.copy(k);
        int taydet_old = atm.getKakkoset();
        boolean valmis = false;
        for (int i = 0; i < rivit.length; i++) {
            for (int j = 0; j < sarakkeet.length; j++) {
                if (k.getKohta(i, j) == 0) {
                    k.setKohta(i, j, 2);
                    
                    taydet++;
                    try {
                        ratko(piirto);
                    } catch(TyhjaListaException e) {
                        k.copy(atm);
                        taydet = taydet_old;
                        continue;
                    }
                    if (taydet < max_pisteet) {
                        BruteForce(atm, 0, piirto);
                    }
                    if (Objects.equals(taydet, max_pisteet) && peli.onkoMahdollinen()) {
//                        System.out.println(k);
                        valmis = true;
                        return;
                    }
                    k.copy(atm);
                    taydet = taydet_old;
                }
            }
            if (valmis) {
                break;
            }
        }
    }
    
    private void ratko(boolean piirto) throws TyhjaListaException {
        boolean muutosta = true;
        arvioiPituudet();
        for (int h = 0; h < 10; h++) {
            muutosta = true;
            if (h == 9) {
                h = 100;
            }
            while (muutosta) {
                if (piirto) {
                    System.out.println("---------------------------------------------");
                    System.out.println(k); 
                }
                muutosta = false;
                for (int i = 0; i < k.getKorkeus(); i++) {
                    if (rivit_pituus[i] <= (h*5)) {
                        Integer[] rivi = ratko_linja(i, rivit[i], k.getRivi(i));
                        if (rivi != null) {
                            for (int j = 0; j < rivi.length; j++) {
                                if (rivi[j] == 1 && k.getKohta(i, j) != 1) {
                                    k.setKohta(i, j, 1);
                                    tyhjat++;
                                    muutosta = true;
                                }
                                if (rivi[j] == 2 && k.getKohta(i, j) != 2) {
                                    k.setKohta(i, j, 2);
                                    taydet++;
                                    muutosta = true;
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < k.getLeveys(); i++) {
                    if (sarakkeet_pituus[i] <= (h*5)) {
                        Integer[] sarake = ratko_linja(i, sarakkeet[i], k.getSarake(i));
                        if (sarake != null) {
                            for (int j = 0; j < sarake.length; j++) {
                                if (sarake[j] == 1 && k.getKohta(j, i) != 1) {
                                    k.setKohta(j, i, 1);
                                    tyhjat++;
                                    muutosta = true;
                                }
                                if (sarake[j] == 2 && k.getKohta(j, i) != 2) {
                                    k.setKohta(j, i, 2);
                                    taydet++;
                                    muutosta = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private Integer[] ratko_linja(int i, ArrayList<Integer> saaret, Integer[] linja) throws TyhjaListaException {
        ArrayList<Integer[]> toimivat_rivit = new ArrayList<>();
        permutaatiot(saaret, linja, linja, 0, true);
//        System.out.print("(");
//        for (int j = 0; j < lista_permuista.size(); j++) {
//            System.out.print(Arrays.toString(lista_permuista.get(j)));
//        }
//        System.out.println(") , " + i+ saaret + Arrays.toString(linja) + k);
        if (lista_permuista.isEmpty()) {
            throw new TyhjaListaException();
        }
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

    public void permutaatiot(ArrayList<Integer> saaret, Integer[] linja, Integer[] tama,  int index, boolean eka) {
        if (eka) {
            lista_permuista = new ArrayList<>();
            lista_permuista.removeAll();
        }
        
        Integer max = linja.length - (sum(saaret) - saaret.get(0)) - saaret.size() + 1;
        
        for (int i = index; i < max - saaret.get(0) + 1; i++) {
            Integer[] uusi_linja = new Integer[linja.length];
            if (eka) {
                System.arraycopy(linja, 0, uusi_linja, 0, linja.length);
            } else {
                System.arraycopy(tama, 0 ,uusi_linja, 0 ,linja.length);
            }
            int koko = saaret.get(0);
            int loppuun = index;
            int n = max < (i + saaret.get(0) + 1) ? max : (i + saaret.get(0) + 1);
            for (int j = index; j < max; j++) {
                if (j >= i && koko > 0) {
                    if (uusi_linja[j] == 1) {
                        break;
                    }
                    uusi_linja[j] = 2;
                    koko--;
                    loppuun++;
                } else {
                    if (uusi_linja[j] == 2) {
                        break;
                    }
                    uusi_linja[j] = 1;
                    loppuun++;
                }
                if (koko == 0 && loppuun != linja.length && saaret.size() > 1) {
                    if (uusi_linja[j+1] == 2) {
                        break;
                    }
                    ArrayList<Integer> uudet_saaret = new ArrayList<>();
                    uudet_saaret.copy(saaret);
                    uusi_linja[j+1] = 1;
                    uudet_saaret.removeFirst();
                    permutaatiot(uudet_saaret, linja, uusi_linja, loppuun + 1, false);

                }
            }
            if (loppuun == linja.length) {
                lista_permuista.add(uusi_linja);
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
    
    public String listaToString() {
        String palaute = "";
        palaute += ("[");
        for (int i = 0; i < lista_permuista.size(); i++) {
            palaute += (Arrays.toString(lista_permuista.get(i)));
            if (i != lista_permuista.size()-1) {
                palaute += (", ");
            }
        }
        palaute += ("]");
        return palaute;
    }

    private void arvioiPituudet() {
        sarakkeet_pituus = new Integer[sarakkeet.length];
        for (int i = 0; i < sarakkeet.length; i++) {
            int tyhjaa = rivit.length;
            int saaret = 0;
            for (int j = 0; j < sarakkeet[i].size(); j++) {
                tyhjaa -= sarakkeet[i].get(j);
                tyhjaa--;
                saaret++;
            }
            tyhjaa = tyhjaa * saaret;
            sarakkeet_pituus[i] = tyhjaa;
        }
        rivit_pituus = new Integer[rivit.length];
        for (int i = 0; i < rivit.length; i++) {
            int tyhjaa = sarakkeet.length;
            int saaret = 0;
            for (int j = 0; j < rivit[i].size(); j++) {
                tyhjaa -= rivit[i].get(j);
                tyhjaa--;
                saaret++;
            }
            tyhjaa = tyhjaa * saaret;
            rivit_pituus[i] = tyhjaa;
        }
        System.out.println(Arrays.toString(rivit_pituus) + ", " + Arrays.toString(sarakkeet_pituus));
    }

}
