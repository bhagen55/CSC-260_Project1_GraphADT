package edu.union.adt.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Map;
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.Collections;

/**
 * A graph that establishes connections (edges) between objects of
 * (parameterized) type V (vertices).  The edges are directed.  An
 * undirected edge between u and v can be simulated by two edges: (u,
 * v) and (v, u).
 *
 * The API is based on one from
 *     http://introcs.cs.princeton.edu/java/home/
 *
 * Some method names have been changed, and the Graph type is
 * parameterized with a vertex type V instead of assuming String
 * vertices.
 *
 * @author Aaron G. Cass
 * @version 1
 *
 *
 */
public class GraphImpl<V> implements Graph<V>
{
    // Holds the vertices of generic type
    private ArrayList<V> vertices;

    /**
     * Holds a record of which objects are connected to which using
     * their index in the 'objects' LinkedList
     */
    private ArrayList<ArrayList> edges;

    /**
     * Create an empty graph.
     */
    public GraphImpl()
    {
      vertices = new ArrayList<V>();
      edges = new ArrayList<ArrayList>();
    }

    /**
    * Gives the number of vertices in the graph.
    *
    * @return the number of vertices in the graph.
    */
    public int numVertices()
    {
      int verticesSize = vertices.size();
      int edgesSize = edges.size();
      if (verticesSize != edgesSize) {
        throw new RuntimeException("Vertices has size " + verticesSize + " while Edges has size " + edgesSize);
      }
      else {
        return verticesSize;
      }
    }

    /**
    * Gives the number of edges in the graph.
    *
    * @return the number of edges in the graph.
    * Iterates through edge arraylist and counts the number of elements in each
    * arraylist contained in the edge arraylist
    */
    public int numEdges()
    {
      int totalEdges = 0;
      for (ArrayList edge: edges) {
        totalEdges = totalEdges + edge.size();
      }
      return totalEdges;
    }

    /**
     * Gets the number of vertices connected by edges from a given
     * vertex.  If the given vertex is not in the graph, throws a
     * RuntimeException.
     *
     * @param vertex the vertex whose degree we want.
     * @return the degree of vertex 'vertex'
     */
    public int degree(V vertex)
    {
      // Check the vertex list for the object. Will return -1 if it does not exist
      int objectIndex = vertices.indexOf(vertex);
      if (objectIndex == -1) {
        throw new RuntimeException("Vertex does not exist");
      }
      else {
        // Finds the vertex's edges in the edges list and returns the size of it
        return (edges.get(objectIndex)).size();
      }
    }

    /**
     * Adds a directed edge between two vertices.  If there is already an edge
     * between the given vertices, does nothing.  If either (or both)
     * of the given vertices does not exist, it is added to the
     * graph before the edge is created between them.
     *
     * @param from the source vertex for the added edge
     * @param to the destination vertex for the added edge
     */
    public void addEdge(V from, V to)
    {
      // Check if the from and to vertex exist. If not, add them.
      if (!vertices.contains(from)) {
        addVertex(from);
      }
      if (!vertices.contains(to)) {
        addVertex(to);
      }

      // Find verteces
      int fromIndex = vertices.indexOf(from);
      int toIndex = vertices.indexOf(to);

      // Get edge array for from Vertex
      ArrayList<Integer> e = edges.get(fromIndex);
      // Add to vertex index
      e.add(toIndex);
    }

    /**
     * Adds a vertex to the graph.  If the vertex already exists in
     * the graph, does nothing.  If the vertex does not exist, it is
     * added to the graph, with no edges connected to it.
     *
     * @param vertex the vertex to add
     */
    public void addVertex(V vertex)
    {
      // Only attempt to add the object if it doesn't exist
      if (!vertices.contains(vertex)) {
        vertices.add(vertex);
        // Add a blank arraylist to the edges arraylist to hold this vertece's edges
        ArrayList<Integer> e = new ArrayList<Integer>();
        edges.add(e);
      }
    }

    /**
    * Gets all vertices in the graph. They are returned in the same order they
    * were added to the graph.
    *
    * @return iterable - an iterable collection for the set of vertices of
    * the graph.
    */
    public Iterable<V> getVertices()
    {
      Iterable<V> iterable = vertices;
      return iterable;
    }

    /**
     * Gets the vertices adjacent to a given vertex.  A vertex y is
     * "adjacent to" vertex x if there is an edge (x, y) in the graph.
     * Because edges are directed, if (x, y) is an edge but (y, x) is
     * not an edge, we would say that y is adjacent to x but that x is
     * NOT adjacent to y.
     *
     * @param from the source vertex
     * @return iterable - an iterable collection for the set of vertices that are
     * the destinations of edges for which 'from' is the source
     * vertex.  If 'from' is not a vertex in the graph, returns an
     * empty iterator.
     */
    public Iterable<V> adjacentTo(V from)
    {
      int fromIndex = vertices.indexOf(from);

      ArrayList<Integer> e = edges.get(fromIndex);

      ArrayList<V> adjacentVertices = new ArrayList<V>();

      for (int vertexIndex: e) {
        adjacentVertices.add(vertices.get(vertexIndex));
      }

      Iterable<V> iterable = adjacentVertices;

      return iterable;
    }

