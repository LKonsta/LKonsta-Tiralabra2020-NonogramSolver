/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgoritmitTest;

import Algoritmit.PermutaatioSolver;
import Algoritmit.Solver;
import Nonogram.Peli;
import Tietorakenteet.ArrayList;
import Tietorakenteet.TyhjaListaException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author khlehto
 */
public class PermutaatioTest {
    
//    private final PrintStream standardOut = System.out;
//    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @Before
    public void setUp() {
//        System.setOut(new PrintStream(outputStreamCaptor));
    }
    
    @Test
    public void permutaatioTest1() throws TyhjaListaException {
        Peli p = new Peli("1","1","");
        PermutaatioSolver j = new PermutaatioSolver(p, false, false);
        
        Integer[] testi_taulukko = new Integer[5];
        Arrays.fill(testi_taulukko, 0);
        ArrayList<Integer> testi_array = new ArrayList<>();
        testi_array.add(3);
        j.permutaatiot(testi_array, testi_taulukko, testi_taulukko, 0 ,true);
        String ratkaisu = "[[2, 2, 2, 1, 1], [1, 2, 2, 2, 1], [1, 1, 2, 2, 2]]";
        Assert.assertEquals(j.listaToString(),(ratkaisu));
    }
    
    @Test
    public void permutaatioTest2() throws TyhjaListaException {
        Peli p2 = new Peli("0","0","");
        PermutaatioSolver j = new PermutaatioSolver(p2, false, false);
        
        Integer[] testi_taulukko2 = new Integer[8];
        Arrays.fill(testi_taulukko2, 0);
        testi_taulukko2[2] = 2;
        ArrayList<Integer> testi_array2 = new ArrayList<>();
        testi_array2.add(5);
        j.permutaatiot(testi_array2, testi_taulukko2, testi_taulukko2, 0, true);
        String ratkaisu2 = "[[2, 2, 2, 2, 2, 1, 1, 1], [1, 2, 2, 2, 2, 2, 1, 1], [1, 1, 2, 2, 2, 2, 2, 1]]";
        Assert.assertEquals(j.listaToString(),(ratkaisu2));
    }
    
    @Test
    public void permutaatioTest3() throws TyhjaListaException {
        Peli p = new Peli("0","0","");
        PermutaatioSolver j = new PermutaatioSolver(p, false, false);
        
        Integer[] testi_taulukko = new Integer[5];
        Arrays.fill(testi_taulukko, 0);
        ArrayList<Integer> testi_array = new ArrayList<>();
        testi_array.add(1);
        testi_array.add(2);
        j.permutaatiot(testi_array, testi_taulukko, testi_taulukko, 0, true);
        j.listaToString();
        String ratkaisu2 = "[[2, 1, 2, 2, 1], [2, 1, 1, 2, 2], [2, 1, 1, 2, 2], [1, 2, 1, 2, 2]]";
        Assert.assertEquals(j.listaToString(),(ratkaisu2));
    }
    
    @Test
    public void permutaatioTest4() throws TyhjaListaException {
        Peli p = new Peli("0", "0","");
        PermutaatioSolver j = new PermutaatioSolver(p, false, false);

        Integer[] testi_taulukko = new Integer[5];
        Arrays.fill(testi_taulukko, 0);
        testi_taulukko[0] = 2;
        testi_taulukko[4] = 2;
        ArrayList<Integer> testi_array = new ArrayList<>();
        testi_array.add(2);
        j.permutaatiot(testi_array, testi_taulukko, testi_taulukko, 0, true);
        j.listaToString();
        String ratkaisu2 = "[]";
        Assert.assertEquals(j.listaToString(), (ratkaisu2));
    }
    
    @Test
    public void permutaatioTest5() throws TyhjaListaException {
        Peli p = new Peli("6,6,7/6,4,5,1/4,1,3,5,3/3,2,4,4/2,1,3/1,2,4/2,2,1,6/2,6,3/2,12/2,12/2,7/2,2,8/2,1,4,5/2,1,3,3/2,3,1,2/2,9/1,1,1,2,2/2,2,1,2/2,1,1,1/2,2,3/3,3/2,2,2/2,1,4/2,2/6",
                "7/11/3,3/3,3/4,2/3,2/2,1,2/3,6,1/2,2,1,2/1,1,6,1/2,2,1,2/3,2,2,2/3,2,4,1,1/2,4,1,1,2,2,1/2,12,2,1/1,1,15,1/1,2,2,6,2,1/5,5,1,1/4,5,3/3,6,2/3,1,5,1/2,1,2,2,2/1,5,1,2/1,5,1,2/7,1,2",
                "kotka");
        Solver ratkoja = new Solver(p);
        ratkoja.permutaatio(false);

        String ratkaisu2 = "[1111112222222111111111111]\n"
                + "[1111222222222221111111111]\n"
                + "[1122211111111222111111111]\n"
                + "[1222111111111112221111111]\n"
                + "[2222111111111111122111111]\n"
                + "[2221111111111111112211111]\n"
                + "[2211111111121111111221111]\n"
                + "[2221111111122222211121111]\n"
                + "[2211111111111122121221111]\n"
                + "[2112111111111122222212111]\n"
                + "[1122111221111112111112211]\n"
                + "[2221111221111112211111221]\n"
                + "[2221111221111122221112121]\n"
                + "[2211112222112112112212212]\n"
                + "[2211112222222222221221112]\n"
                + "[2112111222222222222222112]\n"
                + "[2122122122222211111122112]\n"
                + "[1222221222221111111111212]\n"
                + "[2222111222221111111111222]\n"
                + "[2221112222221111111111221]\n"
                + "[2221112122222111111111211]\n"
                + "[2212122122122111111111111]\n"
                + "[2122222112112211111111111]\n"
                + "[2122222112112211111111111]\n"
                + "[2222222112112211111111111]\n";
        Assert.assertTrue(ratkoja.getRatkaisu().contains(ratkaisu2));
    }
    
    @Test
    public void permutaatioTest6() throws TyhjaListaException {
        Peli p = new Peli("1,1/2,1,1/2,1,2,1/2,2,1,1/4,3/6,3/12/2,3/15/1,9,3/1,7,3/5,3/3,1,1/1,1,1/1", "1,3/2,2/2,3/2,1,2/1,3/2,3/2,4/3,4/4,5/5,5/3,6/2,1,1/12/8/15", "vene");
        Solver ratkoja = new Solver(p);
        ratkoja.permutaatio(false);

        String ratkaisu2 = "[121111122211111]\n"
                + "[221111122111111]\n"
                + "[112211112221111]\n"
                + "[112211212211111]\n"
                + "[111111212221111]\n"
                + "[111112212221111]\n"
                + "[111112212222111]\n"
                + "[111122212222111]\n"
                + "[111222212222211]\n"
                + "[112222212222211]\n"
                + "[111122212222221]\n"
                + "[122111212111111]\n"
                + "[112222222222221]\n"
                + "[111122222222111]\n"
                + "[222222222222222]";
        Assert.assertTrue(ratkoja.getRatkaisu().contains(ratkaisu2));
    }
}
