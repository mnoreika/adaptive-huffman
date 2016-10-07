package HuffmanEncoder;

import HuffmanTree.Node;

import javax.xml.bind.SchemaOutputResolver;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Huffman {
    private static FileInputStream in = null;
    private static HuffmanTree.Tree tree = null;
    private static StringBuilder encoding = new StringBuilder();
    private static FileOutputStream out = null;

    public static void encode(String inputFile) throws IOException {
        //Initialising the tree
        tree = new HuffmanTree.Tree(300);

        in = new FileInputStream(inputFile);
        out = new FileOutputStream("compressed");

        int c;
        while ((c = in.read()) != -1) {
            String symbol = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');

            encodeSymbol(symbol);
        }

        encodeLastByte();

        tree.printTree(tree.getRoot());

        out.close();

        System.out.println("Encoding finished.");


    }

    public static void encodeSymbol(String symbol) throws IOException {
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

    public static void encodeLastByte() throws IOException {
        if (encoding.length() == 0)
            return;

        while (encoding.length() < 8) {
            encoding.append(tree.getSymbolCoding(tree.getNYTNode()));
        }

       flushByteToFile();

    }

    private static void writeEncodingToFile() throws IOException {
        while (encoding.length() >= 8) {
            flushByteToFile();

            encoding.delete(0, 8);
        }
    }

    private static void flushByteToFile() throws IOException {
        int b = Integer.parseInt(encoding.substring(0, 8), 2);

        out.write(b);
    }





}
