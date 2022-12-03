import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class Dijkstra {

	// Keep a fast index to nodes in the map
	private Map<String, Vertex> vertexNames;

	/**
	 * Construct an empty Dijkstra with a map. The map's key is the name of a vertex
	 * and the map's value is the vertex object.
	 */
	public Dijkstra() 
	{
		vertexNames = new HashMap<String, Vertex>();
	}

	/**
	 * Adds a vertex to the dijkstra. Throws IllegalArgumentException if two vertices
	 * with the same name are added.
	 * 
	 * @param v
	 *          (Vertex) vertex to be added to the dijkstra
	 */
	public void addVertex(Vertex v) {
		if (vertexNames.containsKey(v.name))
			throw new IllegalArgumentException("Cannot create new vertex with existing name.");
		vertexNames.put(v.name, v);
	}

	/**
	 * Gets a collection of all the vertices in the dijkstra
	 * 
	 * @return (Collection<Vertex>) collection of all the vertices in the dijkstra
	 */
	public Collection<Vertex> getVertices() {
		return vertexNames.values();
	}

	/**
	 * Gets the vertex object with the given name
	 * 
	 * @param name
	 *          (String) name of the vertex object requested
	 * @return (Vertex) vertex object associated with the name
	 */
	public Vertex getVertex(String name) {
		return vertexNames.get(name);
	}

	/**
	 * Adds a directed edge from vertex u to vertex v
	 * 
	 * @param nameU
	 *          (String) name of vertex u
	 * @param nameV
	 *          (String) name of vertex v
	 * @param cost
	 *          (double) cost of the edge between vertex u and v (so like length)
	 */
	public void addEdge(String nameU, String nameV, Double cost) {
		if (!vertexNames.containsKey(nameU))
			throw new IllegalArgumentException(nameU + " does not exist. Cannot create edge.");
		if (!vertexNames.containsKey(nameV))
			throw new IllegalArgumentException(nameV + " does not exist. Cannot create edge.");
		Vertex sourceVertex = vertexNames.get(nameU);
		Vertex targetVertex = vertexNames.get(nameV);
		Edge newEdge = new Edge(sourceVertex, targetVertex, cost);
		//adding it as adjacent to sourceVertex, <-- why not also add edge to adjacent list in targetVertex
		//maybe adjacent list is just what it's pointing at
		sourceVertex.addEdge(newEdge);
	}

	/**
	 * Adds an undirected edge between vertex u and vertex v by adding a directed
	 * edge from u to v, then a directed edge from v to u
	 * 
	 * @param nameU
	 *          (String) name of vertex u
	 * @param nameV
	 *          (String) name of vertex v
	 * @param cost
	 *          (double) cost of the edge between vertex u and v
	 */
	public void addUndirectedEdge(String nameU, String nameV, double cost) { //undirected means 2 directed
		addEdge(nameU, nameV, cost);
		addEdge(nameV, nameU, cost);
	}

	// STUDENT CODE STARTS HERE

	/**
	 * Computes the euclidean distance between two points as described by their
	 * coordinates
	 * 
	 * @param ux
	 *          (double) x coordinate of point u
	 * @param uy
	 *          (double) y coordinate of point u
	 * @param vx
	 *          (double) x coordinate of point v
	 * @param vy
	 *          (double) y coordinate of point v
	 * @return (double) distance between the two points
	 */
	public double computeEuclideanDistance(double ux, double uy, double vx, double vy) {

		//just distance formula between 2 points 
		double distance = Math.sqrt( Math.pow((vx-ux), 2) + Math.pow((vy-uy), 2));

		return distance; 
	}

	/**
	 * Calculates the euclidean distance for all edges in the map using the
	 * computeEuclideanDistance method.
	 */
	public void computeAllEuclideanDistances() 
	{
		//private Map<String, Vertex> vertexNames;

		//iterating through the map
		for(Map.Entry<String, Vertex> entry: vertexNames.entrySet())
		{
			//for each element, get the edge adjacent edge list
			//entry.getValue is the vertex

			//iterate through the adjacency edge list

			List<Edge> edgeList = entry.getValue().adjacentEdges;

			//iterate through adjacent vertexes
			for(int i = 0; i < edgeList.size(); i++)
			{
				//get the edge at the index of the adjacent vertex
				Edge e = edgeList.get(i);
				e.distance = computeEuclideanDistance(e.source.x, e.source.y, e.target.x, e.target.y);
			}
			//System.out.println("Key = " + entry.getKey() + 
			//      ", Value = " + entry.getValue()); 

			//each vertex contains a list of adjacent vertexes (ones it is pointing at)

		} 
	}


	/**
	 * Dijkstra's Algorithm. 
	 * 
	 * @param s
	 *          (String) starting city name
	 */
	public void doDijkstra(String s) 
	{
		//for debugging purposes
		//System.out.println("Entering dijkstra's!");
		//System.out.println("The size of the vertexNames map is " + vertexNames.size());
		
		// psuedo:
		//okay start at starting city (add to visited set)
		//find all vertexes it's pointing to, update all prev to starting city
		//add the vertexes to a linked list
		//then find the minimum vertex in the linked list and "cross it off" / pop it off
		//find all the vertexes that vertex is pointing to, if the distances are less than current distance
		//stored in the vertex, then make the distance the new distance and update prev to that vertex
		//repeat! until no more on linked list 

		//for debugging purposes
		//set all initial vertex distances to infinite
		//System.out.println("Setting Vertex distances to infinite");
		for(Vertex v: vertexNames.values())
		{
			//System.out.println("Vertex setting to infinite is " + v);
			v.distance = Double.POSITIVE_INFINITY;
		}
		//list to store all visited vertexes
		List<Vertex> visited = new LinkedList<Vertex>();

		//list like priorityQueue
		List<Vertex> pending = new LinkedList<Vertex>();

		//	private Map<String, Vertex> vertexNames;

		//this is the starting vertex
		Vertex start = vertexNames.get(s);
		//add start to pending list
		pending.add(start);
		//set starting distance to 0
		start.distance = 0;

		//for debugging purposes
		//System.out.println("starting vertex is " + start);


		//ok now continue while the pending list isn't empty <-- or another loop mechanism?
		//or while the list length is like not the amount of total vertexes (aka in set
		while(!pending.isEmpty() && visited.size() <vertexNames.size()) //&& visited.size() < vertexNames.size())
		{
			//for debugging purposes
			//System.out.println("pending list size is currently  " + pending.size());

			//find smallest in pending list and pop off
			Vertex min = findMin(pending);

			//add to visited list
			visited.add(min);

			//for debugging purposes
			//System.out.println("visited list is currently " + visited);

			//iterate through all the edges in min's adjacent edges
			//find the target vertices
			//calculate distance between source vertex and target (edge ength)
			//and set it as the distance
			//add to min list, set prev of target
			for(Edge e : min.adjacentEdges)
			{
				//target vertex
				Vertex target = e.target;

				//if the distance is currently infinite, then set the distanece to min distance + edge length
				if(target.distance == Double.POSITIVE_INFINITY)
				{
					target.distance = min.distance + e.distance;

					target.prev = e.source;
				}
				//in order to set the distance, compare with previous
				//set as distance if it is smaller
				else if(min.distance + e.distance < target.distance) //if min + edge is less than current vertex distnace 											
				{
					//then set target as new distance
					target.distance = min.distance + e.distance;

					//update prev of target to min
					target.prev = min;
				}

				//if it is not infinite / not smaller, means just leave the length alone

				//add to pending list only if it hasn't been visited yet
				if(!visited.contains(target) && !pending.contains(target))
				{
					//for debugging purposes
					//System.out.println("JUST ADDED " + target + " TO PENDING LIST");

					pending.add(target);
				}

			}
		}

		System.out.println();
		
		System.out.println("Distances of all the cities from " + start + " are: ");
		for(Vertex v: visited)
		{
			//for debugging purposes
			System.out.println(v + " distance is: " + v.distance);
		}
	}

	//method acts like priority queue
	//finds the minimum 
	//should I make this method remove it from the list too?  and return it?
	public Vertex findMin(List<Vertex> l)
	{
		//for debugging purposes
		//System.out.println("list right now is " + l);
		
		//set min distance to first index
		double min = l.get(0).distance;
		//index of min element
		int minIndex = 0;

		//then iterate through list to find minimum
		//start at index 1
		for(int i = 1; i < l.size(); i++)
		{
			if(l.get(i).distance < min)
			{
				min = l.get(i).distance;
				minIndex = i;	
			}		
		}

		//remove vertex from list
		Vertex v = l.remove(minIndex);

		//for debugging purposes
		//System.out.println("Vertex removed is " + v);
		//System.out.println("After removal, pending list is " + l.size());
		
		return v;

	}

	/**
	 * Returns a list of edges for a path from city s to city t. This will be the
	 * shortest path from s to t as prescribed by Dijkstra's algorithm
	 * 
	 * @param s
	 *          (String) starting city name
	 * @param t
	 *          (String) ending city name
	 * @return (List<Edge>) list of edges from s to t
	 */
	public List<Edge> getDijkstraPath(String s, String t) {

		//for debugging purposes
		//System.out.println("Entering get dijkstra path");
		
		//first do dijkstra to set all paths accordingly
		doDijkstra(s);

		List<Edge> path = new LinkedList<Edge>();


		Vertex firstVertex = vertexNames.get(s);

		Vertex endVertex = vertexNames.get(t);


		while(endVertex != firstVertex) 
		{	
			//for debugging purposes
			//System.out.println("End vertex is " + endVertex);
			
			//sees what was previous to the vertex
			Vertex previous = endVertex.prev;
			
			//If the prev field is null and you haven't reached the starting point just return an empty list.
			if(previous == null)
			{
				//create empty path
				List<Edge> emptyPath = new LinkedList<Edge>();
				return emptyPath;
			}

			//for debugging purposes
			//System.out.println("Previous vertex is " + previous);
			
			//now iterate through that previous vertex's edge adjacency list 
			//find the edge that points to the endVertex and add to the path
			for(Edge e: previous.adjacentEdges)
			{
				if(e.target == endVertex )
				{
					//inserts it to the first index (shifts everything to right)
					path.add(0, e);
				}
			}

			//now update end vertex
			endVertex = previous;


		}

		return path;
	}

	// STUDENT CODE ENDS HERE

	/**
	 * Prints out the adjacency list of the dijkstra for debugging
	 */
	public void printAdjacencyList() 
	{
		//for every name of vertex
		for (String u : vertexNames.keySet()) {
			//create stringbuilder
			StringBuilder sb = new StringBuilder();
			//add the vertex to string builder (aka name of city)
			sb.append(u);
			//add pointing to (the ones adjacent to it) 
			sb.append(" -> [ ");
			//print out all the adjacent edges
			for (Edge e : vertexNames.get(u).adjacentEdges) {
				sb.append(e.target.name);
				sb.append("(");
				sb.append(e.distance);
				sb.append(") ");
			}
			sb.append("]");
			System.out.println(sb.toString());
		}
	}


	/** 
	 * A main method that illustrates how the GUI uses Dijkstra.java to 
	 * read a map and represent it as a graph. 
	 * You can modify this method to test your code on the command line. 
	 */
	public static void main(String[] argv) throws IOException {
		String vertexFile = "cityxy.txt"; 
		String edgeFile = "citypairs.txt";

		Dijkstra dijkstra = new Dijkstra();
		String line;

		// Read in the vertices

		BufferedReader vertexFileBr = new BufferedReader(new FileReader(vertexFile));
		while ((line = vertexFileBr.readLine()) != null) {
			String[] parts = line.split(",");
			if (parts.length != 3) {
				vertexFileBr.close();
				throw new IOException("Invalid line in vertex file " + line);
			}
			String cityname = parts[0];
			int x = Integer.valueOf(parts[1]);
			int y = Integer.valueOf(parts[2]);
			Vertex vertex = new Vertex(cityname, x, y);
			dijkstra.addVertex(vertex);
		}
		vertexFileBr.close();

		BufferedReader edgeFileBr = new BufferedReader(new FileReader(edgeFile));
		while ((line = edgeFileBr.readLine()) != null) {
			String[] parts = line.split(",");
			if (parts.length != 3) {
				edgeFileBr.close();
				throw new IOException("Invalid line in edge file " + line);
			}
			dijkstra.addUndirectedEdge(parts[0], parts[1], Double.parseDouble(parts[2]));
		}
		edgeFileBr.close();

		// Compute distances. 
		// This is what happens when you click on the "Compute All Euclidean Distances" button.
		dijkstra.computeAllEuclideanDistances();

		// print out an adjacency list representation of the graph
		dijkstra.printAdjacencyList();

		// This is what happens when you click on the "Draw Dijkstra's Path" button.

		// In the GUI, these are set through the drop-down menus.
		String startCity = "SanFrancisco";
		String endCity = "Boston";

		//Dijkstra hi = new Dijkstra();
		//dijkstra.doDijkstra(startCity);


		// Get weighted shortest path between start and end city. 
		List<Edge> path = dijkstra.getDijkstraPath(startCity, endCity);

		//for debugging purposes
		//print out the distances in the shortest path 
		//also computes total distance
		
		System.out.println();
		
		System.out.println(startCity + " to " + endCity + " path distances: ");
		double total = 0;
		for(Edge e: path)
		{
			total = total + e.distance;
			System.out.println(e + " distance: " +e.distance);
		}
		
		System.out.println();
		
		System.out.println("TOTAL DISTANCE: " + total);
		System.out.println();
		
		System.out.print("Shortest path between "+startCity+" and "+endCity+": ");
		System.out.println(path);
	}
}
