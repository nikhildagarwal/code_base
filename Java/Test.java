package Java;

import Java.data_structures.node_implementation.Stack;

public class Test {

    public static void main(String[] args){
        Stack<Integer> q = new Stack<>();
        q.push(349);
        q.push(5);
        q.push(6);
        System.out.println(q);
        System.out.println(q.pop());
    }

}
