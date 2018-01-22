package edu.union.adt.graph;

/**
* Interface file for the Graph class
*/

public interface Graph<V> {

  int numVertices();
  int numEdges();
  int degree(V vertex);
  void addEdge(V from, V to);
  void addVertex(V vertex);
  Iterable<V> getVertices();
  Iterable<V> adjacentTo(V from);
  boolean contains(V vertex);
  boolean hasEdge(V from, V to);
  String toString();
  boolean equals(Object obj);

  boolean isEmpty();
  void removeVertex(V toRemove);
  void removeEdge(V from, V to);
  boolean hasPath(V from, V to);
  int pathLength(V from, V to);
  Iterable<V> getPath(V from, V to);

}
