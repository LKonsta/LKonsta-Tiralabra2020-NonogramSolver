package Main;

import Algoritmit.Solver;
import Tietorakenteet.ArrayList;
import Nonogram.Peli;


public class Main {
    
    public static void main(String[] args) {
        
        Peli uusiPeli = new Peli("1,2/1/0/0", "1/1/1/1");
        Solver ratkoja = new Solver(uusiPeli);
        
    }

    
}
