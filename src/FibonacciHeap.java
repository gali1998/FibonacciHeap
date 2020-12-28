/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 */
public class FibonacciHeap {
	private HeapNode min;
	private HeapNode first;
	private int size;

	/**
	 * public boolean isEmpty()
	 *
	 * precondition: none
	 * 
	 * The method returns true if and only if the heap is empty.
	 * 
	 */
	public boolean isEmpty() {
		return this.min == null;
	}

	/**
	 * public HeapNode insert(int key)
	 *
	 * Creates a node (of type HeapNode) which contains the given key, and inserts
	 * it into the heap.
	 * 
	 * Returns the new node created.
	 */
	public HeapNode insert(int key) {
		HeapNode node = new HeapNode(key);

		if (this.isEmpty()) {
			node.next = node;
			node.prev = node;
			this.min = node;
			this.first = node;
			this.size = 1;
		} else {
			this.first.addSibling(node);
			this.size++;
		}

		if (key < this.min.getKey()) {
			this.min = node;
		}
		
		this.first = node;

		return node;
	}

	/**
	 * public void deleteMin()
	 *
	 * Delete the node containing the minimum key.
	 *
	 */
	public void deleteMin() {
		return; // should be replaced by student code

	}

	/**
	 * public HeapNode findMin()
	 *
	 * Return the node of the heap whose key is minimal.
	 *
	 */
	public HeapNode findMin() {
		return new HeapNode(0);// should be replaced by student code
	}

	/**
	 * public void meld (FibonacciHeap heap2)
	 *
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2) {
		return; // should be replaced by student code
	}

	/**
	 * public int size()
	 *
	 * Return the number of elements in the heap
	 * 
	 */
	public int size() {
		return this.size;
	}

	/**
	 * public int[] countersRep()
	 *
	 * Return a counters array, where the value of the i-th entry is the number of
	 * trees of order i in the heap.
	 * 
	 */
	public int[] countersRep() {
		int[] arr = new int[42];
		return arr; // to be replaced by student code
	}

	/**
	 * public void delete(HeapNode x)
	 *
	 * Deletes the node x from the heap.
	 *
	 */
	public void delete(HeapNode x) {
		
	}
	
	public void delete(int x) {
		HeapNode node = this.first;
		HeapNode toDelete = null;
		for (int i = 0; i < this.size; i++) {
			if (node.getKey() == x) {
				toDelete = node;
			}
			
			node = node.next;
		}
		
		toDelete.removeFromTree();
	}

	/**
	 * public void decreaseKey(HeapNode x, int delta)
	 *
	 * The function decreases the key of the node x by delta. The structure of the
	 * heap should be updated to reflect this chage (for example, the cascading cuts
	 * procedure should be applied if needed).
	 */
	public void decreaseKey(HeapNode x, int delta) {
		return; // should be replaced by student code
	}

	/**
	 * public int potential()
	 *
	 * This function returns the current potential of the heap, which is: Potential
	 * = #trees + 2*#marked The potential equals to the number of trees in the heap
	 * plus twice the number of marked nodes in the heap.
	 */
	public int potential() {
		return 0; // should be replaced by student code
	}

	/**
	 * public static int totalLinks()
	 *
	 * This static function returns the total number of link operations made during
	 * the run-time of the program. A link operation is the operation which gets as
	 * input two trees of the same rank, and generates a tree of rank bigger by one,
	 * by hanging the tree which has larger value in its root on the tree which has
	 * smaller value in its root.
	 */
	public static int totalLinks() {
		return 0; // should be replaced by student code
	}

	/**
	 * public static int totalCuts()
	 *
	 * This static function returns the total number of cut operations made during
	 * the run-time of the program. A cut operation is the operation which
	 * diconnects a subtree from its parent (during decreaseKey/delete methods).
	 */
	public static int totalCuts() {
		return 0; // should be replaced by student code
	}

	/**
	 * public static int[] kMin(FibonacciHeap H, int k)
	 *
	 * This static function returns the k minimal elements in a binomial tree H. The
	 * function should run in O(k*deg(H)). You are not allowed to change H.
	 */
	public static int[] kMin(FibonacciHeap H, int k) {
		int[] arr = new int[42];
		return arr; // should be replaced by student code
	}

	// *** REMOVE THIS ****

	public void printHeap() {
		HeapNode node = this.first;

		for (int i = 0; i < this.size; i++) {
			System.out.print(node.getKey() + " ,");
			node = node.next;
		}
	}
	
	public HeapNode getFirst() {
		return this.first;
	}

	/**
	 * public class HeapNode
	 * 
	 * If you wish to implement classes other than FibonacciHeap (for example
	 * HeapNode), do it in this file, not in another file
	 * 
	 */
	public class HeapNode {
		public int key;

		// our fields
		private int rank;
		private boolean marked;
		private HeapNode child, next, prev, parent;

		public HeapNode(int key) {
			this.key = key;
			this.rank = 0;
			this.marked = false;
		}

		public int getKey() {
			return this.key;
		}
		
		public void removeFromTree() {
			// if this is an only child
			if (this.next == this) {
				if (this.parent != null) {
					this.parent.child = null;
					this.parent.rank--;
				}
			} else {
				if (this.parent != null) {
					if (this.parent.child == this) {
						this.parent.child = this.next;
					}
					
					this.parent.rank--;
				}
				
				this.next.prev = this.prev;
				this.prev.next = this.next;
			}
		}

		public void addSibling(HeapNode sibling) {
			HeapNode siblingNext = this;
			HeapNode siblingPrev = this.prev;

			sibling.next = siblingNext;
			sibling.prev = siblingPrev;
			siblingPrev.next = sibling;
			this.prev = sibling;

			if (this.parent != null) {
				this.parent.rank++;
			}
		}

	}
}
