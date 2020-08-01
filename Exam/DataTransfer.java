import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class DataTransfer {

    static int[][] graph;
    static int[] parents;
    static int source;
    static int destination;

    public static void main(String[] args) throws IOException {
        init();
        int max = findMaxFlow();
        System.out.println(max);
    }

    static void init() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int nodes = Integer.parseInt(reader.readLine());

        graph = new int[nodes][nodes];

        int edgesCount = Integer.parseInt(reader.readLine());

        int[] connection = Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        source = connection[0];
        destination = connection[1];

        for (int i = 0; i < edgesCount; i++) {

            int[] edgeInfo = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt).toArray();

            int from = edgeInfo[0];
            int to = edgeInfo[1];
            int weight = edgeInfo[2];

            graph[from][to] = weight;
        }
    }

    public static int findMaxFlow() {
        parents = new int[graph.length];
        Arrays.fill(parents, -1);

        int maxFlow = 0;

        while (bfs()) {

            int node = destination;
            int flow = Integer.MAX_VALUE;

            while (node != source) {
                flow = Math.min(flow, graph[parents[node]][node]);
                node = parents[node];
            }

            maxFlow += flow;
            node = destination;

            while (node != source) {
                graph[parents[node]][node] -= flow;
                graph[node][parents[node]] += flow;
                node = parents[node];
            }
        }

        return maxFlow;
    }

    private static boolean bfs() {
        Deque<Integer> queue = new ArrayDeque<>();

        boolean[] visited = new boolean[graph.length];

        Arrays.fill(parents, -1);

        queue.offer(source);
        visited[source] = true;

        while (!queue.isEmpty()) {

            int node = queue.poll();

            for (int child = 0; child < graph.length; child++) {

                if (graph[node][child] > 0 && !visited[child]) {
                    visited[child] = true;
                    parents[child] = node;
                    queue.offer(child);
                }
            }
        }

        return visited[destination];
    }
}
