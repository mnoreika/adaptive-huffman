package HuffmanTree;

public class Node {

    int weight; // Weight of the node
    int nodeNumber; // Node number of the node
    String symbol; // A sequence of bits that represent a symbol in binary string format
    boolean isLeaf = true; // Tracks if the node is a leaf

    Node leftChild = null; // Left child of the node
    Node rightChild = null; // Right child of the node
    Node parent = null; // The parent of the node

    Node(String symbol) {
        this.symbol = symbol;

        /* Internal nodes are not leafs */
        if (symbol == "INT") {
            isLeaf = false;
        }
    }

    Node(int nodeNumber, String symbol) {
        this.nodeNumber = nodeNumber;
        this.symbol = symbol;
    }

    public int getWeight() {
        return weight;
    }

    public int getNodeNumber() {
        return nodeNumber;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public void incrementWeight() {
        weight++;
    }

    public void setNodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public Node getLeftChild () {
        return leftChild;
    }

    public Node getRightChild () {
        return rightChild;
    }

    public boolean isLeaf() {
        return isLeaf;
    }
}
