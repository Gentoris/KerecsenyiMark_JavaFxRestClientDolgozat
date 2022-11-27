package hu.petrik.kerecsenyimark_javafxrestclientdolgozat;

import com.google.gson.annotations.Expose;

public class Wow {
    private int id;
    @Expose
    private String nev;
    @Expose
    private String osztaly;
    @Expose
    private int lv;
    @Expose
    private boolean noob;

    public Wow(int id, String nev, String osztaly, int lv, boolean noob) {
        this.id = id;
        this.nev = nev;
        this.osztaly = osztaly;
        this.lv = lv;
        this.noob = noob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getOsztaly() {
        return osztaly;
    }

    public void setOsztaly(String osztaly) {
        this.osztaly = osztaly;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public boolean isNoob() {
        return noob;
    }

    public void setNoob(boolean noob) {
        this.noob = noob;
    }
}
