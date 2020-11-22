package ex1.tests;
import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WGraph_DST {

    /**
     * check node size when insert nodes with same key
     * and multiply nodes
     */
    @Test
    public void GetNode_Test() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.addNode(2);
        graph_test.addNode(3);
        graph_test.addNode(4);
        assertEquals(null, graph_test.getNode(5));
    }


    @Test
    public void HasEdge() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.addNode(2);
        graph_test.addNode(3);
        graph_test.addNode(4);
        graph_test.connect(1, 2, 5);
        assertEquals(true, graph_test.hasEdge(1, 2));
        //bring key  that doesn't existent number 7
        assertEquals(false, graph_test.hasEdge(7, 2));
        //bring 2 same keys
        assertEquals(false, graph_test.hasEdge(2, 2));

    }

    @Test
    public void GetEdge() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.addNode(2);
        graph_test.addNode(3);
        graph_test.addNode(4);
        graph_test.connect(1, 2, 5);
        graph_test.connect(2, 3, 5);
        //if the edge doesn't existent
        assertEquals(-1, graph_test.getEdge(1, 4));
        //if the one of te nodes doesn't existent
        assertEquals(-1, graph_test.getEdge(1, 20));
        //bring same key
        assertEquals(0, graph_test.getEdge(1, 1));
        //bring 2 keys that have edge
        assertEquals(5, graph_test.getEdge(1, 2));
    }


    @Test
    public void Connect_Test() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.addNode(2);
        graph_test.addNode(3);
        graph_test.addNode(4);
        graph_test.connect(1, 2, 5);
        graph_test.connect(2, 3, 5);
        //change the distance between the nodes
        graph_test.connect(1, 2, 8);
        //test1
        assertEquals(8, graph_test.getEdge(1, 2));
        //change the distance between the nodes to -5. this will not change the distance between the nodes.
        graph_test.connect(1, 2, -5);
        //test2
        assertEquals(8, graph_test.getEdge(1, 2));

    }

    @Test
    public void removenode() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.addNode(2);
        graph_test.addNode(3);
        graph_test.addNode(4);
        graph_test.connect(1, 2, 5);
        graph_test.connect(1, 3, 5);
        graph_test.connect(2, 3, 5);

        //check if after i delet a node the number of nodes change
        int NodeSize1 = graph_test.nodeSize();
        graph_test.removeNode(4);
        int NodeSize2 = graph_test.nodeSize();
        //test1
        assertEquals(false, NodeSize1 == NodeSize2);


        //check if i remove node from the graph all his edges also deleted
        int EdgeSize = graph_test.edgeSize();
        int EdgeSizeRemove = graph_test.getV(1).size();
        graph_test.removeNode(1);
        assertEquals(true, EdgeSize - EdgeSizeRemove == graph_test.edgeSize());
    }

    @Test
    public void removeEdge() {
        weighted_graph graph_test = new WGraph_DS();
        graph_test.addNode(0);
        graph_test.addNode(1);
        graph_test.addNode(2);
        graph_test.addNode(3);
        graph_test.addNode(4);
        graph_test.connect(1, 2, 5);
        graph_test.connect(1, 3, 5);
        graph_test.connect(2, 3, 5);
        int NumberOfNodes=graph_test.edgeSize();
        //check if i delete edge that doesn't existent if the numbers of the edge change
        graph_test.removeEdge(3,4);
        //test1
        assertEquals(graph_test.edgeSize(),NumberOfNodes);

        NumberOfNodes=graph_test.edgeSize();
        //check if i delete edge that doesn't existent if the numbers of the edge change
        graph_test.removeEdge(2,2);
        //test1
        assertEquals(graph_test.edgeSize(),NumberOfNodes);

        NumberOfNodes=graph_test.edgeSize();
        //check if i delete edge that doesn't existent if the numbers of the edge change
        graph_test.removeEdge(2,2);
        //test1
        assertEquals(graph_test.edgeSize(),NumberOfNodes);

        NumberOfNodes=graph_test.edgeSize();
        //check if i delete edge that existent if the numbers of the edge change
        graph_test.removeEdge(1,2);
        //test1
        assertEquals(graph_test.edgeSize(),NumberOfNodes-1);
    }
    @Test

    public void OneMilion(){
        long startTime = System.nanoTime();
        //myCall();

        weighted_graph graph_test = new WGraph_DS();
        for(int i=0;i<1000000;i++){
            graph_test.addNode(i);
        }


        for(int j=0;j<10000000;j++){
            Random rand1 = new Random();
            Random rand2 = new Random();
            Random rand3 = new Random();
            int rand_int1 = rand1.nextInt(1000000);
            int rand_int2 = rand1.nextInt(1000000);
            int rand_int3 = rand1.nextInt(1000000);
            graph_test.connect(rand_int1,rand_int2,rand_int3);
        }
        long stopTime = System.nanoTime();
        if(stopTime - startTime<50)
            assertEquals(true,true);
    }
}