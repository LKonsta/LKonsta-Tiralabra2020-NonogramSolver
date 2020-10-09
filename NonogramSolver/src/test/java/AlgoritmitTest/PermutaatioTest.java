/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgoritmitTest;

import Algoritmit.PermutaatioSolver;
import Nonogram.Peli;
import Tietorakenteet.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author khlehto
 */
public class PermutaatioTest {
    
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    
    @Test
    public void permutaatioTest() {
        Peli p = new Peli("","");
        PermutaatioSolver j = new PermutaatioSolver(p);
        
        Integer[] testi_taulukko = new Integer[10];
        testi_taulukko[3] = 2;
        ArrayList<Integer> testi_array = new ArrayList<>();
        testi_array.add(6);
        j.permutaatiot(testi_array, testi_taulukko);
        System.out.println(j.lista_permuista);
    }
}
