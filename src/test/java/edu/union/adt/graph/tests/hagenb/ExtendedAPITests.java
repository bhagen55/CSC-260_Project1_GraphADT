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
  }

  @Test
  public void testNotEmpty()
  {
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
    g2.addVertex("baloney");

    g.removeVertex("nope");

    assertTrue("Removing a vertex that doesn't exist does nothing",
                g.equals(g2));

    g.addEdge("foo", "bar");
    g.addEdge("bar", "baloney");

    g.removeVertex("bar");

    assertFalse("Removing a vertex removes the vertex", g.contains("bar"));

    assertEquals("Removing a vertex adjusts the number of vertices",
                  g.numVertices(),2);

    assertFalse("Removed vertex is removed from other vertices edges",
                  g.hasEdge("foo", "bar"));
    assertFalse("Removed vertex's edges are not present",
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
    assertTrue("Trying to remove an edge that doesn't exist does nothing",
                g.equals(g2));

    g.removeEdge("foo", "foo");
    assertFalse("Removing a self-edge works", g.hasEdge("foo","foo"));

    g.removeEdge("foo", "bar");
    assertFalse("Removing an edge between two vertices works",
                  g.hasEdge("foo", "bar"));
  }

  @Test
  public void testHasPathSelf()
  {
    g.addVertex("foo");
    g.addVertex("bar");

    g.addEdge("foo","foo");

    assertTrue("There is a path between a self-edge vertice",
                g.hasPath("foo", "foo"));

    assertTrue("There is a path between a non-self-edge vertice",
                g.hasPath("bar", "bar"));
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

    assertFalse("There is no path if from vertice doesn't exist",
                  g.hasPath("foo", "nope"));
    assertFalse("There is no path if to vertice doesn't exist",
                  g.hasPath("nope", "bar"));

    assertTrue("There is a path between vertices with an edge",
                g.hasPath("foo", "bar"));
    assertTrue("There is a path between vertices separated by one" +
                  " vertice", g.hasPath("foo", "baloney"));
    assertTrue("There is a path between vertices separated by two" +
                  " vertices", g.hasPath("foo", "ham"));
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

    assertEquals("Path length to the same vertex is 0",
                    g.pathLength("bar", "bar"));

    assertEquals("Path length to the same vertex is 0," +
                    " even if it has a self path",
                    g.pathLength("foo", "foo"), 0);

    assertEquals("Path length between two vertices is 1",
                    g.pathLength("foo", "bar"), 1);

    assertEquals("Path length between four vertices is 3",
                    g.pathLength("foo", "ham"), 3);



    assertEquals("There is no backwards path through edges",
                    g.pathLength("ham", "baloney"), Integer.MAX_VALUE);

    assertEquals("Shortest length is chosen between vertices",
                    g.pathLength("baloney", "bar"), 1);
  }

  @Test
  public void testPathLengthNoEdges()
  {
    g.addVertex("lonely");
    assertEquals("Nonexistent path has length Integer.MAX_VALUE",
                    g.pathLength("foo", "lonely"), Integer.MAX_VALUE);
  }

  private boolean iteratorContains(Iterable<String> container, String[] s)
  {
    int i = 0;
      for (String str: container) {
          if (!str.equals(s[i])) {
            return false;
          }
          i++;
      }
      if (i != s.length)
      {
        return false;
      }
      return true;
  }

  private int iteratorLength(Iterable<String> container)
  {
    int length = 0;

    for (String str: container) {
      length++;
    }

    return length;
  }

  @Test
  public void testGetPath()
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

    g.addVertex("lonely");

    String[] testStr1 = {"lonely", "lonely"};

    assertTrue("Path from and to the same vertex is itself to itself",
                  iteratorContains(g.getPath("lonely", "lonely"), testStr1));

    g.addEdge("lonely", "lonely");

    assertTrue("Path from and to the same vertex is itself to itself" +
                "even if there is a self path",
                iteratorContains(g.getPath("lonely", "lonely"), testStr1));



    String[] testStr2 = {"foo", "bar"};
    assertTrue("Path between two vertices works",
                  iteratorContains(g.getPath("foo", "bar"), testStr2));

    assertEquals("Path between vertices connected in one direction but not" +
                  " the other direction is empty when checked",
                  iteratorLength(g.getPath("ham", "baloney")), 0);

    String[] testStr3 = {"foo", "bar", "baloney"};
    assertTrue("Path bteween three vertices works",
                  iteratorContains(g.getPath("foo", "baloney"), testStr3));

    g.addEdge("foo", "baloney");
    String[] testStr4 = {"foo", "baloney"};
    assertTrue("Path is shortest option if multiple routes exist",
                iteratorContains(g.getPath("foo", "baloney"), testStr4));

  }

  @Test
  public void testGetPathEmpty()
  {
    g.addVertex("foo");
    g.addVertex("lonely");

    assertEquals("Path to and from a vertex that doesn't exist is empty",
                  iteratorLength(g.getPath("nope", "also nope")), 0);

    assertEquals("Path between two vertices that aren't connected is empty",
                  iteratorLength(g.getPath("foo", "lonely")), 0);
  }
}
