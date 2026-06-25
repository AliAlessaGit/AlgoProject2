package Back_End;

public class Edge {
    Station destination;
    int weight;

    Edge(Station destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public Edge(String destination, int weight) {
        this.destination = new Station(destination);
        this.weight = weight;
    }
}