
public class DeleteTests {

    public static boolean checkConsolidationIntegrity(FibonacciHeap heap) {
        FibonacciHeap.HeapNode[] trees = new FibonacciHeap.HeapNode[(int) Math.log(heap.size())];
        FibonacciHeap.HeapNode first = heap.getFirst();
        FibonacciHeap.HeapNode curr = first;

        do {
            if (trees[curr.rank] != null) {
                return false;
            }
            trees[curr.rank] = curr;
            curr = curr.next;
        } while (curr != first);

        return true;
    }

    public static FibonacciHeap.HeapNode findNextMin(FibonacciHeap heap) {
        FibonacciHeap.HeapNode nextMin = null;
        FibonacciHeap.HeapNode min = heap.findMin();
        FibonacciHeap.HeapNode first = heap.findMin();
        nextMin = findNextMinInList(first, min);
        FibonacciHeap.HeapNode minChild = null;
        if (min.child != null) {
            minChild = findNextMinInList(min.child, min);
        }

        if ((nextMin != null) && (minChild != null)) {
            if (nextMin.key < minChild.key) {
                return nextMin;
            } else {
                return minChild;
            }
        }
        if (nextMin != null) {
            return nextMin;
        }
        return minChild;
    }

    public static FibonacciHeap.HeapNode findNextMinInList(FibonacciHeap.HeapNode first, FibonacciHeap.HeapNode min) {
        FibonacciHeap.HeapNode nextMin = null;
        FibonacciHeap.HeapNode curr = first;
        do {
            if (nextMin == null) {
                if (curr.key > min.key) {
                    nextMin = curr;
                }
            }
            if ((curr.key > min.key) && (nextMin.key > curr.key)) {
                nextMin = curr;
            }
            curr = curr.next;
        } while(curr != first);

        return nextMin;
    }

    public static boolean deleteMinTest(FibonacciHeap heap) {
        int expectedSize = heap.size() - 1;
        FibonacciHeap.HeapNode expectedMin = findNextMin(heap);
        heap.deleteMin();
        if (!checkConsolidationIntegrity(heap)) {
            System.out.println("Bad consolidation integrity!");
            return false;
        }
        if (expectedMin != heap.findMin()) {
            System.out.println("Bad min! expected " + expectedMin.key + ", received " + heap.findMin().key);
            return false;
        }
        if (expectedSize != heap.size()) {
            System.out.println("Bad size! expected " + expectedSize + ", received " + heap.size());
            return false;
        }
        if (!TestUtils.checkHeapRule(heap)) {
            System.out.println("Heap rule broken!");
            return false;
        }

        return true;
    }


}
