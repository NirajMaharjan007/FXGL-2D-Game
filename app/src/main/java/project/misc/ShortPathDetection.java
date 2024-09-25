package project.misc;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ShortPathDetection {
    private static final int[][] DIRECTIONS = {
            {0, 1}, {1, 0}, {0, -1}, {-1, 0}  // Up, Right, Down, Left
    };
    private static final int TILE_SIZE = 16;
    private Node[][] grid;
    private int gridWidth, gridHeight;

    public ShortPathDetection(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;

        // Initialize the grid
        grid = new Node[gridWidth][gridHeight];
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                grid[x][y] = new Node(x, y);
            }
        }
    }

    public List<Node> findPath(int startX, int startY, int targetX, int targetY) {
        Node startNode = grid[startX][startY];
        Node targetNode = grid[targetX][targetY];

        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<Node> closedList = new HashSet<>();

        startNode.gCost = 0;
        startNode.hCost = heuristic(startNode, targetNode);
        startNode.fCost = startNode.gCost + startNode.hCost;
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            // If the target node is reached, reconstruct the path
            if (current.equals(targetNode)) {
                return reconstructPath(current);
            }

            closedList.add(current);

            // Explore neighbors
            for (int[] direction : DIRECTIONS) {
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];

                if (isValidPosition(newX, newY)) {
                    Node neighbor = grid[newX][newY];

                    if (closedList.contains(neighbor)) {
                        continue;
                    }

                    double tentativeGCost = current.gCost + 1;  // Assuming each move has a cost of 1

                    if (tentativeGCost < neighbor.gCost || !openList.contains(neighbor)) {
                        neighbor.gCost = tentativeGCost;
                        neighbor.hCost = heuristic(neighbor, targetNode);
                        neighbor.fCost = neighbor.gCost + neighbor.hCost;
                        neighbor.parent = current;

                        if (!openList.contains(neighbor)) {
                            openList.add(neighbor);
                        }
                    }
                }
            }
        }

        // Return an empty path if no path is found
        return new ArrayList<>();
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < gridWidth && y < gridHeight;
    }

    // Manhattan distance heuristic (since no diagonal movement)
    private double heuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }


    public static class Node implements Comparable<Node> {
        public int x, y;
        public double gCost, hCost, fCost;
        public Node parent;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(@NotNull Node other) {
            return Double.compare(this.fCost, other.fCost);
        }
    }
}
