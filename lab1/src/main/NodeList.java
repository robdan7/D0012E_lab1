package main;

import java.util.Iterator;

/**
 * This class represents a list containing linked nodes. Every node has a next and a previous node.
 * @author Robin
 *
 */
public class NodeList<A> implements Iterable<A> {
	private Node start, end;
	private int size = 0;
	
	public NodeList() {
		this.start = new Node(null);
		this.end = new Node(null);
		this.start.setNext(this.end);
		this.end.setPrevious(this.start);
	}
	
	/**
	 * Add an element to the end of the list.
	 * @param item
	 */
	public void appendEnd(A item) {
		this.size ++;
		this.end.insertPrevious(new Node(item));
	}
	
	/**
	 * Add an element to the beginning of the list.
	 * @param item
	 */
	public void appendStart(A item) {
		this.size ++;
		this.start.insertNext(new Node(item));
	}
	
	/**
	 * Remove the first element from the list.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public A rmStart() {
		if (this.getSize() == 0) {
			throw new IndexOutOfBoundsException();
		}
		this.size --;
		return (A) this.start.removeNext().getValue();
	}
	
	/**
	 * Remove the last element from the list.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public A rmEnd() {
		if (this.getSize() == 0) {
			throw new IndexOutOfBoundsException();
		}
		this.size --;
		return (A) this.end.removePrevious().getValue();
	}
	
	/**
	 * Get the list size.
	 * @return List size.
	 */
	public int getSize() {
		return this.size;
	}
	
	@Override
	public Iterator iterator() {
		Iterator n = new Iterator() {
			Node n = start;
			@Override
			public boolean hasNext() {
				return !n.getNext().equals(end);
			}

			@Override
			public Object next() {
				
				n = n.getNext();
				return n.getValue();
			}
			
		};
		return n;
	}
	
	/**
	 * Node class.
	 * @author Robin
	 *
	 * @param <E> - The object type to use.
	 */
	private static class Node<E> {
		private E value;
		private Node next, previous;
		
		Node(E value , Node next, Node previous) {
			this.next = next;
			this.previous = previous;
			this.value = value;
		}
		
		Node(E value) {
			this.value = value;
		}
		
		/**
		 * Remove the next node in the chain.
		 * @return The next node in the chain.
		 */
		Node removeNext() {
			Node n = this.getNext();
			this.setNext(n.getNext());
			return n;
		}
		
		/**
		 * Remove the previous node in the chain.
		 * @return the previous node in the chain.
		 */
		Node removePrevious() {
			Node n = this.getPrevious();
			this.setPrevious(n.getPrevious());
			return n;
		}
		
		
		/**
		 * Insert a previous node. The chain is preserved.
		 * @param n - the new node to insert.
		 */
		void insertPrevious(Node n) {
			n.setPrevious(this.getPrevious());
			n.setNext(this);
			this.getPrevious().setNext(n);
			this.setPrevious(n);
			//this.previous = n;
		}
		
		/**
		 * Insert a new node after this one. The chain is preserved.
		 * @param n - The new node to insert.
		 */
		void insertNext(Node n) {
			n.setNext(this.getNext());
			n.setPrevious(this);
			this.getNext().setPrevious(n);
			this.setNext(n);
			//this.next = n;
		}
		
		/**
		 * Override the previous node.
		 * @param n
		 */
		void setPrevious(Node n) {
			this.previous = n;
		}
		
		/**
		 * Override the next node.
		 * @param n
		 */
		void setNext(Node n) {
			this.next = n;
		}
		
		/**
		 * Get the next node.
		 * @return
		 */
		Node getNext() {
			return this.next;
		}
		
		/**
		 * Get the previous node.
		 * @return
		 */
		Node getPrevious() {
			return this.previous;
		}
		
		/**
		 * The the node value.
		 * @return Node value of type <strong>E</strong>.
		 */
		E getValue() {
			return this.value;
		}
	}
}
