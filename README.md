# ex1
An implementation of the Undirected (positive) Weighted Graph. 

**Links:**
* To understand shortestPath and shortestPathDist better see https://www.youtube.com/watch?v=pVfj6mxhdMw&t=411s


**class node_info:**
In node_info there are 3 variable key,info and tag.
  
* The `getKey()` method uses to get the node's key.
* The `getInfo()` method uses to get the node's info.
* The `getTag()` method uses to get the node's info.
* The `setInfo(String s)` method used to set info to the node's info.
* The `setTag(double t)` method used to set tag to the node's tag.


**class WGraph_DS:**

*The `getNode(int key)` method used to return node_info by the key.
*The `hasEdge(int node1, int node2)` method return if there is edge between the nodes.
*The `getEdge(int node1, int node2) ` method used to return the weight of the edge between the nodes,if the edge doesn't exist retun -1. 
*The `addNode(int key)` method used to init the new node to the graph,if this node already exist don't do nothing. 
*The `connect(int node1, int node2, double w)` method used to connect beween nodes in the graph with given distance(only if the distance equals or bigger than 0)
*The `getV()` method used to return a collection of all the nodes in the graph. 
*The `getV(int node_id)` method used to return a collection of all the noed_id given neighbors.
*The `removeNode(int key)` method used to remove node from the graph if this node already exist and also remove all the edges that connected to this node.and return the node.if the node doesn't exist return null. 
*The ` removeEdge(int node1, int node2)` method used to remove edge from the graph.
*The `nodeSize()` method used to return the number of nodes in the graph.
*The `edgeSize()` method used to return the number of edges in the graph.
*The `getMC()` method used to return the number of changes in the graph.


**class WGraph_Algo:**

*The `WGraph_Algo()` method used to
*The `WGraph_Algo(weighted_graph algo)` method used to
*The `init(weighted_graph g)` method used to get a weighted_graph and init the graph on which this set of algorithms operates on.
*The `getGraph()` method used to return the underlying graph of which this class works.
*The `copy()` method used to Compute a deep copy of this weighted graph.
*The `isConnected()` method used to return true if there is a valid path from EVREY node to each other node,else false.
*The `shortestPathDist(int src, int dest)` method used to return the distance of the shortest path from src to dest,if there is no way return -1.To understand this code better see https://www.youtube.com/watch?v=pVfj6mxhdMw&t=411s
*The `shortestPath(int src, int dest)` method used to return a list with the shortest way from src to dest,if there is no way return null.To understand this code better see https://www.youtube.com/watch?v=pVfj6mxhdMw&t=411s
*The `save(String file) ` method used to get String file and Saves this weighted graph to the given file name,if this succeed return true else false. 
*The `load(String file)` method used to get String file and load a graph to this graph algorithm,if this succeed return true else false. 
