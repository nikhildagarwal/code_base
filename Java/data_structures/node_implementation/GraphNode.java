package Java.data_structures.node_implementation;

public class GraphNode<T> {

    private T val;
    private Node<GraphNode<T>> connections;

    public GraphNode(){
        this.val = null;
        this.connections = null;
    }

    public GraphNode(T val){
        this.val = val;
        this.connections = null;
    }

    public void addConnection(GraphNode<T> node){
        if(!searchAndFound(node)){
            Node<GraphNode<T>> start = new Node<>(node);
            start.next(connections);
            connections = start;
            Node<GraphNode<T>> newStart = new Node<>(this);
            newStart.next(node.connections);
            node.connections = newStart;
        }
    }

    @Override
    public String toString() {
        String ans = "Node: "+val+"\n";
        ans += "Connections: [";
        Node<GraphNode<T>> start = connections;
        while(start!=null){
            ans += start.val().val;
            if(start.next()!=null){
                ans += ", ";
            }
            start = start.next();
        }
        return ans + "]\n---------------";
    }

    private boolean searchAndFound(GraphNode<T> node){
        Node<GraphNode<T>> start = connections;
        while(start!=null){
            if(node==start.val()){
                return true;
            }
            start = start.next();
        }
        return false;
    }

    public static void main(String[] args){
        GraphNode<String> LAX = new GraphNode<>("LAX");
        GraphNode<String> EWR = new GraphNode<>("EWR");
        GraphNode<String> JFK = new GraphNode<>("JFK");
        LAX.addConnection(EWR);
        LAX.addConnection(JFK);
        JFK.addConnection(LAX);
        System.out.println(LAX);
        System.out.println(EWR);
        System.out.println(JFK);
    }

}
