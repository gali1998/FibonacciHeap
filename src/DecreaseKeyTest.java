public class DecreaseKeyTest {

    public static boolean decreaseKeyTests(FibonacciHeap heap, int delta, FibonacciHeap.HeapNode x) {
        int numTrees = TestUtils.countTrees(heap);
        int priorCuts = FibonacciHeap.totalCuts();
        heap.decreaseKey(x, delta);
        int treesDelta = TestUtils.countTrees(heap) - numTrees;
        int cutsDelta = FibonacciHeap.totalCuts() - priorCuts;

        if (treesDelta != cutsDelta) {
            System.out.println("Bad number of cuts! trees delta: " + treesDelta + ", cuts delta: " + cutsDelta);
            return false;
        }
        if (!TestUtils.checkHeapRule(heap)) {
            System.out.println("Heap rule broken!");
            return false;
        }

        return true;
    }
}
