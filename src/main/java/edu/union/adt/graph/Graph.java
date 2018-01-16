/**
* Interface file for the Graph class
*/

public interface Graph<V> {

  int numVertices();
  int numEdges();
  int degree();
  void addEdge();
  void addVertex();
  Iterable<V> getVertices();
  Iterable<V> adjacentTo();
  boolean contains();
  boolean hasEdge();
  String toString();
  boolean equals();

}
