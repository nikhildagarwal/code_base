package Java.data_structures.node_implementation;

/**
 * Java implementation for Nodes in a Singly-LinkedList
 * Init as -->  Node<T> node1 = new Node<>(Object); // initialized with value set to Object
 *              Node<T> node2 = new Node<>(Object,node1); // initialized with value set to Object and the next Node
 *                                                                   set to node1.
 * Methods -->  T val()
 *              void val(T val)
 *              Node<T> next()
 *              void next(Node<T>)
 *              @Override String toString()
 *              @Override boolean equals(Object obj)
 * @param <T> Generic Object Limiter
 * @author Nikhil Daehee Agarwal
 */
public class Node<T> {

    private T val;
    private Node<T> next;

    /**
     * Constructor for Node
     * Must be initialized with a given Generic Object
     * @param val Generic Object
     */
    public Node(T val){
        this.val = val;
        this.next = null;
    }

    /**
     * OverLoad constructor for Node Object
     * Sets the value of our Node, and Sets the value of the next Node in our LinkedList
     * @param val Generic Object value
     * @param next Pointer to the next Node
     */
    public Node(T val, Node<T> next){
        this.val = val;
        this.next = next;
    }

    /**
     * get the value in the Node
     * @return Generic Object
     */
    public T val(){
        return val;
    }

    /**
     * Sets the value of the Node to a specified Generic Object
     * @param val value we want to store in Node
     */
    public void val(T val){
        this.val = val;
    }

    /**
     * return the memory pointer to the next Node<T> Object
     * @return
     */
    public Node<T> next(){
        return next;
    }

    /**
     * sets the pointer to the next Node to a specified Node Object
     * @param ln next Node Object
     */
    public void next(Node<T> ln){
        this.next = ln;
    }

    /**
     * String visualization of Node and pointer to the next node
     * @return String of Node
     */
    @Override
    public String toString(){
        String thisOne = val==null? "null": "("+val+")";
        String nextOne = next == null ? "null" : "("+next.val()+")";
        return thisOne+"-->"+nextOne;
    }

    /**
     * Checks to see if two Node Objects are the same.
     * Nodes are considered "equal" if they point to the same address in memory
     * @param obj Node object we want to compare
     * @return true if nodes are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        Node<T> node = (Node<T>) obj;
        return node == this;
    }
}
