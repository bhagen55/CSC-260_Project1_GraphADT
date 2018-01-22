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

import java.util.ArrayList;

@RunWith(JUnit4.class)
public class AdditionalTests
{
  private GraphFactory factory;

  private Graph<String> g;
  private Graph<String> g2;

  private Graph<int[]> g3;
  private Graph<int[]> g4;

  private Graph<Integer> g5;

  private Graph<Object> objectGraph;

  private String str;

  private ArrayList<String> arraylist;

  private int[] a1;
  private int[] a2;

  @Before
  public void setUp()
  {
    factory = new GraphFactory();
    g = factory.createGraph();
    g2 = factory.createGraph();

    g3 = factory.createGraph();
    g4 = factory.createGraph();

    g5 = factory.createGraph();


    str = "string";
    arraylist = new ArrayList<String>();

    a1 = new int[2];
    a2 = new int[2];
  }

  @Test
  public void equalsObjectMismatch()
  {
    assertFalse("An empty graph is not equal to a string", g.equals(str));

    g.addVertex("Foo");
    arraylist.add(str);
    assertFalse("A graph is not equal to a string", g.equals(str));
    assertFalse("A graph is not equal to an arrayList", g.equals(arraylist));
  }

  @Test
  public void addingIdentical()
  {
    g.addVertex("A");
    g.addVertex("A");
    g2.addVertex("A");

    assertEquals("Attempting to add two identical vertices should not add the"
                + " second",
                g, g2);
  }

  @Test
  public void typesInArray()
  {
    a1[0] = 100;
    a1[1] = 200;
    g3.addVertex(a1);

    a2[0] = 200;
    a2[1] = 100;
    g4.addVertex(a2);

    assertFalse("Adding two vertices with same type but differing data should"
                + " not equal each other",
                g3.equals(g4));
  }

  @Test
  public void largeVertices()
  {
    int[] iArr = new int[100];

    for (int i = 0; i < 100; i++) {
      g5.addVertex(i);
      iArr[i] = i;
    }
    assertTrue("Adding 100+ vertices behaves correctly", iteratorContains(g5.getVertices(), iArr));
  }

  private boolean iteratorContains(Iterable<Integer> container, int[] x)
  {
    int index = 0;
      for (int i: container) {
          if (i != x[index]) {
            return false;
          }
          index++;
      }
      if (index != x.length)
      {
        return false;
      }
      return true;
  }

  @Test
  public void edgeSelf()
  {
    g.addVertex("Foo");
    g.addEdge("Foo", "Foo");

    assertTrue("Adding an edge to and from the same vertice behaves correctly",
              g.hasEdge("Foo", "Foo"));
    assertEquals("Adding an edge to and from the same vertice increments the" +
              " degree properly",
              g.degree("Foo"), 1);
  }

  @Test(expected=RuntimeException.class)
  public void degreeFail()
  {
    g.addVertex("Foo");
    g.addVertex("Bar");

    int result = g.degree("Nope");
  }

}
