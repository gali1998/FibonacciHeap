public class Measurements2 {
    public static void main(String[] args) {
//        measure(10000);
        System.out.println("M, Run-Time, totalLinks, totalCuts, Potential");
        for (int m: new int[] {1, 1, 2 ,3}) {
            measure((int) (m*Math.pow(10,3)));
        }
    }
    public static void measure(int m) {
        FibonacciHeap heap = new FibonacciHeap();
        int initLinks = FibonacciHeap.totalLinks();
        int initCuts = FibonacciHeap.totalCuts();

        long start = System.nanoTime();
        for (int i = m; i > 0; i--) {
            heap.insert(i);
        }

        for (int i = 0; i < m/2; i++) {
            heap.deleteMin();
        }
        long e = System.nanoTime() - start;

        double elapsed = (double) ((e) * Math.pow(10,-6));
        System.out.println(m +","+
                elapsed + "," +
                (FibonacciHeap.totalLinks() - initLinks) + "," +
                (FibonacciHeap.totalCuts() - initCuts) + "," +
                heap.potential());
    }
}
