import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class HuffmanTests {

    @Test
    public void freqMapTest() {
        Map<Character, Integer> freqMap = Huffman.buildFrequencyMap("Geewillickersg");
        assertEquals(new Integer(1), freqMap.get('G'));
        assertEquals(new Integer(3), freqMap.get('e'));
        assertEquals(new Integer(1), freqMap.get('w'));
        assertEquals(new Integer(2), freqMap.get('i'));
        assertEquals(new Integer(2), freqMap.get('l'));
        assertEquals(new Integer(1), freqMap.get('c'));
        assertEquals(new Integer(1), freqMap.get('k'));
        assertEquals(new Integer(1), freqMap.get('r'));
        assertEquals(new Integer(1), freqMap.get('s'));
        assertEquals(new Integer(1), freqMap.get('g'));
    }    

    @Test
    public void freqMapTest2() {
        Map<Character, Integer> freqMap = Huffman.buildFrequencyMap("Galifinacis");
        assertEquals(new Integer(1), freqMap.get('G'));
        assertEquals(new Integer(2), freqMap.get('a'));
        assertEquals(new Integer(1), freqMap.get('l'));
        assertEquals(new Integer(3), freqMap.get('i'));
        assertEquals(new Integer(1), freqMap.get('f'));
        assertEquals(new Integer(1), freqMap.get('n'));
        assertEquals(new Integer(1), freqMap.get('c'));
        assertEquals(new Integer(1), freqMap.get('s'));
    }

    @Test
    public void treeTest1() {
        Map<Character, Integer> freqMap = new HashMap<Character, Integer>();
        freqMap.put('b', 1);
        freqMap.put('p', 1);
        freqMap.put('j', 3);
        freqMap.put('u', 5);
        freqMap.put(' ', 12);
        freqMap.put('`', 2);
        freqMap.put('m', 2);
        freqMap.put('r', 5);
        freqMap.put('a', 4);
        freqMap.put('i', 4);
        freqMap.put('o', 3);
        freqMap.put('d', 3);
        freqMap.put('e', 8);
        freqMap.put('l', 6);
        freqMap.put('s', 6);
        Node tree = Huffman.buildHuffmanTree(freqMap);
        sop("");
        // print(tree, "");
        sop("");
    }

    @Test
    public void encodingMap1() {
        Map<Character, Integer> freqMap = Huffman.buildFrequencyMap("Galifinacis");
        Node tree = Huffman.buildHuffmanTree(freqMap);
        Map<Character, EncodedString> encMap = Huffman.buildEncodingMap(tree);

        for(Map.Entry<Character, EncodedString> entry : encMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + encString(entry.getValue()));
        }
    }

    @Test
    public void encode1() {
        Map<Character, Integer> freqMap = Huffman.buildFrequencyMap("I'm Zac Galifinacis and I'm fat.");
        Node tree = Huffman.buildHuffmanTree(freqMap);
        Map<Character, EncodedString> encMap = Huffman.buildEncodingMap(tree);
        for(Map.Entry<Character, EncodedString> entry : encMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + encString(entry.getValue()));
        }
        EncodedString string = Huffman.encode(encMap, "I'm Zac Galifinacis and I'm fat.");

        System.out.println("Encoded String: "  + Math.ceil(string.length()/8) + " bytes");
        System.out.println("Normal String: "  + "I'm Zac Galifinacis and I'm fat.".length() + " bytes");
        System.out.println(encString(string));

    }

    @Test
    public void decode1() {
        Map<Character, Integer> freqMap = Huffman.buildFrequencyMap("I'm Zac Galifinacis and I'm fat.");
        Node tree = Huffman.buildHuffmanTree(freqMap);
        Map<Character, EncodedString> encMap = Huffman.buildEncodingMap(tree);
        EncodedString enc = Huffman.encode(encMap, "I'm Zac Galifinacis and I'm fat.");

        assertEquals("I'm Zac Galifinacis and I'm fat.", Huffman.decode(tree, enc));
    }

    @Test
    public void decode2() {
        Map<Character, Integer> freqMap = Huffman.buildFrequencyMap("Polly Wolly Snack Pack Pocket");
        Node tree = Huffman.buildHuffmanTree(freqMap);
        Map<Character, EncodedString> encMap = Huffman.buildEncodingMap(tree);
        EncodedString enc = Huffman.encode(encMap, "Polly Wolly Snack Pack Pocket");

        assertEquals("Polly Wolly Snack Pack Pocket", Huffman.decode(tree, enc));
    }    

    @Test
    public void test1() {
        Map<Character, Integer> freqMap = Huffman.buildFrequencyMap("aaaaa");
        assertEquals(new Integer(5), freqMap.get('a'));
        Node tree = Huffman.buildHuffmanTree(freqMap);
        Map<Character, EncodedString> encMap = Huffman.buildEncodingMap(tree);
        EncodedString zero = new EncodedString();
        zero.zero();
        // assertEquals(zero, encMap.get('a'));
        EncodedString enc = Huffman.encode(encMap, "aaaaa");
        assertEquals(5, enc.length());
        assertEquals("aaaaa", Huffman.decode(tree, enc));
    }

    @Test
    public void randomTests() {
        Map<Character, Integer> freqMap;
        Node tree;
        Map<Character, EncodedString> encMap;
        EncodedString enc;
        String message;
        StringBuilder builder;
        Random rand;
        boolean passed;


        for(int i = 0;i<1000;i++) {
            rand = new Random();
            builder = new StringBuilder();
            for(int j =0;j<10000;j++) {
                builder.append((char)(rand.nextInt(527) + 133));
            }
            message = builder.toString();
            freqMap = Huffman.buildFrequencyMap(message);
            tree = Huffman.buildHuffmanTree(freqMap);
            encMap = Huffman.buildEncodingMap(tree);
            enc = Huffman.encode(encMap, message);
            if(i % 50 == 0) {
                System.out.println("Test " + i + " of 10000");
                System.out.println(message);
            }
            passed = message.equals(Huffman.decode(tree, enc));
            if(!passed)
                System.out.println("FAILED: Message was: " + message);
            assertTrue(passed);

        }
    }





    /**************************************************************/
    /* Print stuff yo       */
    /**************************************************************/

    private static String encString(EncodedString str) {
        Iterator<Byte> iter = str.iterator();
        String result = "";
        while(iter.hasNext()) {
            result += iter.next();
        }
        return result;
    }


    public static void betterPrint(Node root) {
        if(null != root) {
            LinkedList<Node> list1 = new LinkedList<Node>();
            list1.add(root);
            betterPrint(list1, new LinkedList<Node>(), "");
        }
    }

    private static LinkedList betterPrint(LinkedList<Node> list, LinkedList<Node> list2, String result) {
        if(!list.isEmpty()) {
            Node curr = list.remove();
            result += "  " + curr.character + ":" + curr.frequency + "  ";
            if(null != curr.left) list2.add(curr.left);
            if(null != curr.right) list2.add(curr.right);

            list2 = betterPrint(list, list2, result);
        }
        else if(!list2.isEmpty()){
            result += "\n";
            list = new LinkedList<Node>();
            list2 = betterPrint(list2, list, result);
        }
        else {
            String[] lines = result.split("\n");
            for(int i=0;i<lines.length;i++) {
                String line = "";
                for(int j=0;j<(lines[lines.length-1].length() - lines[i].length())/2;j++) line += " ";
                line += "  " + lines[i] + "  ";
                sop(line);
            }
        }
        return list2;

    }

    private static void sop(Object o) {
        System.out.println(o.toString());
    }

    private static void print(Node n, String depth) {
        String depthIncrementer = "   ";
        System.out.println(depth + n.character +":" + n.frequency + depth);
        if(!(n.left == null && n.right == null)) {
            if(n.left != null) print(n.left, depth + depthIncrementer);
            else System.out.println(depth + depthIncrementer  + "**");
            if(n.right != null) print(n.right, depth + depthIncrementer);
            else System.out.println(depth + depthIncrementer + "**");
        }
    }

}