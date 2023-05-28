package Java;

import Java.data_structures.node_implementation.Queue;

public class Test {

    public static void main(String[] args){
        Queue<Integer> q = new Queue<>();
        q.push(349);
        q.push(5);
        q.push(6);
        System.out.println(q.contains(4));
        System.out.println(q.contains(6));
    }

}
