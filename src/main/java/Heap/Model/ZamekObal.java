package Heap.Model;

public class ZamekObal implements Comparable<ZamekObal> {
    private Zamek zamek;
    private double vzdalenost ;

    public ZamekObal(Zamek zamek, double vzdalenost) {
        this.zamek = zamek;
        this.vzdalenost = vzdalenost;
    }

    public ZamekObal() {
    }

    public Zamek getZamek() {
        return zamek;
    }

    public void setZamek(Zamek zamek) {
        this.zamek = zamek;
    }

    public double getVzdalenost() {
        return vzdalenost;
    }

    public void setVzdalenost(double vzdalenost) {
        this.vzdalenost = vzdalenost;
    }

    @Override
    public String toString() {
        return zamek +
                ", vzdalenost=" + vzdalenost +
                '}';
    }

    @Override
    public int compareTo(ZamekObal o) {
        return Double.compare(vzdalenost,o.vzdalenost);
    }
}
