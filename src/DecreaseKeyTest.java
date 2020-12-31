import java.util.concurrent.ThreadLocalRandom;

public class DecreaseKeyTest {

    public static boolean decreaseKeyTests(FibonacciHeap heap, int delta, FibonacciHeap.HeapNode x) {
        if (x.parent != null) {
            if (x.parent.marked) {
                int jasdf = 1;
            }
        }
        int numTrees = TestUtils.countTrees(heap);
        int n = heap.getNumTrees();
        int priorCuts = FibonacciHeap.totalCuts();
        heap.decreaseKey(x, delta);
        int treesDelta = TestUtils.countTrees(heap) - numTrees;
        int td = heap.getNumTrees() - n;
        int cutsDelta = FibonacciHeap.totalCuts() - priorCuts;

        if (treesDelta != cutsDelta) {
            System.out.println("Bad number of cuts! trees delta: " + treesDelta + ", cuts delta: " + cutsDelta);
            System.out.println("Bad number of cuts! td: " + td);
            return false;
        }
        if (!TestUtils.checkHeapRule(heap)) {
            System.out.println("Heap rule broken!");
            return false;
        }
        if (!TestUtils.checkSize(heap)) {
            System.out.println("Bad size!");
            return false;
        }
        if (!TestUtils.checkHeapNodeSingleReference(heap)) {
            System.out.println("Bad nodes referencing!");
            return false;
        }
        if (!TestUtils.checkHeapLineage(heap)) {
            System.out.println("Bad heap lineage!");
            return false;
        }
        if (!TestUtils.checkHeapCirculation(heap)) {
            System.out.println("Bad heap circulation!");
            return false;
        }
        if (!TestUtils.checkHeapRank(heap)) {
            System.out.println("Bad rank keeping!");
            return false;
        }

        return true;
    }

    public static FibonacciHeap.HeapNode getRandomNode(FibonacciHeap heap) {
        int maxSteps = 1000;
        FibonacciHeap.HeapNode curr = heap.getFirst();
        if (curr == null) {
            return null;
        }
        boolean stop = ThreadLocalRandom.current().nextInt(0, 10) < 1;
        while(!stop) {
            int numNext = ThreadLocalRandom.current().nextInt(0, maxSteps);

            for (int j = 0; j < numNext; j++) {
                curr = curr.next;
            }

            stop = ThreadLocalRandom.current().nextInt(0, 10) < 1;

            if (!stop) {
                if (curr.child == null) {
                    return curr;
                }
                curr = curr.child;
            }

            stop = ThreadLocalRandom.current().nextInt(0, 10) < 1;
        }

        return curr;
    }

    public static boolean randomHeapsTest() {
        int numIters = 10000;
        int maxDeletes = 1000;
        int maxSize = 1000;
        FibonacciHeap heap = null;
        for (int i =0; i < numIters; i++) {
            int size = ThreadLocalRandom.current().nextInt(0, maxSize);
            int delta = ThreadLocalRandom.current().nextInt(0, size+1) + 1000;
            int numDel = ThreadLocalRandom.current().nextInt(0, Math.min(size,maxDeletes)+1);
            heap = TestUtils.getRandomHeap(size);
            heap.deleteMin();
            for (int j = 0; j < numDel; j++) {
                FibonacciHeap.HeapNode node = getRandomNode(heap);
                if (node == null) {
                    continue;
                }
                if (!decreaseKeyTests(heap, delta, node)) {
                    heap.printHeap();
                    return false;
                }
            }
            System.out.println(i);
        }

        return true;
    }

    public static void main(String[] args) {
        randomHeapsTest();
    }
}
