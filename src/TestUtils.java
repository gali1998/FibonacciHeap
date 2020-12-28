
public class TestUtils {

    public static int countTrees(FibonacciHeap heap) {
        FibonacciHeap.HeapNode first = heap.getFirst();
        FibonacciHeap.HeapNode curr = first;
        int numTrees = 0;
        if (first != null) {
            numTrees++;
        }
        do {
            numTrees++;
            curr = curr.next;
        } while (curr != first);
        return numTrees;
    }

    public static boolean checkHeapRule(FibonacciHeap heap) {
        FibonacciHeap.HeapNode first = heap.getFirst();
        FibonacciHeap.HeapNode curr = first;
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
                if (!checkTreeRule(curr)) {
                    return false;
                }
            }
            curr = curr.next;
        } while (curr != root);

        return true;
    }
}
