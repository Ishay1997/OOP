package ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms,java.io.Serializable {
    private weighted_graph graph1;

    public WGraph_Algo() {
        this.graph1 = new WGraph_DS();
    }
    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     * Note:init in O(1) time.
     */
    @Override
    public void init(weighted_graph g) {
        graph1 = g;
    }


    /**
     * Return the underlying graph of which this class works.
     * Note:run in O(1) time.
     * @return graph1
     */
    @Override
    public weighted_graph getGraph() {
        return graph1;
    }

    /**
     * Make a deep copy graph.
     * First make a new graph and init the new graph with the nodes from graph1.this running will take O(n) time.
     * second make a connection distance in the new graph like graph1 distance,this running will take O(n*n).
     * Note:run in O(n^2).
     * @return CopyGraph.
     */
    @Override
    public weighted_graph copy() {
        //make a new graph
        weighted_graph CopyGraph = new WGraph_DS();
        //copy all the nodes from graph1 to CopyGraph,will take O(n) runtime.
        for (node_info i : graph1.getV()) {
            CopyGraph.addNode(i.getKey());
        }
        //make connection and init  CopyGraph like in graph1  neighbours,will take O(n^2) runtime.
        for (node_info i : graph1.getV()) {
            for (node_info j : graph1.getV(i.getKey())) {
                CopyGraph.connect(i.getKey(), j.getKey(), graph1.getEdge(i.getKey(), j.getKey()));
            }

        }
        return CopyGraph;
    }
    /**
     * Returns true if  there is a valid path from every node to each
     * other node,else false.
     * The function take one of the nodes in the graph and change his tag to 1,and put him in queue .
     * The function will run over the queue until the queue empty,all run over the queue pop the first node from the
     * queue and run over his neighbor if there tag is 0 put them in queue and init there tag to 1 else don't do nothing.
     * do this until the queue will be empty.
     * Note:run in O(V+E) time  (bfs running time).
     * @return run over all the nodes if there is a node's tag that equal to 0 return false.
     * @return else return true

     */
    @Override
    public boolean isConnected() {
        node_info FirstNode;
        node_info curr;
        int count=0;
        Queue<node_info> QueueNodes = new LinkedList<>();

        //reset the setTag graph1 ,take's O(n) time.
        if (graph1 != null && graph1.getV() != null) {
            for (node_info i : graph1.getV()) {
                i.setTag(0);
            }

            Collection<node_info> NodesCollection = graph1.getV();
            Iterator<node_info> PointerNodes = NodesCollection.iterator();
            //begin to run over the graph1 with Iterator and init the PointerNodes to one Node.

            //if the graph is empty return true else init FirstNode.setTag(1) and put him in Queue.
            if (!PointerNodes.hasNext()) {
                return true;
            }
            else
                FirstNode = PointerNodes.next();
            FirstNode.setTag(1);
            count++;
            QueueNodes.add(FirstNode);

            while (QueueNodes.size() != 0)//if the queue is not empty
            {
                Collection<node_info> QueueNodesOutNi = graph1.getV(QueueNodes.peek().getKey());
                Iterator<node_info> PointerQueueNodesOutNi = QueueNodesOutNi.iterator();
                while (PointerQueueNodesOutNi.hasNext()) {//run over the graph with dfs over all the FirstNode connect nodes
                    curr = PointerQueueNodesOutNi.next();
                    if (curr.getTag() == 0) {
                        curr.setTag(1);
                        QueueNodes.add(curr);
                    }
                }
                QueueNodes.poll();
            }
            //run over the nodes and check if one of the nodes equal 0 return false else true
            for (node_info i : graph1.getV()) {
                if (i.getTag() == 0)
                    return false;
            }
        }
        return true;
    }
    /**
     * returns the length of the shortest path between src to dest
     * if no such path between the nodes --> returns -1
     * if one of the nodes doesn't exist -->return -1
     * if the two nodes are same return--> 0
     * @param src - start node
     * @param dest - end (target) node
     * Note:run in
     * @return the shortest path distance in this graph from src to dest.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        //if one of the id nodes doesn't exist in graph1 return -1
        if(!graph1.getV().contains(graph1.getNode(src))||!graph1.getV().contains(graph1.getNode(dest))) {
            return -1;
        }
        Comparator<node_info> InfoComparator = Comparator.comparingDouble(node_info::getTag);
        Queue<node_info> Queue = new PriorityQueue<>(InfoComparator);

        //init all node's Tags to 0,run in O(n) time.
        for (node_info i : graph1.getV()) {
            i.setTag(0);
        }

        // init all node's info to be "NeverBeenHere" ,run in O(n) time.
        for(node_info i:graph1.getV()){
            i.setInfo("NeverBeenHere");
        }

        node_info temp;
        HashMap<node_info, node_info> SmallestPathFather = new HashMap<>();
        HashSet<node_info> BlackList = new HashSet<>();


        //init the first node and put in SmallestPath and queue.
        // graph1.getNode(src).setTag(0);
        SmallestPathFather.put(graph1.getNode(src), graph1.getNode(src));
        Queue.add(graph1.getNode(src));
        graph1.getNode(src).setInfo("BeenHere");

        //run until queue will be empty or the dest exist in Blacklist.
        while (Queue.size() != 0 && !BlackList.contains(dest)) {
            //run over first node nei that is in the first in queue.
            for (node_info i : graph1.getV(Queue.peek().getKey())) {
                if (!BlackList.contains(i)) {
                    //init the distance if it's the first time you came to this node.
                    //if there is a new way shorter to this node from src so init the new distance.
                    // and init the SmallestPathFather hashmap
                    if ((Queue.peek().getTag() + graph1.getEdge(Queue.peek().getKey(), i.getKey()) < i.getTag()) || (i.getInfo()== "NeverBeenHere"))
                    {
                        i.setTag(Queue.peek().getTag() + graph1.getEdge(Queue.peek().getKey(), i.getKey()));
                        SmallestPathFather.put(i, Queue.peek());
                        i.setInfo("BeenHere");
                    }
                }
            }

            //pool the shortest distance form the src to the nodes in the queue and init temp this take O(1).
            temp = Queue.poll();
            // run over temp nei.
            for (node_info i : graph1.getV(temp.getKey())) {
                //if this node doesn't exist in Queue or in the black list BlackList put this node to Queue.
                if (!BlackList.contains(i)&&!Queue.contains(i))
                    Queue.add(i);
            }
            //after over all temp nei move him to the BlackList
            BlackList.add(temp);
        }
        if (graph1.getNode(dest).getTag()==0&&graph1.getNode(dest).getInfo()=="NeverBeenHere")
              return -1;

        //if the 2 nodes are diffrent and the dest tag is 0 so there no way from src to dest and return -1

        //else there is a way between the nodes return the distance
        return graph1.getNode(dest).getTag();
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * Note if no such path --> returns null;
     * Note if src or dest doesn't exist -->return null;
     * Note if src equal to dest and they exist in this graph -->return a list with src node in.
     * @param src - start node
     * @param dest - end (target) node
     * @return list of the path.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {

        //make new list.
        List<node_info> list = new ArrayList<>();

        //if the src and dest are equals and they are in the graph return list with src node.
        if (src==dest&&graph1.getV().contains(graph1.getNode(src))) {
            list.add(graph1.getNode(src));
            return list;
        }

        //if src or dest doesn't exist in the graph return null;
        if(!graph1.getV().contains(graph1.getNode(src))||!graph1.getV().contains(graph1.getNode(dest)))
            return null;

        Comparator<node_info> InfoComparator = Comparator.comparingDouble(node_info::getTag);
        Queue<node_info> Queue = new PriorityQueue<>(InfoComparator);

        //init all Tags to 0.
        for (node_info i : graph1.getV()) {
            i.setTag(0);
        }

        //init all nodes info to be "NeverBeenHere".
        for(node_info i:graph1.getV()){
            i.setInfo("NeverBeenHere");
        }

        node_info temp;
        HashMap<node_info, node_info> SmallestPathFather = new HashMap<>();
        HashSet<node_info> BlackList = new HashSet<>();


        //init the first node info to be "BeenHere" and put in  SmallestPathFather hashmap and also in queue.
        SmallestPathFather.put(graph1.getNode(src), graph1.getNode(src));
        Queue.add(graph1.getNode(src));
        graph1.getNode(src).setInfo("BeenHere");

        //run until queue will be empty or the dest exist in Blacklist.
        while (Queue.size() != 0 && !BlackList.contains(dest)) {
            //run over first node nei that is in the first in queue.
            for (node_info i : graph1.getV(Queue.peek().getKey())) {
                if (!BlackList.contains(i)) {
                    //init the distance if it's the first time you came to this node.
                    //if there is a new way shorter to this node from src so init the new distance.
                    if ((Queue.peek().getTag() + graph1.getEdge(Queue.peek().getKey(), i.getKey()) < i.getTag()) || (i.getInfo() == "NeverBeenHere"))
                    {
                        i.setTag(Queue.peek().getTag() + graph1.getEdge(Queue.peek().getKey(), i.getKey()));
                        SmallestPathFather.put(i, Queue.peek());
                        i.setInfo("BeenHere");
                    }
                }
            }
            //pool the shortest distance form the src to the nodes in the queue and init temp this take O(1)
            temp = Queue.poll();

            // run over temp nei.
            for (node_info i : graph1.getV(temp.getKey())) {

                //if this node doesn't exist in Queue or in the black list BlackList put this node to Queue.
                if (!BlackList.contains(i)&&!Queue.contains(i))
                    Queue.add(i);
            }
            BlackList.add(temp);
        }

        shortestPathDist(src,dest);
        Stack<node_info> stack2 = new Stack<>();



        if(graph1.getNode(dest).getInfo()=="NeverBeenHere")
            return null;


        int revers = dest;
        //run from the dest to src with SmallestPathFather hashmap and init all the node in stack.
        while (SmallestPathFather.get(graph1.getNode(revers)).getKey() != src) {
            stack2.push(graph1.getNode(revers));
            revers = SmallestPathFather.get(graph1.getNode(revers)).getKey();
        }

        //if the SmallestPathFather hashmap equal to 1 return list with node src.
        if(SmallestPathFather.size()==1) {
            list.add(graph1.getNode(src));
            return list;
        }

        //move the nodes from the stack to the list this running will organise the list from src to dest.
        stack2.push(graph1.getNode(revers));
        stack2.push(graph1.getNode(src));
       while(stack2.size()!=0){
            list.add(stack2.pop());
        }
        return list;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    public boolean  save(String file) {
        try{
            //make a new FileOutputStream with given file and call him fileOutputStream
            FileOutputStream fileOutputStream =new FileOutputStream(file);
            //make a new ObjectOutputStream with given fileOutputStream and cll him objectOutputStream
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.graph1);
            //close fileOutputStream
            fileOutputStream.close();
            //close objectOutputStream
            objectOutputStream.close();

        }catch (IOException e){
            e.printStackTrace();
            return false;
        }


        return true;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */

    public boolean load(String file) {
        try {
            //make a new FileOutputStream with given file and call him fileOutputStream
            FileInputStream fileInputStream = new FileInputStream(file);
            //make a new ObjectOutputStream with given fileOutputStream and cll him objectOutputStream
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            //init in graph1
            this.graph1 = (weighted_graph) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
}