    /**
     * Tells whether or not a vertex is in the graph.
     *
     * @param vertex a vertex
     * @return true iff 'vertex' is a vertex in the graph.
     */
    public boolean contains(V vertex)
    {
      return vertices.contains(vertex);
    }

    /**
     * Tells whether an edge exists in the graph connecting two vertices.
     *
     * @param from the source vertex
     * @param to the destination vertex
     *
     * @return true iff there is an edge from the source vertex to the
     * destination vertex in the graph.  If either of the given
     * vertices are not vertices in the graph, then there is no edge
     * between them.
     */
    public boolean hasEdge(V from, V to)
    {
      // Get index of from vertex
      int fromIndex = vertices.indexOf(from);
      // Get index of to vertex
      int toIndex = vertices.indexOf(to);

      if (fromIndex != -1)
      {
        // Find from vertice edge ArrayList
        ArrayList<Integer> fromEdges = edges.get(fromIndex);

        if (!fromEdges.contains(toIndex))
        {
          return false;
        }
        else
        {
          return true;
        }
      }
      else
      {
        return false;
      }
    }

    /**
     * Gives a string representation of the graph.  The representation
     * is a series of lines, one for each vertex in the graph.  On
     * each line, the vertex is shown followed by ": " and then
     * followed by a list of the vertices adjacent to that vertex.  In
     * this list of vertices, the vertices are separated by ", ".  For
     * example, for a graph with String vertices "A", "B", and "C", we
     * might have the following string representation:
     *
     * <PRE>
     * A: A, B
     * B:
     * C: A, B
     * </PRE>
     *
     * This representation would indicate that the following edges are
     * in the graph: (A, A), (A, B), (C, A), (C, B) and that B has no
     * adjacent vertices.
     *
     * Note: there are no extraneous spaces in the output.  So, if we
     * replace each space with '*', the above representation would be:
     *
     * <PRE>
     * A:*A,*B
     * B:
     * C:*A,*B
     * </PRE>
     *
     * @return the string representation of the graph
     */
    public String toString()
    {
      // String to store as we go
      StringBuffer str = new StringBuffer();

      int index = 0;
      // Iterate through each vertex in the graph
      for (V vertex: vertices) {
        str.append(vertex.toString() + ":");
        // Find the list of edges for this vertex
        ArrayList<Integer> e = edges.get(index);
        // Add the verteces that this vertex is connected to
        int edgeIndex = 0;
        for (int edge: e) {
          str.append(" " + vertices.get(edge).toString());
          if (edgeIndex < e.size()-1) {
            str.append(",");
          }
          edgeIndex++;
        }
        str.append("\n");
        index++;
      }
      System.out.println(str.toString());
      return str.toString();
    }

    /**
    * Checks if a given object is equal to self.
    *
    * Does not check for order of addition to the graph or any other
    * type of ordering within the graph.
    *
    * @param obj the graph to compare this graph to
    *
    * @return true iff the graphs are equal
    */
    public boolean equals(Object obj) {

      // Check if object is of type Graph
      if (!(obj instanceof Graph)) {
        return false;
      }

      // Since object is of type Graph, cast it as such
      Graph otherGraph = (Graph) obj;

      Iterable<V> otherVertices = otherGraph.getVertices();

      // Check if both graphs have the same vertices
      for (V vertice : otherVertices) {
        if (contains(vertice)) {
          // Check if the edges are the same
          Iterable<V> otherEdges = otherGraph.adjacentTo(vertice);
          for (V edge : otherEdges) {
            if (!contains(edge)) {
              return false;
            }
          }
        }
        else {
          return false;
        }
      }
      return true;
    }

    /**
     * Tells whether the graph is empty.
     *
     * @return true iff the graph is empty. A graph is empty if it has
     * no vertices and no edges.
     */
    public boolean isEmpty()
    {
      if (vertices.size() == 0)
      {
        if (edges.size() != 0)
        {
          throw new RuntimeException("Graph has edges but no vertices");
        }
      }
      else
      {
        return false;
      }
      return true;
    }

    /**
     * Removes and vertex from the graph.  Also removes any edges
     * connecting from the edge or to the edge.
     *
     * <p>Postconditions:
     *
     * <p>If toRemove was in the graph:
     * <ul>
     * <li>numVertices = numVertices' - 1
     * <li>toRemove is no longer a vertex in the graph
     * <li>for all vertices v: toRemove is not in adjacentTo(v)
     * </ul>
     *
     * @param toRemove the vertex to remove.
     */
    public void removeVertex(V toRemove)
    {
      int removeIndex = vertices.indexOf(toRemove);

      // Only attempt to remove if the index actually exists
      if (removeIndex != -1)
      {
        // Delete the edges coming from the vertex to remove
        edges.remove(removeIndex);

        // Delete the edges going to the vertex to remove
        for (ArrayList<Integer> edge : edges)
        {
          edge.remove(new Integer(removeIndex));
        }

        // Decrement the edge indexes of vertices at greater than the removed index
        for (ArrayList<Integer> edge : edges)
        {
          for (int index : edge)
          {
            if (index > removeIndex)
            {
              edge.set(index, index - 1);
            }
          }
        }
        // Remove the index after all its remnants are gone
        vertices.remove(removeIndex);
      }
    }

