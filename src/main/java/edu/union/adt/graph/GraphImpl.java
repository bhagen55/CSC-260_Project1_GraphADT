package edu.union.adt.graph;

import java.util.ArrayList;
import java.util.Iterator;

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
 * Assumptions/Limitations:
 *  Will have issues with adding the same object, ie adding two "a" vertices
 *
 */
public class Graph<V> implements GraphFactory
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
    public Graph()
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
      // Get index of from vertices
      int fromIndex = vertices.indexOf(from);
      // Get index of to vertice
      int toIndex = vertices.indexOf(to);

      // Find from vertice edge ArrayList
      ArrayList<Integer> fromEdges = edges.get(fromIndex);

      if (fromEdges.indexOf(toIndex) == -1) {
        return false;
      }
      else {
        return true;
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

}