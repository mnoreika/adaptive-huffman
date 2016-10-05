package HuffmanEncoder;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Huffman.encode("testFiles/test2.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
