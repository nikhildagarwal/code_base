package Java.structures;

public class Stack<T> {

    private static final int INIT_CAPACITY = 100;

    private T[] stack;
    private int size;
    private int capacity;

    public Stack(){
        this.capacity = INIT_CAPACITY;
        this.stack = (T[]) new Object[capacity];
        this.size = 0;
    }

    public Stack(int capacity){
        this.capacity = capacity;
        this.stack = (T[]) new Object[capacity];
        this.size = 0;
    }

    public void push(T value){
        if(size >= stack.length){
            grow();
        }
        stack[size] = value;
        size++;
    }

    public T peek(){
        if(size == 0){
            throw new IndexOutOfBoundsException("Stack is Empty!");
        }
        return stack[size-1];
    }

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

    public int capacityAvailable(){
        return stack.length-size;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clear(){
        this.stack = (T[]) new Object[capacity];
        this.size = 0;
    }

    public void reverse(){
        int pR = size-1;
        for(int i = 0;i<size/2;i++){
            T temp = stack[i];
            stack[i] = stack[pR];
            stack[pR] = temp;
            pR--;
        }
    }

    public boolean contains(T value){
        for(int i = 0;i<size;i++){
            if(stack[i].equals(value)){
                return true;
            }
        }
        return false;
    }

    public int count(T value){
        int ans = 0;
        for(int i = 0;i<size;i++){
            if(stack[i].equals(value)){
                ans++;
            }
        }
        return ans;
    }

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

    private T[] getStack(){
        return stack;
    }

    private void grow(){
        T[] newS = (T[]) new Object[stack.length+capacity];
        for(int i = 0;i<size;i++){
            newS[i] = stack[i];
        }
        this.stack = newS;
    }

    private void shrink(){
        T[] newS = (T[]) new Object[stack.length-capacity];
        for(int i = 0;i<size;i++){
            newS[i] = stack[i];
        }
        this.stack = newS;
    }

}
