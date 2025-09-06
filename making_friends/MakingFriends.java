import java.util.ArrayList;
import java.util.Scanner;

public class MakingFriends {
    int N;                  /* Number of nodes */
    int M;                  /* Number of edges */
    Graph g;                /* Graph */
    ArrayList<Tree> trees;  /* Node trees */

    /**
     * Read input from stdin
     */
    private void readInput(){
        Scanner scanner = new Scanner(System.in);
        String sizeRow = scanner.nextLine();
        String[] sizes = sizeRow.split(" ");
        N = Integer.parseInt(sizes[0]);
        M = Integer.parseInt(sizes[1]);
        g = new Graph(N, M);
        while(scanner.hasNext()){
            String row = scanner.nextLine();
            String[] input = row.split(" ");
            int u = Integer.parseInt(input[0]);
            int v = Integer.parseInt(input[1]);
            int w = Integer.parseInt(input[2]);
            Node uNode = g.addNode(u);
            Node vNode = g.addNode(v);
            g.connectNodes(uNode, vNode, w);
        }
        scanner.close();
    }

    /**
     * Get the tree which the node is currently in.
     * O(1)
     * @param node Node to get the tree for
     * @return Tree which contains the node
     */
    private Tree getTreeFromNode(Node node){
        return trees.get(node.index - 1);
    }

    /**
     * Check if adding the edge to the spanning tree will create a cycle,
     * i.e. if both nodes of the edge belong to the same tree
     * O(1)
     * @param e Edge which should be checked
     * @return true if adding the edge to the spanning tree would create a cycle, false otherwise
     */
    private boolean createsCycle(Edge e){
        return getTreeFromNode(e.u).equals(getTreeFromNode(e.v));
    }

    /**
     * Calculate the weight of the minimal spanning tree
     * @param tree List of edges of the minimal spanning tree
     * @return total weight of the MST
     */
    private int getWeight(ArrayList<Edge> tree){
        int treeWeight = 0;
        for (Edge e : tree){
            treeWeight += e.w;
        }
        return treeWeight;
    }

    /**
     * Connect two trees of the graph together by adding the nodes of one tree to the other.
     * Then, update the trees list so that the indexes of the nodes in tree2 refer to tree1
     * @param tree1 Tree which nodes should be merged into
     * @param tree2 Tree which nodes should be merged from
     */
    private void connectTrees(Tree tree1, Tree tree2){
        tree1.addNodes(tree2.nodes);
        for (Node n : tree2.nodes){
            trees.set(n.index - 1, tree1);
        }
    }

    /**
     * Implementation of Kruskal's Algorithm to calculate the Minimal Spanning Tree
     * of a graph.
     * @return The weight of the resulting MST
     */
    private int Kruskal() {
        ArrayList<Edge> mst = new ArrayList<>();
        trees = new ArrayList<>(N);
        int subTreeSize = N;
        for (Node n : g.nodes){
            Tree subtree = new Tree();
            subtree.addNode(n);
            trees.add(subtree);
        } 
        while (subTreeSize > 1){
            Edge e = g.edges.removeFirst();
            if (!createsCycle(e)){
                mst.add(e);
                Tree tree1 = getTreeFromNode(e.u);
                Tree tree2 = getTreeFromNode(e.v);
                connectTrees(tree1, tree2);
                subTreeSize--;
            }
        }
        return getWeight(mst);
    }

    /**
     * Run the process of reading the input and executing Kruskal's Algorithm
     */
    private void run(){
        // long start = System.currentTimeMillis();
        readInput();
        g.sortEdges();
        // long mst = System.currentTimeMillis();
        // System.out.println("Init: " + (mst - start) + " ms");
        int mstWeight = Kruskal();
        // long end = System.currentTimeMillis();
        // System.out.println("mst: " + (end - mst) + " ms");
        System.out.println(mstWeight);
    }
    public static void main(String[] args){
        MakingFriends mf = new MakingFriends();
        mf.run();
    }
}
