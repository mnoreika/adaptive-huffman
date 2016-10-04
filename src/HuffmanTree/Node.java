package HuffmanTree;

public class Node {
    int weight;
    int nodeNumber;
    String symbol;
    boolean isRightChild = true;


    Node leftChild = null;
    Node rightChild = null;
    Node parent = null;

    Node(String symbol) {
        this.symbol = symbol;
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

    public boolean isRightChild() {
        return isRightChild;
    }

    public void setChildOrientation(boolean isRight) {
        isRightChild = isRight;
    }
}
