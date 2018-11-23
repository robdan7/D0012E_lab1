package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents a list containing linked nodes. Every node has a link to the next and previous node.
 * @author Robin
 *
 */
public class NodeList<A> implements Iterable<A> {
	private Node<A> start, end;
	private int size = 0;
	
	public NodeList() {
		this.start = new Node<A>(null);
		this.end = new Node<A>(null);
		this.start.setNext(this.end);
		this.end.setPrevious(this.start);
	}
	
	/**
	 * Create a node chain containing all elements in this list.
	 * @return A node chain containing all elements.
	 */
	public NodeChain<A> createNodeChain() {
		if (this.isEmpty()) {
			throw new NullPointerException("the list is empty!");
		}
		return new NodeChain<A>(this.getFirstNode(), this.getLastNode(), this.getSize());
	}
	
	
	/**
	 * Add an element to the end of the list.
	 * @param item
	 */
	public void appendEnd(A item) {
		this.size ++;
		this.end.insertPrevious(new Node<A>(item));
	}
	
	/**
	 * Add an element to the beginning of the list.
	 * @param item
	 */
	public void appendStart(A item) {
		this.size ++;
		this.start.insertNext(new Node<A>(item));
}
	
	@Deprecated
	/**
	 * find a node based on index.
	 * @param i
	 * @return
	 */
	public Node<A> findNode(int i) {
		if (i >= this.getSize() || this.getSize() == 0) {
			throw new IndexOutOfBoundsException();
		}
		return this.start.getNext().findNode(i);
	}
	
	@Deprecated
	/**
	 * Create a list subset of this list. DO NOT USE THIS.
	 * @param start - the start node.
	 * @param end - the end node.
	 */
	public NodeList<A> createListSubset(Node<A> start, Node<A> end) {
		NodeList<A> list = new NodeList<A>();
		
		int i = 1;
		Node<A> n = start;
		

		while (n != null && !n.equals(end)) {
				i ++;
				n = n.getNext();
		}
		
		list.size = i;

		
		start.insertPrevious(list.start);
		end.insertNext(list.end);
		return list;
	}
	

	/**
	 * @return The first element in the list.
	 */
	private Node<A> getFirstNode() {
		if (this.isEmpty()) {
			throw new IndexOutOfBoundsException("the array is empty");
		}
		return this.start.getNext();
	}

	/**
	 * @return The last element in the list.
	 */
	private Node<A> getLastNode() {
		if (this.isEmpty()) {
			throw new IndexOutOfBoundsException("the array is empty");
		}
		return this.end.getPrevious();
	}
	
	public A getFirst() {
		return this.getFirstNode().getValue();
	}
	
	public A getLast() {
		return this.getLastNode().getValue();
	}
	
	/**
	 * Remove the first element from the list.
	 * @return The element of the first node.
	 */
	public A rmStart() {
		if (this.isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		this.size --;
		return (A) this.start.removeNext().getValue();
}
	
	/**
	 * Remove the last element from the list.
	 * @return The element of the last node.
	 */
	public A rmEnd() {
		if (this.isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		this.size --;
		return (A) this.end.removePrevious().getValue();
	}
	
	
	public int getSize() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return this.getSize() == 0;
	}
	
	@Override
	public Iterator<A> iterator() {
		Iterator<A> iterator = new Iterator<A>() {
			Node<A> n = start;
			@Override
			public boolean hasNext() {
				return !n.getNext().equals(end);
			}

			@Override
			public A next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				}
				n = n.getNext();
				return n.getValue();
			}
			
		};
		return iterator;
	}
	

	public NodeList<A> copy() {
		NodeList<A> newList = new NodeList<A>();
		for (A value : this) {
			newList.appendEnd(value);
		}
		return newList;
	}
	
	@Override
	public String toString() {
		return "{" + this.start.getNext().toString() + "}";
	}	
}
