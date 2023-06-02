package Java;

import Java.data_structures.node_implementation.TreeNode;

public class Test {

    public static void main(String[] args){
        TreeNode<String> t = new TreeNode<>();
        t.val("Nikhil");
        TreeNode<String> tr = new TreeNode();
        TreeNode<String> tl = new TreeNode<>();
        t.right(tr);
        t.left(tl);
        System.out.println(t);
    }

}
