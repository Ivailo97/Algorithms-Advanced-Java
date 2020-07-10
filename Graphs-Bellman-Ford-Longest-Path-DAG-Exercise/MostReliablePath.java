import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MostReliablePath {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int nodes = Integer.parseInt(reader.readLine().split("\\s+")[1]);

        String[] pathInfo = reader.readLine().split("\\s+");

        int src = Integer.parseInt(pathInfo[1]);

        int dest = Integer.parseInt(pathInfo[3]);

        int edgesCount = Integer.parseInt(reader.readLine().split("\\s+")[1]);

        int[][] graph = new int[nodes][nodes];

        for (int i = 0; i < edgesCount; i++) {
            String[] tokens = reader.readLine().split("\\s+");
            int from = Integer.parseInt(tokens[0]);
            int to = Integer.parseInt(tokens[1]);
            int reliability = Integer.parseInt(tokens[2]);
            graph[from][to] = reliability;
            graph[to][from] = reliability;
        }

        Deque<Integer> shortestPath = dijkstraAlgorithm(graph, src, dest);

        while (!shortestPath.isEmpty()) {

            System.out.print(shortestPath.pop());

            if (shortestPath.size() > 0){
                System.out.print(" -> ");
            }
        }
    }

    public static Deque<Integer> dijkstraAlgorithm(int[][] graph, int sourceNode, int destinationNode) {

        double[] reliabilities = new double[graph.length];

        int[] prev = new int[graph.length];

        boolean[] visited = new boolean[graph.length];

        reliabilities[sourceNode] = 100.00;

        Arrays.fill(prev, -1);

        Queue<Integer> queue = new PriorityQueue<>((f, s) -> Double.compare(reliabilities[s], reliabilities[f]));

        queue.offer(sourceNode);

        while (!queue.isEmpty()) {

            int parent = queue.poll();

            visited[parent] = true;

            for (int child = 0; child < graph[parent].length; child++) {

                int weight = graph[parent][child];

                if (weight != 0 && !visited[child]) {

                    double newReliability = reliabilities[parent] * weight / 100.00;

                    if (newReliability > reliabilities[child]) {
                        reliabilities[child] = newReliability;
                        prev[child] = parent;
                    }

                    queue.offer(child);
                }
            }
        }

        System.out.printf("Most reliable path reliability: %.2f%%%n", reliabilities[destinationNode]);

        Deque<Integer> path = new ArrayDeque<>();

        while (destinationNode != -1) {
            path.push(destinationNode);
            destinationNode = prev[destinationNode];
        }

        return path;
    }
}
