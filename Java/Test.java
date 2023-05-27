package Java;

import Java.data.*;
import Java.structures.*;

public class Test {

    public static void main(String[] args){
        ListNode<Integer> l1 = new ListNode<>(7);
        ListNode<Integer> start = l1;
        ListNode<Integer> l2 = new ListNode<>(9);
        ListNode<Integer> l3 = new ListNode<>(3);
        l1.next(l2);
        l2.next(l3);
        l3.next(l1);
        int signal = 1;
        int count =0;
        while(signal > 0){
            System.out.println(start.val());
            start = start.next();
            if(count > 14){
                signal = -1;
            }
            count++;
        }
    }

}
