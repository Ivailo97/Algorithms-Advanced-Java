import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class CableNetwork {

    static Map<Integer, List<Edge>> graph = new LinkedHashMap<>();

    static class Edge implements Comparable<Edge> {

        private int from;

        private int to;

        private int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    static int cost;

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int budget = Integer.parseInt(reader.readLine().split("\\s+")[1]);
        int nodes = Integer.parseInt(reader.readLine().split("\\s+")[1]);
        int edgesCount = Integer.parseInt(reader.readLine().split("\\s+")[1]);

        boolean[] used = new boolean[nodes];

        for (int i = 0; i < edgesCount; i++) {

            String[] tokens = reader.readLine().split("\\s+");

            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            int weight = Integer.parseInt(tokens[2]);

            Edge edge = new Edge(from, to, weight);

            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(edge);

            if (tokens.length == 4) {
                used[from] = used[to] = true;
            }
        }

        prim(used, budget);

        System.out.println("Budget used: " + cost);
    }

    static void prim(boolean[] used, int budget) {

        PriorityQueue<Edge> edges = graph.values()
                .stream().flatMap(List::stream)
                .filter(e -> !used[e.from] && used[e.to] || (!used[e.to] && used[e.from]))
                .collect(Collectors.toCollection(PriorityQueue::new));

        while (!edges.isEmpty()) {

            Edge minEdge = edges.poll();

            int from = minEdge.from;
            int to = minEdge.to;
            int weight = minEdge.weight;

            int removedValue = -1;

            if (used[from] && !used[to]) {
                used[to] = true;
                removedValue = weight;
            } else if (!used[from] && used[to]) {
                used[from] = true;
                removedValue = weight;
            }

            edges = graph.values()
                    .stream().flatMap(List::stream)
                    .filter(e -> !used[e.from] && used[e.to] || (!used[e.to] && used[e.from]))
                    .collect(Collectors.toCollection(PriorityQueue::new));

            if (removedValue != -1 && budget - removedValue > 0) {
                budget -= removedValue;
                cost += removedValue;
            } else if (budget - removedValue < 0) {
                return;
            }
        }
    }
}
