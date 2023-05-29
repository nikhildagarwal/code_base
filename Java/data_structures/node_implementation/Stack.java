package Java.data_structures.node_implementation;

public class Stack<T> {

    private Node<T> front;
    private int size;

    public Stack(){
        this.front = null;
        this.size = 0;
    }

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

    public T peek(){
        if(front == null){
            throw new NullPointerException("Stack is Empty!");
        }
        return front.val();
    }

    public T pop(){
        if(front == null){
            throw new NullPointerException("Stack is Empty!");
        }
        T value = front.val();
        front = front.next();
        size--;
        return value;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clear(){
        this.size = 0;
        this.front = null;
    }

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
