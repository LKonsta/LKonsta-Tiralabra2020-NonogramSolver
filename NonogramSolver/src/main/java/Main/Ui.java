/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Algoritmit.Solver;
import Nonogram.Peli;
import Tietorakenteet.ArrayList;
import Tietorakenteet.TyhjaListaException;
import java.util.Scanner;

/**
 *
 * @author khlehto
 */
public class Ui {
    
    Scanner s;
    
    public Ui() throws TyhjaListaException {
        s = new Scanner(System.in);
        System.out.println("Nonogram Solver");
        
        System.out.println(" 1. Uusi Peli");
        System.out.println(" 2. Valmis Pohja");
        System.out.println(" 3. Poistu");
        
        int vastaus = tarkistaOnkoNumero(s.nextLine(), 3);
        
        switch (vastaus) {
            case 1:
                uusiPeli();
                break;
            case 2:
                alustaValmiit();
                valmisPohja();
                break;
            case 3:
                PeliLoppui();
                break;
            default:
                break;
        }
    }

    

    private void uusiPeli() throws TyhjaListaException {
        
        System.out.println("Valitsit uuden pelin");
        System.out.println(" 1. Rivit ja Sarakkeet");
        System.out.println(" 2. Ohjeet");
        
        int vastaus = tarkistaOnkoNumero(s.nextLine(), 2);
        
        switch (vastaus) {
            case 1: 
                aloitaPeli();
                break;
            case 2: 
                ohjeet();
                break;
            default:
                break;
        }
    }
    
    private void aloitaPeli() throws TyhjaListaException {
        
        boolean tarkistusR = false;
        boolean tarkistusS = false;
        String rivit = "";
        String sarakkeet = "";
        
        System.out.println("Takaisin pääsee komennolla (-1)");
        boolean takaisin = false;
        
        while (!tarkistusR && !tarkistusS) {
            System.out.print("Anna rivit: ");
            rivit = s.nextLine();
            
            if (rivit.equals("-1")) {
                takaisin = true;
                break;
            }
            
            tarkistusR = tarkistaOikeaMuotoisuus(rivit);
            if (!tarkistusR) {
                System.out.println("Väärin kirjoitetut Rivit");
                continue;
            }
            
            System.out.print("Anna sarakkeet: ");
            sarakkeet = s.nextLine();
            
            if (sarakkeet.equals("-1")) {
                takaisin = true;
                break;
            }
            
            tarkistusS = tarkistaOikeaMuotoisuus(sarakkeet);
            
            if (!tarkistusS) {
                System.out.println("Väärin kirjoitetut Sarakkeet");
            }
        }
        if (takaisin) {
            uusiPeli();
        } else {
            
            Peli uusi_peli = new Peli(rivit, sarakkeet, "uusiPeli");
            pelaa(uusi_peli);
        }
    }

    private void ohjeet() throws TyhjaListaException {
        System.out.println("-------------------------------");
        System.out.println("Ohjeet:");
        System.out.println("Rivit ja Sarakkeet annetaan muodossa jossa rivit erotellaan (/) merkillä ja saaret erotellaan (,) merkillä");
        System.out.println("Esimerkiksi siis tapaus jossa Rivit: 0/3/0 ja Sarakkeet: 1/1/1 palauttausi ohjelma Nonogrammin");
        System.out.println("111");
        System.out.println("222");
        System.out.println("111");
        System.out.println("tässä 1 ovat tyhjiä ja 2 ovat saaria");
        System.out.println("Toinen esimerkki Rivit: 1/1,1/1 ja Sarakkeet: 1/1,1/1");
        System.out.println("121");
        System.out.println("212");
        System.out.println("121");
        System.out.println("--------------------------------");
        uusiPeli();
    }
    
    ArrayList<Peli> lista_peleja = new ArrayList<>();
    
