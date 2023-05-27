package Java.data_structures;

/**
 * Java implementation for Nodes in a Singly-LinkedList
 * Init as -->  ListNode<T> node1 = new ListNode<>(Object); // initialized with value set to Object
 *              ListNode<T> node2 = new ListNode<>(Object,node1); // initialized with value set to Object and the next ListNode
 *                                                                   set to node1.
 * Methods -->  T val()
 *              void val(T val)
 *              ListNode<T> next()
 *              void next(ListNode<T>)
 * @param <T> Generic Object Limiter
 * @author Nikhil Daehee Agarwal
 */
public class ListNode<T> {

    private T val;
    private ListNode<T> next;

    /**
     * Constructor for ListNode
     * Must be initialized with a given Generic Object
     * @param val Generic Object
     */
    public ListNode(T val){
        this.val = val;
        this.next = null;
    }

    /**
     * OverLoad constructor for ListNode Object
     * Sets the value of our ListNode, and Sets the value of the next Node in our LinkedList
     * @param val Generic Object
     * @param next Pointer to the next ListNode
     */
    public ListNode(T val, ListNode<T> next){
        this.val = val;
        this.next = next;
    }

    /**
     * get the value in the ListNode
     * @return Generic Object
     */
    public T val(){
        return val;
    }

    /**
     * Sets the value of the ListNode to a specified Generic Object
     * @param val value we want to store in ListNode
     */
    public void val(T val){
        this.val = val;
    }

    /**
     * return the memory pointer to the next ListNode<T> Object
     * @return
     */
    public ListNode<T> next(){
        return next;
    }

    /**
     * sets the pointer to the next ListNode to a specified ListNode Object
     * @param ln next ListNode Object
     */
    public void next(ListNode<T> ln){
        this.next = ln;
    }

}
