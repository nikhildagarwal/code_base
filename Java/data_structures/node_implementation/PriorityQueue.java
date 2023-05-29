package Java.data_structures.node_implementation;

/**
 * Java Implementation of PriorityQueue using Nodes (LinkedList Queue)
 * Dynamic sizing, no capacity specification needed, simply a chain of pointers pointing to the next element.
 * Automatically sorts all the elements in order from lowest to highest.
 * Init as -->  PriorityQueue<T> q = new PriorityQueue<>();
 * Methods -->  void push(T value);
 *              T peek();
 *              T poll();
 *              int size();
 *              boolean isEmpty();
 *              boolean contains(T value);
 *              void clear();
 *              int count(T value);
 *              @Override boolean equals(Object obj);
 *              @Override int hashCode();
 *              @Override String toString();
 * @param <T> Generic Object Limiter
 * @author Nikhil Daehee Agarwal
 */
public class PriorityQueue<T>{

    private Node<T> front;
    private Node<T> end;
    private int size;

    /**
     * Default constructor for Priority Queue
     * Sets front and end points to null and size to 0;
     */
    public PriorityQueue(){
        this.front = null;
        this.end = null;
        this.size = 0;
    }

    /**
     * Adds a T value to our priorityQueue.
     * Inserts the new node into our LinkedList IN ORDER.
     * <Integer> First out: lowest number, Last out: highest number;
     * Same is as follows for all other comparable classes. Low object out first, high object out last.
     * @param value value to be added
     */
    public void push(T value){
        if(this.front == null){
            this.end = new Node<>(value);
            this.front = this.end;
            size++;
        }else{
            Node<T> n = new Node<>(value);
            if(comp(value,front.val())<=0){
                n.next(front);
                front = n;
                size++;
            }else if(comp(value,end.val())>=0){
                end.next(n);
                end = n;
                size++;
            }else{
                Node<T> start = front;
                while(start.next()!=null){
                    if(comp(start.val(), value) <= 0 && comp(value,start.next().val())<=0){
                        n.next(start.next());
                        start.next(n);
                        size++;
                        return;
                    }
                    start = start.next();
                }
            }
        }
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

    /**
     * Clear the Queue
     */
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
     * Check PriorityQueue object for value and return the number of occurrences of that value.
     * @param value T value that we want to find.
     * @return int number of occurrences of specified value
     */
    public int count(T value){
        int c = 0;
        Node<T> start = front;
        while(start!=null){
            if(start.val().equals(value)){
                c++;
            }
            start = start.next();
        }
        return c;
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
        PriorityQueue<T> q = (PriorityQueue<T>) obj;
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

    /**
     * Helper method to compare two generic objects
     * Throws error if values are not of class Integer, String, Double or Char.
     * Throws error if there is a Type Case fail.
     * @param insertVal First Object to compare
     * @param queueVal Second Object to compare
     * @return <0 if left than, 0 if equal, >0 if greater than
     */
    private int comp(T insertVal, T queueVal){
        Class c = insertVal.getClass();
        if(c.equals(String.class)){
            String s1 = (String) insertVal;
            String s2 = (String) queueVal;
            return s1.compareTo(s2);
        }else if(c.equals(Double.class)){
            double s1 = (double) insertVal;
            double s2 = (double) queueVal;
            return Double.compare(s1,s2);
        }else if(c.equals(Character.class)){
            char s1 = (char) insertVal;
            char s2 = (char) queueVal;
            return Character.compare(s1,s2);
        }else if(c.equals(Integer.class)){
            int s1 = (int) insertVal;
            int s2 = (int) queueVal;
            return Integer.compare(s1,s2);
        }else{
            throw new ClassCastException("Cannot compare Objects!");
        }
    }

}
