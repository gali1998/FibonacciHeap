package src;

/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 */
public class FibonacciHeap {
	private HeapNode min;
	private HeapNode first;
	private int size = 0;
	private int numMarked = 0;
	private int numTrees = 0;

	private static int linksCounter = 0;
	private static int cutsCounter = 0;

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
		this.numTrees++;

		return node;
	}

	private void unmark(HeapNode node) {
		if (node.marked) {
			node.marked = false;
			this.numMarked--;
		}
	}

	private void mark(HeapNode node) {
		node.marked = true;
		this.numMarked++;
	}

	/**
	 * public void deleteMin()
	 *
	 * Delete the node containing the minimum key.
	 *
	 */
	public void deleteMin() {
		if (this.size == 0) {
			return;
		}

		HeapNode subtree = this.min.child;
		if (subtree != null) {

			// first we cut out all min subtrees
			do {
				subtree.parent = null;
				this.unmark(subtree);
				subtree = subtree.next;
			} while (subtree != this.min.child);

			this.meldRoots(subtree);
		}

		// if the min node is the first, we advance it.
		if (first == min) {
			first = first.next;
		}
		this.min.removeFromTree();
		this.min = null;

		this.size--;

		// if we now have an empty tree, we set all pointers to null;
		if (size == 0) {
			first = null;
		} else {
			this.consolidate();
			this.updateMin();
		}

	}

	/**
	 * updating the minimum value key
	 */
	private void updateMin() {
		HeapNode curr = this.first;
		this.min = curr;
		do {
			if (curr.key < this.min.key) {
				this.min = curr;
			}
			curr = curr.next;
		} while (curr != this.first);
	}

	/**
	 * iterating over all roots. upon finding 2 roots of equal rank, we link them
	 * until we finish iterating over known same rank roots.
	 */
	private void consolidate() {
		HeapNode[] buckets = this.toBuckets();
		this.fromBuckets(buckets);
	}

	/**
	 * Sending all trees to buckets in according to rank, and linking trees with the
	 * same buckets
	 * 
	 * @return the buckets containing trees indexed according to rank
	 */
	private HeapNode[] toBuckets() {
		HeapNode[] rankedTrees = new HeapNode[(int) Math.ceil(Math.log(this.size) / Math.log(1.618)) + 1]; // the max
																											// achievable
																											// rank is
																											// log_phi(n)
		this.first.prev.next = null;
		this.first.prev = null;
		HeapNode x = this.first;

		while (x != null) {
			HeapNode y = x;
			x = x.next;
			y.next = y;
			y.prev = y;
			while (rankedTrees[y.rank] != null) {
				y = link(y, rankedTrees[y.rank]);
				rankedTrees[y.rank - 1] = null;
			}
			rankedTrees[y.rank] = y;
		}

		return rankedTrees;
	}

	/**
	 * Resetting the heap to contain all trees from the buckets according to
	 * ascending order.
	 * 
	 * @param buckets - the buckets containing trees indexed by rank
	 */
	private void fromBuckets(HeapNode[] buckets) {
		this.first = null;
		this.numTrees = 0;

		for (HeapNode consolidatedRoot : buckets) {
			if (consolidatedRoot != null) {
				this.numTrees++;
				if (this.first == null) {
					this.first = consolidatedRoot;
					this.first.next = this.first;
					this.first.prev = this.first;
				} else {
					this.first.addSibling(consolidatedRoot);
				}
			}
		}
	}

	/**
	 * private function, linking 2 Fib trees of the same rank
	 * 
	 * @param root1 - one of the roots
	 * @param root2 - second root
	 * @return - the parent node
	 */
	private static HeapNode link(HeapNode root1, HeapNode root2) {
		linksCounter++;
		if (root1.rank != root2.rank) {
			System.out.print("AAAAAA");
		}
		if (root1.getKey() > root2.getKey()) {
			root2.addChild(root1);
			return root2;
		} else {
			root1.addChild(root2);
			return root1;
		}
	}

	/**
	 * public HeapNode findMin()
	 *
	 * Return the node of the heap whose key is minimal.
	 *
	 */
	public HeapNode findMin() {
		return this.min;
	}

	/**
	 * public void meld (FibonacciHeap heap2)
	 *
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2) {
		
		// other heap is empty. no need to meld.
		if (heap2.isEmpty()) {
			return;
		}
		
		// This heap is empty. this heap becomes the other heap.
		if (this.isEmpty()) {
			this.first = heap2.first;
			this.min = heap2.min;
			this.size = heap2.size; 
			this.numMarked = heap2.numMarked;
			this.numTrees = heap2.numTrees;
			
			return;
		}
		
		// No heap is empty. Meld as ususal.
		HeapNode heap1Last = this.first.prev;
		HeapNode heap2Last = heap2.first.prev;
		
		heap1Last.next = heap2.first;
		heap2.first.prev = heap1Last;
		
		heap2Last.next = this.first;
		this.first.prev = heap2Last;
		
		if (heap2.min.getKey() < this.min.getKey()) {
			this.min = heap2.min;
		}
		
		this.size+= heap2.size();
		this.numTrees += heap2.numTrees;
	}

	/**
	 * executing the actual trees lists meld
	 * 
	 * @param first - first of the root list
	 */
	private void meldRoots(HeapNode first) {
		HeapNode last = this.first.prev;
		last.next = first;
		first.prev.next = this.first;
		this.first.prev = first.prev;
		first.prev = last;
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
		int[] result = new int[this.getMaxRank() + 1];

		HeapNode node = this.first.next;

		result[this.first.rank]++;

		while (node != this.first) {
			result[node.rank]++;
			node = node.next;
		}

		return result;
	}

	public int getMaxRank() {
		int max = this.first.rank;

		HeapNode node = this.first.next;

		while (node != this.first) {
			if (node.rank > max) {
				max = node.rank;
			}

			node = node.next;
		}

		return max;
	}

	/**
	 * public void delete(HeapNode x)
	 *
	 * Deletes the node x from the heap.
	 *
	 */
	public void delete(HeapNode x) {
		if (x == null) {
			return;
		}
		int delta = 1 + x.getKey() - this.findMin().getKey(); // we will reduce the key to one less than the minimum

		this.decreaseKey(x, delta);
		this.deleteMin();
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
		x.key = x.key - delta;
		if (x.parent != null) {
			if (x.parent.key > x.key) {
				cascadingCut(x);
			}
		}
		if (x.key < this.min.getKey()) {
			this.min = x;
		}
	}

	/**
	 * performs cascading cuts on x and all successive marked parents
	 * 
	 * @param x - node to cut from the tree
	 */
	private void cascadingCut(HeapNode x) {
		HeapNode xParent = x.parent;
		this.cut(x);
		xParent.rank--;
		if (xParent.parent != null) {
			if (!xParent.marked) {
				this.mark(xParent);
			} else {
				cascadingCut(xParent);
			}
		}
	}

	/**
	 * performing cut operation on x
	 * 
	 * @param x - the root of the subtree we cut from the entire tree
	 */
	private void cut(HeapNode x) {
		x.removeFromTree();
		this.unmark(x);
		this.first.addSibling(x);
		numTrees++;
		cutsCounter++;
	}

	/**
	 * public int potential()
	 *
	 * This function returns the current potential of the heap, which is: Potential
	 * = #trees + 2*#marked The potential equals to the number of trees in the heap
	 * plus twice the number of marked nodes in the heap.
	 */
	public int potential() {
		return numTrees + 2 * numMarked;
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
		return linksCounter;
	}

	/**
	 * public static int totalCuts()
	 *
	 * This static function returns the total number of cut operations made during
	 * the run-time of the program. A cut operation is the operation which
	 * diconnects a subtree from its parent (during decreaseKey/delete methods).
	 */
	public static int totalCuts() {
		return cutsCounter;
	}

	/**
	 * public static int[] kMin(FibonacciHeap H, int k)
	 *
	 * This static function returns the k minimal elements in a binomial tree H. The
	 * function should run in O(k*deg(H)). You are not allowed to change H.
	 */
	public static int[] kMin(FibonacciHeap H, int k) {
		if (H == null || H.isEmpty()) {
			return new int[0];
		}

		int[] result = new int[k];
		
		// This heap will store H's smallest values
		FibonacciHeap candidates = new FibonacciHeap();
		HeapNode next = H.findMin();

		for (int i = 0; i < k; i++) {
			if (next != null) {
				HeapNode stop = next;
				HeapNode node = candidates.insert(next.getKey());
				node.freePointer = next;

				HeapNode iter = next.next;
				
				while (iter != stop) { // at most deg(H) iterations
					next = iter;
					node = candidates.insert(next.getKey());
					node.freePointer = next;
					iter = iter.next;
				}
			}
			
			next = candidates.findMin().freePointer;
	        result[i] = next.getKey();
	        candidates.deleteMin();
	        next = next.child;
		}

		return result;
	}

	// *** REMOVE THIS ****

	public int getNumTrees() {
		return this.numTrees;
	}

	public void printHeap() {
		System.out.println(this.size);
		HeapNode node = this.first;

		for (int i = 0; i < this.size; i++) {
			System.out.print(node.getKey() + " ,");
			node = node.next;
		}

		System.out.println();
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
		public int rank;
		public boolean marked;
		public HeapNode child, next, prev, parent;
		private HeapNode freePointer;

		public HeapNode(int key) {
			this.key = key;
			this.rank = 0;
			this.marked = false;
			this.next = this;
			this.prev = this;
		}

		public int getKey() {
			return this.key;
		}

		public void removeFromTree() {
			// if this is an only child
			if (this.next == this) {
				if (this.parent != null) {
					this.parent.child = null;
					this.parent = null;
				}
			} else {
				if (this.parent != null) {
					if (this.parent.child == this) {
						this.parent.child = this.next;
					}
					this.parent = null;
				}
				this.next.prev = this.prev;
				this.prev.next = this.next;
				this.next = this;
				this.prev = this;
			}
		}

		/**
		 * adds sibling between this, and this.prev (post = (prev, sibling, this))
		 * 
		 * @param sibling - the sibling to insert between prev and this
		 */
		public void addSibling(HeapNode sibling) {
			HeapNode siblingNext = this;
			HeapNode siblingPrev = this.prev;

			sibling.prev.next = siblingNext;
			sibling.prev = siblingPrev;
			siblingPrev.next = sibling;
			this.prev = sibling;
		}

		public void addChild(HeapNode child) {
			if (this.child != null) {
				this.child.addSibling(child);
			}
			this.rank++;
			this.child = child;
			child.parent = this;
		}
	}
}
