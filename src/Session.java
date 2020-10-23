import java.util.ArrayList;
import java.util.Collections;

public class Session {

    private ArrayList<Hotspot> hotspots;
    private ArrayList<Edge> edges;
    private int numberOfFireStations;
    private double[][] weightedGraph;

    private ArrayList<ArrayList<Hotspot>> trees;
    private ArrayList<Station> stations;

    Session(ArrayList<Hotspot> hotspots, int numberOfFireStations) {
        System.out.println("Session");
        this.hotspots = hotspots;
        this.numberOfFireStations = numberOfFireStations;
        this.weightedGraph = new double[hotspots.size()][hotspots.size()];
        this.edges = new ArrayList<Edge>();
        this.trees = new ArrayList<ArrayList<Hotspot>>();
    }

    public void run() {
        printHello();
        setWeightedGraph();
        printWeightedGraph();
        printInfo();
        kruskals();
        ArrayList<Station> stations = generateStations(numberOfFireStations);
        printStations(stations);
        System.out.print("Inter-clustering distance: " + interClusteringDistance(stations));
        printGoodbye();

    }

    private void printGoodbye() {
        System.out.println("Thank you for using Kruskal’s Clustering. Bye.");
    }

    private void printHello() {
        System.out.println("Hello and welcome to Kruskal’s Clustering! \n \n ");
    }

    private void printInfo() {
        System.out.println("There are " + hotspots.size() + " hotspots.");
        System.out.println("You have requested " + numberOfFireStations + " temporary fire stations. \n \n");

    }

    private void printStations(ArrayList<Station> stations) {
        for (Station station : stations) {
            System.out.print(station + "\n\n");
        }
    }

    private void setWeightedGraph() {

        for (int i = 0; i < hotspots.size(); i++) {
            for (int j = 0; j < hotspots.size(); j++) {
                double distance = calculateDistance(hotspots.get(i), hotspots.get(j));
                weightedGraph[i][j] = distance;

                if (i > j) {
                    edges.add(new Edge(hotspots.get(i), hotspots.get(j), distance));
                }

            }
        }
        Collections.sort(edges);
    }

    private double interClusteringDistance(ArrayList<Station> stations) {
        // Ensure that there are at least 2 stations
        if (stations.size() < 2) {
            throw new IllegalArgumentException();
        }

        double interCd = Double.POSITIVE_INFINITY;
        ArrayList<Hotspot> s1Cluster = stations.get(0).getHotspots();
        for (int i = 1; i < stations.size(); i++) {
            ArrayList<Hotspot> s2Cluster = stations.get(i).getHotspots();
            for (Hotspot h : s1Cluster) {
                for (Hotspot j : s2Cluster) {
                    if (calculateDistance(h, j) < interCd) {
                        interCd = calculateDistance(h, j);
                    }
                }
            }
        }

        // Return the inter-clustering distance
        return interCd;
    }

    private double calculateDistance(Hotspot i, Hotspot j) {
        double distance = Math.sqrt(Math.pow(i.getX() - j.getX(), 2) + Math.pow(i.getY() - j.getY(), 2));
        return distance;
    }

    private void printWeightedGraph() {
        System.out.println("The weighted graph of hotspots: \n \n");
        for (int i = 0; i < weightedGraph.length; i++) {
            for (int j = 0; j < weightedGraph.length; j++) {
                System.out.print(String.format("%.2f", weightedGraph[i][j]) + " ");
            }
            System.out.println("");
        }
    }

    private void kruskals() {

        for (Hotspot hotspot : hotspots) {
            ArrayList<Hotspot> tree = makeset(hotspot);
            trees.add(tree);
        }

        for (Edge e : edges) {

            if (trees.size() == numberOfFireStations) {
                break;
            }

            if (findset(e.getH1()) != findset(e.getH2())) {
                union(e.getH2(), e.getH1());
            }

        }
    }

    private ArrayList<Hotspot> makeset(Hotspot hotspot) {

        ArrayList<Hotspot> set = new ArrayList<>();
        set.add(hotspot);
        return set;
    }

    private ArrayList<Hotspot> findset(Hotspot hotspot) {

        for (ArrayList<Hotspot> tree : trees) {
            if (tree.contains(hotspot)) {
                return tree;
            }
        }

        return null;
    }

    private void union(Hotspot v1, Hotspot v2) {
        // Find the two disjoint sets containing v1 and v2, respectively
        ArrayList<Hotspot> v1Set = findset(v1);
        ArrayList<Hotspot> v2Set = findset(v2);

        // Remove the two disjoint sets from the list of disjoint sets
        trees.remove(v1Set);
        trees.remove(v2Set);

        // Make a new disjoint set and merge the two previously found disjoint sets
        ArrayList<Hotspot> tree = new ArrayList<>();
        tree.addAll(v1Set);
        tree.addAll(v2Set);

        // Add the new disjoint set to the list of disjoint sets
        trees.add(tree);
    }

    private ArrayList<Station> generateStations(int numberOfStations) {

        ArrayList<Station> stations = new ArrayList<>();
        for (int i = 0; i < trees.size(); i++) {

            Station station = new Station(i, trees.get(i));
            stations.add(station);
        }

        // Return the stations
        return stations;
    }

}