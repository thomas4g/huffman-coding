import java.util.*;

public class Huffman {

	/**
	 * Builds a frequency map of characters for the given string.
	 * 
	 * This should just be the count of each character.
	 * 
	 * @param s
	 * @return
	 */
	public static Map<Character, Integer> buildFrequencyMap(String s) {
		Map<Character, Integer> freqMap = new HashMap<Character, Integer>();
		Integer freq = 0;
		for(Character c : s.toCharArray()) {
			freq = null == (freq = freqMap.get(c)) ? 1 : ++freq;
			freqMap.put(c, freq);
		}
		return freqMap;
	}
	
	/**
	 * Build the Huffman tree using the frequencies given.
	 * 
	 * The frequency map will not necessarily come from the buildFrequencyMap() method.
	 * 
	 * @param freq
	 * @return
	 */
	public static Node buildHuffmanTree(Map<Character, Integer> freq) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		for(Map.Entry<Character, Integer> entry : freq.entrySet()) {
			pq.add(new Node(entry.getKey(), entry.getValue()));
		}
		if(pq.size() == 1) {
			pq.add(new Node(pq.poll(), new Node((char)0, 0)));
		}
		else {
			while(pq.size() > 1) {
				pq.add(new Node(pq.poll(), pq.poll()));
			}
		}
		return pq.poll();
	}
	
 	/**
 	 * Traverse the tree and extract the encoding for each character in the tree
 	 * 
 	 * The tree provided will be a valid huffman tree but may not come from the buildHuffmanTree() method.
 	 * 
 	 * @param tree
 	 * @return
 	 */
 	public static Map<Character, EncodedString> buildEncodingMap(Node tree) {
		return buildEncodingMap(tree, new EncodedString(), new HashMap<Character, EncodedString>());
 	}

 	private static Map<Character, EncodedString> buildEncodingMap(Node tree, EncodedString soFar, Map<Character, EncodedString> encMap) {
 		if(tree.character != 0) {
 			EncodedString str = new EncodedString();
 			str.concat(soFar);
 			encMap.put(tree.character, str);
 		}
 		if(null != tree.left)
 		{
 			//go left
 			EncodedString str = new EncodedString();
 			str.concat(soFar);
 			str.zero();
 			buildEncodingMap(tree.left, str, encMap);
 		}
 		if(null != tree.right)
 		{
 			//go right
 			EncodedString str = new EncodedString();
 			str.concat(soFar);
 			str.one();
 			buildEncodingMap(tree.right, str, encMap);
 		}
 		return encMap;
 	}
	
	/**
	 * Encode each character in the string using the map provided.
	 * 
	 * If a character in the string doesn't exist in the map ignore it.
	 * 
	 * The encoding map may not necessarily come from the buildEncodingMap() method, but will be correct
	 * for the tree given to decode() when decoding this method's output.
	 * 
	 * @param encodingMap
	 * @param s
	 * @return
	 */
	public static EncodedString encode(Map<Character, EncodedString> encodingMap, String s) {
		EncodedString str = new EncodedString();
		for(char c : s.toCharArray()) {
			if(null != encodingMap.get(c))
				str.concat(encodingMap.get(c));
		}
		return str;
	}
	
	/**
	 * Decode the encoded string using the tree provided.
	 * 
	 * The encoded string may not necessarily come from encode, but will be a valid string for the given tree.
	 * 
	 * (tip: use StringBuilder to make this method faster -- concatenating strings is SLOW)
	 * 
	 * @param tree
	 * @param es
	 * @return
	 */
	public static String decode(Node tree, EncodedString es) {
		Iterator<Byte> iter = es.iterator();
		StringBuilder builder = new StringBuilder();
		Byte next;
		Node curr = tree;
		while(iter.hasNext()) {
			next = iter.next();
			curr = next == 0 ? curr.left : curr.right;

			if(null == curr) {
				//crash and burn
			}

			else if(curr.character != 0) {
				builder.append(curr.character);
				curr = tree;
			}
		}

		return builder.toString();
	}
}
