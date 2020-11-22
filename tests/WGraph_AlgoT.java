package ex1.tests;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import ex1.src.*;
import org.junit.jupiter.api.Test;


public class WGraph_AlgoT {
    @Test
     void isConnected() {
        weighted_graph graph_test = new WGraph_DS();
        weighted_graph_algorithms graph_algo_test = new WGraph_Algo();
        graph_algo_test.init(graph_test);
        graph_test.addNode(0);
        //only 1 node in the graph
        assertEquals(true, graph_algo_test.isConnected());
        graph_test.addNode(1);
        graph_test.addNode(2);
        graph_test.addNode(3);
        graph_test.addNode(4);
        graph_test.connect(0,1,5);
        graph_test.connect(1,2,5);
        graph_test.connect(2,3,5);
        //node 4 didn't connect
        assertEquals(false, graph_algo_test.isConnected());
        graph_test.connect(2,4,5);
        //node 4 connect
        assertEquals(true, graph_algo_test.isConnected());

    }
    @Test
     void Copy(){
        weighted_graph graph_test = new WGraph_DS();
        weighted_graph_algorithms graph_algo_test = new WGraph_Algo();
       graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.addNode(2);
        graph_test.addNode(3);
        graph_test.addNode(4);
        graph_test.connect(0,1,5);
        graph_test.connect(1,2,5);
        graph_test.connect(2,3,5);
        graph_algo_test.init(graph_test);
        weighted_graph copy_graph = new WGraph_DS();
        //copy graph algo test
        copy_graph = graph_algo_test.copy();
        //change graph_test after the copy to see if it is deep copy
        graph_test.addNode(7);

       // assertEquals(false,copy_graph ,graph_test);
        assertNotEquals(copy_graph ,graph_test);

        graph_test.removeNode(7);
        //after the remove they are equals
        assertEquals(copy_graph ,graph_test);
    }
    @Test
    void shortestPathDist(){
       weighted_graph graph_test = new WGraph_DS();
       weighted_graph_algorithms graph_algo_test = new WGraph_Algo();
       graph_algo_test.init(graph_test);
       graph_test.addNode(0);
       //the disstance from node to himself  is 0
       assertEquals(0,graph_algo_test.shortestPathDist(0,0));
       graph_test.addNode(1);
       graph_test.addNode(2);
       graph_test.addNode(3);
       graph_test.addNode(4);
       graph_test.connect(0,1,5);
       graph_test.connect(1,2,5);
       graph_test.connect(2,3,5);
       //the disstance is 15
       assertEquals(15,graph_algo_test.shortestPathDist(0,3));
       //no way from 0 to 4
       assertEquals(-1,graph_algo_test.shortestPathDist(0,4));
       //change distance
         graph_test.connect(0,1,1);
       assertEquals(11,graph_algo_test.shortestPathDist(0,3));

        //change distance to error
       graph_test.connect(0,1,-2);
       assertEquals(11,graph_algo_test.shortestPathDist(0,3));

    }
    @Test
    void shortestPath(){

        List<node_info> list = new ArrayList<>();
        List<node_info> list2= new ArrayList<>();
        int [] array = {0};
        weighted_graph graph_test = new WGraph_DS();
        weighted_graph_algorithms graph_algo_test = new WGraph_Algo();
        graph_algo_test.init(graph_test);
        graph_test.addNode(0);
        //check the shortestPath list from node to himself
        list2=graph_algo_test.shortestPath(0,0);
        while(!list2.isEmpty()) {
            assertEquals(0, list2.get(0).getKey());
            list2.remove(0);
        }
        graph_test.addNode(1);
        graph_test.addNode(2);
        graph_test.addNode(3);
        graph_test.addNode(4);
        graph_test.connect(0,1,5);
        graph_test.connect(1,2,5);
        graph_test.connect(2,3,5);

        int []arr = {0,1,2,3};
        int i=0;
        list=graph_algo_test.shortestPath(0,3);
        //check if the numbers of nodes in the arr are same in the list
        while(!list.isEmpty()){
            assertEquals(arr[i],list.get(0).getKey());
            list.remove(0);
            i++;
        }
        assertEquals(0,list.size());
    }
}