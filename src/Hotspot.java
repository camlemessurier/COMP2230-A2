public class Hotspot {

    private int id;
    private double x;
    private double y;

    public Hotspot(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getID() {
        return id;
    }

    public void print() {
        System.out.println("Hotspot");
        System.out.println("id: " + id);
        System.out.println("x: " + x);
        System.out.println("y: " + y);
    }

}