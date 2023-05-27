package Java;

import Java.data.*;
import Java.structures.*;

public class Test {

    public static void main(String[] args){
        Stack<Integer> stack = new Stack<>(10000000);
        for(int i = 0;i<100000;i++){
                stack.push(3);

        }
        System.out.println(stack);
    }

}
