import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RectangleIntersection {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        List<Rectangle> rectangles = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            int[] coordinates = Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

            int xMin = coordinates[0];
            int xMax = coordinates[1];
            int yMin = coordinates[2];
            int yMax = coordinates[3];

            Rectangle rectangle = new Rectangle(xMin, yMin, xMax, yMax);

            rectangles.add(rectangle);
        }

        boolean[][] used = new boolean[rectangles.size()][rectangles.size()];

        int totalArea = 0;

        for (int i = 0; i < rectangles.size(); i++) {
            Rectangle first = rectangles.get(i);

            for (int j = 0; j < rectangles.size(); j++) {
                Rectangle second = rectangles.get(j);

                if (i == j) {
                    continue;
                }

                if (!used[i][j] && !used[j][i]) {
                    totalArea += overlappingArea(first, second);
                    used[i][j] = true;
                    used[j][i] = true;
                }
            }
        }

        System.out.println(totalArea);
    }

    static class Rectangle {

        int xMin;
        int yMin;
        int xMax;
        int yMax;

        public Rectangle(int xMin, int yMin, int xMax, int yMax) {
            this.xMin = xMin;
            this.yMin = yMin;
            this.xMax = xMax;
            this.yMax = yMax;
        }
    }

    static int overlappingArea(Rectangle a, Rectangle b) {

        int area = 0;

        int dx = Math.min(a.xMax, b.xMax) - Math.max(a.xMin, b.xMin);
        int dy = Math.min(a.yMax, b.yMax) - Math.max(a.yMin, b.yMin);

        if (dx >= 0 && dy >= 0) {
            area = dx * dy;
        }

        return area;
    }
}
