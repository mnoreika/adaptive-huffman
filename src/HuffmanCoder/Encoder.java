package HuffmanCoder;

import HuffmanTree.Node;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encoder {
    private FileInputStream in = null;
    private HuffmanTree.Tree tree = null;
    private StringBuilder encoding = new StringBuilder();
    private FileOutputStream out = null;

    public void encode(String inputPath) throws IOException {
        //Initialising the tree
        tree = new HuffmanTree.Tree(300);

        in = new FileInputStream(inputPath);
        out = new FileOutputStream(inputPath + ".huf");

        int c;
        while ((c = in.read()) != -1) {
            String symbol = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');

            encodeSymbol(symbol);
        }

        encodeLastByte();

        out.close();

    }

    public void encodeSymbol(String symbol) throws IOException {
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
        if (encoding.length() == 0)
            return;

        while (encoding.length() < 8) {
            encoding.append(tree.getSymbolCoding(tree.getNYTNode()));
        }

       flushByteToFile();

    }

    private void writeEncodingToFile() throws IOException {
        while (encoding.length() >= 8) {
            flushByteToFile();

            encoding.delete(0, 8);
        }
    }

    private void flushByteToFile() throws IOException {
        int b = Integer.parseInt(encoding.substring(0, 8), 2);

        out.write(b);

    }





}
