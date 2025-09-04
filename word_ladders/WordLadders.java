import java.util.ArrayList;
import java.util.Scanner;

public class WordLadders {
    int N;          /* Number of Nodes */
    int Q;          /* Number of Queries */
    Graph g;        /* Graph with nodes and edges (edges through neighbor node lists) */
    int[][] query;  /* Queries with index u (start) and index v (end) */

    /**
     * Read input from stdin and parse data
     */
    public void readInput() {
        Scanner scanner = new Scanner(System.in);
        String firstLine = scanner.nextLine();
        String[] firstLineNumbers = firstLine.split(" ");
        N = Integer.parseInt(firstLineNumbers[0]);
        Q = Integer.parseInt(firstLineNumbers[1]);
        query = new int[Q][2];
        g = new Graph(N);
        for (int i = 0; i < N; i++){
            g.addNode(i, scanner.nextLine());
        }
        for (int i = 0; i < Q; i++){
            String line = scanner.nextLine();
            String[] words = line.split(" ");
            int u = g.getNodeIndex(words[0]);
            int v = g.getNodeIndex(words[1]);
            query[i][0] = u;
            query[i][1] = v;
        }
        scanner.close();
    }

    /**
     * Breadth-first search
     * @param u index of the start node
     * @param v index of the end node
     * @return number of steps from u to v, -1 if v is unreachable from u
     */
    public int bfs(int u, int v){
        if (u == v) return 0;
        int layer = 0;
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.addLast(g.getNode(u));
        g.visited[u] = true;
        while(!nodes.isEmpty()){
            ArrayList<Node> nextNodes = new ArrayList<>();
            for (Node n : nodes){
                ArrayList<Node> neighborNodes = n.getNeighbors();
                for (Node neighbor : neighborNodes){
                    if (neighbor.index == v){
                        return layer + 1;
                    }
                    if(!g.visited[neighbor.index]){
                        g.visited[neighbor.index] = true;
                        nextNodes.addLast(neighbor);
                    }
                }
            }
            nodes = nextNodes;
            layer++;
        }
        return -1;
    }

    /**
     * Run BFS on each query from the input
     */
    public void run() {
        readInput();
        for (int i = 0; i < Q; i++){
            g.resetVisited();
            int steps = bfs(query[i][0], query[i][1]);
            if (steps == -1){
                System.out.println("Impossible");
            }
            else{
                System.out.println(steps);
            }
        }
    }

    public static void main(String[] args) {
        WordLadders bfs = new WordLadders();
        // long start = System.currentTimeMillis();
        bfs.run();
        // long end = System.currentTimeMillis();
        // System.out.println("Runtime: " + (end - start) + " ms");
    }
}
