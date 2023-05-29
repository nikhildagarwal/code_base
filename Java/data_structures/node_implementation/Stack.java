package Java.data_structures.node_implementation;

/**
 * Java Implementation for Dynamic Stack.
 * DOES NOT USE ARRAYS, instead uses nodes (LinkedList implementation);
 * Space grows and shrinks as needed.
 * Init as -->  Stack<T> myStack = new Stack<>();
 * Methods -->  void push(T value)
 *              T peek()
 *              T pop()
 *              int size()
 *              boolean isEmpty()
 *              void clear()
 *              boolean contains(T value)
 *              int count(T value)
 *              @Override boolean equals(Object obj)
 *              @Override int hashCode()
 *              @Override String toString()
 * @param <T> Generic Object
 * @author Nikhil Daehee Agarwal
 */
public class Stack<T> {

    private Node<T> front;
    private int size;

    /**
     * Default Constructor
     */
    public Stack(){
        this.front = null;
        this.size = 0;
    }

    /**
     * add T value to stack
     * @param value T to add
     */
    public void push(T value){
        if(front == null){
            front = new Node<>(value);
        }else{
            Node<T> n = new Node<>(value);
            n.next(front);
            front = n;
        }
        size++;
    }

    /**
     * returns the top value of our stack WITHOUT removing it.
     * @return T value
     */
    public T peek(){
        if(front == null){
            throw new NullPointerException("Stack is Empty!");
        }
        return front.val();
    }

    /**
     * Returns the top value of our stack while also removing it.
     * @return T value
     */
    public T pop(){
        if(front == null){
            throw new NullPointerException("Stack is Empty!");
        }
        T value = front.val();
        front = front.next();
        size--;
        return value;
    }

    /**
     * size of stack (number of elements in the stack)
     * @return int size
     */
    public int size(){
        return size;
    }

    /**
     * returns if the stack is empty or not (true if has elements, false otherwise);
     * @return true if empty, falso otherwise
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Clears the stack
     */
    public void clear(){
        this.size = 0;
        this.front = null;
    }

    /**
     * Checks to see if a given T value is in our stack.
     * @param value T value
     * @return true if value is found in stack, false otherwise
     */
    public boolean contains(T value){
        Node<T> start = front;
        while(start != null){
            if(!start.val().equals(value)){
                return false;
            }
            start = start.next();
        }
        return true;
    }

    /**
     * returns the number of times a value is found in the stack
     * @param value T value to find.
     * @return int number of times value was encountered.
     */
    public int count(T value) {
        int c = 0;
        Node<T> start = front;
        while(start!= null){
            if(start.val().equals(value)){
                c++;
            }
            start = start.next();
        }
        return c;
    }

    /**
     * Checks to see if two stacks are equal.
     * @param obj stack object
     * @return true if the sizes are the same and each element is the same in order.
     */
    @Override
    public boolean equals(Object obj){
        Stack<T> stack = (Stack<T>) obj;
        if(stack.size()!=this.size()){
            return false;
        }
        Node<T> start1 = stack.front;
        Node<T> start2 = this.front;
        while(start1!=null){
            if(!start1.val().equals(start2.val())){
                return false;
            }
            start1 = start1.next();
            start2 = start2.next();
        }
        return true;
    }

    /**
     * Returns the hashcode of the set.
     * Takes care of overflow.
     * @return int hashcode.
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
     * Returns the stack as a string to better visualize the data structure.
     * @return String
     */
    @Override
    public String toString(){
        String ans = "--in--out-->[";
        Node<T> start = front;
        while(start!=null){
            ans+=start.val();
            if(start.next()!=null){
                ans+=", ";
            }
            start = start.next();
        }
        return ans+"]";
    }

}
