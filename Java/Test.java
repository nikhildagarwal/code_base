package Java;

import Java.data_structures.Pair;
import Java.data_structures.node_implementation.PriorityQueue;
import Java.data_structures.node_implementation.Stack;

public class Test {

    public static void main(String[] args){
        PriorityQueue<Integer> p = new PriorityQueue<>();
        int[] n = {2,6,3,7,2,3,1,5,7,-2,4,-2,2,-7,43,20,-56,545};
        for(int no:n){
            p.push(no);
        }
        System.out.println(p);
        System.out.println(p.size());
        System.out.println(p.poll());
        System.out.println(p);
        System.out.println(p.size());
    }

}
