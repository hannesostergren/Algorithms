import java.util.ArrayList;

public class Graph {
    Node[] nodes;           /* Nodes in the graph */
    ArrayList<Edge> edges;  /* Edges in the graph */

    /**
     * Initialize a graph
     * @param N Number of nodes
     * @param M Number of edges
     */
    public Graph(int N, int M) {
        nodes = new Node[N];
        edges = new ArrayList<>(M);
    }

    /**
     * Retrieve the node with the specified index
     * @param index Index of the node to retrieve
     * @return Node with index or null if doesn't exist
     */
    private Node getNodeWithIndex(int index){
        try{
            for (Node n : nodes){
                if (n.index == index) return n;
            }
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Add a node to the graph
     * @param index index of the node
     * @return Node which was added
     */
    public Node addNode(int index){
        Node nodeWithIndex = getNodeWithIndex(index);
        if (nodeWithIndex != null){
            return nodeWithIndex;
        }
        Node newNode = new Node(index);
        nodes[index - 1] = newNode;
        return newNode;
    }

    /**
     * Connect two nodes by an undirected edge and add it to the graph
     * @param u First node of the edge
     * @param v Second node of the edge
     * @param w Weight of the edge
     */
    public void connectNodes(Node u, Node v, int w){
        Edge e = new Edge(u, v, w);
        edges.add(e);
    }

    /**
     * Sort the edges of the graph by ascending weights
     */
    public void sortEdges(){
        edges.sort( (a, b) -> { return a.w - b.w; });
    }
}