import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Undefined {

    static class Edge {

        int from;

        int to;

        int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    static List<Edge> edges = new ArrayList<>();

    static int nodes;

    static int source;

    static int destination;

    public static void main(String[] args) throws IOException {

        init();

        try {
            bellmanFord();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }

    static void bellmanFord() {

        int[] distance = new int[nodes + 1];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        int[] prev = new int[nodes + 1];
        prev[source] = -1;

        for (int i = 0; i < nodes - 1; i++) {

            for (Edge edge : edges) {

                int from = edge.from;
                int to = edge.to;
                int weight = edge.weight;

                if (distance[from] != Integer.MAX_VALUE && distance[from] + weight < distance[to]) {
                    distance[to] = distance[from] + weight;
                    prev[to] = from;
                }
            }
        }

        for (Edge edge : edges) {

            int from = edge.from;
            int to = edge.to;
            int weight = edge.weight;

            if (distance[from] != Integer.MAX_VALUE && distance[from] + weight < distance[to]) {
                throw new IllegalStateException("Undefined");
            }
        }

        //Reconstruct path
        List<Integer> path = new ArrayList<>();

        int last = destination;

        while (prev[last] != -1) {
            path.add(last);
            last = prev[last];
        }

        path.add(last);

        Collections.reverse(path);

        for (Integer vertex : path) {
            System.out.print(vertex + " ");
        }
        System.out.println();
        System.out.println(distance[destination]);
    }

    static void init() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        nodes = Integer.parseInt(reader.readLine());
        int edgesCount = Integer.parseInt(reader.readLine());

        for (int i = 0; i < edgesCount; i++) {

            int[] edgeInfo = Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
            Edge edge = new Edge(edgeInfo[0], edgeInfo[1], edgeInfo[2]);
            edges.add(edge);
        }

        source = Integer.parseInt(reader.readLine());
        destination = Integer.parseInt(reader.readLine());
    }
}
