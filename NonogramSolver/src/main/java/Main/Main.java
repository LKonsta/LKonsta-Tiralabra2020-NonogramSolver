package Main;

import Algoritmit.Solver;
import Tietorakenteet.ArrayList;
import Nonogram.Peli;


public class Main {
    
    public static void main(String[] args) {
        
        Peli uusiPeli = new Peli("2/1,2/1,2/3/4", "1/5/2/4/2,1");
        
        Peli peli2 = new Peli("3/2,1/3,2/2,2/6/1,5/6/1/2", "1,2/3,1/1,5/7,1/5/3/4/3");
        Peli peli3 = new Peli("2/2/2/4/2/2","0/1/6/6/1/0/0");
        Solver ratkoja = new Solver(peli2);

    }

    
}
