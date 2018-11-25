package list;

/**
 * Internal node class for lists and chains.
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
			if (n.hasNext()) {
				n.getNext().setPrevious(this);
			}
			return n;
		}
		throw new NullPointerException();
	}
	
	/**
	 * Remove the previous node in the chain. The chain is preserved.
	 * @return the previous node in the chain.
	 */
	Node<E> removePrevious() {
		if (this.hasPrevious()) {
			Node<E> n = this.getPrevious();
			this.setPrevious(n.getPrevious());
			if (n.hasPrevious()) {
				n.getPrevious().setNext(this);
			}
			return n;
		}
		throw new NullPointerException();
	}
	
	/**
	 * Remove this node from the chain. The chain is preserved.
	 * @return this node.
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
	
	/**
	 * Get the previous node.
	 * @return
	 */
	Node<E> getPrevious() {
		return this.previous;
	}
	
	boolean hasNext() {
		return this.next != null;
	}
	
	boolean hasPrevious() {
		return this.previous != null;
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
