package Java.data_structures.node_implementation;

/**
 * Java Implementation of Graph Node Object (Build graphs)
 * Build undirected graphs using this Data structure. Each Node contains a value and linkedList of Nodes that this Node points to.
 * If a node adds a connections to another node, the connections is bidirectional.
 * Likewise, if a connection is removed in one node, the connections is severed from both ends.
 * Init as -->  GraphNode<T> graphNode = new GraphNode<>(); //Null node
 *              GraphNode<T> graphNode = new GraphNode<>(val); //Init with value val
 * Methods -->  boolean addConnection(GraphNode<T> node);
 *              boolean removeConnection(GraphNode<T> node);
 *              void val(T value);
 *              T val();
 *              int numConnections();
 *              GraphNode<T>[] getConnections();
 *              @Override String toString();
 * @param <T> Class Limiter
 * @author Nikhil Daehee Agarwal
 */
public class GraphNode<T> {

    private T val;
    private Node<GraphNode<T>> connections;
    private int numberOfConnections;

    /**
     * Default Constructor for GraphNode Object. Null Node
     */
    public GraphNode(){
        this.val = null;
        this.connections = null;
    }

    /**
     * Init GraphNode Object with a given value
     * @param val Value of Node
     */
    public GraphNode(T val){
        this.val = val;
        this.connections = null;
    }

    /**
     * getter method for the value stored in this current node.
     * @return T value
     */
    public T val(){
        return val;
    }

    /**
     * setter method to set the value of this node to given value
     * @param value T object
     */
    public void val(T value){
        this.val = value;
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

    /**
     * getter method for an array of nodes that this node points to.
     * @return GraphNode<T>[] containing array of pointers to connection nodes.
     */
    public GraphNode<T>[] getConnections(){
        GraphNode<T>[] array = new GraphNode[numberOfConnections];
        Node<GraphNode<T>> start = connections;
        int i = 0;
        while(start!=null){
            array[i] = start.val();
            i++;
            start = start.next();
        }
        return array;
    }

    /**
     * getter method for the total number of nodes this node has connections to.
     * @return int number of connections
     */
    public int numConnections(){
        return numberOfConnections;
    }

    /**
     * Removes a connection from this Node to another specified Node.
     * @param gnode The other node to remove the connection with.
     * @return true if connection was found and removed, false otherwise.
     */
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

    /**
     * Visualization of Node Object.
     * Prints the current value of the Node as well as the values of the nodes this node has connections with.
     * @return String node
     */
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

    /**
     * helper method to remove the connection from a given node to this node.
     * Ensures that if we remove a connection from one node the connections is severed on both ends.
     * @param gnode The node we want to remove this node from.
     */
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

    /**
     * Searches a linked List of This node's connections to see if this node already has a connection with the given node.
     * @param node Node object we are searching for.
     * @return true if connection already exists, false otherwise.
     */
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

}
