package HuffmanCoder;

import HuffmanTree.Node;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encoder {

    private FileInputStream in = null; // File input stream
    private HuffmanTree.Tree tree = null; // Huffman tree for encoder
    private StringBuilder encoding = new StringBuilder(); // Encoding string
    private FileOutputStream out = null; // File output stream

    public static final int MAX_NODE_NUMBER = 300; // Number higher than all possibilities of binary that 1 byte can have

    /**
     * Encodes the input file
     * @param inputPath
     * @throws IOException
     */
    public void encode(String inputPath) throws IOException {
        /* Initialising the tree and input, output streams */
        tree = new HuffmanTree.Tree(MAX_NODE_NUMBER);
        in = new FileInputStream(inputPath);
        out = new FileOutputStream(inputPath + ".huf");

        /* Reading file and encoding it byte by byte */
        int c;
        while ((c = in.read()) != -1) {
            /* Building a string presentation of a byte */
            String symbol = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');

            /* Encoding a byte */
            encodeSymbol(symbol);
        }

        /* Encoding the last byte */
        encodeLastByte();

        /* Closing the file */
        out.close();

    }

    /**
     * Encodes a symbol
     * @param symbol
     * @throws IOException
     */
    public void encodeSymbol(String symbol) throws IOException {
        /* Determining if symbol is seen and encoding, updating the tree accordingly */
        if (tree.symbolSeen(symbol)) {
            Node symbolNode = tree.getSymbols().get(symbol);

            encoding.append(tree.getSymbolCoding(symbolNode));
            tree.updateSymbol(symbol);

        }
        else {
            encoding.append(tree.getSymbolCoding(tree.getNYTNode()));
            encoding.append(symbol);
            tree.addSymbol(symbol);
        }

        writeEncodingToFile();
    }

    public void encodeLastByte() throws IOException {
        /* If the encoding contains some extra bits for the last byte, the remaining bits are filled with path to the NYT symbol */
        if (encoding.length() == 0)
            return;

        while (encoding.length() < 8) {
            encoding.append(tree.getSymbolCoding(tree.getNYTNode()));
        }

       flushByteToFile();

    }

    private void writeEncodingToFile() throws IOException {
        /* When 8bits of the encoding are collected, writing a byte to a output file */
        while (encoding.length() >= 8) {
            flushByteToFile();

            encoding.delete(0, 8);
        }
    }

    private void flushByteToFile() throws IOException {
        /* Converting a binary string representation of a byte to integer and writing that as a byte */
        int b = Integer.parseInt(encoding.substring(0, 8), 2);

        out.write(b);

    }





}
