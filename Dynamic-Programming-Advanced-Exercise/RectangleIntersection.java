import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class RectangleIntersection {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        int[][] matrix = new int[2001][2001];
        int result = 0;

        for (int i = 0; i < n; i++) {

            int[] coordinates = Arrays.stream(reader.readLine().split("\\s+")).mapToInt(x -> Integer.parseInt(x) + 1000).toArray();

            int minX = coordinates[0];
            int maxX = coordinates[1];
            int minY = coordinates[2];
            int maxY = coordinates[3];

            for (int x = minX; x < maxX; x++) {
                for (int y = minY; y < maxY; y++) {
                    matrix[y][x] += 1;
                }
            }
        }

        for (int[] row : matrix) {
            for (int col : row) {
                if (col > 1) {
                    result++;
                }
            }
        }

        System.out.println(result);
    }
}
