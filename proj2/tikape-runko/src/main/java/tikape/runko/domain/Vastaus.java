package tikape.runko.domain;

public class Vastaus {
    private int id;
    private String vastaus;
    private boolean oikein;
    private int kysymys_id;

    public Vastaus(int id, String vastaus, boolean oikein, int kysymys_id) {
        this.id = id;
        this.vastaus = vastaus;
        this.oikein = oikein;
        this.kysymys_id = kysymys_id;
    }

    public int getId() {
        return id;
    }

    public String getVastaus() {
        return vastaus;
    }

    public boolean isOikein() {
        return oikein;
    }

    public int getKysymys_id() {
        return kysymys_id;
    }
}
