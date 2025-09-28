public class Edge {
    int index;
    Node u;
    Node v;
    int c;
    int flow;
    Node flowingFrom;

    /**
     * Initialize a new undirected edge
     * @param u First node of the edge
     * @param v Second node of the edge
     * @param c Capacity of the edge
     */
    public Edge(Node u, Node v, int c, int index){
        this.index = index;
        this.u = u;
        this.v = v;
        this.c = c;
        this.flow = 0;
        this.flowingFrom = null;
    }

    public int residualCapacity(Node from, Node to){
        if (flowingFrom == null) return c;
        else if (from == flowingFrom) return c - flow;
        else return flow;
    }

    public void addFlow(Node from, int b){
        if (flowingFrom == null){
            flowingFrom = from;
        }
        if (from == flowingFrom){
            flow += b;
        }
        else{
            flow -= b;
            if (flow == 0) flowingFrom = null;
        }
    }

    public Node getOther(Node u){
        if (this.u == u){
            return v;
        }
        else return this.u;
    }
    
}
