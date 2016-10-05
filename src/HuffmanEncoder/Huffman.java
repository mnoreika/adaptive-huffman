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
        tree = new HuffmanTree.Tree(50);

        in = new FileInputStream(inputFile);
        out = new FileOutputStream("compressed.huf");

        int c;
        while ((c = in.read()) != -1) {
            String symbol = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');

            encodeSymbol(symbol);
        }


        System.out.println("Encoding finished.");
//        System.out.println();
//        tree.printTree(tree.getRoot());

        out.close();
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

    private static void writeEncodingToFile() throws IOException {
        while (encoding.length() >= 8) {
            short a = Short.parseShort(encoding.substring(0, 8), 2);
            ByteBuffer bytes = ByteBuffer.allocate(2).putShort(a);

            byte[] array = bytes.array();

            out.write(array[0]);
            encoding.delete(0, 8);
        }
    }





}
