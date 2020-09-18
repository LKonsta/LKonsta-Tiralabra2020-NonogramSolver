package Main;

import Algoritmit.Solver;
import Tietorakenteet.ArrayList;
import Nonogram.Peli;


public class Main {
    
    public static void main(String[] args) {
        
        Peli uusiPeli = new Peli("2/1,2/1,2/3/4", "1/5/2/4/2,1");
        Solver ratkoja = new Solver(uusiPeli);
    }

    
}
