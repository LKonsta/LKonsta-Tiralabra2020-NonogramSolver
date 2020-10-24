
package Algoritmit;

import Nonogram.Kentta;
import Nonogram.Peli;
import Tietorakenteet.ArrayList;
import Tietorakenteet.TyhjaListaException;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Objects;

public class PermutaatioSolver {
    
    private final ArrayList<Integer>[] rivit;
    private Integer[] rivit_pituus;
    private final ArrayList<Integer>[] sarakkeet;
    private Integer[] sarakkeet_pituus;
    private final Kentta k;
    private final Peli peli;
    private Integer taydet;
    private Integer max_pisteet;
    
    /**
     * Aloittava metodi joka asettaa koodissa käytettävät listat ja muut oikein.
     * tarkistaa myös että onko annettu nonogrammi mahdollista ratkaista tarkistamalla
     * onko siinnä saman verran pisteitä sarakkeissa ja riveissä.
     * 
     * @param peli peli jonka permutaatio solveri ratkoo
     * @param ratkooko boolean siitä että ratkotaanko peliä vai ei (tehty testejä varten)
     * @param piirto piirretäänkö välissä
     * @throws TyhjaListaException Exception joka toimii yhteydessä bruteforce osuuden kanssa.
     */
    public PermutaatioSolver(Peli peli, boolean ratkooko, boolean piirto) throws TyhjaListaException {
        this.peli = peli;                   //asetetaan peli
        k = peli.kentta;                    //asetetaan kenttä pelin mukaan
        rivit = peli.getRivit();            //asetetaan rivit
        sarakkeet = peli.getSarakkeet();    //asetetaan sarakkeet
        taydet = 0;                         
        
        // { 2 for looppia jotka keräävät sarakkeiden ja rivien saaret yhteen ja vertaa niitä
        Integer pisteetS = 0;               // sarakkeiden pisteet
        for (ArrayList<Integer> sarakkeet1 : sarakkeet) {
            for (int j = 0; j < sarakkeet1.size(); j++) {
                pisteetS += sarakkeet1.get(j);
            }
        }
        Integer pisteetR = 0;               // rivien pisteet
        for (ArrayList<Integer> rivit1 : rivit) {
            for (int j = 0; j < rivit1.size(); j++) {
                pisteetR += rivit1.get(j);
            }
        }
        boolean mahdoton = false;
        max_pisteet = pisteetS;
        if (!(pisteetS.equals(pisteetR))) {     // jos pisteet eivät täsmää niin peli lopetetaan
            System.out.println("mahdoton: " + pisteetS + "/" + pisteetR);
            mahdoton = true;
        }
        // }
        
        // { aloittaa pelin
        if (ratkooko && !mahdoton) {
            ratko(piirto);
            if (taydet < max_pisteet) {         // ratkomisen jälkeen jos kenttä ei olekaan vielä täytetty
                Kentta atm = new Kentta(0, 0);
                atm.copy(k);
                BruteForce(atm, 0, piirto);     // aloitetaan bruteforceaminen.
            }
        }
        // }
    }
    

