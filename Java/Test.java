package Java;

import Java.data_structures.*;
import Java.data_structures.array_implementation.*;
import Java.data_structures.node_implementation.*;

public class Test {

    public static void main(String[] args){
        Queue<Integer> q = new Queue<>(4);
        System.out.println(q);
        for(int i = 0;i<19;i++){
            q.push(i);
            System.out.println(q);
        }
        for(int i = 0;i<9;i++){
            q.poll();
            System.out.println(q);
        }

    }

}
