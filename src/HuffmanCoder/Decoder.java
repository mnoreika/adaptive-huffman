package HuffmanCoder;


import HuffmanTree.Node;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decoder {

    private FileInputStream in = null; // File input stream
    private HuffmanTree.Tree tree = null; // Tree for the decoder
    private StringBuffer bitBuffer = null; // String buffer to store bits
    private FileOutputStream out = null; // File output stream
    private Node current = null; // Tracks the current node in the tree

    /**
     * Decodes an input file
     * @param inputPath
     * @throws IOException
     */
    public void decode(String inputPath) throws IOException {
        /* Initialising the tree and input output streams*/
        tree = new HuffmanTree.Tree(300);
        in = new FileInputStream(inputPath);
        out = new FileOutputStream(inputPath.substring(0, inputPath.length() - 4) + ".decoded");
        bitBuffer = new StringBuffer();

        /* Setting the current node to root */
        current = tree.getRoot();

        /* Reading the initial bytes */
        readBytes();

        /* Decoding bit by bit while the bit buffer has code to decode */
        while (bitBuffer.length() != 0) {

            /* If buffer is smaller than 64, read more bits from the file
            * This should increase efficiency, as it only read new bytes when it needs them
            */
            if (bitBuffer.length() < 64) {
                readBytes();
            }

            /* Decoding a new symbol if current is NYT node */
            if (current == tree.getNYTNode()) {

                /* Determining if buffer is big enough for a new symbol */
                if (bitBuffer.length() >= 8) {
                    /* Adding the symbol to the tree */
                    String firstSymbol = bitBuffer.substring(0, 8);
                    tree.addSymbol(firstSymbol);

                    /* Writing the symbol to a file */
                    int b = Integer.parseInt(firstSymbol, 2);
                    out.write(b);

                    /* Deleting the symbol from the buffer */
                    bitBuffer.delete(0, 8);

                    /* Resetting the current node to the start of the tree */
                    current = tree.getRoot();
                }
                /* If buffer contains less than a symbol size, that means the last byte has been reached and decoding is finished */
                else
                    break;

            }
            /* Traversing the tree bit by bit until leaf node is reached */
            else {
                if (current.isLeaf()) {

                    /* Updating the symbol in the tree */
                    updateSymbol(current.getSymbol());

                    /* Writing the symbol to a file */
                    int b = Integer.parseInt(current.getSymbol(), 2);
                    out.write(b);

                    /* Resetting the current node to the start of the tree */
                    current = tree.getRoot();

                }

                /* Moving in the tree bit by bit */
                String oneBit = bitBuffer.substring(0, 1);
                bitBuffer.delete(0, 1);

                /* Moving left if bit equals 0 and right if - 1 */
                if (oneBit.equals("0")) {
                    current = current.getLeftChild();
                } else if (oneBit.equals("1")) {
                    current = current.getRightChild();
                }

            }

        }

        /* Closing the file */
        out.close();
    }


    private void addSymbol(String symbol) {
        tree.addSymbol(symbol);
    }

    private void updateSymbol(String symbol) {
        tree.updateSymbol(symbol);
    }

    public void readBytes() throws IOException {
        int c;
        int bytesRead = 0;

        /* Reading 8 bytes from a file */
        while (bytesRead < 8 && (c = in.read()) != -1) {
            String symbol = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');

            bitBuffer.append(symbol);

            bytesRead++;
        }
    }
}
