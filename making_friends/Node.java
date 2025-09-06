import java.util.ArrayList;

public class Node {
    int index;                  /* Index of the node */
    int numEdges;               /* Number of edges */
    ArrayList<Node> neigbors;   /* Node neighbors */

    /**
     * Initialize a new node
     * @param index Index of the node
     */
    public Node(int index){
        this.index = index;
        this.numEdges = 0;
        this.neigbors = new ArrayList<>();
    }
}