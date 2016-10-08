package HuffmanCoder;


import HuffmanTree.Node;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decoder {
    private FileInputStream in = null;
    private HuffmanTree.Tree tree = null;
    private StringBuffer bitBuffer = null;
    private FileOutputStream out = null;
    private boolean firstSymbolRead = false;
    private Node current = null;
    private String currentByte;

    private static FileInputStream in2 = null;

    public void decode(String inputPath) throws IOException {
        //Initialising the tree
        tree = new HuffmanTree.Tree(300);

        in = new FileInputStream(inputPath);
        out = new FileOutputStream(inputPath.substring(0, inputPath.length() - 4) + ".decoded");
        bitBuffer = new StringBuffer();
        current = tree.getRoot();

        readBytes();

        //System.out.println(bitBuffer.length());

        while (bitBuffer.length() != 0) {
//            System.out.println(bitBuffer.toString());
            if (bitBuffer.length() < 64) {
                readBytes();
            }


            //Decoding first symbol
            if (current == tree.getNYTNode()) {
                //Case of padding with NYT symbols
                if (bitBuffer.length() >= 8) {
                    String firstSymbol = bitBuffer.substring(0, 8);
                    tree.addSymbol(firstSymbol);
                    //System.out.println(firstSymbol);
                    int b = Integer.parseInt(firstSymbol, 2);
                    out.write(b);
                    bitBuffer.delete(0, 8);

                    current = tree.getRoot();
                }
                else
                    break;

            }
            else {
                if (current.isLeaf()) {

                        //System.out.println("-" + current.getSymbol());
                        updateSymbol(current.getSymbol());
                        int b = (Integer.parseInt(current.getSymbol() , 2));
                        out.write(b);
                        current = tree.getRoot();

                }


                    String oneBit = bitBuffer.substring(0, 1);
                    bitBuffer.delete(0, 1);

                    if (oneBit.equals("0")) {
                        current = current.getLeftChild();
                    } else if (oneBit.equals("1")) {
                        current = current.getRightChild();
                    }




            }

        }

        //System.out.println("----------\n" + bitBuffer.toString());

        out.close();

        //tree.printTree(tree.getRoot());

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
        while (bytesRead < 8 && (c = in.read()) != -1) {
            String symbol = String.format("%8s", Integer.toBinaryString(c & 0xFF)).replace(' ', '0');

            bitBuffer.append(symbol);

            bytesRead++;
        }
    }




}
