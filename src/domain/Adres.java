package domain;

public class Adres {
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reisigerid;

    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, int reisigerid) {
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat=straat;
        this.woonplaats=woonplaats;
        this.reisigerid=reisigerid;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public int getReisigerid() {
        return reisigerid;
    }

    public void setReisigerid(int reisigerid) {
        this.reisigerid = reisigerid;
    }

    public Adres(String straat) {
        this.straat = straat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return "Adres{" +
                "id=" + id +
                ", postcode='" + postcode + '\'' +
                '}';
    }


}

