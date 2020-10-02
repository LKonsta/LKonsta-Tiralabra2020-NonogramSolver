package AlgoritmitTest;


import Algoritmit.Solver;
import Nonogram.Peli;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.*;


public class MainTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    @Test
    public void SimppeliTest1() {
        Peli uusiPeli = new Peli("0/1/0", "0/1/0");
        Solver ratkoja = new Solver(uusiPeli);
        ratkoja.simppeli();
        System.out.println(ratkoja.getRatkaisu());
        String ratkaisu = ("[000]\n" +
                            "[010]\n" +
                            "[000]");
        Assert.assertTrue(outputStreamCaptor.toString().contains(ratkaisu));
    }
    
    @Test
    public void SimppeliTest2() {
        Peli uusiPeli = new Peli("1,1/1,1/1,1/1,1", "1,1/1,1/1,1/1,1");
        Solver ratkoja = new Solver(uusiPeli);
        ratkoja.simppeli();
        System.out.println(ratkoja.getRatkaisu());
        String ratkaisu = ("[1010]\n" +
                            "[0101]\n" +
                            "[1010]\n" +
                            "[0101]");
        Assert.assertTrue(outputStreamCaptor.toString().contains(ratkaisu));
    }
    
    @Test
    public void SimppeliTest3() {
        Peli uusiPeli = new Peli("2/1,2/1,2/3/4", "1/5/2/4/2,1");
        Solver ratkoja = new Solver(uusiPeli);
        ratkoja.simppeli();
        System.out.println(ratkoja.getRatkaisu());
        String ratkaisu = ("[11000]\n" +
                            "[01011]\n" +
                            "[01011]\n" +
                            "[01110]\n" +
                            "[01111]" );
        Assert.assertTrue(outputStreamCaptor.toString().contains(ratkaisu));
    }
    
//    @Test
//    public void ViisaampiTest1() {
//        Peli uusiPeli = new Peli("2/1,2/1,2/3/4", "1/5/2/4/2,1");
//        Solver ratkoja = new Solver(uusiPeli);
//        ratkoja.viisaampi();
//        System.out.println(ratkoja.getRatkaisu());
//        String ratkaisu = ("[11888]\n" +
//                            "[81811]\n" +
//                            "[81811]\n" +
//                            "[81118]\n" +
//                            "[81111]" );
//        Assert.assertTrue(outputStreamCaptor.toString().contains(ratkaisu));
//    }
    
    @Test
    public void ViisaampiTest2() {
        Peli uusiPeli = new Peli("3/2,1/3,2/2,2/6/1,5/6/1/2", "1,2/3,1/1,5/7,1/5/3/4/3");
        Solver ratkoja = new Solver(uusiPeli);
        ratkoja.viisaampi();
        System.out.println(ratkoja.getRatkaisu());
        String ratkaisu = ("[81118888]\n"
                + "[11818888]\n"
                + "[81118811]\n"
                + "[88118811]\n"
                + "[88111111]\n"
                + "[18111118]\n"
                + "[11111188]\n"
                + "[88881888]\n"
                + "[88811888]" );
        Assert.assertTrue(outputStreamCaptor.toString().contains(ratkaisu));
    }
    
}
