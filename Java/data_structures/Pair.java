package Java.data_structures;

/**
 * Implementation of C++ pair object in Java
 * Init as -->  Pair<A,B> p1 = new Pair<>();
 * Methods -->  A getFirst();
 *              B getSecond();
 *              void setFirst(A a);
 *              void setSecond(B b);
 *              void set(A a, B b);
 *              @Override boolean equals(Object obj);
 *              @Override int hashCode();
 *              @Override String toString();
 * @param <A> Generic Object
 * @param <B> Generic Object
 * @author Nikhil Daehee Agarwal
 */
public class Pair<A, B> {

    private A value1;
    private B value2;

    /**
     * Default constructor for null Pair object
     */
    public Pair() {
        this.value1 = null;
        this.value2 = null;
    }

    /**
     * Constructor for Pair Object while initializing values
     * @param a value of first object in pair
     * @param b value of second object in pair
     */
    public Pair(A a, B b) {
        this.value1 = a;
        this.value2 = b;
    }

    /**
     * Constructor to create duplicate Pair object
     * @param p Pair Object to duplicate
     */
    public Pair(Pair<A,B> p){
        this.value1 = p.getFirst();
        this.value2 = p.getSecond();
    }

    /**
     * gets the first value in the pair
     * @return Object of first value
     */
    public A getFirst() {
        return value1;
    }

    /**
     * gets the second value in the pair
     * @return Object of the second value
     */
    public B getSecond() {
        return value2;
    }

    /**
     * sets the first value in the pair to a specified object A
     * @param a Object A
     */
    public void setFirst(A a) {
        this.value1 = a;
    }

    /**
     * sets the second value in the pair to the specified object B
     * @param b Object B
     */
    public void setSecond(B b) {
        this.value2 = b;
    }

    /**
     * Sets both values of the pair
     * @param a Object A
     * @param b Object B
     */
    public void set(A a, B b) {
        this.value1 = a;
        this.value2 = b;
    }

    /**
     * Override method from Java Object class
     * Checks to see if two Pairs are equal
     * @param obj Pair we want to compare
     * @return true if pairs are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        Pair<A, B> newPair = (Pair<A, B>) obj;
        return newPair.getFirst().equals(value1) && newPair.getSecond().equals(value2);
    }

    /**
     * Overrides HashCode method
     * @return hashCode of pair based on hashCodes of value1 and value2;
     */
    @Override
    public int hashCode(){
        long inter = (value1.hashCode() + value2.hashCode()) % Integer.MAX_VALUE;
        long fin = inter < 0 ? inter*(-1) : inter;
        return (int) fin;
    }

    /**
     * Override of Java Object class toString().
     * @return Pair as a string --> < value1 : value2 >
     */
    @Override
    public String toString() {
        String ans = "<";
        ans += value1;
        ans += ": ";
        ans += value2;
        return ans + ">";
    }

}
