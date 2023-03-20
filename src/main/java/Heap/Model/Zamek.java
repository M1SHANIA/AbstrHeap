package Heap.Model;

import Heap.enun.eTypKey;

public class Zamek {
    private int id;
    private String nazev;
    private String gps;



    public Zamek(int id, String nazev, String gps) {
        this.id = id;
        this.nazev = nazev;
        this.gps=gps;


    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }


    @Override
    public String toString() {
        return "Zamek{" +
                "id=" + id +
                ", nazev='" + nazev + '\'' +
                ", gps='" + gps + '\'' +
                '}';
    }
}
