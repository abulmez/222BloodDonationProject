package model;

public class Schedule {

    private String ora;
    private Integer availablespots;

    public Schedule(String ora, Integer availablespots) {
        this.ora = ora;
        this.availablespots = availablespots;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public Integer getAvailablespots() {
        return availablespots;
    }

    public void setAvailablespots(Integer availablespots) {
        this.availablespots = availablespots;
    }
}
