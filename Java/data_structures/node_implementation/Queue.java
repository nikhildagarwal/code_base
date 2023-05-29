package Java.data_structures.node_implementation;

/**
 * Java Implementation of Queue using Nodes (LinkedList Queue)
 * Dynamic sizing, no capacity specification needed, simply a chain of pointers pointing to the next element
 * Init as -->  Queue<T> q = new Queue<>();
 * Methods -->  void push(T value);
 *              T peek();
 *              T poll();
 *              int size();
 *              boolean isEmpty();
 *              boolean contains();
 *              void clear();
 *              @Override boolean equals(Object obj);
 *              @Override int hashCode();
 *              @Override String toString();
 * @param <T> Generic Object Limiter
 * @author Nikhil Daehee Agarwal
 */
public class Queue<T> {

    private Node<T> front;
    private Node<T> end;
    private int size;

    /**
     * Default Constructor for Queue Object
     * Sets front and end pointers to null and size to 0
     */
    public Queue(){
        this.front = null;
        this.end = null;
        this.size = 0;
    }

    /**
     * push a T value to the back of the Queue
     * @param value Generic Object T
     */
    public void push(T value){
        if(front==null){
            end = new Node<>(value);
            front = end;
        }else{
            Node<T> adder = new Node<>(value);
            end.next(adder);
            end = end.next();
        }
        size++;
    }

    /**
     * Grabs the value at the front of the Queue
     * @return error if no such node found, T value if there exists a node at the front of the queue
     */
    public T peek(){
        if(front==null){
            throw new NullPointerException("Queue is empty!");
        }
        return front.val();
    }

    /**
     * Grabs the value and the front of the Queue and removes it from the list.
     * @return error if no such node found, T value if there exists a node at the front of the Queue.
     */
    public T poll(){
        if(front==null){
            throw new NullPointerException("Queue is empty!");
        }
        T valToReturn = front.val();
        front = front.next();
        if(front == null){
            end = null;
        }
        size--;
        return valToReturn;
    }

    /**
     * gets the size of our Queue Object (number of elements in the Queue)
     * @return int size
     */
    public int size(){
        return size;
    }

    public void clear(){
        this.size = 0;
        this.front = null;
        this.end = null;
    }

    /**
     * Checks to see if there are no elements in our Queue.
     * @return true if size is 0, else otherwise.
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Checks our Queue to see the values exists in the Queue.
     * @param value Generic Object we are searching for
     * @return ture if the value was found, false otherwise.
     */
    public boolean contains(T value){
        Node<T> start = front;
        while(start!=null){
            if(start.val().equals(value)){
                return true;
            }
            start = start.next();
        }
        return false;
    }

    /**
     * HashCode of our Queue based on the sum of all the unique hashCodes of the individual elements.
     * Curtails the hashcode to take care of overflow
     * @return int hashCOde
     */
    @Override
    public int hashCode(){
        long sum = 0;
        Node<T> start = front;
        while(start!=null){
            sum += start.val().hashCode();
            sum %= Integer.MAX_VALUE;
            start = start.next();
        }
        sum = sum < 0 ? sum * (-1) : sum;
        return (int) sum;
    }

    /**
     * Checks to see if two queues are equal
     * @param obj The Second queue that we want to compare
     * @return true if the two queues are the same size and contain all the same elements in order, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        Queue<T> q = (Queue<T>) obj;
        if(q.size() != this.size()){
            return false;
        }
        Node<T> s1 = q.front;
        Node<T> s2 = this.front;
        while(s1!=null){
            if(!s1.val().equals(s2.val())){
                return false;
            }
            s1 = s1.next();
            s2 = s2.next();
        }
        return true;
    }

    /**
     * Returns the Queue as visualized string
     * @return String of the Queue
     */
    @Override
    public String toString(){
        String ans = "[";
        Node<T> start = front;
        while(start != null){
            ans += start.val();
            if (start.next()!=null) {
                ans += ", ";
            }
            start = start.next();
        }
        return "<-out- "+ans + "] <-in-";
    }

}
