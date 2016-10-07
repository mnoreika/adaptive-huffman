package HuffmanEncoder;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Huffman.encode("testFiles/fail.png");

            Decoder.decode("compressed");

            System.out.println("Decoder finished.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
