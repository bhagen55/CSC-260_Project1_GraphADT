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
    Graph<V> g = new Graph<V>();
    return g;
  }
}
