
package ex1.src;
import ex1.src.node_info;
import ex1.src.weighted_graph;

import java.util.Collection;
import java.util.HashMap;


public class WGraph_DS implements weighted_graph,java.io.Serializable {

    private HashMap<Integer, node_info> MyGraph ;
    private HashMap<node_info, HashMap<node_info, Double>> neighbors ;
    private int EdgeSize=0;
    private int changes = 0;

    //constructor
    public WGraph_DS() {
        this.MyGraph=new HashMap<>();
        this.neighbors= new HashMap<>();
        this.EdgeSize = 0;
    }

    /**
     * return the node_data by the node_id,if this node_id doesn't existent in the graph return null.
     * @param key -node_id
     * @return the node_data, null if the key doesn't exist.
     * run in O(1)
     */
    @Override
    public node_info getNode(int key) {
        // if this key exist in this graph.
        if(MyGraph.containsKey(key)) {
            return MyGraph.get(key);
        }
        else
            return null;
    }
    /**
     * @return true if there is a edge between node1 and node2 ,else false.
     * @param node1
     * @param node2
     * run in O(1)
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        //if one of the nodes doesn't exists or the nodes are same one return false.
        if ((!MyGraph.containsKey(node1)) || (!MyGraph.containsKey(node2))||(node1==node2))
        {
            return false;
        }
        else {
            //return if there is a edge between the nodes.
            return neighbors.get(MyGraph.get(node2)).containsKey(MyGraph.get(node1));
        }
    }
    /**
     * Note:run in O(1) time.
     * @param node1
     * @param node2
     * @return the weight if the edge between node1 to node2,if there is no such edge - should return -1.
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(node1==node2&&MyGraph.containsKey(node1)&&MyGraph.containsKey(node2))
            return 0;
        if (!hasEdge(node1, node2))
            return -1;
        else
            return neighbors.get(getNode(node1)).get(getNode(node2));
    }
    /**
     * add a new node to the graph with given key.
     * Note:run in O(1).
     * Note2: if there is already a node with such a key -> no action should be performed.
     * @param key
     */
    @Override
    public void addNode(int key) {
        //if the node doesn't exists make this node.
         if (!MyGraph.containsKey(getNode(key))) {
            //Make a new node_info and init the node id with key.
            node_info temp=new NodeInfo(key);
            //init the graph and neighbors
            MyGraph.put(key,temp);
            neighbors.put(temp,new HashMap<>());
            changes++;
        }
    }
    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note:run in O(1) time.
     * Note2: if the edge node1-node2 already exists check if the weight is different  if so updates the weight of the edge,
     * else  do noting.
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        //if w equal to 0 or smaller don't do nothing
        if (w >= 0) {
            //if the edge already exists
            if (hasEdge(node1, node2)) {
                //if the weight of the edge between the nodes is different change it.
                if (neighbors.get(getNode(node1)).get(getNode(node2)) != w) {
                    neighbors.get(getNode(node1)).put(getNode(node2), w);
                    neighbors.get(getNode(node2)).put(getNode(node1), w);
                    changes++;
                }
            }
            //if there isn't edge between the nodes ,make a new edge and init
            else {
                if ((MyGraph.containsKey(node1)) && (MyGraph.containsKey(node2)) && (node1 != node2)) {
                    neighbors.get(getNode(node1)).put(getNode(node2), w);
                    neighbors.get(getNode(node2)).put(getNode(node1), w);
                    EdgeSize++;
                    changes++;
                }
            }
        }
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection with all the nodes in the graph.
     * Note:run in O(1) tim
     * @return collection of all the nodes in the graph.
     */
    @Override
    public Collection<node_info> getV() {
        return MyGraph.values();
    }
    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note:run in O(1).
     * @return if this node_id have neighbors return-> collection of all this node's neighbors.
     * @return else return-> null
     *
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        //if this node_id existent in the graph return all his neighbors, else return null
       if(MyGraph.containsKey(node_id))
           return neighbors.get(MyGraph.get(node_id)).keySet();
       else
           return null;
    }
    /**
     * Delete the node from the graph -
     * and removes all edges which starts or ends at this node.
     * Note:run in O(n) is the worst time in case the node that remove connected to all nodes in the graph.
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        //if the graph doesn't contains the key return null else remove this node and all his edges.
        if (!MyGraph.containsKey(key))
            return null;

        else {
            //run over key neighbors and remove all his neighbors and init EdgeSize.
            for (node_info i : neighbors.get(getNode(key)).keySet()) {
                neighbors.get(i).remove(getNode(key));
                EdgeSize--;
                changes++;
            }
            //remove from the hashmap MyGraph and hashmap neighbors this key
            node_info ToReturn = MyGraph.get(key);
            neighbors.remove(key);
            MyGraph.remove(key);
            changes++;
            //return deleted node_info copy.
            return ToReturn;
        }
    }

    /**
     * Delete the edge from the graph,
     * Note:run in O(1) time.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        //check if the nodes  existent and also the nodes are diffrent  check if they have a edge.
        if ((MyGraph.containsKey(node1)) && (MyGraph.containsKey(node2))&&(node1!=node2)) {
            //if the two nodes have a edge between them remove the edge
            if (hasEdge(node1, node2)) {
                neighbors.get(getNode(node1)).remove(getNode(node2));
                neighbors.get(getNode(node2)).remove(getNode(node1));
                changes++;
                EdgeSize--;
            }
        }
    }

    /**
     * Note:run in O(1) time.
     * @return number of nodes in the graph
     */
    @Override
    public int nodeSize() {
        return MyGraph.size();
    }

    /**
     *
     * Note:run in O(1) time.
     * @return number of edges in the graph.
     */
    @Override
    public int edgeSize() {
        return EdgeSize;
    }
    /**
     *
     * Note:run in O(1) time.
     * @return number of changes that occur in this graph.
     */
    @Override
    public int getMC() {
        return changes;
    }


    private static class NodeInfo implements node_info,java.io.Serializable {
        private int id;
        private String info;
        private double tag;


        public NodeInfo(int key) {
            this.id = key;
            this.info = "";
            this.tag = 0;
        }

        /**
         *
         * Note:run in O(1) time.
         * @return the key (id) associated with this node.
         */
        public int getKey() {
            return id;
        }

        /**
         * Note:O(1).
         * @return this node's info.
         * Note:run in O(1) time.
         */
        public String getInfo() {
            return info;
        }

        /**
         * Allows changing this node's info.
         * @param s- the new value of the node's info.
         * Note:init in O(1) time.
         */

        public void setInfo(String s) {
            info = s;
        }

        /**
         * Note:run in O(1) time.
         * @return this node's tag.
         */
        public double getTag() {
            return tag;
        }

        /**
         * Allow changing this node's tag.
         * @param t - the new value of the node's tag.
         * Note:init in O(1) time.
         */
        public void setTag(double t) {
            tag = t;
        }
    }
}