    /**
     * Ratko funktio jota käydään suurimmaksi osaksi permutaatio solverissa. 
     * Tämä koostuu While loopista jota käydään läpi kunnes "muutosta" eli 
     * uusi pisteitä tai tyhjiä ei enään aseteta. Ratko funktiota kutsutaan myös
     * kun aloitetaan bruteforceus jolloinka kenttään asetetaan piste ja testataan
     * että saako ratkoja uuden pisteen avulla löydettyä uusia täysiä / tyhjiä 
     * kohtia.
     * 
     * @param piirto tämä on vain ui:ta varten jos halutaan siis että ohjelma 
     * piirtää välivaiheet niin tämä on true.
     * @throws TyhjaListaException tämä on exception jota käytetään bruteforcauksessa
     */
    private void ratko(boolean piirto) throws TyhjaListaException {
        boolean muutosta = true;                    // asetetaan muutosta olevan totta
        arvioiPituudet();                           // asetetaan riveille ja sarakkeille oletus pituus siitä kuinka monta eri permutaatiota niistä syntyy.
        // { asetetaan boolean listat josta katsotaan että onko for looppissa käyty jo kyseisellä rivillä / sarakkeella.
        boolean[] rivit_kaydyt = new boolean[rivit.length];     
        boolean[] sarakkeet_kaydyt = new boolean[sarakkeet.length];
        // }
        
        // { aloitetaan for loopilla jossa h arvoa nostetaan pikku hiljaa. 
        // tämä on sitä varten että verrataan arvioiPituudet() antamiin arvoihin 
        // ja käydään ensin ne rivit ja sarakkeet jossa oletettavasti on vähemmän 
        // eri permutaatioita, jotta kun pääsemme myöhemmin niihin jossa on oletettavasti 
        // enemmän niin jo löydettyjen arvojen avulla permutaatioita onkin vähemmän
        for (int h = 0; h < 10000; h += 300) {
            if (Objects.equals(taydet, max_pisteet)) { // jos kaikki pisteet onkin jo löydetty pysäytetään for looppi.
                break;
            }
            muutosta = true;    
            if (h == 9900) {                // kun päästään vikaan h arvoon pakotetaan kaikki (mahdollisesti hyvin suuret arvot)
                h = 100000000;              // rivit ja sarakkeet käytäväksi.
            }
            
            // { aloitetaan itse while looppi jota käydään siihen asti kunnes uusi muutoksia kenttään ei tule
            while (muutosta) {
                if (piirto) {
                    System.out.println("---------------------------------------------");
                    System.out.println(k); 
                }
                muutosta = false;               // asetetaan muutos falseksi
                
                // { 2 for looppia riveille ja sarakkeille 
                for (int i = 0; i < k.getKorkeus(); i++) {
                    // { tarkistetaan että onko rivillä jo käyty ja että onko sille 
                    // annettu oletettu pituus vielä tarpeeksi pieni että tämä rivi käytäisiin.
                    if (rivit_pituus[i] <= (h) && !rivit_kaydyt[i]) { 
                        rivit_kaydyt[i] = true; // asetetaan rivi käydyksi.
                        // { kutsutaan ratko_linja metodia joka palauttaa listan siitä miltä uusi rivi näyttää.
                        Integer[] rivi = ratko_linja(i, rivit[i], k.getRivi(i));
                        if (rivi != null) {
                            // { asetetaan riviltä arvot 1 (tyhjä) tai 2 (täysi) pelin kenttään.
                            for (int j = 0; j < rivi.length; j++) {
                                if (rivi[j] == 1 && k.getKohta(i, j) != 1) {
                                    sarakkeet_kaydyt[j] = false;    // huomataan että riviä muuttaessa kyseinen sarake ei olekkaan ennään käyty
                                    k.setKohta(i, j, 1);            // asetetaan kohta kenttään
                                    muutosta = true;                // muutosta on tapahtunut joten while looppi jatkuu
                                }
                                if (rivi[j] == 2 && k.getKohta(i, j) != 2) {
                                    sarakkeet_kaydyt[j] = false;    // huomataan että riviä muuttaessa kyseinen sarake ei olekkaan ennään käyty
                                    k.setKohta(i, j, 2);            // asetetaan kohta kenttään
                                    taydet++;                       // piste on laitettu kentälle
                                    muutosta = true;                // muutosta on tapahtunut joten while looppi jatkuu
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < k.getLeveys(); i++) {
                    // { tarkistetaan että onko sarakkeella jo käyty ja että onko sille 
                    // annettu oletettu pituus vielä tarpeeksi pieni että tämä sarake käytäisiin.
                    if (sarakkeet_pituus[i] <= (h) && !sarakkeet_kaydyt[i]) {
                        sarakkeet_kaydyt[i] = true; // asetetaan sarake käydyksi
                        // { kutsutaan ratko_linja metodia joka palauttaa listan siitä miltä uusi sarake näyttää.
                        Integer[] sarake = ratko_linja(i, sarakkeet[i], k.getSarake(i));
                        if (sarake != null) {
                            // { asetetaan riviltä arvot 1 (tyhjä) tai 2 (täysi) pelin kenttään.
                            for (int j = 0; j < sarake.length; j++) {
                                if (sarake[j] == 1 && k.getKohta(j, i) != 1) {
                                    rivit_kaydyt[j] = false;        // huomataan että saraketta muuttaessa kyseinen rivi ei olekkaan ennään käyty
                                    k.setKohta(j, i, 1);            // asetetaan kohta kenttään
                                    muutosta = true;                // muutosta on tapahtunut joten while looppi jatkuu
                                }
                                if (sarake[j] == 2 && k.getKohta(j, i) != 2) {
                                    rivit_kaydyt[j] = false;        // huomataan että saraketta muuttaessa kyseinen rivi ei olekkaan ennään käyty
                                    k.setKohta(j, i, 2);            // asetetaan kohta kenttään
                                    taydet++;                       // piste on laitettu kentälle
                                    muutosta = true;                // muutosta on tapahtunut joten while looppi jatkuu
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Funktio joka ratkoo permutaatiot. Funktio siis tarkistaa kaikki annetut 
     * permutaatiot, ja kertoo mitä niissä on samaa ja mitä eri.
     * @param i monesko rivi / sarake on kyseessä
     * @param saaret lista saarista jota rivillä on
     * @param linja tämän hetkinen sarake / rivi johon on merkitty jo löydetyt 
     *              eli pakolliset kohdat tyhjiksi / täysiksi
     * @return palauttaa linjan jossa on muutokset.
     * @throws TyhjaListaException 
     */
    private Integer[] ratko_linja(int i, ArrayList<Integer> saaret, Integer[] linja) throws TyhjaListaException {
        ArrayList<Integer[]> toimivat_rivit = new ArrayList<>();
        permutaatiot(saaret, linja, linja, 0, true); // kutsutaan permutaatio funktiota
                                                     //joka lisää list_permuista kaikki 
                                                     //mahdolliset permutaatiot tämänm hetkisen linjan perusteella.
        if (lista_permuista.isEmpty()) { // jos lista on tyhjä heittää exceptionin.
            throw new TyhjaListaException(); // tätä kutsutaan vain kun olemme bruteforce kohdassa.
        }
        // { aletaan käymään listaa läpi 
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
        Integer[] uusi_rivi = toimivat_rivit.get(0);  // otetaan permutoinnista syntynyt ensimmäinen linja
        if (toimivat_rivit.size() > 0) {
            toimivat_rivit.removeFirst();
            for (Integer[] per2 : toimivat_rivit) {
                // { verrataan kaikkia listassa olevia ensimmäiseen permutaatioon
                // ja jos löydetään jotain mikä ei täsmää asetetaan se palautettavassa listassa
                // nollaksi. jos kaikissa muissa permutaatioissa on jotain samaa kuten pakollinen
                // tyhjä (1) tai täysi (2) niin sitä ei muuteta nollaksi ja se jätetään riviin.
                for (int j = 0; j < uusi_rivi.length; j++) {
                    if (per2[j] != uusi_rivi[j]) {      
                        uusi_rivi[j] = 0;
                    }
                }
            }
        }
        return uusi_rivi;
    }
    /**
     * lista permutaatioista joka luodaan permutaatiot funktiossa ja jota 
     * käydään läpi ratko_linja funktiossa.
     */
    public ArrayList<Integer[]> lista_permuista;

    /**
     * listaa lista_permuista kaikki mahdolliset permutaatiot. 
     * @param saaret saaret joita pitää lisätä listaan. saaret lista pienenee sitä mukaan kun saari on lisätty listaan
     * @param linja tämän hetkinen lista joka täsmää kenttään
     * @param tama tämän hetkinen lista johon on lisätty jonkin verran tyhjää ja saaria
     * @param index kohta missä mennnään tama täytössä
     * @param eka jos on kyseessä eka niin lista_peruista alustetaan tyhjäksi
     */
    public void permutaatiot(ArrayList<Integer> saaret, Integer[] linja, Integer[] tama,  int index, boolean eka) {
        if (eka) {
            lista_permuista = new ArrayList<>();
            lista_permuista.removeAll();
        }
        // max on tällä hetkellä kohta mihin piirretään maximissaan ettei tulevien saarien koot tule vastaa.
        Integer max = linja.length - (sum(saaret) - saaret.get(0)) - saaret.size() + 1;
        
        // { aloitetaan for loppi jossa käydään läpi "tama"n listan täyttöä
        for (int i = index; i < max - saaret.get(0) + 1; i++) {
            Integer[] uusi_linja = new Integer[linja.length];
            if (eka) {
                System.arraycopy(linja, 0, uusi_linja, 0, linja.length);
            } else {
                System.arraycopy(tama, 0 ,uusi_linja, 0 ,linja.length);
            }
            int koko = saaret.get(0);
            int loppuun = index;
//            int n = max < (i + saaret.get(0) + 1) ? max : (i + saaret.get(0) + 1);
            // { 
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
                    permutaatiot(uudet_saaret, linja, uusi_linja, loppuun + 1, false); // kutsutaan permutaatiota rekursiivisesti jokaista saarta kohden.

                }
            }
            if (loppuun == linja.length) {
                lista_permuista.add(uusi_linja);
            }
        }
    }
    
    /**
     * Brute force funktio joka kutsuu vielä keskeneräisen kentän ratkomista. 
     * Tämä tapahtuu asettamalla ensimmäiseen tyhjään kohtaan jonka for looppi löytää 
     * pisteen ja yrittämällä ratkoa kenttää ratko funkriolla.
     * @param vali 
     * @param muutosta
     * @param piirto 
     */
    private void BruteForce(Kentta vali, Integer muutosta, boolean piirto) {
        Kentta atm = new Kentta(0, 0);
        atm.copy(k);
        int taydet_old = atm.getKakkoset();
        boolean valmis = false;
        for (int i = 0; i < rivit.length; i++) {
            for (int j = 0; j < sarakkeet.length; j++) {
                if (k.getKohta(i, j) == 0) {
                    k.setKohta(i, j, 2);

                    taydet++;
                    try { // yritetän ratkoa kenttää nytkun sinne on asetetty piste joka on ehkä mahdollinen lopputuloksessa.
                        ratko(piirto);
                    } catch (TyhjaListaException e) { // jos törmätään tyhjään listaan niin palautetaan aiempi kenttä ja jatketaan bruteforcea eri kohdasta.
                        k.copy(atm);
                        taydet = taydet_old;
                        continue;
                    }
                    if (taydet < max_pisteet) { // jos ratkominen loppui ja kenttä on vielläkin tyhjä koitamme bruteforcea uudelleen. seuraavasta kohdasta
                        BruteForce(atm, 0, piirto);
                    } 
                    // { jos kenttä on täysi ja täsmää nonogrammille annetut saaret niin se on valmis.
                    if (Objects.equals(taydet, max_pisteet) && peli.onkoMahdollinen()) {
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
    
    /**
     * laskee listassa olevat saaret yhteen.
     * 
     * @param t lista saaria.
     * @return palauttaa yhteen lasketun summan
     */
    private int sum(ArrayList<Integer> t) {
        int palaute = 0;
        for (int i = 0; i < t.size(); i++) {
            palaute += t.get(i);
        }
        return palaute;
    }

    /**
     * asettaa pituus oletukset permutaatio listaukselle.
     * tämä siis asettaa oletus pituuden sille että kuinka monta eri permutaatiota
     * permutaatio funktio tuottaa. Mitä vähemmän sitä nopeampi ohjelma on. joten
     * haluamme että ohjelma käy ensin nopeiten ratkottavat rivit ja sarakkeet 
     * jotta suuremmissa on jo mahdollisesti ratkottu osia jotta ne olisi nopeampi ratkaista.
     * 
     * laskee oletuksen sen mukaan kuinka monta tyhjää kohtaa rivillä on johon
     * on mahdollista asettaa saaria ja kertoa tyhjät saarien määrällä.
     */
    private void arvioiPituudet() {
        sarakkeet_pituus = new Integer[sarakkeet.length];
        for (int i = 0; i < sarakkeet.length; i++) {
            int tyhjaa = rivit.length;
            int saaret = 1;
            for (int j = 0; j < sarakkeet[i].size(); j++) {
                tyhjaa -= sarakkeet[i].get(j);
                tyhjaa--;
                saaret *= 2;
            }
            tyhjaa = tyhjaa * saaret;
            sarakkeet_pituus[i] = tyhjaa;
        }
        rivit_pituus = new Integer[rivit.length];
        for (int i = 0; i < rivit.length; i++) {
            int tyhjaa = sarakkeet.length;
            int saaret = 1;
            for (int j = 0; j < rivit[i].size(); j++) {
                tyhjaa -= rivit[i].get(j);
                tyhjaa--;
                saaret *= 2;
            }
            tyhjaa = tyhjaa * saaret;
            rivit_pituus[i] = tyhjaa;
        }
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



}
