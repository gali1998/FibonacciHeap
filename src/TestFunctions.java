package src;

import java.util.ArrayList;
import java.util.List;

public class TestFunctions {

    public static  void MyTester(int expected, int actual) throws TestException {
        if (expected != actual){
            String str = String.format(" TEST FAIL:  expected %d, actual %d", expected, actual);
            throw new TestException(str);
        }
    }
    public static  void MyTester(String expected, String actual) throws TestException {
        if (!expected.equals(actual)) {
            String str = String.format(" TEST FAIL:  expected %s, actual %s", expected, actual);
            throw new TestException(str);
        }
    }
    public static  void MyTester (boolean  expected, boolean  actual) throws TestException {
        if (expected != actual) {
            String str = String.format(" TEST FAIL:  expected %s, actual %s", expected, actual);
            throw new TestException(str);
        }
    }
    public static  void MyTester (int[]  expected, int[]  actual) throws TestException {
        int n = expected.length;
        int m = actual.length;
        if (n != m) {
            String str = String.format(" TEST FAIL: DIFF LENGTH  expected len: %s, actual len: %s", n, m);
            throw new TestException(str);
        }
        List<Integer> diff_ind = new ArrayList<>();
        List<Integer> diff_n = new ArrayList<>();
        List<Integer> diff_m = new ArrayList<>();
        for (int i=0;i<n;i++){
            if (expected[i]!=actual[i]){
                diff_ind.add(i);
                diff_n.add(expected[i]);
                diff_m.add(actual[i]);
            }

            }

            if (diff_ind.size() != 0) {
                String str = String.format(" TEST FAIL: Arrays differ in %d places ", diff_ind.size());
                str+="\n";
                str+=" i   exp   act";
                for (int i=0;i<diff_ind.size();i++){
                    str+="\n";
                str+=String.format("%2d    %2d   %2d ", diff_ind.get(i),diff_n.get(i),diff_m.get(i));


            }
                throw new TestException(str);


        }
    }






}
