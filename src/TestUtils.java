import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
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

    public static boolean checkHeapNodeSingleReference(FibonacciHeap heap) {
        FibonacciHeap.HeapNode node = heap.getFirst();
        boolean heapOkay = true;
        Set<FibonacciHeap.HeapNode> nodeSet = new HashSet<>();
        if (node != null) {
            do {
                heapOkay = heapOkay && !nodeSet.contains(node);
                nodeSet.add(node);
                heapOkay = heapOkay && checkTreeNodeSingleReference(node.child, nodeSet);
                node = node.next;
            } while (node != heap.getFirst());
        }
        return heapOkay;
    }

    public static boolean checkTreeNodeSingleReference(FibonacciHeap.HeapNode first, Set<FibonacciHeap.HeapNode> nodeSet) {
        FibonacciHeap.HeapNode node = first;
        boolean treeOkay = true;
        if (first != null) {
            do {
                treeOkay = treeOkay && !nodeSet.contains(node);
                nodeSet.add(node);
                treeOkay = treeOkay && checkTreeNodeSingleReference(node.child, nodeSet);
                node = node.next;
            } while (node != first);
        }
        return treeOkay;
    }

    public static boolean checkHeapLineage(FibonacciHeap heap) {
        FibonacciHeap.HeapNode node = heap.getFirst();
        boolean heapOkay = true;
        if (node != null) {
            do {
                heapOkay = heapOkay && (node.parent == null);
                heapOkay = heapOkay && checkNodeLineage(node);
                node = node.next;
            } while (node != heap.getFirst());
        }
        return heapOkay;
    }

    public static boolean checkNodeLineage(FibonacciHeap.HeapNode first) {
        FibonacciHeap.HeapNode curr = first;
        boolean treeOkay = true;
        if (curr != null) {
            do {
                if (curr.child != null) {
                    treeOkay = treeOkay && (curr == curr.child.parent);
                    treeOkay = treeOkay && checkNodeLineage(curr.child);
                }
                curr = curr.next;
            } while (curr != first);
        }

        return treeOkay;
    }

    public static boolean checkHeapCirculation(FibonacciHeap heap) {
        return checkTreeCirculation(heap.getFirst());
    }

    public static boolean checkTreeCirculation(FibonacciHeap.HeapNode node) {
        FibonacciHeap.HeapNode first = node;
        Stack<FibonacciHeap.HeapNode> nodeStack = new Stack<>();
        boolean circulationOkay = true;
        if (node != null) {
            do {
                circulationOkay = circulationOkay && checkTreeCirculation(node.child);
                node = node.next;
                nodeStack.push(node);
            } while (node != first);
            do {
                circulationOkay = circulationOkay && (node == nodeStack.pop());
                node = node.prev;
            } while (node != first);
        }
        return circulationOkay;
    }

    public static boolean checkHeapRank(FibonacciHeap heap) {
        FibonacciHeap.HeapNode node = heap.getFirst();
        boolean ranksOkay = true;
        if (node != null) {
            do {
                ranksOkay = ranksOkay && checkTreeRank(node);
                node = node.next;
            } while (node != heap.getFirst());
        }
        return ranksOkay;
    }

    public static boolean checkTreeRank(FibonacciHeap.HeapNode node) {
        FibonacciHeap.HeapNode child = node.child;
        int numChildren = 0;
        if (child != null) {
            boolean ranksOkay = true;
            do {
                ranksOkay = ranksOkay && checkTreeRank(child);
                numChildren++;
                child = child.next;
            } while (child != node.child);

            return ranksOkay && (numChildren == node.rank);
        }
        return ((node.rank == numChildren) && (child == null));
    }

    public static boolean checkNumTrees(FibonacciHeap heap) {
        FibonacciHeap.HeapNode curr = heap.getFirst();
        int numTrees = 0;
        if (curr != null) {
            do {
                numTrees++;
                curr = curr.next;
            } while (curr != heap.getFirst());
        }
        return heap.getNumTrees() == numTrees;
    }

    public static boolean checkRootsNotMarked(FibonacciHeap heap) {
        FibonacciHeap.HeapNode curr = heap.getFirst();
        if (curr != null) {
            do {
                if (curr.marked) {
                    return false;
                }
                curr = curr.next;
            } while (curr != heap.getFirst());
        }
        return true;
    }

    public static boolean checkNumMarkedHeap(FibonacciHeap heap) {
        FibonacciHeap.HeapNode node = heap.getFirst();
        int numMarked = 0;
        if (node != null) {
            do {
                numMarked += checkNumMarkedTree(node);
                node = node.next;
            } while(node != heap.getFirst());
        }
        return numMarked == heap.getNumMarked();
    }

    public static int checkNumMarkedTree(FibonacciHeap.HeapNode node) {
        FibonacciHeap.HeapNode curr = node.child;
        int numMarked = 0;
        if (curr != null) {
            do {
                if (curr.marked) {
                    numMarked++;
                }
                numMarked += checkNumMarkedTree(curr);
                curr = curr.next;
            } while (curr != node.child);
        }

        return numMarked;
    }
}
