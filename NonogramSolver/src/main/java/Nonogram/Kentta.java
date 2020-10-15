package Nonogram;

/**
 * kenttä jolla peliä pelataan
 *
 */
public class Kentta {
    
    private Integer[][] alue;
    private Integer korkeus;
    private Integer leveys;
    private Integer ykkoset;
    
    /**
     * kenttä luodaan korkeudella ja leveydellä. kenttä myös pohjustetaan nollilla.
     *
     * @param l leveys
     * @param k korkeus
     */
    public Kentta(Integer k, Integer l) {
        this.alue = new Integer[l][k];

        this.korkeus = l;
        this.leveys = k;
//        System.out.println("kenttä alustettaan: " + k + ", " + l);
        ykkoset = 0;
        for (int i = 0; i < leveys; i++) {
            for (int j = 0; j < korkeus; j++) {
                alue[j][i] = 0;
            }
        }
//        System.out.println(this);
    }
    
    public Integer getYkkoset() {
        return ykkoset;
    }

    public Integer[][] getKentta() {
        return alue;
    }
    
    public void setKentta(Integer[][] kentta) {
        alue = kentta;
    }

    public Integer getKorkeus() {
        return korkeus;
    }

    public Integer getLeveys() {
        return leveys;
    }
    public int getKohta(int i, int j) {
//        System.out.println("haetaan kohtaa (" + i + ", " + j + ")");
        return alue[i][j];
    }
    
    /**
     * 
     * @param i x koordinaatti
     * @param j y koordinaatti
     * @param k numero joka asetetaan x ja y koordinaatteihin
     */
    public void setKohta(int i, int j, int k) {
        if (k == 1 && alue[i][j] == 0) {
            ykkoset++;
        } 
        else if (alue[i][j] == 1 && k == 0) {
            ykkoset--;
        }
        alue[i][j] = k;
    }
    
    public Integer[] getRivi(int i) {
        return alue[i];
    }
    
    public Integer[] getSarake(int i) {
        Integer[] palaute = new Integer[korkeus];
//        System.out.println(palaute.length);
        for (int j = 0; j < korkeus; j++) {
//            System.out.println("(" + j + ", " + i + ")");
            palaute[j] = alue[j][i];
        }
        return palaute;
    }
    
    @Override
    public String toString() {
        String palaute = "";
        for (int i = 0; i < alue.length; i++) {
            palaute += "[";
            for (Integer item : alue[i]) {
                palaute = palaute + item;
            }
            if (i+1==alue.length) {
                palaute += "]";
            } else {
                palaute += "]\n";
            }
            
        }
        return palaute;
    }

    


}
