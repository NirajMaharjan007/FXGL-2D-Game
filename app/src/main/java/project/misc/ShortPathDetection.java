package project.misc;

import org.jetbrains.annotations.NotNull;

import javafx.util.*;

import java.util.*;

public class ShortPathDetection {
    private static final int[][] DIRECTIONS = {
            { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } // Up, Right, Down, Left
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

    // Find shortest path from player to enemy
    public List<Pair<Integer, Integer>> findPath(int startX, int startY, int targetX, int targetY) {
        Node startNode = new Node(startX / TILE_SIZE, startY / TILE_SIZE);
        Node targetNode = new Node(targetX / TILE_SIZE, targetY / TILE_SIZE);

        // Priority queue to store nodes based on fCost (lowest fCost first)
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fCost));
        Set<Pair<Integer, Integer>> closedList = new HashSet<>();

        // Add the start node to the open list
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();

            // If we reached the target node, reconstruct and return the path
            if (currentNode.x == targetNode.x && currentNode.y == targetNode.y) {
                return reconstructPath(currentNode);
            }

            closedList.add(new Pair<>(currentNode.x, currentNode.y));

            // Get valid neighboring nodes (up, down, left, right)
            for (Node neighbor : getNeighbors(currentNode)) {
                if (closedList.contains(new Pair<>(neighbor.x, neighbor.y))) {
                    continue;
                }

                double tentativeGCost = currentNode.gCost + 1; // Assume uniform cost of 1 per move

                if (tentativeGCost < neighbor.gCost || !openList.contains(neighbor)) {
                    neighbor.calculateCosts(targetNode, tentativeGCost);
                    neighbor.parent = currentNode;

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }

        // Return an empty path if no valid path is found
        return new ArrayList<>();
    }

    private List<Pair<Integer, Integer>> reconstructPath(Node currentNode) {
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        Node temp = currentNode;
        while (temp != null) {
            path.add(new Pair<>(temp.x * TILE_SIZE, temp.y * TILE_SIZE));
            temp = temp.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();

        // Add neighbors (up, down, left, right)
        if (node.x > 0)
            neighbors.add(new Node(node.x - 1, node.y));
        if (node.x < gridWidth / TILE_SIZE - 1)
            neighbors.add(new Node(node.x + 1, node.y));
        if (node.y > 0)
            neighbors.add(new Node(node.x, node.y - 1));
        if (node.y < gridHeight / TILE_SIZE - 1)
            neighbors.add(new Node(node.x, node.y + 1));

        return neighbors;
    }

    public static class Node implements Comparable<Node> {
        int x, y;
        double gCost; // Movement cost from start to this node
        double hCost; // Heuristic cost from this node to target
        double fCost; // Sum of gCost and hCost
        Node parent;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void calculateCosts(Node target, double gCostFromStart) {
            this.gCost = gCostFromStart;
            this.hCost = calculateHeuristic(target);
            this.fCost = this.gCost + this.hCost;
        }

        private double calculateHeuristic(Node target) {
            // Use Manhattan distance as heuristic
            return Math.abs(target.x - this.x) + Math.abs(target.y - this.y);
        }

        @Override
        public int compareTo(@NotNull Node other) {
            return Double.compare(this.fCost, other.fCost);
        }

    }
}
