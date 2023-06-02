package Java.data_structures.node_implementation;

public class GraphNode<T> {

    private T val;
    private Node<GraphNode<T>> connections;
    private int numberOfConnections;

    public GraphNode(){
        this.val = null;
        this.connections = null;
    }

    public GraphNode(T val){
        this.val = val;
        this.connections = null;
    }

    /**
     * Adds connection from this node to a given node
     * @param node GraphNode to connect to
     * @return true if connections was added, false if connection already exists.
     */
    public boolean addConnection(GraphNode<T> node){
        if(!searchAndFound(node)){
            Node<GraphNode<T>> start = new Node<>(node);
            start.next(connections);
            connections = start;
            if(node!=this){
                Node<GraphNode<T>> newStart = new Node<>(this);
                newStart.next(node.connections);
                node.connections = newStart;
                node.numberOfConnections++;
            }
            this.numberOfConnections++;
            return true;
        }
        return false;
    }

    public int numConnections(){
        return numberOfConnections;
    }

    public boolean removeConnection(GraphNode<T> gnode){
        if(connections == null){
            return false;
        }
        if(connections.val()==gnode){
            connections = connections.next();
            numberOfConnections--;
            if(this!=gnode){
                cleanup(gnode);
            }
            return true;
        }
        Node<GraphNode<T>> start = connections;
        while(start.next()!=null){
            if(start.next().val()==gnode){
                start.next(start.next().next());
                numberOfConnections--;
                if(this!=gnode){
                    cleanup(gnode);
                }
                return true;
            }
            start = start.next();
        }
        return false;
    }

    @Override
    public String toString() {
        String ans = "Node: "+val+"\n";
        ans += "Count: "+numberOfConnections+"\n";
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

    private void cleanup(GraphNode<T> gnode){
        if(gnode.connections.val()==this){
            gnode.connections = gnode.connections.next();
            gnode.numberOfConnections--;
        }else{
            Node<GraphNode<T>> gnodeStart = gnode.connections;
            while(gnodeStart.next()!=null){
                if(gnodeStart.next().val()==this){
                    gnodeStart.next(gnodeStart.next().next());
                    gnode.numberOfConnections--;
                }
                gnodeStart = gnodeStart.next();
            }
        }
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
        GraphNode<String> JFK = new GraphNode<>("JFK");
        GraphNode<String> LAX = new GraphNode<>("LAX");
        GraphNode<String> DAL = new GraphNode<>("DAL");
        GraphNode<String> EWR = new GraphNode<>("EWR");
        JFK.addConnection(LAX);
        JFK.addConnection(DAL);
        JFK.addConnection(EWR);

        System.out.println(DAL.removeConnection(JFK));
        System.out.println(JFK);
        System.out.println(LAX);
        System.out.println(DAL);
        System.out.println(EWR);
    }

}
