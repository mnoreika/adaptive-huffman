package HuffmanEncoder;

public class Huffman {

    public static void encode(String message) {
        //Initialising the tree
        HuffmanTree.Tree tree = new HuffmanTree.Tree(message.length());

        //Iterating trough each symbol and building the tree
        for (int i = 0; i < message.length(); i++) {
            tree.addSymbol(Character.toString(message.charAt(i)));
        }

        tree.printTree(tree.getRoot());



    }
}
