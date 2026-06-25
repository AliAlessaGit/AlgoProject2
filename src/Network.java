import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Network {

    private Map<Station, ArrayList<Edge>> network;

    public Map<Station, ArrayList<Edge>> getNetwork() {
        return network;
    }

    public void setnetwork(Map<Station, ArrayList<Edge>> network) {
        this.network = network;
    }

    public Network() {
        network = new HashMap<>();
    }

    public void put(String key, ArrayList<Edge> value) {
        network.put(new Station(key), value);
    }

    public List<Edge> get(String k) {
        Station key = new Station(k);
        return network.get(key);
    }

    // إضافة محطة
    public void addStation(Station station) {
        network.putIfAbsent(station, new ArrayList<>());
    }

    // إضافة مسار
    public void addRoute(String source, String destination, int distance) {
        Station src = new Station(source);
        Station dest = new Station(destination);
        addStation(src);
        addStation(dest);

        network.get(src).add(new Edge(dest, distance));

    }

    public String exportNetwork() {

        StringBuilder sb = new StringBuilder();

        for (Station station : network.keySet()) {

            sb.append(station.name).append(" -> ");

            ArrayList<Edge> edges = network.get(station);

            for (int i = 0; i < edges.size(); i++) {

                Edge edge = edges.get(i);

                sb.append(edge.destination)
                        .append("(")
                        .append(edge.weight)
                        .append(")");

                if (i < edges.size() - 1)
                    sb.append(", ");
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    public void importNetwork(String text) {

        String[] lines = text.split("\n");

        for (String line : lines) {

            if (line.trim().isEmpty())
                continue;

            String[] parts = line.split("->");

            String source = parts[0].trim();

            addStation(new Station(source));

            if (parts.length < 2)
                continue;

            String destinations = parts[1].trim();

            if (destinations.isEmpty())
                continue;

            String[] routes = destinations.split(",");

            for (String route : routes) {

                route = route.trim();

                int open = route.indexOf('(');
                int close = route.indexOf(')');

                String destination = route.substring(0, open).trim();

                int distance = Integer.parseInt(
                        route.substring(open + 1, close));

                addRoute(source, destination, distance);
            }
        }
    }

    public Station chooseStation(Scanner input) {

        ArrayList<Station> stations = new ArrayList<>(network.keySet());

        if (stations.isEmpty()) {
            System.out.println("No stations found.");
            return null;
        }

        System.out.println("\nStations:");

        for (int i = 0; i < stations.size(); i++) {
            System.out.println((i + 1) + ". " + stations.get(i).name);
        }

        System.out.print("Choose station number: ");
        int choice = input.nextInt();

        Station station = stations.get(choice - 1);

        System.out.println("Selected station: " + station.name);

        return station;
    }

    public void loadSampleData() {

        String text = "Damascus -> Homs(120), Daraa(90)\n" +
                "Homs -> Tartous(95), Aleppo(180)";

        importNetwork(text);
    }

    public void printNetwork() {

        for (Station station : network.keySet()) {

            System.out.print(station.name + " -> ");

            ArrayList<Edge> edges = network.get(station);

            if (edges.isEmpty()) {
                System.out.println("No Connections");
                continue;
            }

            for (int i = 0; i < edges.size(); i++) {

                Edge edge = edges.get(i);

                System.out.print(
                        edge.destination.name +
                                "(" + edge.weight + ")");

                if (i < edges.size() - 1)
                    System.out.print(", ");
            }

            System.out.println();
        }
    }

    // Third Request (Printing)
    public void readNetworkFromFile() throws IOException {

        Path path = Paths.get("ReadingFile.txt");

        String content = Files.readString(path);
        importNetwork(content);

    }

    public void writeToFile() throws IOException{
        String content = asciiPrint("Damascus");
        Path path = Paths.get("WritingFile.txt");
        Files.writeString(path, content, StandardOpenOption.CREATE);
    }

    public void readAndPrintInFile() {
        try {
            readNetworkFromFile();

        } catch (Exception e) {
            System.out.println("Exception handled");

        }

        try {
            writeToFile();
        } catch (IOException e) {
            System.out.println("Exception handled");
        }
    }

    public String asciiPrint(String start) {
        Set<String> visited = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        sb.append(start).append("\n");
        visited.add(start);

        printAscii(start, "", visited, sb);

        return sb.toString();
    }

    private void printAscii(String node, String prefix, Set<String> visited, StringBuilder sb) {

        List<Edge> neighbors = network.get(new Station(node));
        if (neighbors == null || neighbors.isEmpty())
            return;

        for (int i = 0; i < neighbors.size(); i++) {

            Edge edge = neighbors.get(i);
            boolean isLast = (i == neighbors.size() - 1);

            sb.append(prefix)
                    .append(isLast ? "└── " : "├── ")
                    .append("(").append(edge.weight).append(") ")
                    .append(edge.destination.name)
                    .append("\n");

            if (!visited.contains(edge.destination.name)) {
                visited.add(edge.destination.name);
                printAscii(
                        edge.destination.name,
                        prefix + (isLast ? "    " : "│   "),
                        visited,
                        sb);
            }
        }
    }
    // --------------

    public int shortestPath(String source, String destination) {
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, Integer> distancesMap = new HashMap<>();
        PriorityQueue<Node> distancesQueue = new PriorityQueue<>(
                Comparator.comparingInt(n -> n.distanceFromsource));

        Node sourceNode = new Node(source, 0);
        distancesQueue.add(sourceNode);
        distancesMap.put(source, 0);
        visited.put(source, false);

        String ch;
        for (Station st : network.keySet()) {
            ch = st.name;
            if (ch.equals(source)) {
                continue;
            }
            distancesMap.put(ch, Integer.MAX_VALUE);
            distancesQueue.add(new Node(ch, Integer.MAX_VALUE));
            visited.put(ch, false);
        }

        Node minNode;
        String currentCity;
        int newPathWeight;
        while (true) {
            minNode = distancesQueue.poll();
            currentCity = minNode.data;
            if (visited.get(currentCity) || distancesMap.get(currentCity) != minNode.distanceFromsource) {
                continue;
            }

            visited.put(currentCity, true);

            if (currentCity.equals(destination)) {
                return distancesMap.get(destination);
            }

            for (Edge edge : this.get(currentCity)) {
                newPathWeight = distancesMap.get(currentCity) + edge.weight;
                if (newPathWeight < distancesMap.get(edge.destination.name)) {
                    distancesMap.put(edge.destination.name, newPathWeight);
                    distancesQueue.add(new Node(edge.destination.name, newPathWeight));
                }
            }
        }
    }

    public boolean containsCycle() {
        Map<String, Integer> deg = new HashMap<>();
        PriorityQueue<Degree> degreesQueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.deg));

        for (Station station : network.keySet()) {
            deg.put(station.name, 0);
        }

        for (Station station : network.keySet()) {
            for (Edge edge : network.get(station)) {
                deg.put(edge.destination.name, deg.get(edge.destination.name) + 1);
            }
        }

        for (String station : deg.keySet()) {
            degreesQueue.add(new Degree(station, deg.get(station)));
        }

        Degree stWithMinDegree;
        String currentStation;
        int newDeg;
        while (true) {
            stWithMinDegree = degreesQueue.poll();
            currentStation = stWithMinDegree.stationName;
            if (stWithMinDegree.deg != 0) {
                break;
            }

            deg.put(currentStation, -1);

            for (Edge edge : network.get(new Station(currentStation))) {
                newDeg = deg.get(edge.destination.name) - 1;
                deg.put(edge.destination.name, newDeg);
                degreesQueue.add(new Degree(edge.destination.name, newDeg));
            }
        }

        for (String st : deg.keySet()) {
            if (deg.get(st) != -1) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Station> sort() {
        Map<String, Integer> deg = new HashMap<>();
        PriorityQueue<Degree> degreesQueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.deg));

        for (Station station : network.keySet()) {
            deg.put(station.name, network.get(station).size());
        }

        for (Station station : network.keySet()) {
            for (Edge edge : network.get(station)) {
                deg.put(edge.destination.name, deg.get(edge.destination.name) + 1);
            }
        }

        for (String station : deg.keySet()) {
            degreesQueue.add(new Degree(station, deg.get(station)));
        }

        ArrayList<Station> result = new ArrayList<>();
        while (!degreesQueue.isEmpty()) {
            result.add(new Station(degreesQueue.poll().stationName));
        }

        return result;
    }

}