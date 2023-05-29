package Java.data_structures.array_implementation;

/**
 * Java Implementation of Queue using an array
 * Dynamic sizing as needed (growing and shrinking)
 * Init as -->  Queue<T> q = new Queue<>(); // Init capacity is the var INIT_CAPACITY
 *              Queue<T> q = new Queue<>(8); // Init capacity is 8;
 * Methods -->  void push(T value);
 *              T peek();
 *              T poll();
 *              int size();
 *              boolean isEmpty();
 *              int capacityAvailable();
 *              @Override int hashCode();
 *              @Override String toString();
 *              @Override boolean equals(Object obj);
 * @param <T> Generic Object Limiter
 * @author Nikhil Daehee Agarwal
 */
public class Queue<T> {

    private static final int INIT_CAPACITY = 10;

    private T[] queue;
    private int end;
    private int capacity;
    private int front;

    /**
     * Default Constructor for Queue Object
     * capacity is set to var INIT_CAPACITY
     */
    public Queue(){
        this.capacity = INIT_CAPACITY;
        this.end = 0;
        this.queue = (T[]) new Object[this.capacity];
        this.front = 0;
    }

    /**
     * Overload Constructor for Queue Object
     * Capacity is set to a specified integer capacity
     * @param capacity specified capacity
     */
    public Queue(int capacity){
        if(capacity <= 0){
            throw new NegativeArraySizeException("capacity must be a positive value");
        }
        this.capacity = capacity;
        this.end = 0;
        this.queue = (T[]) new Object[this.capacity];
        this.front = 0;
    }

    /**
     * Push a value to the back of our Queue
     * @param value Generic Object we want to add to the Queue
     */
    public void push(T value){
        if(end == queue.length){
            grow();
        }
        queue[end] = value;
        end++;
    }

    /**
     * Return the value at the front of the queue
     * Throws an error if the queue is empty (no front element)
     * @return Generic Value of front element
     */
    public T peek(){
        if(this.isEmpty()){
            throw new NegativeArraySizeException("Queue is Empty");
        }
        return queue[front];
    }

    /**
     * Return the value at the front of the queue
     * Throws an error if the queue is empty (no front element)
     * Also removes the front element from the list.
     * @return Generic Value of the front element
     */
    public T poll(){
        if(this.isEmpty()){
            throw new NegativeArraySizeException("Queue is Empty!");
        }
        T toReturn = queue[front];
        front++;
        if(front == capacity){
            shrink();
        }
        return toReturn;
    }

    /**
     * gets the size of the queue
     * @return int size
     */
    public int size(){
        return end-front;
    }

    /**
     * checks to see if our queue is empty or not
     * checks to the see if front pointer value and back pointer value are the same.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty(){
        return front==end;
    }

    /**
     * checks to see the remaining capacity available before we need to resize our queue
     * for developer use, does not affect the automatic dynamic resizing of the queue
     * @return int capacity available
     */
    public int capacityAvailable(){
        return queue.length-end;
    }

    /**
     * HashCode of our Queue based on the sum of all the unique hashCodes of the individual elements.
     * Curtails the hashcode to take care of overflow
     * @return int hashCOde
     */
    @Override
    public int hashCode(){
        long sum =0;
        for(int i = front;i<end;i++){
            sum += (queue[i].hashCode());
            sum %= Integer.MAX_VALUE;
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
        if(this.size()!=q.size()){
            return false;
        }
        int startOfQ = q.getFront();
        T[] qArray = q.getQueue();
        for(int i = front;i<end;i++){
            if(!queue[i].equals(qArray[startOfQ])){
                return false;
            }
            startOfQ++;
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
        for (int i = front; i < end; i++) {
            ans += queue[i];
            if (i != end - 1) {
                ans += ", ";
            }
        }
        return "<-out- "+ans + "] <-in-";
    }

    /**
     * getter method for Queue array
     * @return Generic Object array
     */
    private T[] getQueue(){
        return queue;
    }

    /**
     * getter method for front pointer value
     * @return index of front in Q array
     */
    private int getFront(){
        return front;
    }

    /**
     * getter method for end pointer value
     * @return index of end in Q array
     */
    private int getEnd(){
        return end;
    }

    /**
     * getter method for internal capacity value
     * @return int capacity
     */
    private int getCapacity(){
        return capacity;
    }

    /**
     * helper method to shrink the size of queue once the front pointer has moved higher than the initial capacity of the queue.
     */
    private void shrink(){
        T[] newQ = (T[]) new Object[queue.length-capacity];
        int index = 0;
        for(int i = front;i<end;i++){
            newQ[index] = queue[i];
            index++;
        }
        front = 0;
        end = index;
        queue = newQ;
    }

    /**
     * helper method to grow the size of the queue array once the end pointer is equal to the length of our array.
     */
    private void grow(){
        T[] newQ = (T[]) new Object[end + capacity];
        for(int i = front;i<end;i++){
            newQ[i] = queue[i];
        }
        this.queue = newQ;
    }

}
