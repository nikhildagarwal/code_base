package Java.data_structures;

/**
 * Implementation of C++ pair object in Java
 * Init as -->  Pair<A,B> p1 = new Pair<>();
 * Methods -->  A getFirst();
 *              B getSecond();
 *              void setFirst(A a);
 *              void setSecond(B b);
 *              void set(A a, B b);
 *              int compareTo(Object obj);
 *              @Override boolean equals(Object obj);
 *              @Override int hashCode();
 *              @Override String toString();
 *              @Override int compareTo(Object obj);
 * @param <A> Generic Object
 * @param <B> Generic Object
 * @author Nikhil Daehee Agarwal
 */
public class Pair<A, B> implements Comparable{

    private static final int STRING = 0;
    private static final int INTEGER = 1;
    private static final int CHARACTER = 2;
    private static final int DOUBLE = 3;

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

    /**
     * Method to compare two Pair objects. Gives priority to the first element. Otherwise, compares the second element.
     * @param obj the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Object obj){
        Pair<A,B> pair = (Pair<A, B>) obj;
        if(pair.value1.getClass().equals(String.class)){
            String s2 = (String) pair.value1;
            String s1 = (String) this.value1;
            int compare = s1.compareTo(s2);
            if(compare == 0){
                return checkSecond(STRING,this.value2,pair.value2);
            }else{
                return compare;
            }
        }else if(pair.value1.getClass().equals(Integer.class)){
            int i2 = (int) pair.value1;
            int i1= (int) this.value1;
            int compare = Integer.compare(i1,i2);
            if(compare == 0){
                return checkSecond(INTEGER,this.value2,pair.value2);
            }else{
                return compare;
            }
        }else if(pair.value1.getClass().equals(Character.class)){
            char c2 = (char) pair.value1;
            char c1 = (char) this.value1;
            int compare = Character.compare(c1,c2);
            if(compare == 0){
                return checkSecond(CHARACTER,this.value2,pair.value2);
            }else{
                return compare;
            }
        }else if(pair.value1.getClass().equals(Double.class)){
            double d1 = (double) this.value1;
            double d2 = (double) pair.value1;
            int compare = Double.compare(d1,d2);
            if(compare == 0){
                return checkSecond(DOUBLE,this.value2,pair.value2);
            }else{
                return compare;
            }
        }else{
            throw new ClassFormatError("Cannot compare the Objects!");
        }
    }

    private int checkSecond(int indicator, B thisB, B pairB){
        switch(indicator){
            case STRING:
                String s1 = (String) thisB;
                String s2 = (String) pairB;
                return s1.compareTo(s2);
            case INTEGER:
                int i1 = (int) thisB;
                int i2 = (int) pairB;
                return Integer.compare(i1,i2);
            case DOUBLE:
                double d1 = (double) thisB;
                double d2 = (double) pairB;
                return Double.compare(d1,d2);
            case CHARACTER:
                char c1 = (char) thisB;
                char c2 = (char) pairB;
                return Character.compare(c1,c2);
        }
        return 0;
    }

}
