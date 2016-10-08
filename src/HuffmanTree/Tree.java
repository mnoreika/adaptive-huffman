package HuffmanTree;

import java.util.*;

public class Tree {
    Node root = null; // Pointer to the root of the tree
    Map<String, Node> symbols = new HashMap<>(); // Hashmap that stores seen symbols
    Node nytNode = null; //Pointer to the Not Yet Transmitted node

    public Tree (int m) {
        /* Initialising the NYT node and setting its node number to account for the maximum number of different symbols */
        nytNode = new Node(2 * m + 1, "NYT");
        root = nytNode;

        /* Setting the weight of NYT node to 0 */
        root.setWeight(0);
    }

    /**
     * Updates the symbol node in the tree
     * @param symbol
     */
    public void updateSymbol(String symbol) {
        /* Finding the node in the Hashmap */
        Node symbolNode = symbols.get(symbol);

        /* Updating the tree to check for potential swaps and to keep the invariant */
        updateTree(symbolNode);
    }

    /**
     * Adds a new symbol to the tree
     * @param symbol
     */
    public void addSymbol(String symbol) {
        /* Creating a symbol node for the new symbol */
        Node symbolNode = new Node(symbol);

        /* Creating an internal node*/
        Node internalNode = new Node("INT");

        /* Switching the old NYT node with the internal node */
        internalNode.parent = nytNode.parent;

        /* If NYT is not root, changing the leftChild of old NYT's parent */
        if (root != nytNode)
            nytNode.parent.leftChild = internalNode;

        /* Setting internal the parent of NYT */
        internalNode.leftChild = nytNode;
        nytNode.parent = internalNode;

        /* Setting internal node the parent of symbol node (leaf node) */
        internalNode.rightChild = symbolNode;
        symbolNode.parent = internalNode;

        /* Setting the node numbers for the targeted nodes */
        internalNode.setNodeNumber(nytNode.getNodeNumber());
        symbolNode.setNodeNumber(nytNode.getNodeNumber() - 1);
        nytNode.setNodeNumber(nytNode.getNodeNumber() - 2);

        /* Setting the weights for all the new nodes */
        internalNode.setWeight(1);
        symbolNode.setWeight(1);

        /* Adding the symbol to the seen symbol HashMap */
        symbols.put(symbol, symbolNode);

        /* Switching the root to the new internal node */
        if (root == nytNode)
            root = internalNode;
        else {
            /* Calling the update function to update the tree */
            updateTree(symbolNode.parent.parent);
        }

    }


    /**
     * Updating the tree and keeping the invariant
     * @param currentNode
     */
    public void updateTree(Node currentNode) {

        /* Climbing up the tree until root is reached */
        while (currentNode != null) {
            /* Looking for the highest node in the block (Block - nodes with the same weight) */
            Node highestInBlock = findHighestNodeInBlock(currentNode.getWeight(), currentNode.getNodeNumber());

            /* Determining if a swap could be performed for the targeted nodes */
            if (highestInBlock != null && highestInBlock != currentNode.parent && currentNode != highestInBlock) {

                /* If the nodes to be swapped are siblings, then pointers of the shared parent node are swapped */
                if (areSiblings(currentNode, highestInBlock)) {
                    Node parent = currentNode.parent;
                    Node leftChild = parent.leftChild;

                    parent.leftChild = parent.rightChild;
                    parent.rightChild = leftChild;

                }

                /* Swapping pointers between targeted nodes and their parents */
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


                /* Swapping the node numbers of the nodes that are being switched */
                int highestNodeNumber = highestInBlock.getNodeNumber();

                /* Changing the node numbers of the swapped nodes */
                highestInBlock.setNodeNumber(currentNode.getNodeNumber());
                currentNode.setNodeNumber(highestNodeNumber);

            }

            /* Incrementing the weight of the current node*/
            currentNode.incrementWeight();

            /* Going up to the tree */
            currentNode = currentNode.parent;
        }
    }

    /**
     * Finds the highest node in the block
     * @param weight
     * @param nodeNumber
     * @return
     */
    public Node findHighestNodeInBlock (int weight, int nodeNumber) {
        Queue<Node> queue = new LinkedList<>();

        /* Clearing the queue and adding the root to it */
        queue.clear();
        queue.add(root);

        /* Using depth first traversal to traverse the tree */
        while(!queue.isEmpty()) {
            Node currentNode = queue.remove();

            /* Returning the current node if it is the block and has a higher node number */
            if (currentNode.getWeight() == weight && currentNode.getNodeNumber() > nodeNumber)
                return currentNode;

            /* Stopping the search if current node in not in the block (has lower weight than targeted node)
            *  Potentially, should optimise the search by a half in terms of time complexity
            */
            if (currentNode.getWeight() < weight)
                return null;

            /* Recursively traversing the tree, right child first! (This way the highest numbered node in the block is reached first) */
            if (currentNode.rightChild != null) queue.add(currentNode.rightChild);
            if (currentNode.leftChild != null) queue.add(currentNode.leftChild);

        }

        return null;
    }

    /**
     * Finds a coding for a particular symbol in the tree
     * @param symbol
     * @return
     */
    public String getSymbolCoding(Node symbol) {
        /* Using stack to store the coding for a particular symbol in reverse */
        Stack coding = new Stack();

        if (symbol.parent == null)
            return "";

        /* Going up the tree and determining the coding */
        while (symbol.parent != null) {
            if (symbol.parent.leftChild == symbol)
                coding.push("0");
            else
                coding.push("1");

            symbol = symbol.parent;
        }

        StringBuilder encoding = new StringBuilder();

        /* Transforming the coding from the stack into a string */
        while (!coding.empty())
            encoding.append(coding.pop());

        return encoding.toString();

    }

    public boolean symbolSeen(String symbol) {
        return symbols.get(symbol) != null;
    }

    public void printTree(Node root) {
        preorderTraversal(root, true);
    }

    /**
     * Traverses the tree in depth first pre order way
     */
    int indentation = 5; // Variable used to keep track of how much indentation is required for a particular node being printed
    public void preorderTraversal(Node currentNode, boolean lastChild) {

        if(currentNode != null) {

            /* Printing the node in particular way depending if it's a right or a left child or parent*/
            if (currentNode == root) {
                System.out.println(String.format("%" + indentation + "s", "") +  "└── " + printNode(currentNode));
            }
            else if (lastChild) {
                System.out.println(String.format("%" + indentation + "s", "") +  "└── " + printNode(currentNode));
            }
            else {
                System.out.println(String.format("%" + indentation + "s", "") +  "├── " + printNode(currentNode));
            }

            /* Increasing the indentation before printing the children and decreasing it after that */
            indentation += 8;
            preorderTraversal(currentNode.rightChild, false);
            preorderTraversal(currentNode.leftChild, true);
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

    public Node getRoot() {
        return root;
    }

    public Node getNYTNode() {
        return nytNode;
    }


}
