
public class Node implements Comparable<Node> {

	private static int timer = 0;
	
	public final int time;
	public final int frequency;
	public final char character;
	
	public final Node left;
	public final Node right;
	
	public Node(char character, int frequency) {
		this.time = timer++;
		this.character = character;
		this.frequency = frequency;
		this.left = null;
		this.right = null;
	}
	
	public Node(Node less, Node more) {
		this.time = timer++;
		this.character = 0;
		this.frequency = less.frequency + more.frequency;
		this.left = less;
		this.right = more;
	}
	
	@Override
	public int compareTo(Node that) {
		if (this.frequency == that.frequency) {
			if (this.character == that.character) {
				return this.time - that.time;
			} else {
				return this.character - that.character;
			}
		} else {
			return this.frequency - that.frequency;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			return ((Node) obj).time == time;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return time;
	}
}
