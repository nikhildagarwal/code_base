package Java.structures;

/**
 * Java Implementation for Dynamic Stack.
 * Space grows and shrinks as needed.
 * Init as -->  Stack<T> myStack = new Stack<>(); //default capacity is var INIT_CAPACITY
 *              Stack<T> myStack = new Stack<>(4); //set init capacity to 4
 * Methods -->  void push(T value)
 *              T peek()
 *              T pop()
 *              int capacityAvailable()
 *              int size()
 *              boolean isEmpty()
 *              void clear()
 *              void reverse()
 *              boolean contains()
 *              int count()
 *              @Override boolean equals()
 *              @Override String toString()
 * @param <T> Generic Object
 * @author Nikhil Daehee Agarwal
 */
public class Stack<T> {

    private static final int INIT_CAPACITY = 100;

    private T[] stack;
    private int size;
    private int capacity;

    /**
     * Default constructor for Stack Object;
     */
    public Stack(){
        this.capacity = INIT_CAPACITY;
        this.stack = (T[]) new Object[capacity];
        this.size = 0;
    }

    /**
     * Creates Stack Object with a specified init capacity.
     * @param capacity
     */
    public Stack(int capacity){
        this.capacity = capacity;
        this.stack = (T[]) new Object[capacity];
        this.size = 0;
    }

    /**
     * Push a value onto the top of our stack.
     * @param value Generic object to push
     */
    public void push(T value){
        if(size >= stack.length){
            grow();
        }
        stack[size] = value;
        size++;
    }

    /**
     * get the value at the top of our stack WITHOUT removing it
     * @return generic value at the top stack
     */
    public T peek(){
        if(size == 0){
            throw new IndexOutOfBoundsException("Stack is Empty!");
        }
        return stack[size-1];
    }

    /**
     * get the top value of our stack and remove it from the stack
     * @return
     */
    public T pop(){
        if(size == 0){
            throw new IndexOutOfBoundsException("Stack is Empty!");
        }
        T val = stack[size-1];
        size--;
        if(stack.length-size >= capacity && size != 0){
            shrink();
        }
        return val;
    }

    /**
     * Checks to see how much capacity is available before we resize the stack
     * For developer use (maximize efficiency)
     * @return int number of null spaces in stack
     */
    public int capacityAvailable(){
        return stack.length-size;
    }

    /**
     * size of our stack (number of elements in the stack)
     * @return
     */
    public int size(){
        return size;
    }

    /**
     * method to check and see if our stack is empty or not
     * @return true if stack has no elements, false otherwise
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * clear our stack and reset it.
     */
    public void clear(){
        this.stack = (T[]) new Object[capacity];
        this.size = 0;
    }

    /**
     * reverse our stack in place.
     * The bottom now becomes the top
     */
    public void reverse(){
        int pR = size-1;
        for(int i = 0;i<size/2;i++){
            T temp = stack[i];
            stack[i] = stack[pR];
            stack[pR] = temp;
            pR--;
        }
    }

    /**
     * Check to see if the stack object contains a certain element
     * @param value generic value T we want to check for
     * @return true if we find that value, false otherwise
     */
    public boolean contains(T value){
        for(int i = 0;i<size;i++){
            if(stack[i].equals(value)){
                return true;
            }
        }
        return false;
    }

    /**
     * return the number of time an element is found in our stack object
     * @param value generic value T we want to check for
     * @return int number of times that element is present in our stack
     */
    public int count(T value){
        int ans = 0;
        for(int i = 0;i<size;i++){
            if(stack[i].equals(value)){
                ans++;
            }
        }
        return ans;
    }

    /**
     * Override method for equals in Java.Object class.
     * checks if two Stack objects are equal to each other.
     * @param obj Stack Object we want to check
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        Stack<T> s = (Stack<T>) obj;
        System.out.println(this);
        System.out.println(s);
        if(s.size() != size) {
            return false;
        }
        T[] secondStackArray = s.getStack();
        for(int i = 0;i<size;i++){
            if(!secondStackArray[i].equals(stack[i])){
                return false;
            }
        }
        return true;
    }

    /**
     * Coverts Stack Object to a printable string.
     * Easier visualization.
     * @return String of format --> { |val1 ]val2 ]val3 ]... ]valn }
     */
    @Override
    public String toString(){
        String ans = "{";
        for(int i = 0;i<size;i++){
            if(i == 0){
                ans += ("|"+stack[i]);
            }else{
                ans += (" ]"+stack[i]);
            }

        }
        return ans+" }";
    }

    private int getCapacity(){
        return capacity;
    }

    /**
     * getter method for T[] stack array
     * @return T[] stack array
     */
    private T[] getStack(){
        return stack;
    }

    /**
     * helper method to grow stack dynamically
     */
    private void grow(){
        T[] newS = (T[]) new Object[stack.length+capacity];
        for(int i = 0;i<size;i++){
            newS[i] = stack[i];
        }
        this.stack = newS;
    }

    /**
     * helper method to shrink our stack
     */
    private void shrink(){
        T[] newS = (T[]) new Object[stack.length-capacity];
        for(int i = 0;i<size;i++){
            newS[i] = stack[i];
        }
        this.stack = newS;
    }

}