    private void alustaValmiit() {
        
        Peli testi1 = new Peli("7/7/1,1,1,1/3,3/3,3/2,1,1,2/3,3/9/7/2,2/2,3/6,1/2,2,3/4,3,1/2,3,1,3/3,1,5/4,5/4,5/1,1,1,1/1,1,1,1",
                "4/7/3,2,3/4,1,4,4/2,2,3,2,3/3,8,1/2,4,2/2,2,2/2,6,1/3,6,3/2,2,3,3,3/4,1,1,6/3,5/6/3","testi1");
        
        Peli mustekala = new Peli("4/9/11/13/4,5/3,4/3,4,3/3,4,6/8,4,8/6,5,10/2,5,4,3/9,5,3,3/11,5,3,4/12,5,4,4/18,2,5,3/4,16,12,1/3,17,12/4,18,3,5/3,19,4,2/6,10,9,1/7,10,7,2/2,9,4,2/14,2/3,17,3/4,25,3/8,27,5/45/27,12/25,6/26/26/27/34/36/8,27/6,27/2,12,9/12,6/14,5/15,5/15,4/3,4,11,4/7,4,10,4,3/3,10,5,5,3,4/2,8,5,4,3,5/1,6,4,4,1,3,2/5,6,3,2/5,7,4,3/13,8,8/13,5,3,5/13,3,3/13,2,3/3,2,3/3,3,3/3,4,5/3,2,9/4,2,6/8,2/4",
                "1/1,2/2,7/3,10/3,11,4/2,12,3/4,13,3/5,13,2/5,12,3/6,11,3,4/5,10,4,6/4,8,3,8/4,11,3,4,3/4,12,3,4,2/4,13,4,4,3/5,4,13,5,4,2/7,5,13,7,4,2/8,7,20,4,2/4,34,4,3/3,3,29,4,3/3,2,28,5/4,2,28,6/4,1,38/4,39/5,40/46,7/13,30,7/11,6,6,12,3,4/8,5,6,13,2,3/5,5,4,7,3,3/4,5,4,7,2,4/4,5,4,8,3/4,4,5,14/2,4,4,5,11/7,4,4,5,9/8,4,4,6,2/5,4,4,4,7,2/4,3,3,3,10/4,3,4,3,14/4,6,3,14/4,6,3,14/12,3,3/9,3,3,2/9,3,3,2/2,3,4,3,3/3,3,6/2,4,5/3,4/1,8/4",
                "mustekala");
        
//        4/9/11/13/4,5/3,4/3,4,3/3,4,6/8,4,8/6,5,10/2,5,4,3/9,5,3,3/11,5,3,4/12,5,4,4/18,2,5,3/4,16,12,1/3,17,12/4,18,3,5/3,19,4,2/6,10,9,1/7,10,7,2/2,9,4,2/14,2/3,17,3/4,25,3/8,27,5/45/27,12/25,6/26/26/27/34/36/8,27/6,27/2,12,9/12,6/14,5/15,5/15,4/3,4,11,4/7,4,10,4,3/3,10,5,5,3,4/2,8,5,4,3,5/1,6,4,4,1,3,2/5,6,3,2/5,7,4,3/13,8,8/13,5,3,5/13,3,3/13,2,3/3,2,3/3,3,3/3,4,5/3,2,9/4,2,6/8,2/4]
        
//        1/1,2/2,7/3,10/3,11,4/2,12,3/4,13,3/5,13,2/5,12,3/6,11,3,4/5,10,4,6/4,8,3,8/4,11,3,4,3/4,12,3,4,2/4,13,4,4,3/5,4,13,5,4,2/7,5,13,7,4,2/8,7,20,4,2/4,34,4,3/3,3,29,4,3/3,2,28,5/4,2,28,6/4,1,38/4,39/5,40/46,7/13,30,7/11,6,6,12,3,4/8,5,6,13,2,3/5,5,4,7,3,3/4,5,4,7,2,4/4,5,4,8,3/4,4,5,14/2,4,4,5,11/7,4,4,5,9/8,4,4,6,2/5,4,4,4,7,2/4,3,3,3,10/4,3,4,3,14/4,6,3,14/4,6,3,14/12,3,3/9,3,3,2/9,3,3,2/2,3,4,3,3/3,3,6/2,4,5/3,4/1,8/4
        
        
        Peli hirvi = new Peli("2/3,3/2,2,2/4,6/5,7/2,7/3,1,4/7/2,4/2,6/11/14/4,9/1,8/4,7/3,5/","2,2,2,2/2,2,1,1,2,2/4,1,1,2,2/5,5/2,5/7/1,1,5/11/12/14/8,6/1,3,6/5,6/3,6","hirvi");
        
        Peli ankka = new Peli("1,2/3,1/1,5/7,1/5/3/4/3","3/2,1/3,2/2,2/6/1,5/6/1/2","ankka");
        
        Peli kotka = new Peli("6,6,7/6,4,5,1/4,1,3,5,3/3,2,4,4/2,1,3/1,2,4/2,2,1,6/2,6,3/2,12/2,12/2,7/2,2,8/2,1,4,5/2,1,3,3/2,3,1,2/2,9/1,1,1,2,2/2,2,1,2/2,1,1,1/2,2,3/3,3/2,2,2/2,1,4/2,2/6",
                "7/11/3,3/3,3/4,2/3,2/2,1,2/3,6,1/2,2,1,2/1,1,6,1/2,2,1,2/3,2,2,2/3,2,4,1,1/2,4,1,1,2,2,1/2,12,2,1/1,1,15,1/1,2,2,6,2,1/5,5,1,1/4,5,3/3,6,2/3,1,5,1/2,1,2,2,2/1,5,1,2/1,5,1,2/7,1,2",
                "kotka");
        
        Peli W = new Peli("1/1/2/4/7/9/2,8/1,8/8/1,9/2,7/3,4/6,4/8,5/1,11/1,7/8/1,4,8/6,8/4,7/2,4/1,4/5/1,4/1,5/7/5/3/1/1",
                 "8,7,5,7/5,4,3,3/3,3,2,3/4,3,2,2/3,3,2,2/3,4,2,2/4,5,2/3,5,1/4,3,2/3,4,2/4,4,2/3,6,2/3,2,3,1/4,3,4,2/3,2,3,2/6,5/4,5/3,3/3,3/1,1/", "W");
        Peli etana = new Peli("1,2/4/10/8,2/1,3,2/2,2,1/2,1,1/1,2,1,1/1,1,1,1,1/1,2,2,1/1,3,1/2,1/2,1,1/4,2/2/1", 
                "1,1/1,1/3/4/4/2,6/2,2,2/4,2,2/3,1,2,1/1,2,2,1,1/1,1,2,1/2,4,1/2,1,2/7,4", "etana");
        Peli logo = new Peli("3/8/4,5/3,3/3,5,3/2,3,6/3,2,5/2,2,4/4,3/5,4/4,15/4,15/4,15/4,15/4,4,3,2/4,4,4,2/4,4,6/3,4,7/3,15/2,8,6/3,7,6/2,5,1,4/2,1,2/2,1/6",
                "8/13/17/4,14/3,3,3/3,2,10,2/2,2,11,2/2,2,12,1/2,2,12,1/3,1,4,4,1/3,2,4,4,1/2,2,12,1/3,2,11,1/6,9,1/11,7/10,5/16/7,5/4,5/4,5", "logo");
        Peli omena = new Peli("4/6/2,3/2/3,1,3/1,5,1/1,1/1,2,1/1,1,1/1,1/1,1/1,1/1,1/2,1,2/3,3", "5/1,2/1,1,1/1,2,2/1,2,1/2,1,1/1,3,1/2,1,1/3,2,1/3,1,2/3,1,1/1,1,2/5", "omena");
        Peli vene = new Peli("1,1/2,1,1/2,1,2,1/2,2,1,1/4,3/6,3/12/2,3/15/1,9,3/1,7,3/5,3/3,1,1/1,1,1/1", "1,3/2,2/2,3/2,1,2/1,3/2,3/2,4/3,4/4,5/5,5/3,6/2,1,1/12/8/15", "vene");

        Peli perhonen = new Peli("8,3,1,2/2,3,1,1,3,1/2,5,2,2/2,3,2,1,2/2,6,2,2/2,2,3,3,1/2,2,3,1,2/2,2,4,2/2,9/3,1,1,1,1,2/3,1,1,1,1,2/2,9/2,2,4,2/2,2,3,1,2/2,2,3,3,1/2,6,2,2/2,3,2,1,2/2,5,2,2/2,3,1,1,3,1/7,3,1,2/1,2,2,1,4,2/5,6,4/4,2,5/4,2,3/1,3,4/1,7/2,6/1,1,4/8/4", 
                "7/1,1,1,4,1,2/2,2,2,2,4,3/3,2,2,9,2/1,2,2,2,2,3,3,2/2,2,2,2,2,2,2,4/3,2,4,2,4,2,4/7,2,9,6/1,3,2,2,2,3,1,2,4/5,2,2,5,7/1,1,3,4,3,1,1,4/2,4,4,6/1,1,8,1,4/3,2,2,3,1/2,2,6,2,2,2/2,1,2,1,1,2,1,2,1/2,2,4,2,2,1/3,2,2,3,2/1,1,2,2,2,1,2/2,2,2,2,2", "perhonen");

        if (lista_peleja.isEmpty()) {
            lista_peleja.add(kotka);
            lista_peleja.add(ankka);
            lista_peleja.add(vene);
            lista_peleja.add(hirvi);
            lista_peleja.add(omena);
            lista_peleja.add(logo);
            lista_peleja.add(etana);
            lista_peleja.add(W);
            lista_peleja.add(perhonen);
            lista_peleja.add(mustekala);
            lista_peleja.add(testi1);
        }
    }
    
