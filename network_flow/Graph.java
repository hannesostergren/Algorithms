import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    HashMap<Integer, Node> nodes;           /* Nodes in the graph */
    HashMap<Integer, Edge> edges;  /* Edges in the graph */
    HashMap<Integer, ArrayList<Edge>> nodeEdges;

    /**
     * Initialize a graph
     * @param N Number of nodes
     * @param M Number of edges
     */
    public Graph(int N, int M) {
        nodes = new HashMap<>(N);
        edges = new HashMap<>(M);
        nodeEdges = new HashMap<>();
        // for (int i = 0; i < N; i++) {
        //     nodes.add(null);
        // }
    }

    /**
     * Retrieve the node with the specified index
     * @param index Index of the node to retrieve
     * @return Node with index or null if doesn't exist
     */
    public Node getNodeWithIndex(int index){
        return nodes.get(index);
        // try{
        //     for (Node n : nodes){
        //         if (n.index == index) return n;
        //     }
        //     return null;
        // }
        // catch (Exception e) {
        //     return null;
        // }
    }

    public void addNodeFromOtherGraph(Integer i, Node node){
        nodes.put(i, node);
        nodeEdges.put(i, new ArrayList<>());
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
        nodes.put(index, newNode);
        nodeEdges.put(index, new ArrayList<>());
        return newNode;
    }


    public void removeEdge(Edge edge){
        edges.remove(edge.index);
        nodeEdges.get(edge.u.index).remove(edge);
        nodeEdges.get(edge.v.index).remove(edge);
        // for (ArrayList<Edge> nodeEdgeList : nodeEdges.values()){
        //     nodeEdgeList.remove(edge);
        // }
    }

    public Node getNode(int index){
        return nodes.get(index);
    }

    public ArrayList<Edge> getNodeEdges(Node node){
        return nodeEdges.get(node.index);
    }

    public Edge getEdgeWithIndex(int index){
        return edges.get(index);
        // for (Edge e : edges){
        //     if (e.index == index){
        //         return e;
        //     }
        // }
        // return null;
    }

    public void connectNodesWithIndex(Node u, Node v, int c, int index){
        Edge e = new Edge(u, v, c, index);
        edges.put(index, e);
        nodeEdges.get(u.index).add(e);
        nodeEdges.get(v.index).add(e);
    }
}