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
import org.junit.Assert;
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
    public void permutaatioTest1() {
        Peli p = new Peli("1","1");
        PermutaatioSolver j = new PermutaatioSolver(p, false);
        
        Integer[] testi_taulukko = new Integer[5];
        ArrayList<Integer> testi_array = new ArrayList<>();
        testi_array.add(3);
        j.permutaatiot(testi_array, testi_taulukko, testi_taulukko, 0 ,true);
        j.listaToString();
        String ratkaisu = "[[2, 2, 2, 1, 1], [1, 2, 2, 2, 1], [1, 1, 2, 2, 2]]";
        Assert.assertTrue(outputStreamCaptor.toString().contains(ratkaisu));
    }
    
    @Test
    public void permutaatioTest2() {
        Peli p2 = new Peli("0","0");
        PermutaatioSolver j = new PermutaatioSolver(p2, false);
        
        Integer[] testi_taulukko2 = new Integer[8];
        testi_taulukko2[2] = 2;
        ArrayList<Integer> testi_array2 = new ArrayList<>();
        testi_array2.add(5);
        j.permutaatiot(testi_array2, testi_taulukko2, testi_taulukko2, 0, true);
        j.listaToString();
        String ratkaisu2 = "[[2, 2, 2, 2, 2, 1, 1, 1], [1, 2, 2, 2, 2, 2, 1, 1], [1, 1, 2, 2, 2, 2, 2, 1]]";
        Assert.assertTrue(outputStreamCaptor.toString().contains(ratkaisu2));
    }
    
    @Test
    public void permutaatioTest3() {
        Peli p = new Peli("0","0");
        PermutaatioSolver j = new PermutaatioSolver(p, false);
        
        Integer[] testi_taulukko = new Integer[5];
        ArrayList<Integer> testi_array = new ArrayList<>();
        testi_array.add(1);
        testi_array.add(2);
        j.permutaatiot(testi_array, testi_taulukko, testi_taulukko, 0, true);
        j.listaToString();
        String ratkaisu2 = "[[2, 1, 2, 2, 1], [2, 1, 1, 2, 2], [2, 1, 1, 2, 2], [1, 2, 1, 2, 2]]";
        Assert.assertTrue(outputStreamCaptor.toString().contains(ratkaisu2));
    }
}
