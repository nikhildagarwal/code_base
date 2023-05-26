package Java;

import Java.data.*;
import Java.structures.*;

public class Test {

    public static void main(String[] args){
        Stack<Integer> stack = new Stack<>();
        for(int i = 0;i<1000000000;i++){
            stack.push(i);
        }
        System.out.println(stack);
    }

}
