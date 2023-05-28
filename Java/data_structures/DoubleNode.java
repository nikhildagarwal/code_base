package Java.data_structures;

/**
 * Java implementation for DoubleNodes in a Doubly-LinkedList
 * Init as -->  DoubleNode<T> d1 = new DoubleNode<>(Object); // initialized with value set to Object
 *              DoubleNode<T> d2 = new DoubleNode<>(Object,d1,null); // initialized with value set to Object and the next Node
 *                                                                   set to node1, and the previous node set to null.
 * Methods -->  T val()
 *              void val(T val)
 *              DoubleNode<T> next()
 *              void next(DoubleNode<T>)
 *              DoubleNode<T> prev()
 *              void prev(DoubleNode<T>)
 *              @Override String toString()
 *              @Override boolean equals(Object obj)
 * @param <T> Generic Object Limiter
 * @author Nikhil Daehee Agarwal
 */
public class DoubleNode<T> {

    private T val;
    private DoubleNode<T> next;
    private DoubleNode<T> prev;

    /**
     * Constructor for DoubleNode Object
     * Must be init with a given Generic Object
     * @param val Generic Object
     */
    public DoubleNode(T val){
        this.next = null;
        this.val = val;
        this.prev = null;
    }

    /**
     * Overload constructor for DoubleNode Object
     * Sets the value of our DoubleNode, and the prev and next DoubleNode
     * @param val Generic Object value
     * @param next Pointer to next DoubleNode
     * @param prev Pointer to previous DoubleNode
     */
    public DoubleNode(T val, DoubleNode<T> next, DoubleNode<T> prev){
        this.val = val;
        this.next = next;
        this.prev = prev;
    }

    /**
     * returns the previous DoubleNode that this DoubleNode Object points to.
     * @return pointer to previous DoubleNode
     */
    public DoubleNode<T> prev(){
        return prev;
    }

    /**
     * Sets the previous Node pointer to a specified DoubleNode Object.
     * @param dln DoubleNode Object we want to set as previous node.
     */
    public void prev(DoubleNode<T> dln){
        this.prev = dln;
    }

    /**
     * gets the value of the current DoubleNode object
     * @return Generic Object value
     */
    public T val(){
        return val;
    }

    /**
     * Sets the value of the current node to a specified value
     * @param v specified value
     */
    public void val(T v){
        this.val = v;
    }

    /**
     * get the DoubleNode Object that this current Node points to next.
     * @return DoubleNode Object that this object points to next.
     */
    public DoubleNode<T> next(){
        return next;
    }

    /**
     * Points the next state of this object to a specified DoubleNode Object.
     * @param d specified DoubleNode Object we want to point to.
     */
    public void next(DoubleNode<T> d){
        this.next = d;
    }

    /**
     * String visualization of DoubleNode and pointer to the next node and previous node
     * @return String of DoubleNode
     */
    @Override
    public String toString(){
        String thisOne = val == null ? "null" : "("+val+")";
        String nextOne = next == null ? "null" : "("+next.val().toString()+")";
        String prevOne = prev == null ? "null" : "("+prev.val().toString()+")";
        return prevOne+"<--"+thisOne+"-->"+nextOne;
    }

    /**
     * Checks to see if two doubleNode objects are equal
     * they are considered equal if both nodes point to the same address in memory
     * @param obj doubleNode object we are comparing too.
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        DoubleNode<T> dn = (DoubleNode<T>) obj;
        return dn == this;
    }

}
