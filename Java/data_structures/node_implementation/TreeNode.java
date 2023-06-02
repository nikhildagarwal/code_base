package Java.data_structures.node_implementation;

/**
 * Java Implementation for TreeNode (Nodes used Binary Search Trees)
 * Build BST with this Class.
 * Each Node contains a value and points to the right child and the left child.
 * Init as -->  TreeNode<T> t = new TreeNode<>(); // null Node
 *              TreeNode<T> t1 = new TreeNode<>(val); //Node with value "val", null pointers to right and left Nodes
 *              TreeNode<T> t2 = new TreeNode<>(val,t,t1); //Node with value "val", right pointer to t, left pointer to t1
 * Methods -->  T val();
 *              void val(T value);
 *              TreeNode<T> right();
 *              void right(TreeNode<T> r);
 *              TreeNode<T> left();
 *              void left(TreeNode<T> l);
 *              @Override int hashCode();
 *              @Override boolean equals(Object obj);
 *              @Override String toString();
 * @param <T>   Class Limiter
 * @author Nikhil Daehee Agarwal
 */
public class TreeNode<T> {

    private T val;
    private TreeNode<T> right;
    private TreeNode<T> left;

    /**
     * Null Node Object, All inner variables are set to null
     */
    public TreeNode(){
        this.val = null;
        this.right = null;
        this.left = null;
    }

    /**
     * Initialize TreeNode Object with a certain Generic Object value
     * @param val Object to put in Node
     */
    public TreeNode(T val){
        this.val = val;
        this.right = null;
        this.left = null;
    }

    /**
     * Initialize TreeNode Object with a generic object value,
     * Also set the right and left TreeNodes to given TreeNode Objects
     * @param val Value of Node
     * @param right TreeNode right
     * @param left TreeNode left
     */
    public TreeNode(T val, TreeNode<T> right, TreeNode<T> left){
        this.val = val;
        this.right = right;
        this.left = left;
    }

    /**
     * getter method for value of node
     * @return val of node
     */
    public T val(){
        return val;
    }

    /**
     * Set the value of this Node to a given value
     * @param val Value to set
     */
    public void val(T val){
        this.val = val;
    }

    /**
     * getter method for the Node on the right of this Node
     * @return TreeNode Object
     */
    public TreeNode<T> right(){
        return right;
    }

    /**
     * Sets the right Node of the current Node to a given Node
     * @param r TreeNode right
     */
    public void right(TreeNode<T> r){
        this.right = r;
    }

    /**
     * getter method for the Node on the left of this Node
     * @return TreeNode Object
     */
    public TreeNode<T> left(){
        return left;
    }

    /**
     * Sets the left Node of the current Node to a given Node
     * @param l TreeNode left
     */
    public void left(TreeNode<T> l){
        this.left = l;
    }

    /**
     * Override method for HashCode of TreeNode Object
     * gets the hashCode of the VALUE of the node
     * @return hashCode of this.val
     */
    @Override
    public int hashCode(){
        return this.val.hashCode();
    }

    /**
     * Override method for equals method in Object class
     * Checks to see if two TreeNodes are equal SOLELY BY COMPARING VALUES
     * @param obj TreeNode to compare
     * @return true if values of object are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        TreeNode<T> t = (TreeNode<T>) obj;
        return t.val.equals(this.val);
    }

    /**
     * Method to convert this TreeNode object to a String.
     * Makes it easier to visualize the data structure.
     * @return This TreeNode as a String  Ex: "() <--left-- () --right--> ()";
     */
    @Override
    public String toString(){
        String rootVal = this.val == null ? "(null)" : "("+val+")";
        String rightVal = right == null ? "null" : "("+right.val+")";
        String leftVal = left == null ? "null" : "("+left.val+")";
        return leftVal + " <--left-- " + rootVal + " --right--> " + rightVal;
    }

}
