import java.util.ArrayList;

/**
 * This should probably be replaced by a Union-find structure.
 * We could keep a list of child trees and add a child to the
 * tree by just appending to this list. Connecting two trees
 * would then be done in constant time. Now it's dependent on
 * the addAll method which is O(n) according to the docs
 */
public class Tree {
    ArrayList<Node> nodes; /* Nodes in the tree */

    /**
     * Initialize a new tree
     */
    public Tree(){
        nodes = new ArrayList<>();
    }

    /**
     * Add a list of nodes to the tree
     * @param nodes List of nodes to add
     */
    public void addNodes(ArrayList<Node> nodes){
        this.nodes.addAll(nodes);
    }

    /**
     * Add a single node to the tree
     * @param node The node to add
     */
    public void addNode(Node node){
        nodes.add(node);
    }
}