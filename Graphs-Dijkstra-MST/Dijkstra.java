import java.util.*;

public class Dijkstra {

    public static List<Integer> dijkstraAlgorithm(int[][] graph, int sourceNode, int destinationNode) {

        int[] distances = new int[graph.length];

        int[] prev = new int[graph.length];

        boolean[] visited = new boolean[graph.length];

        Arrays.fill(distances, Integer.MAX_VALUE);

        Arrays.fill(prev, -1);

        distances[sourceNode] = 0;

        Queue<Integer> queue = new PriorityQueue<>(Comparator.comparing(node -> distances[node]));

        queue.offer(sourceNode);

        while (!queue.isEmpty()) {

            int parent = queue.poll();

            if (distances[parent] == Integer.MAX_VALUE) {
                break;
            }

            visited[parent] = true;

            for (int child = 0; child < graph[parent].length; child++) {

                if (graph[parent][child] != 0 && !visited[child]) {

                    queue.offer(child);

                    int newDistance = distances[parent] + graph[parent][child];

                    if (newDistance < distances[child]) {
                        distances[child] = newDistance;
                        prev[child] = parent;
                    }
                }
            }
        }

        if (distances[destinationNode] == Integer.MAX_VALUE) {
            return null;
        }

        List<Integer> path = new ArrayList<>();

        while (destinationNode != -1) {
            path.add(destinationNode);
            destinationNode = prev[destinationNode];
        }

        Collections.reverse(path);

        return path;
    }
}
