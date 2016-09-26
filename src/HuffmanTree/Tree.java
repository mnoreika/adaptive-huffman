package HuffmanTree;

import java.util.ArrayList;

public class Tree {
    Node root = null;
    ArrayList symbolList = new ArrayList();

    public Tree (int messageLength) {
        root = new Node(0, 2 * messageLength + 1, "NYT");
    }

    public Node getRoot() {
        return root;
    }

    public void addSymbol(String symbol) {
        if (symbolSeen(symbol)) {
            System.out.println("Seen");
        } else {
            symbolList.add(symbol);
            System.out.println("Not seen");
        }
    }

    private boolean symbolSeen(String symbol) {
        for (int i = 0; i < symbolList.size(); i++) {
            if (symbolList.get(i).equals(symbol))
                return true;
        }

        return false;
    }


    //Might not be good
    public String char2binary(char letter)
    {
        int num = (int)letter;
        StringBuffer backwards = new StringBuffer(10);
        StringBuffer binary = new StringBuffer(10);

        while (num > 0)
        {
            backwards.append( num % 2 );
            num /= 2;
        }

        binary.setLength( backwards.length() );

        for (int i = backwards.length()-1; i >= 0 ; i--)
        {
            binary.setCharAt( backwards.length()-1 - i, backwards.charAt(i) );
        }

        return (binary.toString());
    }




}
