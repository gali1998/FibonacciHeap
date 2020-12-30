import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TestUtils {

    public static int countTrees(FibonacciHeap heap) {
        if (heap.isEmpty()) {
            return 0;
        }
        FibonacciHeap.HeapNode first = heap.getFirst();
        FibonacciHeap.HeapNode last = heap.getFirst().prev;
        last.next = null;
        FibonacciHeap.HeapNode curr = first;
        int numTrees = 0;

        while (curr != null) {
            numTrees++;
            curr = curr.next;
        }
        last.next = first;
        return numTrees;
    }

    public static boolean checkRankRule(FibonacciHeap heap) {
        FibonacciHeap.HeapNode first = heap.getFirst();
        FibonacciHeap.HeapNode curr = first;
        if (heap.size() == 0) {
            return first == null;
        }
        do {
            if (curr.child != null) {
                if (!checkTreeRankRule(curr)) {
                    return false;
                }
            }
            curr = curr.next;
        } while (curr != first);
        return true;
    }

    public static boolean checkTreeRankRule(FibonacciHeap.HeapNode root) {
        FibonacciHeap.HeapNode curr = root.child;

        do {

            if (curr.rank > root.rank) {
                return false;
            }
            if (curr.child != null) {
                if (curr.rank == 0) {
                    System.out.println("Terminal rank node has child");
                }
                if (!checkTreeRule(curr)) {
                    return false;
                }
            }
            curr = curr.next;
        } while (curr != root.child);

        return true;
    }

    public static boolean checkHeapRule(FibonacciHeap heap) {
        FibonacciHeap.HeapNode first = heap.getFirst();
        FibonacciHeap.HeapNode curr = first;
        if (heap.size() == 0) {
            return first == null;
        }
        do {
            if (curr.child != null) {
                if (!checkTreeRule(curr)) {
                    return false;
                }
            }
            curr = curr.next;
        } while (curr != first);
        return true;
    }

    public static boolean checkTreeRule(FibonacciHeap.HeapNode root) {
        FibonacciHeap.HeapNode curr = root.child;

        do {

            if (curr.getKey() < root.getKey()) {
                return false;
            }
            if (curr.child != null) {
                if (curr.rank == 0) {
                    System.out.println("Terminal rank node has child");
                }
                if (!checkTreeRule(curr)) {
                    return false;
                }
            }
            curr = curr.next;
        } while (curr != root.child);

        return true;
    }

    public static int randomKey(int sizeFactor) {
        return ThreadLocalRandom.current().nextInt(0, sizeFactor*10000);
    }

    public static int[] getShuffledArray(int size) {
        ArrayList<Integer> lst = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            lst.add(i);
        }

        java.util.Collections.shuffle(lst);

        int[] array = new int[size];
        int i = 0;

        for (int item : lst) {
            array[i] = item;
            i++;
        }

        return array;
    }

    public static FibonacciHeap getRandomHeap(int sizeFactor) {
        FibonacciHeap heap = new FibonacciHeap();
        int[] shuffledArray = getShuffledArray(sizeFactor);

        for (int item: shuffledArray) {
            heap.insert(item);
        }

        return heap;
    }

    public static boolean checkSize(FibonacciHeap heap) {
        if (heap.size() < 0) {
            return false;
        }
        if ((heap.size() == 0) && (heap.getFirst() != null)) {
            return false;
        }
        return true;
    }
}
