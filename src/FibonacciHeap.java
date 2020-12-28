/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 */
public class FibonacciHeap {
	private HeapNode min;
	private HeapNode first;
	private int size;

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

		return node;
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
		HeapNode node = this.min;
		HeapNode subtree = this.min.child;

		// first we cut out all min subtrees
		do {
			subtree.parent = null;
			subtree = subtree.next;
		} while(subtree != this.min.child);

		this.meldRoots(subtree);

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
		}

		this.consolidate();

		this.updateMin();
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
	 * iterating over all roots. upon finding 2 roots of equal rank, we link them until we finish iterating over
	 *	known same rank roots.
	 */
	private void consolidate() {
		HeapNode[] rankedTrees = new HeapNode[(int) Math.log(this.size)]; // the max achievable rank is log2(n)
		HeapNode last = this.first.prev;
		HeapNode curr = this.first;
		rankedTrees[curr.rank] = curr;

		do {
			curr = attemptConsolidating(curr, rankedTrees);
		} while (curr != last);

		attemptConsolidating(curr, rankedTrees);

		this.resetHeap(rankedTrees);
	}

	/**
	 * after linking all possible root pairs, we set the heap to link to all the trees in the rankedTrees array.
	 * @param rankedTrees - array of all trees post consolidation
	 */
	private void resetHeap(HeapNode[] rankedTrees) {
		HeapNode curr = null;
		this.first = null;

		for (HeapNode consolidatedRoot: rankedTrees) {
			if (consolidatedRoot != null) {
				if (this.first == null) {
					this.first = consolidatedRoot;
					curr = this.first;
					curr.next = this.first;
					curr.prev = this.first;
				} else {
					consolidatedRoot.prev = curr;
					consolidatedRoot.next = this.first;
					curr.next = consolidatedRoot;
				}
			}
		}
	}

	/**
	 * Attempts to link the tree at curr, with any of the already familiar trees in rankedTrees.
	 * 	If rankedTrees[curr.rank] == null (we aren't familiar with trees of the corresponding rank) we add the tree
	 * 	to the array.
	 * @param curr - curr tree to attempt consolidating
	 * @param rankedTrees - familiar ranked trees
	 * @return - the next node to carry on from.
	 */
	private HeapNode attemptConsolidating(HeapNode curr, HeapNode[] rankedTrees) {
		HeapNode consolidated = curr;
		curr = curr.next;

		while (rankedTrees[consolidated.rank] != null) { // we can link
			HeapNode toLink = rankedTrees[consolidated.rank];
			rankedTrees[consolidated.rank] = null;

			consolidated = FibonacciHeap.link(consolidated, toLink);
		}
		rankedTrees[consolidated.rank] = consolidated;

		return curr;
	}


	/**
	 * private function, linking 2 Fib trees of the same rank
	 * @param root1 - one of the roots
	 * @param root2 - second root
	 * @return - the parent node
	 */
	private static HeapNode link(HeapNode root1, HeapNode root2) {
		linksCounter++;
		if (root1.getKey() > root2.getKey()) {
			root1.removeFromTree();
			root2.addChild(root1);
			return root2;
		} else {
			root2.removeFromTree();
			root1.addChild(root1);
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
	 * executing the actual trees lists meld
	 * @param first - first of the root list
	 */
	private void meldRoots(HeapNode first) {
		HeapNode last = this.first.prev;
		last.next = first;
		this.first.prev = first.prev;
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
		x.key =  x.key - delta;
		if (x.parent != null) {
			if (x.parent.key > x.key) {
				cut(x);
			}
		}
	}

	/**
	 * performing cut operation on x, and all marked parents of x recursively
	 * @param x - the root of the subtree we cut from the entire tree
	 */
	private void cut(HeapNode x) {
		HeapNode parent = x.parent;
		x.removeFromTree();
		x.marked = false;
		this.meldRoots(x);
		if (parent.parent != null) {
			if (parent.marked) {
				cut(parent);
			} else {
				parent.marked = true;
			}
		}
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
		public int rank;
		public boolean marked;
		public HeapNode child, next, prev, parent;

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

		public void addChild(HeapNode child) {
			if (this.child != null) {
				this.child.addSibling(child);
			} else {
				this.rank++;
			}
			this.child = child;
			child.parent = this;
		}
	}
}
