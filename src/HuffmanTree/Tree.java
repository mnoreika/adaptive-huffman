package HuffmanTree;

import java.util.*;

public class Tree {
    Node root = null;

    public Map<String, Node> symbols = new HashMap<>();

    Node nytNode = null;

    public Tree (int messageLength) {
        nytNode = new Node(2 * messageLength + 1, "NYT");
        root = nytNode;

        root.setWeight(0);
    }

    public Node getRoot() {
        return root;
    }

    public void updateSymbol(String symbol) {
        Node symbolNode = symbols.get(symbol);


        updateTree(symbolNode);

        symbolNode.incrementWeight();
    }

    public void addSymbol(String symbol) {
        Node symbolNode = new Node(symbol);
        Node externalNode = new Node("EXT");

        externalNode.parent = nytNode.parent;

        if (root != nytNode)
            nytNode.parent.leftChild = externalNode;

        externalNode.leftChild = nytNode;
        nytNode.parent = externalNode;




        externalNode.rightChild = symbolNode;
        symbolNode.parent = externalNode;

        //Setting the node numbers for the targeted nodes
        externalNode.setNodeNumber(nytNode.getNodeNumber());
        symbolNode.setNodeNumber(nytNode.getNodeNumber() - 1);
        nytNode.setNodeNumber(nytNode.getNodeNumber() - 2);

        //Setting the weights for all the new nodes
        externalNode.setWeight(1);
        symbolNode.setWeight(1);

        symbols.put(symbol, symbolNode);

        //updateHighestNodeInBlock(symbolNode);


        if (root == nytNode)
            root = externalNode;
        else {
            updateTree(symbolNode.parent.parent);
        }

    }



    public void updateTree(Node currentNode) {
        //Increasing the weights of the nodes
        while (currentNode != null) {
            Node highestInBlock = findHighestNodeInBlock(currentNode.getWeight(), currentNode.getNodeNumber());

            if (highestInBlock != null && highestInBlock != currentNode.parent && currentNode != highestInBlock) {

                if (areSiblings(currentNode, highestInBlock)) {
                    Node parent = currentNode.parent;
                    Node leftChild = parent.leftChild;

                    parent.leftChild = parent.rightChild;
                    parent.rightChild = leftChild;


                }
                else {
                    if (highestInBlock.parent.leftChild == highestInBlock)
                        highestInBlock.parent.leftChild = currentNode;
                    else
                        highestInBlock.parent.rightChild = currentNode;

                    if (currentNode.parent.leftChild == currentNode)
                        currentNode.parent.leftChild = highestInBlock;
                    else
                        currentNode.parent.rightChild = highestInBlock;

                    Node currentParent = currentNode.parent;
                    currentNode.parent = highestInBlock.parent;

                    highestInBlock.parent = currentParent;

                }




                //Swapping the node numbers of the nodes that are being switched
                int highestNodeNumber = highestInBlock.getNodeNumber();

                highestInBlock.setNodeNumber(currentNode.getNodeNumber());
                currentNode.setNodeNumber(highestNodeNumber);

            }


            if (currentNode.leftChild == null && currentNode.rightChild != null)
                currentNode.setWeight(currentNode.rightChild.getWeight());
            else if (currentNode.leftChild != null && currentNode.rightChild == null)
                currentNode.setWeight(currentNode.leftChild.getWeight());
            else if (currentNode.leftChild != null && currentNode.rightChild != null)
                currentNode.setWeight(currentNode.rightChild.getWeight() + currentNode.leftChild.getWeight());

            currentNode = currentNode.parent;
        }
    }

//    private void updateHighestNodeInBlock(Node symbolNode) {
//        if (blocks.get(symbolNode.weight) != null) {
//            if (blocks.get(symbolNode.weight).getNodeNumber() < symbolNode.getNodeNumber())
//                blocks.put(symbolNode.weight, symbolNode);
//        }
//        else
//            blocks.put(symbolNode.weight, symbolNode);
//    }

    public Node findHighestNodeInBlock (int weight, int nodeNumber) {
        Queue<Node> queue = new LinkedList<Node>();

        queue.clear();
        queue.add(root);

        while(!queue.isEmpty()) {
            Node currentNode = queue.remove();

            if (currentNode.getWeight() == weight && currentNode.getNodeNumber() > nodeNumber) {
                return currentNode;
            }

            if (currentNode.rightChild != null) queue.add(currentNode.rightChild);
            if (currentNode.leftChild != null) queue.add(currentNode.leftChild);

        }

        return null;
    }

    public boolean symbolSeen(String symbol) {
        return symbols.get(symbol) != null;
    }


    public void printTree(Node root) {
        preorder(root, true);
    }

    public String getSymbolCoding(Node symbol) {
        Stack coding = new Stack();

        if (symbol.parent == null)
            return "";

        while (symbol.parent != null) {
            if (symbol.parent.leftChild == symbol)
                coding.push("0");
            else
                coding.push("1");

            symbol = symbol.parent;
        }

        StringBuilder encoding = new StringBuilder();

        while (!coding.empty())
            encoding.append(coding.pop());

        return encoding.toString();

    }

    public Node getNYTNode() {
        return nytNode;
    }

    int indentation = 5;
    public void preorder(Node currentNode, boolean lastChild) {

        if(currentNode != null) {

            if (currentNode == root) {
                System.out.println(String.format("%" + indentation + "s", "") +  "└── " + printNode(currentNode));
            }
            else if (lastChild) {
                System.out.println(String.format("%" + indentation + "s", "") +  "└── " + printNode(currentNode));
            }
            else {
                System.out.println(String.format("%" + indentation + "s", "") +  "├── " + printNode(currentNode));
            }


            indentation += 8;
            preorder(currentNode.rightChild, false);
            preorder(currentNode.leftChild, true);
            indentation -= 8;
        }
    }



    private String printNode(Node node) {
        return node.getWeight() + " |" + node.getSymbol() + "| " + node.getNodeNumber();
    }

    private boolean areSiblings(Node firstNode, Node secondNode) {
        if (firstNode.parent == secondNode.parent)
            return true;

        return false;
    }

    public Map<String, Node> getSymbols() {
        return symbols;
    }


}
