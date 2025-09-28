import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

class NetworkFlow{
    int N, M, C, P;
    Graph g;
    ArrayList<Edge> removeEdges;

    private void readInput(){ // readinput verkar ok
        Scanner scanner = new Scanner(System.in);
        String inputSize = scanner.nextLine();
        String[] inputSizes = inputSize.split(" ");
        removeEdges = new ArrayList<>();
        N = Integer.parseInt(inputSizes[0]);
        M = Integer.parseInt(inputSizes[1]);
        C = Integer.parseInt(inputSizes[2]);
        P = Integer.parseInt(inputSizes[3]);
        g = new Graph(N, M);
        for (int i = 0; i < M; i++){
            String inputLine = scanner.nextLine();
            String[] segments = inputLine.split(" ");
            int u = Integer.parseInt(segments[0]);
            int v = Integer.parseInt(segments[1]);
            int c = Integer.parseInt(segments[2]);
            Node uNode = g.addNode(u);
            Node vNode = g.addNode(v);
            g.connectNodesWithIndex(uNode, vNode, c, i); // Flow from source
            // removeEdges.add(e);
        }
        for (int i = 0; i < P; i++){
            int removeEdge = scanner.nextInt();
            Edge e = g.getEdgeWithIndex(removeEdge);
            removeEdges.add(e);
        }
        // setFlowingFromSource();
        scanner.close();
    }
    
    // private void setFlowingFromSource(){
    //     Node source = g.getNodeWithIndex(0);
    //     for (Edge e : source.getEdges()){
    //         e.flowingFrom = source;
    //     }
    // }

    private int bottleneck(ArrayList<Edge> P){
        int minResidual = Integer.MAX_VALUE;
        Node current = g.nodes.get(N - 1);
        for (Edge e : P.reversed()){
            Node from = e.getOther(current);
            minResidual = Math.min(e.residualCapacity(from, current), minResidual);
        }
        return minResidual;
    }

    private int augment(int f, ArrayList<Edge> P){
        int b = bottleneck(P);
        Node current = g.nodes.get(N - 1);
        for (Edge e : P.reversed()){
            Node from = e.getOther(current);
            e.addFlow(from, b);
            current = from;
        }
        return f + b;
    }

    private ArrayList<Edge> backtrack(Node u, Node v){
        ArrayList<Edge> path = new ArrayList<>();
        Node current = v;
        while (current != u){
            Edge parentEdge = current.parentEdge;
            path.add(0, parentEdge);
            current = parentEdge.getOther(current);
        }
        return path;
    }

    /**
     * Breadth-first search
     * @param u index of the start node
     * @param v index of the end node
     * @return number of steps from u to v, -1 if v is unreachable from u
     */
    public ArrayList<Edge> bfs(int u, int v){
        if (u == v) return new ArrayList<>();
        boolean[] visited = new boolean[g.nodes.size()];
        LinkedList<Node> nodes = new LinkedList<>();
        nodes.addLast(g.getNode(u));
        visited[0] = true;
        while(!nodes.isEmpty()){
            Node current = nodes.pop();
            ArrayList<Edge> edges = g.getNodeEdges(current);
            for (Edge e : edges){
                Node neighbor = e.getOther(current);
                if (!visited[neighbor.index] && e.residualCapacity(current, neighbor) > 0){
                    neighbor.setParentEdge(e);
                    if (neighbor.index == v){
                        return backtrack(g.getNode(u), g.getNode(v));
                    }
                    visited[neighbor.index] = true;
                    nodes.add(neighbor);
                }
            }
        }
        return null;
    }

    private int fordFulkerson(){
        for (Edge e : g.edges.values()) {
            e.flow = 0;
            e.flowingFrom = null;
        }
        int flow = 0;
        while (true){
            ArrayList<Edge> path = bfs(0, N - 1);
            if (path == null) break;
            flow = augment(flow, path);
        }
        return flow;
    }

    public void run(){
        readInput();
        int numRemoved = 0;
        int maxFlow = 0;
        int newMaxFlow;
        for (Edge e : removeEdges){
            newMaxFlow = fordFulkerson();
            // System.out.println(String.format("Removed %d edges", numRemoved - 1));
            g.removeEdge(e);
            if (newMaxFlow < C){
                System.out.println(String.format("%d %d", numRemoved - 1, maxFlow));
                return;
            }
            maxFlow = newMaxFlow;
            numRemoved++;
        }
        System.out.println(String.format("%d %d", numRemoved - 1, maxFlow));
    }

    public static void main(String[] args) {
        NetworkFlow nf = new NetworkFlow();
        nf.run();
    }
}