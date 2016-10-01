package HuffmanTree;

import java.util.*;

public class Tree {
    Node root = null;
    Map<String, Node> symbols = new HashMap<>();
    Node nytNode = null;
    int treeDepth = 0;

    public Tree (int messageLength) {
        nytNode = new Node(2 * messageLength + 1, "NYT");
        root = nytNode;


        root.setWeight(0);
    }

    public Node getRoot() {
        return root;
    }

    public void addSymbol(String symbol) {
        if (symbolSeen(symbol)) {

            Node symbolNode = symbols.get(symbol);
            symbolNode.incrementWeight();
            updateTree(symbolNode.parent);

        } else {

            Node symbolNode = new Node(symbol);
            Node externalNode = new Node("NL");

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
            treeDepth++;

            if (root == nytNode)
                root = externalNode;
            else {
                updateTree(symbolNode.parent);
            }

        }
    }

    public void updateTree(Node parentNode) {
        while (parentNode != root) {

            parentNode = parentNode.parent;
        }
    }

    private boolean symbolSeen(String symbol) {
        if (symbols.get(symbol) != null)
            return true;
        else
            return false;
    }



    Queue<Node> queue = new LinkedList<Node>() ;
    public  void printTree(Node root) {
        if (root == null)
            return;

        queue.clear();
        queue.add(root);

        ArrayList<Node> treeNodes = new ArrayList();

        while(!queue.isEmpty()){
            Node node = queue.remove();
            treeNodes.add(node);
            if(node.leftChild != null) queue.add(node.leftChild);
            if(node.rightChild != null) queue.add(node.rightChild);
        }

        boolean rootPrinted = false;
        boolean firstChildren = true;
        int indentation = treeNodes.size() * 2 * 2 * 2;
        int slashSpaceSize = 3 * treeDepth + 3;
        int childrenSpaceSize = 3 * treeDepth + 2;

        for (int i = 0; i < treeNodes.size(); i++) {

            if (rootPrinted == false) {
                System.out.println(String.format("%" + indentation + "s", "") +  printNode(treeNodes.get(i)));
                indentation -= treeDepth;
                rootPrinted = true;
            }
            else {

                String slashSpace = String.format("%" + slashSpaceSize + "s", "");


                System.out.println(String.format("%" + indentation + "s", "") +  "/" + slashSpace + "\\");
                indentation -= 1;
                System.out.println(String.format("%" + indentation + "s", "") +  "/" + slashSpace + "  " + "\\");
                indentation -= treeDepth - 1;


                String spaceBetweenChildren = String.format("%" + childrenSpaceSize + "s", "");

//                if (treeNodes.get(i).getSymbol() == "NYT")
//                    spaceBetweenChildren = "           ";
//                else
//                    spaceBetweenChildren = "            ";

                System.out.println(String.format("%" + indentation + "s", "") + printNode(treeNodes.get(i)) + spaceBetweenChildren + printNode(treeNodes.get(i + 1)));
                childrenSpaceSize -= 3;
                indentation -= 2;
                slashSpaceSize -= 5;
                i++;
            }
        }

    }

    private String printNode(Node node) {
        return node.getWeight() + " |" + node.getSymbol() + "| " + node.getNodeNumber();
    }
}
