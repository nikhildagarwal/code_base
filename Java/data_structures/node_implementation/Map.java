package Java.data_structures.node_implementation;

public class Map<A,B> {

    private static final int DIVISOR = 256;
    private static final int BUCKETS_CAPACITY = Integer.MAX_VALUE / DIVISOR;

    private Node<Integer> front;
    private Node<Integer> end;


}
