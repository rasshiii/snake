import java.util.*;

class SnakeLadder {
    static class Node {
        int vertex;
        int distance;

        Node(int v, int d) {
            vertex = v;
            distance = d;
        }
    }

    private static final int BOARD_SIZE = 100;

    public static int minMoves(int[] moves) {
        boolean[] visited = new boolean[BOARD_SIZE];
        Queue<Node> queue = new LinkedList<>();
        visited[0] = true;
        queue.add(new Node(0, 0));

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            int v = node.vertex;

            if (v == BOARD_SIZE - 1) {
                return node.distance;
            }

            for (int i = v + 1; i <= (v + 6) && i < BOARD_SIZE; ++i) {
                if (!visited[i]) {
                    visited[i] = true;
                    int nextVertex = (moves[i] != -1) ? moves[i] : i;
                    queue.add(new Node(nextVertex, node.distance + 1));
                }
            }
        }
        return -1;
    }

    public static int[] analyzeImpact(int[] moves, int start, int end, boolean isSnake) {
        int originalMoves = minMoves(moves);

        if (isSnake) {
            moves[start] = end;
        } else {
            moves[start] = end;
        }

        int newMoves = minMoves(moves);
        moves[start] = -1; // Remove the added snake/ladder

        return new int[]{originalMoves, newMoves};
    }

    public static void identifyBestSnake(int[] moves) {
        int maxIncrease = 0;
        int bestStart = -1, bestEnd = -1;

        for (int start = 1; start < BOARD_SIZE; ++start) {
            for (int end = 0; end < start; ++end) {
                int[] impact = analyzeImpact(moves, start, end, true);
                int increase = impact[1] - impact[0];

                if (increase > maxIncrease) {
                    maxIncrease = increase;
                    bestStart = start;
                    bestEnd = end;
                }
            }
        }

        if (bestStart != -1 && bestEnd != -1) {
            System.out.println("Best snake to add: from " + bestStart + " to " + bestEnd + " increases optimal moves by " + maxIncrease);
        } else {
            System.out.println("No beneficial snake found.");
        }
    }

    public static void main(String[] args) {
        int[] moves = new int[BOARD_SIZE];
        Arrays.fill(moves, -1);

        // Add ladders
        moves[2] = 21;
        moves[4] = 7;
        moves[10] = 25;
        moves[19] = 28;

        // Add snakes
        moves[16] = 3;
        moves[18] = 6;
        moves[20] = 8;
        moves[26] = 0;

        System.out.println("Minimum moves to reach end: " + minMoves(moves));

        identifyBestSnake(moves);
    }
}
