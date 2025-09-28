public class Node {
    int index;                  /* Index of the node */
    Edge parentEdge;

    /**
     * Initialize a new node
     * @param index Index of the node
     * @param value Value of the node (word)
     */
    public Node(int index){
        this.index = index;
    }

    public void setParentEdge(Edge parentEdge){
        this.parentEdge = parentEdge;
    }
}