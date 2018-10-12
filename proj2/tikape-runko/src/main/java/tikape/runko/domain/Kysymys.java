package tikape.runko.domain;

public class Kysymys {
    private int id;
    private String kurssi;
    private String aihe;
    private String kysymysteksti;

    public Kysymys(int id, String kurssi, String aihe, String kysymysteksti) {
        this.id = id;
        this.kurssi = kurssi;
        this.aihe = aihe;
        this.kysymysteksti = kysymysteksti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAihe() {
        return aihe;
    }

    public void setAihe(String aihe) {
        this.aihe = aihe;
    }

    public String getKurssi() {
        return kurssi;
    }

    public void setKurssi(String kurssi) {
        this.kurssi = kurssi;
    }

    public void setKysymysteksti(String kysymysteksti) {
        this.kysymysteksti = kysymysteksti;
    }

    public String getKysymysteksti() {
        return kysymysteksti;
    }

    
    
    
}
