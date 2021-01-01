import java.util.concurrent.ThreadLocalRandom;

public class DeleteMinTests {

    public static boolean checkOrder(FibonacciHeap heap) {
        FibonacciHeap.HeapNode first = heap.getFirst();
        FibonacciHeap.HeapNode curr = first;
        int largestRank = -1;
        if (curr != null) {
            do {
                if (curr.rank <= largestRank) {
                    return false;
                }
                largestRank = curr.rank;
                curr = curr.next;
            } while (curr != first);
        }
        return true;
    }

    public static boolean checkConsolidationIntegrity(FibonacciHeap heap) {
        if (heap.size() == 0) {
            if (heap.getFirst() != null) {
                return false;
            }
            return true;
        }
        FibonacciHeap.HeapNode[] trees = new FibonacciHeap.HeapNode[(int)Math.ceil(Math.log(heap.size())/Math.log(1.618)) + 1];
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
        FibonacciHeap.HeapNode first = heap.getFirst();
        nextMin = findNextMinInList(first, min);
        FibonacciHeap.HeapNode minChild = null;
        if (heap.size() == 0) {
            return null;
        }
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
        if (first == null) {
            return null;
        }
        do {
            if (curr.key > min.key) {
                if (nextMin == null) {
                    nextMin = curr;
                } else if (nextMin.key > curr.key) {
                    nextMin = curr;
                }
            }
            curr = curr.next;
        } while(curr != first);

        return nextMin;
    }

    public static boolean deleteMinTest(FibonacciHeap heap) {
        int expectedSize = heap.size() - 1;
        if (heap.size() == 0) {
            expectedSize = 0;
        }
        FibonacciHeap.HeapNode expectedMin = findNextMin(heap);
        heap.deleteMin();
        if (!checkConsolidationIntegrity(heap)) {
            System.out.println("Bad consolidation integrity!");
            return false;
        }
        if (!TestUtils.checkSize(heap)) {
            System.out.println("Bad size!");
            return false;
        }
        if (!checkOrder(heap)) {
            System.out.println("Bad order!");
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
        if (!TestUtils.checkRankRule(heap)) {
            System.out.println("Rank rule broken!");
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

    public static void main(String[] args) {
        System.out.println(Math.log(0));
        simpleCase();
//        randomHeapsTest();
    }

    public static boolean simpleCase() {
        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(1);

        for (int i =0; i< 10; i++) {
            if (!deleteMinTest(heap)) {
                heap.printHeap();
                return false;
            }

            if (i%2 == 0) {
                heap.insert(i);
            }
        }
        return true;
    }

    public static boolean randomHeapsTest() {
        int numIters = 10000;
        int maxDeletes = 1000;
        int maxSize = 1000;
        FibonacciHeap heap = null;
        for (int i =0; i < numIters; i++) {
            int size = ThreadLocalRandom.current().nextInt(0, maxSize);
            int numDel = ThreadLocalRandom.current().nextInt(0, Math.min(size,maxDeletes)+1);
            heap = TestUtils.getRandomHeap(size);
            for (int j = 0; j < numDel; j++) {
                if (!deleteMinTest(heap)) {
                    heap.printHeap();
                    return false;
                }
            }
            System.out.println(i);
        }

        return true;
    }
}
