package Java.data_structures;

public class Queue<T> {

    private static final int INIT_CAPACITY = 10;

    private T[] queue;
    private int size;
    private int capacity;
    private int front;

    public Queue(){
        this.capacity = INIT_CAPACITY;
        this.size = 0;
        this.queue = (T[]) new Object[this.capacity];
        this.front = 0;
    }

    public Queue(int capacity){
        if(capacity <= 0){
            throw new NegativeArraySizeException("capacity must be a positive value");
        }
        this.capacity = capacity;
        this.size = 0;
        this.queue = (T[]) new Object[this.capacity];
        this.front = 0;
    }

    @Override
    public int hashCode(){
        long sum =0;
        for(int i = front;i<size;i++){
            sum += (queue[i].hashCode());
            sum %= Integer.MAX_VALUE;
        }
        long fin = sum < 0 ? sum * (-1) : sum;
        return (int) fin;
    }

    @Override
    public boolean equals(Object obj){

    }

    @Override
    public String toString(){

    }


}
