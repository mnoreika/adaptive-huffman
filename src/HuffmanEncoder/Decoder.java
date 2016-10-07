package HuffmanEncoder;


import HuffmanTree.Node;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decoder {
    private static FileInputStream in = null;
    private static HuffmanTree.Tree tree = null;
    private static StringBuffer bitBuffer = null;
    private static FileOutputStream out = null;
    private static boolean firstSymbolRead = false;
    private static Node current = null;
    private static String currentByte;

    public static void decode(String inputPath) throws IOException {
        //Initialising the tree
        tree = new HuffmanTree.Tree(300);

        in = new FileInputStream(inputPath);
        out = new FileOutputStream("decoded");
        bitBuffer = new StringBuffer();
        current = tree.getRoot();

        readAllBytes();

        System.out.println(bitBuffer.length());

        while (bitBuffer.length() != 0) {
            //System.out.println(symbol);

            //Decoding first symbol
            if (current == tree.getNYTNode()) {
                //Case of padding with NYT symbols
                if (bitBuffer.length() >= 8) {
                    String firstSymbol = bitBuffer.substring(0, 8);
                    tree.addSymbol(firstSymbol);
                    System.out.println(firstSymbol);
                    int b = Integer.parseInt(firstSymbol, 2);
                    out.write(b);
                    bitBuffer.delete(0, 8);

                    current = tree.getRoot();
                }
                else
                    break;

            }
            else {
                decodeSymbols();
            }

        }

        System.out.println("----------\n" + bitBuffer.toString());

        out.close();

        tree.printTree(tree.getRoot());

    }

    static int countPath = 0;
    private static void decodeSymbols() throws IOException {

        while (bitBuffer.length() != 0) {
            String oneBit = bitBuffer.substring(0, 1);

            if (oneBit.equals("0")) {
                current = current.getLeftChild();
                countPath++;
            }
            else if (oneBit.equals("1")) {
                current = current.getRightChild();
                countPath++;
            }


            if (current.isLeaf()) {
                if (current.getSymbol().equals("NYT")) {
                    countPath = 0;
                    bitBuffer.delete(0, 1);

                    return;
                }
                else {
                    System.out.println("-" + current.getSymbol());
                    updateSymbol(current.getSymbol());
                    int b = (Integer.parseInt(current.getSymbol() , 2));
                    out.write(b);
                    current = tree.getRoot();
                    countPath = 0;
                }



            }

            bitBuffer.delete(0, 1);
        }

    }

    private static void addSymbol(String symbol) {
        tree.addSymbol(symbol);
    }

    private static void updateSymbol(String symbol) {
        tree.updateSymbol(symbol);
    }

    public static void readAllBytes() throws IOException {
        int c;
        while ((c = in.read()) != -1) {
            String symbol = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');

            bitBuffer.append(symbol);
        }
    }




}
