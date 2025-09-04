import java.util.ArrayList;

public class Node {
    int index;                  /* Index of the node */
    int numEdges;               /* Number of edges */
    String value;               /* Value of the node (The word it contains) */
    ArrayList<Node> neigbors;   /* Node neighbors */

    /**
     * Initialize a new node
     * @param index Index of the node
     * @param value Value of the node (word)
     */
    public Node(int index, String value){
        this.index = index;
        this.value = value;
        this.numEdges = 0;
        this.neigbors = new ArrayList<>();
    }

    /**
     * Increment the number of edges
     */
    public void incrementNumberOfEdges(){
        numEdges++;
    }

    /**
     * Add a neighbor to the node
     * @param n The node to add as a neighbor
     */
    public void addNeighbor(Node n){
        neigbors.add(n);
    }

    /**
     * Retrieve the neighbors of the node
     * @return All neighbor nodes of this node
     */
    public ArrayList<Node> getNeighbors(){
        return neigbors;
    }
}