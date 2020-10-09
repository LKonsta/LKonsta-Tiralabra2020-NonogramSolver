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
    public Kentta(Integer l, Integer k) {
        this.alue = new Integer[l][k];

        this.korkeus = k;
        this.leveys = l;
        ykkoset = 0;
        for (int i = 0; i < leveys; i++) {
            for (int j = 0; j < korkeus; j++) {
                alue[i][j] = 0;
            }
        }
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
        Integer[] palaute = new Integer[alue.length];
        for (int j = 0; j < alue.length; j++) {
            palaute[j] = alue[j][i];
        }
        return palaute;
    }
    
    @Override
    public String toString() {
        String palaute = "";
        for (int i = 0; i < alue.length; i++) {
            palaute += "[";
            for (int j = 0; j < alue[i].length; j++) {
                palaute = palaute + alue[i][j];
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
