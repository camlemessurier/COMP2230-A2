import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        try {
            if (args.length != 2) {
                throw new Exception("ERROR: program needs two input parameters");
            }

            if (!args[0].contains(".txt")) {
                throw new Exception("ERROR: file must be a txt file");
            }
            if (!(args[1].matches(".*\\d.*"))) {
                throw new Exception("ERROR: second input must be an integer");
            }
            String filename = args[0];
            int numOfFireStations = Integer.parseInt(args[1]);
            if (numOfFireStations < 2) {
                throw new Exception("ERROR: There must be more than two firestations");
            }
            ArrayList<Hotspot> hotspots = getHotspotsFromFile(filename);
            Session session = new Session(hotspots, numOfFireStations);
            session.run();

        } catch (Exception e) {
            System.err.println("\n" + e.getMessage());
            System.err.println("USAGE: java kcluster [file_name].txt [int: number of firestations wanted]");
            System.exit(0);
        }

    }

    private static ArrayList<Hotspot> getHotspotsFromFile(String filename) {

        ArrayList<Hotspot> hotspots = new ArrayList<>();

        try {
            File inputFile = new File(filename);
            Scanner sc = new Scanner(inputFile);
            sc.useDelimiter("([,\\n])");

            while (sc.hasNextLine()) {
                int id = sc.nextInt();
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                Hotspot hotspot = new Hotspot(id, x, y);
                if (hotspots.contains(hotspot)) {
                    throw new Exception("ERROR: Input file cannot handle duplicate entries");
                }
                if (id < 0 || x < 0 || y < 0) {
                    throw new Exception("ERROR: Input file has negative entries");
                } else {
                    hotspots.add(hotspot);
                }
            }
            sc.close();
        } catch (Exception e) {
            System.err.println("\n" + e.getMessage());
            System.err.println("USAGE: [int: id], [double: x], [double: y] // new line for next entry ");
            System.exit(0);
        }

        return hotspots;

    }
}
