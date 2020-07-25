import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class ZigZagMatrix {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int rows = Integer.parseInt(reader.readLine());
        int cols = Integer.parseInt(reader.readLine());

        int[][] matrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            matrix[i] = Arrays.stream(reader.readLine().split(",")).mapToInt(Integer::parseInt).toArray();
        }

        int[][] maxDP = new int[rows][cols];
        int[][] prev = new int[rows][cols];

        for (int row = 1; row < rows; row++) {
            maxDP[row][0] = matrix[row][0];
        }

        for (int col = 1; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                int prevMax = 0;
                if (col % 2 != 0) {
                    for (int i = row + 1; i < rows; i++) {
                        if (maxDP[i][col - 1] > prevMax) {
                            prevMax = maxDP[i][col - 1];
                            prev[row][col] = i;
                        }
                    }
                } else {
                    for (int i = 0; i < row; i++) {
                        if (maxDP[i][col - 1] > prevMax) {
                            prevMax = maxDP[i][col - 1];
                            prev[row][col] = i;
                        }
                    }
                }
                maxDP[row][col] = prevMax + matrix[row][col];
            }
        }

        List<Integer> result = new ArrayList<>();

        int index = cols - 1;
        int rowIndex = 0;
        int max = -1;

        for (int row = 0; row < maxDP.length; row++) {
            if (maxDP[row][index] > max) {
                rowIndex = row;
                max = maxDP[row][index];
            }
        }

        while (index >= 0) {
            result.add(matrix[rowIndex][index]);
            rowIndex = prev[rowIndex][index];
            index--;
        }

        Collections.reverse(result);

        String output = result.stream()
                .mapToInt(e -> e)
                .sum() + " = " + result.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" + "));

        System.out.println(output);
    }
}
