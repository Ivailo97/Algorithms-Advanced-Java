import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SupplementGraphToMakeItStronglyConnected {

    public static boolean[][] graph;
    public static boolean[][] reversedGraph;
    public static boolean[] visited;
    public static Deque<Integer> stack = new ArrayDeque<>();
    public static List<List<Integer>> components = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int nodes = Integer.parseInt(reader.readLine().split(": ")[1]);
        int edges = Integer.parseInt(reader.readLine().split(": ")[1]);
        graph = new boolean[nodes][nodes];
        reversedGraph = new boolean[nodes][nodes];
        visited = new boolean[nodes];

        for (int i = 0; i < edges; i++) {
            String[] edgeInfo = reader.readLine().split(" -> ");
            int src = Integer.parseInt(edgeInfo[0]);
            int dst = Integer.parseInt(edgeInfo[1]);
            graph[src][dst] = true;
            reversedGraph[dst][src] = true;
        }

        for (int node = 0; node < nodes; node++) {
            if (!visited[node]) {
                dfs(node);
            }
        }

        Arrays.fill(visited, false);

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (!visited[node]) {
                components.add(new ArrayList<>());
                reversedDfs(node);
            }
        }

        boolean[][] DAG = new boolean[components.size()][components.size()];

        for (int i = 0; i < components.size(); i++) {
            List<Integer> subGraph = components.get(i);
            for (Integer element : subGraph) {
                for (int j = 0; j < nodes; j++) {
                    if (graph[element][j] && j != element) {
                        for (int k = 0; k < components.size(); k++) {
                            if (components.get(k).contains(j) && k != i) {
                                DAG[i][k] = true;
                            }
                        }
                    }
                }
            }
        }

        int zeroIncomingDegree = 0;
        for (int col = 0; col < DAG.length; col++) {
            boolean hasEdge = false;
            for (int row = 0; row < DAG[col].length; row++) {
                hasEdge = DAG[row][col];
                if (hasEdge) {
                    break;
                }
            }
            if (!hasEdge) {
                zeroIncomingDegree++;
            }
        }

        int zeroOutgoingDegree = 0;
        for (int row = 0; row < DAG.length; row++) {
            boolean hasEdge = false;
            for (int col = 0; col < DAG[row].length; col++) {
                hasEdge = DAG[row][col];
                if (hasEdge) {
                    break;
                }
            }
            if (!hasEdge) {
                zeroOutgoingDegree++;
            }
        }

        System.out.println(Math.max(zeroIncomingDegree, zeroOutgoingDegree));
    }

    private static void reversedDfs(int node) {
        if (!visited[node]) {
            visited[node] = true;
            components.get(components.size() - 1).add(node);
            for (int i = 0; i < reversedGraph[node].length; i++) {
                if (reversedGraph[node][i]) {
                    reversedDfs(i);
                }
            }
        }
    }

    private static void dfs(int node) {
        if (!visited[node]) {
            visited[node] = true;
            for (int i = 0; i < graph[node].length; i++) {
                if (graph[node][i]) {
                    dfs(i);
                }
            }
            stack.push(node);
        }
    }
}
