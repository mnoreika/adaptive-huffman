package HuffmanCoder;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;

public class Huffman {

    public static void main(String[] args) {

        try {

            /* Reading arguments from the console */
            if (args.length > 0) {

                /* Initialising the encoder and decoding the file */
                if (args[0].equals("encode")) {
                    Encoder encoder = new Encoder();

                    System.out.println("Encoding file " + args[1] + "...");

                    encoder.encode(args[1]);

                    System.out.println("Encoding finished.\n");

                }

                /* Initialising the decoder and decoding the file */
                else if (args[0].equals("decode")) {
                    Decoder decoder = new Decoder();

                    System.out.println("Decoding file " + args[1] + "...");

                    decoder.decode(args[1]);

                    System.out.println("Decoding finished.\n");
                }

                /* Printing out an error message if arguments are invalid */
                else {
                    System.out.println("Arguments are invalid.");
                }

            }

            /* Printing out an error message if arguments are missing */
            else {
                System.out.println("Arguments are missing.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
}

