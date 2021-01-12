package src;


import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class FibTester_01 {

    public static void main(String[] args) throws TestException {
       testEmpty2();
       testMeld();
        testDeleteMin();
        testCut();
        test16();
        test19();
        System.out.println("Great Success !");


    }
    public static void  testDeleteMin() throws TestException {
        for (int j = 0; j <= 10; j++) {
            FibonacciHeap h2 = new FibonacciHeap();
            insertN(h2, 10000);
//        h2.display();
            for (int i = 1; i <= 10000; i++) {
                TestFunctions.MyTester(i, h2.findMin().getKey());
                DeleteMinN(h2, 1);
            }
            FibonacciHeap h3 = new FibonacciHeap();
            insertN(h3, -10, 5010);
            DeleteMinN(h3, 10);
            ;
            insertN(h3, -40, 10);
            for (int i = -40; i < -30; i++) {
                //System.out.println(h3.findMin());
                TestFunctions.MyTester(i, h3.findMin().getKey());
                DeleteMinN(h3, 1);
            }
            for (int i = 0; i < 5000; i++) {
                //System.out.println(h3.findMin());
                TestFunctions.MyTester(i, h3.findMin().getKey());
                DeleteMinN(h3, 1);

            }

        }
        System.out.println(" test Delete Min Pass !");
    }

    public static void testEmpty() throws TestException {
        FibonacciHeap h1 = new FibonacciHeap();
        TestFunctions.MyTester(true, h1.isEmpty());
        TestFunctions.MyTester(0, h1.size());
        h1.insert(5);
        TestFunctions.MyTester(1, h1.size());
        TestFunctions.MyTester(false, h1.isEmpty());
        h1.deleteMin();
        TestFunctions.MyTester(true, h1.isEmpty());
        TestFunctions.MyTester(0, h1.size());
        insertN(h1,100);

        TestFunctions.MyTester(100, h1.size());

        for (int i = 2;i<=51;i++) {
            DeleteMinN(h1,1);
            TestFunctions.MyTester(i, h1.findMin().getKey());
        }
        TestFunctions.MyTester(50, h1.size());
       // h1.display();
        TestFunctions.MyTester(51, h1.findMin().getKey());
        DeleteMinN(h1,49);
//        h1.display();
        TestFunctions.MyTester(100, h1.findMin().getKey());
        TestFunctions.MyTester(false, h1.isEmpty());
        h1.deleteMin();
        TestFunctions.MyTester(true, h1.isEmpty());
        TestFunctions.MyTester(0, h1.size());


    }
    public static void testEmpty2() throws TestException {
        for (int i = 0; i <= 100; i++) {
            testEmpty();
        }
        System.out.println("test Empty Pass !");
    }


    public static void testMeld() throws TestException {
        FibonacciHeap h1 = new FibonacciHeap();
        FibonacciHeap h2 = new FibonacciHeap();
        h1.meld(h2); //merage two empty
        TestFunctions.MyTester(true, h1.isEmpty());
        h1.insert(5); //second is empty
        h1.meld(h2);
        TestFunctions.MyTester(1, h1.size());
        TestFunctions.MyTester(false, h1.isEmpty());
        TestFunctions.MyTester(5, h1.findMin().getKey());

        FibonacciHeap h3 = new FibonacciHeap();

        FibonacciHeap h4 = new FibonacciHeap();

        h4.insert(5);
        h3.meld(h4); //first is empty
        TestFunctions.MyTester(false, h3.isEmpty());
        TestFunctions.MyTester(1, h3.size());
        TestFunctions.MyTester(5, h3.findMin().getKey());

        FibonacciHeap  h9 = new FibonacciHeap();

        FibonacciHeap  h8 = new FibonacciHeap();

        insertN(h9,10,20);

        h9.deleteMin();
//        h9.display(true);
        insertN(h8,-10,17);
        h8.deleteMin();
        h9.meld(h8);
        TestFunctions.MyTester(35, h9.size());
//        h9.display();
        TestFunctions.MyTester(-9, h9.findMin().getKey());
        System.out.println("test meld Done !");







    }

    public static void testCut() throws TestException {
        FibonacciHeap h1 = new FibonacciHeap();

        Hashtable<Integer,FibonacciHeap.HeapNode> map = insertN(h1,10,5,5);
        h1.deleteMin();
//        h1.display();

        h1.delete(map.get(20));
//            h1.display();
        TestFunctions.MyTester(15, h1.findMin().getKey());
//        h1.decreaseKey(map.get(11),9 );
//        h1.display();
//        h1.decreaseKey(map.get(13),12 );
//
//        h1.display();
    }

    static void test16() throws TestException {
        String test = "test16";
        FibonacciHeap h1 = new FibonacciHeap();

        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        h1.insert(1);
        h1.insert(2);
        h1.insert(3);
        TestFunctions.MyTester(3, h1.potential());
        TestFunctions.MyTester(0, FibonacciHeap.totalCuts() - cuts);
        TestFunctions.MyTester(0, FibonacciHeap.totalLinks() - links);
        TestFunctions.MyTester(3, h1.countersRep()[0]);

    }
    static void test19() throws TestException {
        FibonacciHeap fibonacciHeap = new FibonacciHeap();
        fibonacciHeap = new FibonacciHeap();

        int cuts = FibonacciHeap.totalCuts();
        int links = FibonacciHeap.totalLinks();

        fibonacciHeap.insert(4);
        fibonacciHeap.insert(5);
        FibonacciHeap.HeapNode node = fibonacciHeap.insert(6);
        fibonacciHeap.deleteMin();
      //  fibonacciHeap.display();

        fibonacciHeap.insert(1);
        fibonacciHeap.insert(2);
        fibonacciHeap.insert(3);
        fibonacciHeap.deleteMin();
     //   fibonacciHeap.display();

        fibonacciHeap.insert(1);
        fibonacciHeap.deleteMin();
    //    fibonacciHeap.display();
        fibonacciHeap.decreaseKey(node, 2);
   //     fibonacciHeap.display();
        TestFunctions.MyTester(4, fibonacciHeap.potential());
        TestFunctions.MyTester(1, FibonacciHeap.totalCuts() - cuts);
        TestFunctions.MyTester(3, FibonacciHeap.totalLinks() - links);
       }



    public static void insertN(FibonacciHeap h1,int n) {
        insertN( h1,1, n);

        }
    public static Hashtable<Integer,FibonacciHeap.HeapNode> insertN(FibonacciHeap h1,int start, int amount, int jump) {

        Hashtable<Integer,FibonacciHeap.HeapNode> keyMap = new Hashtable();

        Set<Integer> mylist = new HashSet<>();

        for(int i = start; i <  start+amount*jump; i+=jump) {
            mylist.add(i);
        }
//            Collections.shuffle(mylist);
        for (Integer key : mylist) {
            FibonacciHeap.HeapNode node = h1.insert(key);
            keyMap.put(key, node );

        }
        return keyMap;
    }


    public static Hashtable<Integer,FibonacciHeap.HeapNode> insertN(FibonacciHeap h1,int start, int amount) {
       return  insertN( h1,start,amount, 1);

    }

    public static  void DeleteMinN(FibonacciHeap h1,int n) {

        for (int i = 0; i < n; i++) {
                  h1.deleteMin();

        }
    }
}
