import java.util.*;

public class KruskalAlgorithm {

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
