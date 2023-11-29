import java.util.*;

class Node implements Comparable<Node> {
  Node parent;
  int[] position;
  int g;
  int h;
  int f;

  public Node(Node parent, int[] position) {
    this.parent = parent;
    this.position = position;
    this.g = 0;
    this.h = 0;
    this.f = 0;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Node node = (Node) o;
    return Arrays.equals(position, node.position);
  }

  public int compareTo(Node other) {
    return Integer.compare(this.f, other.f);
  }
}

public class AStar {
  public static List<int[]> astar(int[][] maze, int[] start, int[] end) {
    // Step 1: Initialize the start and end nodes with their positions
    Node startNode = new Node(null, start);
    startNode.g = startNode.h = startNode.f = 0;
    Node endNode = new Node(null, end);
    endNode.g = endNode.h = endNode.f = 0;

    // Step 2: Create lists to keep track of open and closed nodes
    List<Node> openList = new ArrayList<>();
    List<Node> closedList = new ArrayList<>();

    // Step 3: Add the start node to the open list
    openList.add(startNode);

    // Step 4: Main A* loop - Continue until the open list is not empty
    while (!openList.isEmpty()) {
        // Step 5: Select the node with the lowest f value from the open list
        Node currentNode = openList.get(0);
        int currentIndex = 0;
        for (int i = 0; i < openList.size(); i++) {
            if (openList.get(i).f < currentNode.f) {
                currentNode = openList.get(i);
                currentIndex = i;
            }
        }

        // Step 6: Remove the current node from the open list and add it to the closed list
        openList.remove(currentIndex);
        closedList.add(currentNode);

        // Step 7: Check if the current node is the goal
        if (currentNode.equals(endNode)) {
            // Step 8: Reconstruct the path from the end to the start
            List<int[]> path = new ArrayList<>();
            Node current = currentNode;
            while (current != null) {
                path.add(current.position);
                current = current.parent;
            }
            Collections.reverse(path);
            return path;
        }

        // Step 9: Generate children nodes in different directions
        List<Node> children = new ArrayList<>();
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] newPosition : directions) {
            int[] nodePosition = {currentNode.position[0] + newPosition[0], currentNode.position[1] + newPosition[1]};

            // Step 10: Check if the new position is within the maze boundaries
            if (nodePosition[0] > maze.length - 1 || nodePosition[0] < 0 || nodePosition[1] > maze[0].length - 1 || nodePosition[1] < 0) {
                continue;
            }

            // Step 11: Check if the new position is not an obstacle
            if (maze[nodePosition[0]][nodePosition[1]] != 0) {
                continue;
            }

            // Step 12: Create a new node for the valid position and add it to the children list
            Node newNode = new Node(currentNode, nodePosition);
            children.add(newNode);
        }

        // Step 13: Process each child node
        for (Node child : children) {
            // Step 14: Skip if the child is already in the closed list
            if (closedList.contains(child)) {
                continue;
            }

            // Step 15: Update the child's g, h, and f values
            child.g = currentNode.g + 1;
            child.h = (int) (Math.pow((child.position[0] - endNode.position[0]), 2) + Math.pow((child.position[1] - endNode.position[1]), 2));
            child.f = child.g + child.h;

            // Step 16: Check if the child is already in the open list and has a lower g value
            boolean shouldAdd = true;
            for (Node openNode : openList) {
                if (child.equals(openNode) && child.g > openNode.g) {
                    shouldAdd = false;
                    break;
                }
            }

            // Step 17: Add the child to the open list if it meets the conditions
            if (shouldAdd) {
                openList.add(child);
            }
        }
    }

    // Step 18: If the open list is empty and no path is found, return null
    return null;
}


  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    int[][] maze = {
    {0, 0, 0, 0, 1},
    {0, 1, 1, 0, 0},
    {0, 0, 1, 0, 1},
    {0, 1, 0, 0, 1},
    {0, 0, 0, 0, 0},
    };
    
    int c = 1;
    for(int i=0; i<5; i++){
      for(int j=0; j<5;j++){
        System.out.print(c++ + " ");
      }
      System.out.print("\n");
    }

    System.out.print("Enter the start position (range 1-25) : ");
    int start_ = scanner.nextInt();

    System.out.print("Enter the end position (range 1-25) :");
    int end_ = scanner.nextInt();

    int rows = (start_ - 1) / 5;
    int cols = (start_ - 1) % 5;

    int rowe = (end_ - 1) / 5;
    int cole = (end_ - 1) % 5;

    int[] start = {rows, cols};
    int[] end = {rowe, cole};

    List<int[]> path = astar(maze, start, end);

    if (path != null) {
      System.out.println("Path:");
      for (int[] position : path) {
        System.out.println("(" + position[0] + ", " + position[1] + ")");
      }
    } else {
      System.out.println("No path found.");
    }

    scanner.close();
  }
}


//Code 2
import java.util.*;

class Node {
    int x, y; // Coordinates of the cell
    int cost; // Cost to reach this cell from the start
    int heuristic; // Heuristic cost (estimated cost to the destination)
    Node parent; // Parent node in the path

    public Node(int x, int y, int cost, int heuristic, Node parent) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.heuristic = heuristic;
        this.parent = parent;
    }
}

public class AStarMazeSolver {
    public static void main(String[] args) {
        char[][] maze = {
            {'S', '0', '1', '0', '0'},
            {'0', '1', '0', '1', '0'},
            {'0', '0', '0', '0', '1'},
            {'0', '1', '0', '0', '0'},
            {'0', '0', '0', '1', 'D'}
        };

        List<Node> path = findPath(maze);
        if (path != null) {
            System.out.println("Shortest path found:");
            for (Node node : path) {
                System.out.println("(" + node.x + ", " + node.y + ")");
            }
        } else {
            System.out.println("No path found.");                                                        
        }   
        
    }

    public static List<Node> findPath(char[][] maze) {
        int rows = maze.length;
        int cols = maze[0].length;
        
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right
        
        PriorityQueue<Node> openSet = new PriorityQueue<>((a, b) -> (a.cost + a.heuristic) - (b.cost + b.heuristic));
        Map<String, Node> closedSet = new HashMap<>();
        
        Node startNode = null, endNode = null;
        
        // Find the start and destination nodes
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 'S') {
                    startNode = new Node(i, j, 0, 8, null);
                    openSet.offer(startNode);
                } else if (maze[i][j] == 'D') {
                    endNode = new Node(i, j, 0, 0, null);
                }
            }
        }
        
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.x == endNode.x && current.y == endNode.y) {
                // Destination reached; reconstruct the path
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }
            
            String currentKey = current.x + "-" + current.y;
            closedSet.put(currentKey, current);
            
            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];
                
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && maze[newX][newY] != '1') {
                    String neighborKey = newX + "-" + newY;
                    if (!closedSet.containsKey(neighborKey)) {
                        boolean isRightObstacle = (newY + 1 < cols && maze[newX][newY + 1] == '1');
                        boolean isDownObstacle = (newX + 1 < rows && maze[newX + 1][newY] == '1');
                        
                        if (!isRightObstacle || !isDownObstacle) {
                            int newCost = current.cost + 1;
                            int heuristic = Math.abs(newX - endNode.x) + Math.abs(newY - endNode.y);
                            Node neighbor = new Node(newX, newY, newCost, heuristic, current);
                            openSet.offer(neighbor);
                        }
                    }
                }
            }
        }
        
        // No path found
        return null;
    }
}
