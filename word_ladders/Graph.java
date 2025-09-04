import java.util.Arrays;

public class Graph {
    int N;              /* Number of nodes */
    Node[] nodes;       /* Node array */
    boolean[] visited;  /* Visited nodes */

    /**
     * Initialize graph
     * @param N Number of nodes in graph
     */
    public Graph(int N){
        this.N = N;
        nodes = new Node[N];
        visited = new boolean[N];
    }

    /**
     * Check if a node contains the last four characters of a word
     * @param word The word to check the last four characters from
     * @param n The node to check if contains the characters
     * @return true if the node value contains the last four characters of the word, false otherwise
     */
    private boolean includesLastFour(String word, Node n){
        String nodeChars = n.value;
        for (int j = 1; j < word.length(); j++){
            String character = word.substring(j, j + 1);
            if (!nodeChars.contains(character)){
                return false;
            }
            nodeChars = nodeChars.replaceFirst(character,  "");
        }
        return true;
    }

    /**
     * Add a node to the graph and create a directed edges to it's neighbors
     * @param i The index where the node should be added
     * @param word The word which should be set as the value of the node
     */
    public void addNode(int i, String word){
        nodes[i] = new Node(i, word);
        for (Node n : nodes){
            if (n == null){
                return;
            }
            if (n.index == i){
                continue;
            }
            if (includesLastFour(word, n)){
                nodes[i].incrementNumberOfEdges();
                nodes[i].addNeighbor(n);
            }
            if (includesLastFour(n.value, nodes[i])){
                n.incrementNumberOfEdges();
                n.addNeighbor(nodes[i]);
            }
        }
    }

    /**
     * Check if all nodes in the graph have been visited
     * @return true if all are visited, else false
     */
    public boolean allVisited(){
        for(boolean b : visited) if(!b) return false;
        return true;
    }

    /**
     * Retrieve the node at a specific index
     * @param index The index to retrieve the node from
     * @return Node at index index
     */
    public Node getNode(int index){
        return nodes[index];
    }

    /**
     * Retrieve the index of a node based on the node value
     * @param word The word which the node should contain
     * @return index of the node which contains word, -1 if the node doesn't exist
     */
    public int getNodeIndex(String word){
        for (int i = 0; i < N; i++){
            if (nodes[i].value.equals(word)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Reset the visited nodes, required before making another search through the graph
     */
    public void resetVisited(){
        Arrays.fill(visited, false);
    }
}