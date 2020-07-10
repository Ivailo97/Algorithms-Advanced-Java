import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheapTownTour {

    public static class Edge implements Comparable<Edge> {

        private int startNode;
        private int endNode;
        private int weight;

        public Edge(int from, int to, int weight) {
            this.startNode = from;
            this.endNode = to;
            this.weight = weight;
        }

        public int getStartNode() {
            return startNode;
        }

        public int getEndNode() {
            return endNode;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int vertices = Integer.parseInt(reader.readLine());

        int edgesCount = Integer.parseInt(reader.readLine());

        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < edgesCount; i++) {

            String[] tokens = reader.readLine().split(" - ");

            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            int weight = Integer.parseInt(tokens[2]);

            Edge edge = new Edge(from, to, weight);
            edges.add(edge);
        }

        List<Edge> mst = kruskal(vertices, edges);

        System.out.println("Total cost: " + mst.stream().mapToInt(Edge::getWeight).sum());
    }

    public static List<Edge> kruskal(int numberOfVertices, List<Edge> edges) {

        List<Edge> forest = new ArrayList<>();

        Collections.sort(edges);

        int[] parent = new int[numberOfVertices];

        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        while (!edges.isEmpty()) {

            Edge edge = edges.remove(0);

            int source = edge.getStartNode();
            int destination = edge.getEndNode();

            int firstRoot = findRoot(source, parent);
            int secondRoot = findRoot(destination, parent);

            if (firstRoot != secondRoot) {
                forest.add(edge);
                parent[firstRoot] = secondRoot;
            }
        }

        return forest;
    }

    public static int findRoot(int node, int[] parent) {

        int root = node;

        while (parent[root] != root) {
            root = parent[root];
        }

        //Disjoint Sets Optimization
        while (node != root) {
            int oldParent = parent[node];
            parent[node] = root;
            node = oldParent;
        }

        return node;
    }
}
