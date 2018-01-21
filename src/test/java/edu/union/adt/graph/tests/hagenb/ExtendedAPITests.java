package edu.union.adt.graph.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import edu.union.adt.graph.*;

@RunWith(JUnit4.class)
public class ExtendedAPITests
{
  private GraphFactory factory;

  private Graph<String> g;
  private Graph<String> g2;

  @Before
  public void setUp()
  {
    factory = new GraphFactory();

    g = factory.createGraph();
    g2 = factory.createGraph();
  }

  @Test
  public void testEmpty()
  {
    assertTrue("A graph with no vertices is empty", g.isEmpty());

    g.addVertex("foo");
    assertFalse("A graph with a vertice is not empty", g.isEmpty());
  }

  @Test
  public void testRemoveVertex()
  {
    g.addVertex("foo");
    g.addVertex("bar");
    g.addVertex("baloney");

    g2.addVertex("foo");
    g2.addVertex("bar");

    g.removeVertex("nope");
    assertTrue("Removing a vertex that doesn't exist does nothing",
                g.equals(g2));

    g.addEdge("foo", "bar");
    g.addEdge("bar", "baloney");

    g.removeVertex("bar");
    assertFalse("Removing a vertex removes the vertex", g.contains("bar"));

    g.assertEquals("Removing a vertex adjusts the number of vertices",
                  g.numVertices(),2);

    g.assertFalse("Removed vertex is removed from other vertices edges",
                  g.hasEdge("foo", "bar"));
    g.assertFalse("Removed vertex's edges are not present",
                  g.hasEdge("bar", "baloney"));
  }

  @Test
  public void testRemoveEdge()
  {
    g.addVertex("foo");
    g.addVertex("bar");
    g.addVertex("baloney");

    g.addEdge("foo", "bar");
    g.addEdge("bar", "baloney");
    g.addEdge("foo","foo");

    g2.addVertex("foo");
    g2.addVertex("bar");
    g2.addVertex("baloney");

    g2.addEdge("foo", "bar");
    g2.addEdge("bar", "baloney");
    g2.addEdge("foo","foo");

    g.removeEdge("bar", "nope");
    g.removeEdge("nope", "foo");
    g.assertTrue("Trying to remove an edge that doesn't exist does nothing",
                g.equals(g2));

    g.removeEdge("foo", "foo");
    g.assertFalse("Removing a self-edge works", g.hasEdge("foo","foo"));

    g.removeEdge("foo", "bar");
    g.assertFalse("Removing an edge between two vertices works",
                  g.hasEdge("foo", "bar"));
  }

  @Test
  public void testHasPath()
  {
    g.addVertex("foo");
    g.addVertex("bar");
    g.addVertex("baloney");
    g.addVertex("ham");

    g.addEdge("foo", "bar");
    g.addEdge("bar", "baloney");
    g.addEdge("baloney", "bar");
    g.addEdge("foo","foo");
    g.addEdge("baloney", "ham");

    g.assertFalse("There is no path if from vertice doesn't exist",
                  g.hasPath("foo", "nope"));
    g.assertFalse("There is no path if to vertice doesn't exist",
                  g.hasPath("nope", "bar"));

    g.assertTrue("There is a path between vertices with an edge",
                g.hasPath("foo", "bar"));
    g.assertTrue("There is a path between vertices separated by one" +
                  " vertice", g.hasPath("foo", "baloney"));
    g.assertTrue("There is a path between vertices separated by two" +
                  " vertices", g.hasPath("foo", "ham"));

    g.assertTrue("There is a path between a self-edge vertice",
                g.hasPath("foo", "foo"));

    g.assertFalse("There is no path between a non-self-edge vertice",
                g.hasPath("bar", "bar"));
  }

  @Test
  public void testPathLength()
  {
    g.addVertex("foo");
    g.addVertex("bar");
    g.addVertex("baloney");
    g.addVertex("ham");

    g.addEdge("foo", "bar");
    g.addEdge("bar", "baloney");
    g.addEdge("baloney", "bar");
    g.addEdge("foo","foo");
    g.addEdge("baloney", "ham");

    g.assertEquals("Path length to the same vertex is 0",
                    g.pathLength("bar", "bar"));

    g.assertEquals("Path length to the same vertex is 0," +
                    " even if it has a self path",
                    g.pathLength("foo", "foo"), 0);

    g.assertEquals("Path length between two vertices is 1",
                    g.pathLength("foo", "bar"), 1);

    g.assertEquals("Path length between four vertices is 3",
                    g.pathLength("foo", "ham"), 3);

    g.addVertex("lonely");
    g.assertEquals("Nonexistent path has length Integer.MAX_VALUE",
                    g.pathLength("foo", "lonely"), Integer.MAX_VALUE);

    g.assertEquals("There is no backwards path through edges",
                    g.pathLength("ham", "baloney"), Integer.MAX_VALUE);

    g.assertEquals("Shortest length is chosen between vertices",
                    g.pathLength("baloney", "bar"), 1);
  }

  @Test
  public void testGetPath()
  {
    
  }



}
