public class measurement1 {
	
	public static void main(String[] args) {

		int[] numToBig = new int[3];
		double[] elapsed = new double[3];
		System.out.println("M, Run-Time, totalLinks, totalCuts, Potential");
		for (int k: new int[] {10,11,12}) {
			for (int i = 0; i < 1000; i++) {
				double c = sequence1(new FibonacciHeap(), k);
				if (c > 0.1) {
					numToBig[k-10]++;
				}
				elapsed[k-10] += c;
			}
		}
		System.out.println(elapsed[0]/1000 + ", " + elapsed[1]/1000 + ", " + elapsed[2]/1000);
		System.out.println(numToBig[0] + ", " + numToBig[1] + ", " + numToBig[2]);
	}

	public static double sequence1(FibonacciHeap heap, int k) {
		int initLinks = FibonacciHeap.totalLinks();
		int initCuts = FibonacciHeap.totalCuts();
		int m = (int) Math.pow(2,k);
		FibonacciHeap.HeapNode [] nodes = new FibonacciHeap.HeapNode[m+1];

		long start = System.nanoTime();
		for(int i=m ; i>=0 ; i--) {
			heap.insert(i);
			nodes[i] = heap.getFirst();
		}
		heap.deleteMin();
		int sum;
		for(int i=0 ; i<k ; i++) {
			sum = 0;
			for(int j=1 ; j<=i ; j++) {
				sum += (m * Math.pow(0.5, j));
			}
			sum += 2;
//			System.out.println("about to decrease "+sum);
			heap.decreaseKey(nodes[sum], m+1);
		}
		heap.decreaseKey(nodes[m-1] , m+1);

		long e = System.nanoTime() - start;

		double elapsed = (double) ((e) * Math.pow(10,-6));

		System.out.println(m +","+
				elapsed + "," +
				(FibonacciHeap.totalLinks() - initLinks) + "," +
				(FibonacciHeap.totalCuts() - initCuts) + "," +
				heap.potential());

		return elapsed;
	}

	public static void printRootChildren(FibonacciHeap heap) {
		FibonacciHeap.HeapNode root = heap.getFirst();
		FibonacciHeap.HeapNode c = root.child;
		do {
			System.out.println(c.prev.key + ", " + c.prev.rank);
			c = c.child;
		} while (c != null);
	}
}
