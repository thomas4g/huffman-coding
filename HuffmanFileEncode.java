import java.io.*;
import java.util.*;

/**
 * A simple program to encode a file
 * There's a lot of things to work on, but... yeah.
 *
 * @author Thomas Shields
 * @version 2013-04-14-22:59
 */

public class HuffmanFileEncode {

    /**
     * @param args      fileIn fileOut encode/decode
     * encode/decode: -e or -d
     * ex: java HuffmanFileEncode plain.txt enc.txt -e
     */
    public static void main(String... args) {
        if(args.length < 3) {
            System.out.println("Dude. Arguments. Did you read the javadocs?");
            System.exit(0);
        }

        String file = "";
        Object toWrite = "";
        try {
            switch(args[2]) {
                case "-e":  
                            file = readFile(args[0]);
                            toWrite = encode(file);
                            break;
                case "-d":  
                            file = readBinary(args[0]);
                            toWrite = decode(file);
                            break;
                default: 
                            System.out.println("Get your arguments right."
                                + "Do you even lift, bro?");
                            System.exit(0);
            }
        }
        catch(IOException ex) {
            System.out.println("UGGGH. PROBLEMZZZ VITH UR FILE BRO");
            System.exit(0);
        }

        try {
            writeFile(args[1], toWrite);
        }
        catch (IOException ex) {
            System.out.println("SUM PROBLEMZS OCCURTHED VITH YOUR FILE MISTER SIR MADAM");
            System.exit(0);
        }
    }

    /**
     * encodes the file using Huffman coding
     * @param file      the file contents to encode
     * @return a byte array of stuff to write, yo.
     */
    private static byte[] encode(String file) {
        Node huffmanTree;
        EncodedString encString = Huffman.encode(
                                    Huffman.buildEncodingMap(
                                        (huffmanTree = Huffman.buildHuffmanTree(
                                            Huffman.buildFrequencyMap(file)
                                            ))), file);

        try 
        {    
            System.out.println("Attempting to serialize huffman tree for later decoding use...");
            serializeHuffmanTree(huffmanTree);
            System.out.println("Huffman tree serialized successfully");
        }       
        catch (IOException ex) {
            System.out.println("Failed to serialize huffman tree, you will be unable to decode this file.");
        }
        return encodedToBytes(encString);
    }

    /**
     * Decodes a file encoded with Huffman
     * @param file      the file contents to decode
     * @return a decoded String, fo shizzle.
     */
    private static String decode(String file){
        Node tree = null;
        try {
            tree = deserializeHuffmanTree();
        }
        catch (IOException ex) {
            System.out.println("Unable to find huffman tree for decoding. Massive failure imminent. Virus activated.");
            System.exit(0);
        }
        catch(ClassNotFoundException ex) {
            System.out.println("Corrupted huffman tree serialization. Imminent failure; massive. Viruses detected, activation begun.");
            System.exit(0);
        }

        return Huffman.decode(tree, stringToEncoded(file));
    }

    private static Node deserializeHuffmanTree() throws IOException, ClassNotFoundException{
        Node tree;
        FileInputStream fileIn = new FileInputStream("huffman.tree");
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        tree = (Node) objectIn.readObject();
        objectIn.close();
        fileIn.close();
        return tree;
    }

    private static void serializeHuffmanTree(Node tree) throws IOException {
        FileOutputStream fileOut = new FileOutputStream("huffman.tree");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(tree);
        out.close();
        fileOut.close();
    }

    private static String readFile(String file) throws FileNotFoundException, IOException {
            FileReader fileReader = new FileReader(file);
            BufferedReader buff = new BufferedReader(fileReader);
            //read here
            String line;
            StringBuilder fileBuilder = new StringBuilder();
            while((line = buff.readLine()) != null) {
                fileBuilder.append(line);
                fileBuilder.append('\n');
            }
            buff.close();
            return fileBuilder.toString();
    }

    private static String readBinary(String file) throws FileNotFoundException, IOException {
        DataInputStream ds = new DataInputStream(new FileInputStream(file));
        StringBuilder builder = new StringBuilder();
        try {   
            while(true) {
                byte b = ds.readByte();
                builder.append(toBinary(b));
            }

        }catch(EOFException eof) {}
        ds.close();
        return builder.toString();
    }

    private static void writeFile(String file, Object contents) throws IOException {
        if(contents instanceof String)
            writeFile(file, (String)contents);
        else if(contents instanceof byte[])
            writeFile(file, (byte[])contents);
        else
            throw new IllegalArgumentException();

    }
    private static void writeFile(String file, String contents) throws IOException{
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(contents);
        bw.close();
    }

    private static void writeFile(String file, byte[] contents) throws IOException{
        DataOutputStream os = new DataOutputStream(new FileOutputStream(file));
        for(int b : contents) {
            os.writeByte(b);
        }
        os.close();
    }

    private static EncodedString stringToEncoded(String str) {
        EncodedString enc = new EncodedString();
        for(char c : str.toCharArray()) {
            if(c == '0')
                enc.zero();
            else
                enc.one();
        }
        return enc;
    }

    private static byte[] encodedToBytes(EncodedString str) {
       return bytesFromString(encodedToString(str));
    }

    private static byte[] bytesFromString(String str) {
        byte[] bytes = new byte[(int)Math.ceil((str.length() / 8))];
        int count = 0;
        int i = 0;
        String currentByte = "";
        for(char c : str.toCharArray()) {
            currentByte += c;
            if(++count == 8) {
                bytes[i++] = fromBinary(currentByte);
                currentByte = "";
                count = 0;
            }
        }

        return bytes;
    }

    /**
     * Converts an EncodedString to a String of 0's and 1's
     * @param str   the encoded string
     * @return a string
     */
    private static String encodedToString(EncodedString str) {
        Iterator<Byte> iter = str.iterator();
        StringBuilder builder = new StringBuilder();
        while(iter.hasNext()) {
            builder.append(iter.next());
        }
        return builder.toString();
    }


    private static String toBinary(int num) {
        String result = "";

        int q = num < 0 ? -num: num;
        while(q > 0) {
            result = q % 2 + result;
            q /=2;
        }
        

        while(result.length() < 7)
            result = '0' + result;

        if(num < 0)
            result = '1' + result;
        else
            result = '0' + result;
        return result;
    }

    /**
     * Converts a string of 0's and 1's to the corresponding
     * decimal number
     * @param binary        the string representing the binary number
     * @return an int; the decimal number
     */
    private static byte fromBinary(String binary) {
        byte result = 0;
        for(int i = 1;i<binary.length();i++) {
            int c = Integer.parseInt(binary.substring(i, i+1));
            result += c == 1 ? (int)Math.pow(2, (binary.length() - 1 - i)) : 0;
        }
        //10000000
        result *= binary.charAt(0) == '0' ? 1 : -1;
        return result;
    }
}