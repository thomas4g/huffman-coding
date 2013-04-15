import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

/**
 * @author Jared Moore
 * @version Apr 12, 2013
 */
public class HuffmanTest {

	private final String EASY = "ab", ONE_LETTER = "a", DOUBLE = "aabb",
			SENTENCE = "The quick brown fox trips over the lazy dog.",
			CAPITALS = "aAaAaAaAaA",
			SENTENCE2 = "This is a test sentence for Huffman's algorithm.";

	@Test
	public void testEasy() {
		Node tree = Huffman.buildHuffmanTree(Huffman.buildFrequencyMap(EASY));
		assertEquals(EASY, Huffman.decode(tree, Huffman.encode(Huffman.buildEncodingMap(tree), EASY)));
	}

	@Test
	public void testOneLetter() {
		Node tree = Huffman.buildHuffmanTree(Huffman.buildFrequencyMap(ONE_LETTER));
		assertEquals(ONE_LETTER, Huffman.decode(tree, Huffman.encode(Huffman.buildEncodingMap(tree), ONE_LETTER)));
	}

	@Test
	public void testDouble() {
		Node tree = Huffman.buildHuffmanTree(Huffman.buildFrequencyMap(DOUBLE));
		assertEquals(DOUBLE, Huffman.decode(tree, Huffman.encode(Huffman.buildEncodingMap(tree), DOUBLE)));
	}

	@Test
	public void testSentence() {
		Node tree = Huffman.buildHuffmanTree(Huffman.buildFrequencyMap(SENTENCE));
		assertEquals(SENTENCE, Huffman.decode(tree, Huffman.encode(Huffman.buildEncodingMap(tree), SENTENCE)));
	}

	@Test
	public void testCapitals() {
		Node tree = Huffman.buildHuffmanTree(Huffman.buildFrequencyMap(CAPITALS));
		assertEquals(CAPITALS, Huffman.decode(tree, Huffman.encode(Huffman.buildEncodingMap(tree), CAPITALS)));
	}

	@Test
	public void testSentence2() {
		Node tree = Huffman.buildHuffmanTree(Huffman.buildFrequencyMap(SENTENCE2));
		assertEquals(SENTENCE2, Huffman.decode(tree, Huffman.encode(Huffman.buildEncodingMap(tree), SENTENCE2)));
	}
	
	@Test (timeout = 1000)
	public void testFrequencyTable1(){
		String s = "aaabcdzz";
		Map<Character, Integer> map = Huffman.buildFrequencyMap(s);
		
		assertEquals((Integer) 3, map.get('a'));
		assertEquals((Integer) 1, map.get('b'));
		assertEquals((Integer) 1, map.get('c'));
		assertEquals((Integer) 1, map.get('d'));
		assertEquals((Integer) 2, map.get('z'));
	}
	
	@Test (timeout = 1000)
	public void testBuildHuffmanTree1(){
		HashMap<Character, Integer> frequencyMap = new HashMap<Character,Integer>();
		
		frequencyMap.put('a', 5);
		frequencyMap.put('b', 1);
		frequencyMap.put('c', 2);
		
		Node tree = Huffman.buildHuffmanTree(frequencyMap);
		
		assertEquals((Integer) 8, (Integer) tree.frequency);
		assertEquals((Integer) 3, (Integer) tree.left.frequency);
		assertEquals((Integer) 5, (Integer) tree.right.frequency);
		assertEquals((Integer) 1, (Integer) tree.left.left.frequency);
		assertEquals((Integer) 2, (Integer) tree.left.right.frequency);
	}
	
	@Test (timeout = 1000)
	public void testBuildEncodingMap1(){
		Node rrr = new Node('a',5);
		Node rrl = new Node('b',4);
		
		Node rr = new Node(rrl, rrr);
		Node rl = new Node('c', 3);
		
		Node r = new Node(rl, rr);
		
		Map<Character, EncodedString> encodingMap = Huffman.buildEncodingMap(r);

		assertEquals("0",HuffmanTest.encodedString(encodingMap.get('c')));
		assertEquals("10",HuffmanTest.encodedString(encodingMap.get('b')));
		assertEquals("11",HuffmanTest.encodedString(encodingMap.get('a')));
	}
	
	public static String encodedString(EncodedString s){
		String toReturn = "";
		Iterator<Byte> iterator = s.iterator();
		while(iterator.hasNext()){
			if ((int)iterator.next() == 1)
				toReturn += "1";
			else
				toReturn += "0";
		}
		
		return toReturn;
	}
	
	
}