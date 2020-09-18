package Nonogram;

public class Kentta {
    private Integer[][] alue;
    private Integer korkeus;
    private Integer leveys;
    private Integer ykkoset;
    
    public Kentta(Integer leveys, Integer korkeus) {
        this.alue = new Integer[leveys][korkeus];

        this.korkeus = leveys;
        this.leveys = korkeus;
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
    public void setKohta(int i, int j, int k) {
        if (k == 1 && alue[i][j] == 0) {
            ykkoset++;
        } else if (alue[i][j] == 1 && k == 0) {
            ykkoset--;
        }
        alue[i][j] = k;
    }
    
    @Override
    public String toString() {
        String palaute = "";
        for (int i = 0; i < alue.length; i++) {
            palaute += "[";
            for (int j = 0; j < alue[i].length; j++) {
                palaute = palaute + alue[i][j];
            }
            palaute += "]\n";
        }
        return palaute;
    }


}
