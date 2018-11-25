package list;

import java.util.Iterator;

import misc.Tuple;

/**
 * This class represents a chain of nodes without start and stop nodes. Nodes cannot be removed or added.
 * @author Robin
 *
 * @param <E>
 */
public class NodeChain<E> implements Iterable<Node<E>> {
	private Node<E> first, last;
	private int size;
	
	NodeChain(Node<E> first, Node<E> last, int size) {
		this.first = first;
		this.last = last;
		this.size = size;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Split the chain in two parts. The index is discarded.
	 * @param splitter - The element used for splitting.
	 * @param index - the index connected to the splitter.
	 * @return
	 */
	public Tuple<NodeChain<E>, NodeChain<E>> splitChain(Node<E> splitter, int index) {
		Tuple<Integer,Integer> sizes = this.calcSplitSize(index);

		NodeChain<E> chain1 = new NodeChain<E>(this.first, splitter.getPrevious(), sizes.x);
		NodeChain<E> chain2  = new NodeChain<E>(splitter.getNext(), this.last, sizes.y);
		
		return new Tuple<NodeChain<E>, NodeChain<E>>(chain1,chain2);
	}
	
	private Tuple<Integer, Integer> calcSplitSize(int index) {
		return new Tuple<Integer,Integer>(index, this.getSize()-1-index);
	}
	
	/**
	 * Find the node at a specified index. This function returns a node, not a value(!)
	 * @param index - The index.
	 * @return Node at the index.
	 */
	public Node<E> findNode(int index) {
		if (index < 0 || index >= this.getSize()) {
			throw new IndexOutOfBoundsException();
		}
		int i = 0;
		
		for (Node<E> n : this) {
			if (i == index) {
				return n;
			}
			i ++;
		}
		return null;
	}
	
	/**
	 * @return The value of the first item.
	 */
	public E getFirst() {
		return this.first.getValue(); 
	}
	
	/**
	 * @return The value of the last item.
	 */
	public E getLast() {
		return this.last.getValue();
	}
	
	/**
	 * Move a node to the beginning of the chain.
	 * No exception is thrown if the node does not exist in the chain.
	 * @param node
	 */
	public void moveFirst(Node<E> node) {
		if (node.equals(this.last)) {
			this.last = this.last.getPrevious();
		}
		node.removeThis();
		this.first.insertPrevious(node);
		this.first = node;
		//this.last = this.last.getPrevious();
		//this.first.insertPrevious(node);
		//this.first = node;
	}
	
	/**
	 * Move a node to the end of the chain.
	 * No exception is thrown if the node does not exist in the chain.
	 * @param node
	 */
	public void moveLast(Node<E> node) {
		if (this.first.equals(node)) {
			this.first = this.first.getNext();
		}
		node.removeThis();
		this.last.insertNext(node);
		this.last = node;
	}

	@Override
	public Iterator<Node<E>> iterator() {
		return new Iterator<Node<E>>(){
			Node<E> current = first;
			Node<E> previous = current.getPrevious();
			Node<E> endNode = last;

			@Override
			public boolean hasNext() {
				return !(this.current == null || previous.equals(this.endNode));
			}

			@Override
			public Node<E> next() {
				if (!this.hasNext()) {
					throw new IndexOutOfBoundsException();
				}
				previous = this.current;
				this.current = this.current.getNext();
				return previous;
			}
		};
	}
	
	@Override
	public String toString() {
		String s = "{";
		for (Node<E> n : this) {
			s += "" + n.getValue() + ";";
		}
		
		s += "}";
		return s;
	}	

}
