public class Edge implements Comparable<Edge> {

    private Hotspot h1;
    private Hotspot h2;
    private double weight;

    Edge(Hotspot h1, Hotspot h2, double weight) {
        this.h1 = h1;
        this.h2 = h2;
        this.weight = weight;
    }

    public Hotspot getH1() {
        return h1;
    }

    public Hotspot getH2() {
        return h2;
    }

    public double getWeight() {
        return weight;
    }

    public void printEdge() {
        System.out.println("Edge");
        System.out.println("h1: " + h1.getID());
        System.out.println("h2: " + h2.getID());
        System.out.println(weight);

    }

    @Override
    public int compareTo(Edge edge) {
        if (weight < edge.getWeight()) {
            return -1;
        } else if (weight > edge.getWeight()) {
            return 1;
        } else {
            return 0;
        }
    }

}