    private void valmisPohja() throws TyhjaListaException {
        System.out.println("Valmiit esimerkit:");
        for (int i = 0; i < lista_peleja.size(); i++) {
            System.out.println(" " + (i+1) + ". " + lista_peleja.get(i).getNimi() + " [" + lista_peleja.get(i).getSarakkeidenMaara() + "x" + lista_peleja.get(i).getRivienMaara() + "]");
        }
        int valinta = tarkistaOnkoNumero(s.nextLine(), lista_peleja.size());
        
        
        pelaa(lista_peleja.get(valinta-1));
    }
    
    private void pelaa(Peli p) throws TyhjaListaException {
        Solver ratkoja = new Solver(p);
        
        System.out.println("Näytetäänkö välivaiheet");
        System.out.println(" 1. Kyllä");
        System.out.println(" 2. Ei");
        
        int valinta = tarkistaOnkoNumero(s.nextLine(), 2);
        
        switch (valinta) {
            case 1:
                ratkoja.permutaatio(true);
                System.out.println("---------------------------------------");
                System.out.println(ratkoja.getRatkaisu());
                break;
            case 2:
                ratkoja.permutaatio(false);
                System.out.println("---------------------------------------");
                System.out.println(ratkoja.getRatkaisu());
                break;
        }
        
    }

