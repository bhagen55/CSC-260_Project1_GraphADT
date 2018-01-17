package edu.union.adt.graph;

/**
* Simple class to use the new Graph interface
*/

public class GraphFactory<V>
{


  public GraphFactory()
  {

  }

  public Graph<V> createGraph()
  {
    GraphImpl<V> g = new GraphImpl<V>();
    return g;
  }
}