    /**
     * Removes an edge from the graph.
     *
     * <p>Postcondition: If from and to were in the graph and (from,
     * to) was an edge in the graph, then numEdges = numEdges' - 1
     */
    public void removeEdge(V from, V to)
    {
      // Get index of from vertex
      int fromIndex = vertices.indexOf(from);
      // Get index of to vertex
      int toIndex = vertices.indexOf(to);

      if (fromIndex != -1)
      {
        // Find from vertice edge ArrayList
        ArrayList<Integer> fromEdges = edges.get(fromIndex);

        // Remove the to vertex index if it exists
        fromEdges.remove(new Integer(toIndex));
      }
    }

    /**
     * Tells whether there is a path connecting two given vertices.  A
     * path exists from vertex A to vertex B iff there exists a
     * sequence x_1, x_2, ..., x_n where:
     *
     * <ul>
     * <li>x_1 = A
     * <li>x_n = B
     * <li>for all i from 1 to n-1, (x_i, x_{i+1}) is an edge in the graph.
     * </ul>
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return true iff there is a path from 'from' to 'to' in the graph.
     */
    public boolean hasPath(V from, V to)
    {
      Iterable<V> path = getPath(from, to);

      return iteratorLength(path);

    }

    /**
     * Gets the length of the shortest path connecting two given
     * vertices.  The length of a path is the number of edges in the
     * path.
     *
     * <ol>
     * <li>If from = to, shortest path has length 0
     * <li>Otherwise, shortest path length is length of the shortest
     * possible path connecting from to to.
     * </ol>
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return the length of the shortest path from 'from' to 'to' in
     * the graph.  If there is no path, returns Integer.MAX_VALUE
     */
    public int pathLength(V from, V to)
    {
      return 0;
    }

    /**
     * Returns the vertices along the shortest path connecting two
     * given vertices.  The vertices should be given in the order x_1,
     * x_2, x_3, ..., x_n, where:
     *
     * <ol>
     * <li>x_1 = from
     * <li>x_n = to
     * <li>for all i from 1 to n-1: (x_i, x_{i+1}) is an edge in the graph.
     * </ol>
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return an Iterable collection of vertices along the shortest
     * path from 'from' to 'to'.  The Iterable should include the
     * source and destination vertices.
     */
    public Iterable<V> getPath(V from, V to)
    {
      // Arraylist to hold the shortest path and be converted to an interable
      ArrayList<V> shortList = new ArrayList<V>();


      System.out.println("from: " + from.toString());
      System.out.println("to: " + to.toString());


      // If we are looking from a path to and from the same vertice, save some
      // work and just add the to and from vertices directly to the short list
      if (!contains(from) || !contains(to))
      {
        Iterable<V> iterable = shortList;
        return iterable;
      }
      if (from.equals(to))
      {
        shortList.add(to);
        shortList.add(from);
      }
      // If either of the vertices doesn't exist, don't bother doing a search
      else
      {
        // Queue to store yet-to-be-visited vertices
        Queue<V> queue = new LinkedList<V>();

        // Store vertices that have been visited
        ArrayList<V> visited = new ArrayList<V>();

        // Store a mapping of parent/child verticesSize
        Map<V, V> neighborMap = new TreeMap<V, V>();

        // Add the main node to the queue and mark it as visited
        queue.add(from);
        visited.add(from);

        while (!queue.isEmpty())
        {
          // Get the element from the top of the Queue
          V element = queue.poll();

          // If we have found the node, end the while loop
          if (element.equals(to))
          {
            break;
          }

          // Traverse the element's adjacent vertices
          for (V adjVert : adjacentTo(element))
          {
            // Check if the vertex has been visited already
            if (!visited.contains(adjVert))
            {
              // Mark as visited
              visited.add(adjVert);
              // Add to Queue
              queue.add(adjVert);

              // Add the parent/child pair to the mapping
              neighborMap.put(adjVert, element);
              System.out.println(neighborMap.toString());
            }
          }
        }

        if (!neighborMap.isEmpty())
        {
          V vertex = to;
          while (neighborMap.get(vertex) != null)
          {
            shortList.add(vertex);
            vertex = neighborMap.get(vertex);
          }
          shortList.add(from);

          Collections.reverse(shortList);
        }
      }

      System.out.println(shortList.toString());
      Iterable<V> iterable = shortList;
      return iterable;
    }

    /**
     * Returns the length of a interator by counting its elements
     *
     * @param container the iterable to be counted
     * @return an integer representing the length of the iterable
     */
    private int iteratorLength(Iterable<V> container)
    {
      int length = 0;

      for (V element: container)
      {
      length++;
      }

      return length;
    }
}
