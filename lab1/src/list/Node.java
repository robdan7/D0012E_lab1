package list;

/**
 * Node class for lists and chains.
 * @author Robin
 *
 * @param <E> - The object type to use.
 */
public class Node<E> {
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
	 * Override the previous node. the chain is not preserved.
	 * @param n
	 */
	void setPrevious(Node<E> n) {
		this.previous = n;
	}
	
	/**
	 * Override the next node. The chain is not preserved.
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
	public E getValue() {
		return this.value;
	}
	
	void setValue(E item) {
		this.value = item;
	}
	
	@Deprecated
	/**
	 * Find a node based on index.
	 * @param index
	 * @return
	 */
	Node<E> findNode(int index) {
		if (index == 0 && this.getValue() != null) {
			return this;
		}
		if (this.hasNext()) {
			return this.getNext().findNode(--index);
		}
		return null;
	}
	
	@Deprecated
	E findNodeValue(int index) {
		if(index == 0) {
			return this.getValue();
		}
		if (this.hasNext()) {
			return this.getNext().findNodeValue(--index);
		}
		throw new NullPointerException("could not find value");
	}
	
	/**
	 * find the node index connected to a certain value.
	 * @param value
	 * @return The node index in the list, starting from this node instance.
	 */
	int findNodeIndex(E value) {
		if (this.getValue().equals(value)) {
			return 0;
		}
		return this.getNext().findNodeIndex(value, 1);
	}
	
	/**
	 * Helper method for findNodeIndex.
	 * @param value
	 * @param index
	 * @return - The node index.
	 */
	private int findNodeIndex(E value, int index) {
		if (this.getValue().equals(value)) {
			return index;
		}
		if (this.hasNext()) {
			return this.getNext().findNodeIndex(value, ++index);
		}
		throw new NullPointerException("could not find node");
	}
	
	@Deprecated
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
			return this.getValue() + "," + this.getNext().toString();
		}
		if (this.getValue() != null) {
			return "" + this.getValue();
		}
		return "";
	}
}
