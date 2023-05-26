package Java;

import Java.data_encapsulation.Pair;

public class Test {

    public static void main(String[] args){
        Pair<String,Integer> pair = new Pair<>("My Name is Nikhil",21);
        System.out.println(pair);
        int code = pair.hashCode();
        System.out.println(code);

    }

}
