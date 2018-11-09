package main;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents a list containing linked nodes. Every node has a next and a previous node.
 * @author Robin
 *
 */
public class NodeList<A> implements Iterable<NodeList.Node<A>> {
	private Node<A> start, end;
	private int size = 0;
	
	public NodeList() {
		this.start = new Node<A>(null);
		this.end = new Node<A>(null);
		this.start.setNext(this.end);
		this.end.setPrevious(this.start);
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
	
	public Node<A> findNode(int i) {
		if (i >= this.getSize() || this.getSize() == 0) {
			throw new IndexOutOfBoundsException();
		}
		return this.start.getNext().findNodeIndex(i);
	}
	
	/**
	 * Set this list to be a subset of a larger list. The nodes are the same,
	 * but this list has only access to the nodes within this list.
	 * @param start
	 * @param end
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

		//list.first = start;
		//list.last = end;
		
		start.insertPrevious(list.start);
		end.insertNext(list.end);
		//this.size += 2;
		return list;
	}
	
	/**
	 * @return The first element in the list.
	 */
	public Node<A> getFirst() {
		if (this.isEmpty()) {
			throw new IndexOutOfBoundsException("the array is empty");
		}
		return this.start.getNext();
	}
	
	/**
	 * @return The last element in the list.
	 */
	public Node<A> getLast() {
		if (this.isEmpty()) {
			throw new IndexOutOfBoundsException("the array is empty");
		}
		return this.end.getPrevious();
	}
	
	/**
	 * Remove the first element from the list.
	 * @return
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
	 * @return
	 */
	public A rmEnd() {
		if (this.isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		this.size --;
		return (A) this.end.removePrevious().getValue();
}
	
	public A removeNode(Node<A> n) {
		if (this.isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		n.removeThis();
		this.size --;
		return n.getValue();
	}
	
	/**
	 * Get the list size.
	 * @return List size.
	 */
	public int getSize() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return this.getSize() == 0;
	}
	
	@Override
	public Iterator<NodeList.Node<A>> iterator() {
		Iterator<Node<A>> iterator = new Iterator<Node<A>>() {
			Node<A> n = start;
			@Override
			public boolean hasNext() {
				return !n.getNext().equals(end);
			}

			@Override
			public Node<A> next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				}
				n = n.getNext();
				return n;
			}
			
		};
		return iterator;
	}
	
	@Override
	public String toString() {
		return "{" + this.start.getNext().toString() + "}";
	}
	
	/**
	 * Node class.
	 * @author Robin
	 *
	 * @param <E> - The object type to use.
	 */
	protected static class Node<E> {
		private E value;
		private Node<E> next, previous;
		
		Node(E value , Node<E> next, Node<E> previous) {
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
		Node<E> removeNext() {
			if (this.hasNext()) {
				Node<E> n = this.getNext();
				this.setNext(n.getNext());
				return n;
			}
			return null;
		}
		
		/**
		 * Remove the previous node in the chain.
		 * @return the previous node in the chain.
		 */
		Node<E> removePrevious() {
			if (this.hasPrevious()) {
				Node<E> n = this.getPrevious();
				this.setPrevious(n.getPrevious());
				return n;
			}
			return null;
		}
		
		/**
		 * Remove this node from the chain. Do not remove this node while iterating over it.
		 * @return
		 */
		Node<E> removeThis() {
			this.getNext().setPrevious(this.getPrevious());
			this.getPrevious().setNext(this.getNext());
			return this;
		}
		
		/**
		 * Insert a previous node. The chain is preserved.
		 * @param n - the new node to insert.
		 */
		void insertPrevious(Node<E> n) {
			n.setPrevious(this.getPrevious());
			n.setNext(this);
			if (this.hasPrevious()) {
				this.getPrevious().setNext(n);
			}
			this.setPrevious(n);
			//this.previous = n;
		}
		
		/**
		 * Insert a new node after this one. The chain is preserved.
		 * @param n - The new node to insert.
		 */
		void insertNext(Node<E> n) {
			n.setNext(this.getNext());
			n.setPrevious(this);
			if (this.hasNext()) {
				this.getNext().setPrevious(n);
			}
			this.setNext(n);
			//this.next = n;
		}
		
		/**
		 * Override the previous node.
		 * @param n
		 */
		void setPrevious(Node<E> n) {
			this.previous = n;
		}
		
		/**
		 * Override the next node.
		 * @param n
		 */
		void setNext(Node<E> n) {
			this.next = n;
		}
		
		/**
		 * Get the next node.
		 * @return
		 */
		Node<E> getNext() {
			return this.next;
		}
		
		boolean hasNext() {
			return this.next != null;
		}
		
		boolean hasPrevious() {
			return this.previous != null;
		}
		
		/**
		 * Get the previous node.
		 * @return
		 */
		Node<E> getPrevious() {
			return this.previous;
		}
		
		/**
		 * The the node value.
		 * @return Node value of type <strong>E</strong>.
		 */
		E getValue() {
			return this.value;
		}
		
		void setValue(E item) {
			this.value = item;
		}
		
		/**
		 * Find a node based on index.
		 * @param index
		 * @return
		 */
		Node<E> findNodeIndex(int index) {
			if (index == 0 && this.getValue() != null) {
				return this;
			}
			if (this.hasNext()) {
				return this.getNext().findNodeIndex(--index);
			}
			return null;
		}
		
		int findIndex(Node<E> n ) {
			if (this.equals(n)) {
				return 0;
			}
			if (this.hasNext()) {
				return 1 + this.getNext().findIndex(n);
			}
			return 0;
		}
	
		@Override
		public String toString() {
			if (this.hasNext()) {
				return this.getValue() + ";" + this.getNext().toString();
			}
			if (this.getValue() != null) {
				return "" + this.getValue();
			}
			return "";
		}
	}
	
}
