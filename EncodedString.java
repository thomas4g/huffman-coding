import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class EncodedString implements Iterable<Byte> {
	
	public static final byte ZERO = (byte) 0;
	public static final byte ONE = (byte) 1;
	
	private List<Byte> list;
	
	public EncodedString() {
		list = new ArrayList<>();
	}
	
	public void zero() {
		list.add(ZERO);
	}
	
	public void one() {
		list.add(ONE);
	}
	
	public byte remove() {
		if (!list.isEmpty()) {
			return list.remove(list.size() - 1);
		} else {
			throw new NoSuchElementException(); 
		}
	}
	
	public void concat(EncodedString es) {
		list.addAll(es.list);
	}
	
	public int length() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void clear() {
		list.clear();
	}

	@Override
	public Iterator<Byte> iterator() {
		return new MyIterator();
	}
	
	// Wrapping this class to disable remove.
	private class MyIterator implements Iterator<Byte> {

		private Iterator<Byte> iter;
		
		private MyIterator() {
			iter = list.iterator();
		}
		
		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public Byte next() {
			return iter.next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
