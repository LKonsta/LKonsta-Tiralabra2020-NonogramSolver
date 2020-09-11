package Nonogram;

import Tietorakenteet.ArrayList;

public class Simppeli {
    static Integer[][] kentta;

    
    public static void Simppeli(Peli peli) {
        Integer[][] valikentta = new Integer[peli.getRivienMaara()][peli.getSarakkeidenMaara()];
        ArrayList<Integer>[] Rivit = peli.getRivit();
        ArrayList<Integer>[] Sarakkeet = peli.getSarakkeet();
        
        // asetetaan indexit jokaisen arraylistin ensimmäiseksi numeroksi jotta voimme 
        // erottaa saaret pelikentältä.
        
        int index = 1;
        for (int i = 0; i < Rivit.length; i++) {
            Rivit[i].addFirst(index);
        }
        for (int i = 0; i < Sarakkeet.length; i++) {
            Sarakkeet[i].addFirst(index);
        }
        
        int pisteet = 0;
        
        for (int i = 0; i < Sarakkeet.length; i++) {
             for (int j = 1; j < Sarakkeet[i].size(); j++) {
                pisteet += Sarakkeet[i].get(j);
            }
        }
        System.out.println("pisteet:"+pisteet);
        int x = 0;
        int y = 0;
        
        kentta = SimppeliIterointi(valikentta, Rivit, Sarakkeet, x, y);
        
//        System.out.println(kentta[2][2]);
        for (int i = 0; i < kentta.length; i++) {
            System.out.print("[");
            for (int j = 0; j < kentta[i].length; j++) {
                if (kentta[i][j] == 1)  {
                    System.out.print("x");
                }
                else {
                    System.out.print("o");
                }
                
            }
            System.out.println("");
        }
        System.out.print("]");
        System.out.println("toimii");
    }
    
    private static Integer[][] SimppeliIterointi(Integer[][] kentta, ArrayList<Integer>[] rivit, ArrayList<Integer>[] sarakkeet, Integer x, Integer y) {
        // tämänhetkinenkenttä
        Integer[][] thkentta = kentta;
        ArrayList<Integer>[] tamanrivit = rivit;
        ArrayList<Integer>[] tamansarakkeet = sarakkeet;
        
        for (int i = x; i < kentta.length; i++) {
            for (int j = y; j < kentta[i].length; j++) {
                System.out.println(i + " " + j + " " + " " + tamanrivit[i].get(0) + " " + tamanrivit[i].get(tamanrivit[i].get(0)) + " " + kentta[0][0]);
                if (tamanrivit[i].get(tamanrivit[i].get(0)) > 0 && tamansarakkeet[j].get(tamansarakkeet[j].get(0)) > 0) {
                    thkentta[i][j] = 1;
                    tamanrivit[i].set(tamanrivit[i].get(tamanrivit[i].get(0)), tamanrivit[i].get(tamanrivit[i].get(0))-1);
                    System.out.println(tamanrivit[i].get(tamanrivit[i].get(0)));
                    if (tamanrivit[i].get(tamanrivit[i].get(0)) == 0) {
                        tamanrivit[i].set(0, tamanrivit[i].get(0)+1);
                    }
                    System.out.println(tamanrivit[i].get(0));
                    tamansarakkeet[i].set(tamansarakkeet[i].get(tamansarakkeet[i].get(0)), tamansarakkeet[i].get(tamansarakkeet[i].get(0))-1);
                    if (tamansarakkeet[i].get(tamansarakkeet[i].get(0)) == 0) {
                        tamansarakkeet[i].set(0, tamansarakkeet[i].get(0)+1);
                    }
                    
                    SimppeliIterointi(thkentta, tamanrivit, tamansarakkeet, i+1, j);
                }
                else {
                    thkentta[i][j] = 0;
                }
            }
        }

        return thkentta;
    }
}
