package HuffmanTree;

import java.util.HashMap;
import java.util.Map;

public class Tree {
    Node root = null;
    Map<String, Node> symbols = new HashMap<>();
    Node nytNode = null;

    public Tree (int messageLength) {
        root = new Node(2 * messageLength + 1, "NYT");
        nytNode = root;


        root.setWeight(0);
    }

    public Node getRoot() {
        return root;
    }

    public void addSymbol(String symbol) {
        if (symbolSeen(symbol)) {
            Node leaf = symbols.get(symbol);
            leaf.incrementWeight();

        } else {
            Node leafNode = new Node(symbol);
            Node blockParent = new Node();


            blockParent.leftChild = nytNode;
            nytNode.parent = blockParent;

            blockParent.rightChild = leafNode;
            leafNode = nytNode.parent;

            //Setting the weights for all the nodes in the new block
            blockParent.setNodeNumber(nytNode.getNodeNumber());
            leafNode.setNodeNumber(nytNode.getNodeNumber() - 1);
            nytNode.setNodeNumber(nytNode.getNodeNumber() - 2);

            symbols.put(symbol, leafNode);

            if (root == nytNode)
                root = blockParent;
            else {
                nytNode.parent.leftChild = blockParent;
            }
            
        }
    }

    private boolean symbolSeen(String symbol) {
        if (symbols.get(symbol) != null)
            return true;
        else
            return false;
    }
}