    private void PeliLoppui() {
        System.out.println("Peli lopetetaan");
    }

    private boolean tarkistaOikeaMuotoisuus(String rivi) {
        boolean onko = true;
        String[] splitted = rivi.split("/");
        for (int i = 0; i < splitted.length; i++) {
            String[] saaret = splitted[i].split(",");
            for (int j = 0; j < saaret.length; j++) {
                if (saaret[j].matches("[0-9]+")) {
                    onko = false;
                } else {
                    return false;
                }
            }
        }
        return !onko;
    }
    
    /**
     * Tarkistaja joka palauttaa kirjoitetusta stringista ensimmäiseksi annetun numeron.
     * @param nextLine on String jonka käyttäjä on kirjoittanut
     * @param vaihtoehdot kertoo montako vaihtoehtoa kyseisessä kyselyssä on. esim aluksi vaihtoehtoja on 3 ja seuraavaksi 2 jne.
     * @return palauttaa numeron joka on stringistä ensimmäinen numero / ensimmäiset 2 numeroa. 
     */
    private int tarkistaOnkoNumero(String nextLine, int vaihtoehdot) {
        boolean vastattu = false;
        ArrayList<Integer> v = new ArrayList<>();
        for (int i = 0; i <= vaihtoehdot; i++) {
            v.add(i);
        }
        
        int ekanumero = -1;
        
        boolean eka = true;
        
        while (!vastattu) {
            if (!eka) {
                nextLine = s.nextLine();
            }
            eka = false;

            String[] splitted = nextLine.split("");
            
            for (String splitted1 : splitted) {
                
                if (splitted1.matches("[0-9]+")) {
                    if (ekanumero != -1) {
//                        System.out.println("");
                        String vali = ekanumero + "" + splitted1;
                        ekanumero = Integer.parseInt(vali);
                        break;
                    }
                    ekanumero = Integer.parseInt(splitted1);
                }
            }
            if (v.contains(ekanumero) ) {
                vastattu = true;
            } else {
                System.out.println("numeroa ei köydetty yritä uudestaa.");
            }
        }
        return ekanumero;
    }

    

}
