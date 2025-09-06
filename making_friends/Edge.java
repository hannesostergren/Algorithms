public class Edge {
    Node u;
    Node v;
    int w;

    /**
     * Initialize a new undirected edge
     * @param u First node of the edge
     * @param v Second node of the edge
     * @param w Weight of the edge
     */
    public Edge(Node u, Node v, int w){
        this.u = u;
        this.v = v;
        this.w = w;
    }
}
