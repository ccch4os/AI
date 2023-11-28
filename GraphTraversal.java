import java.util.*;

/*
User Input
Enter the number of nodes: 10
Enter the number of edges: 25
0 1
0 2
1 3
1 4
2 5
2 6
3 7
4 8
5 9
6 9
7 8
8 9
0 3
0 4
1 2
2 3
3 5
4 6
5 7
6 8
7 9
0 5
1 6
2 7
3 8
Enter the starting node: 0
DFS Traversal: 0 1 3 7 8 9 5 4 6 2
BFS Traversal: 0 1 2 3 4 5 6 7 8 9
*/
public class GraphTraversal {
    public static void DFS(ArrayList<ArrayList<Integer>> adj, int startNode, boolean vis[]) {
        System.out.print(startNode + " ");
        vis[startNode] = true;

        for (int i = 0; i < adj.get(startNode).size(); i++) {
            int num = adj.get(startNode).get(i);
            if (!vis[num]) {
                vis[num] = true;
                DFS(adj, num, vis);
            }
        }
    }

    public static void BFS(ArrayList<ArrayList<Integer>> adj, int startNode) {
        Queue<Integer> queue = new LinkedList<>();
        boolean vis[] = new boolean[adj.size()];

        queue.add(startNode);
        vis[startNode] = true;

        while (!queue.isEmpty()) {
            int num = queue.remove();
            System.out.print(num + " ");

            for (int i = 0; i < adj.get(num).size(); i++) {
                int curr = adj.get(num).get(i);
                if (!vis[curr]) {
                    queue.add(curr);
                    vis[curr] = true;
                }
            }
        }
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of nodes: ");
        int numNodes = scanner.nextInt();

        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            adj.add(new ArrayList<>());
        }

        System.out.print("Enter the number of edges: ");
        int numEdges = scanner.nextInt();
        for (int i = 0; i < numEdges; i++) {
            int source = scanner.nextInt();
            int destination = scanner.nextInt();
            adj.get(source).add(destination);
            adj.get(destination).add(source);
        }

        System.out.print("Enter the starting node: ");
        int startNode = scanner.nextInt();

        System.out.print("DFS Traversal: ");
        DFS(adj, startNode, new boolean[adj.size()]);
        
        System.out.print("\nBFS Traversal: ");
        BFS(adj, startNode);

        scanner.close();
    }
}