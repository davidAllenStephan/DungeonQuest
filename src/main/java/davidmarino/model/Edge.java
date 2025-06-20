package davidmarino.model;

public class Edge {
    public Room from, to;
    public double weight;

    public Edge(Room from, Room to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return from + " --(" + weight + ")--> " + to;
    }
}
