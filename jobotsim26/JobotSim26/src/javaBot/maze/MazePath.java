/*
 * Created on Jun 8, 2004
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * Used to signal violations of preconditions for
 * various shortest path algorithms.
 */
package javaBot.maze;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javaBot.Debug;
import weiss.nonstandard.BinaryHeap;
import weiss.nonstandard.PairingHeap;
import weiss.nonstandard.PriorityQueue;

/**
 * DOCUMENT ME!
 *
 * @version $Revision: 1.1 $ last changed Feb 17, 2006
 */
class GraphException extends RuntimeException
{
	/**
	 * Constructor
	 *
	 * @param name
	 */
	public GraphException(String name)
	{
		super(name);
	}
}


/**
 * Represents an edge in the graph.
 */
class Edge
{
	private Vertex	dest;	// Second vertex in Edge
	private double	cost;	// Edge cost

	/**
	 * DOCUMENT ME!
	 *
	 * @param d Second vertex in Edge
	 * @param c Edge cost
	 */
	public Edge(Vertex d, double c)
	{
		dest = d;
		cost = c;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the cost.
	 */
	public double getCost()
	{
		return cost;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the dest.
	 */
	public Vertex getDest()
	{
		return dest;
	}
}


/**
 * Represents an entry in the priority queue for Dijkstra's algorithm.
 */
class Path implements Comparable
{
	private Vertex	dest;	// w
	private double	cost;	// d(w)

	/**
	 * Constructor
	 *
	 * @param d
	 * @param c
	 */
	public Path(Vertex d, double c)
	{
		dest = d;
		cost = c;
	}

	/**
	 * compareTo
	 *
	 * @param rhs
	 *
	 * @return -1 or 0
	 */
	public int compareTo(Object rhs)
	{
		double otherCost = ((Path) rhs).cost;

		return (cost < otherCost) ? (-1) : ((cost > otherCost) ? 1 : 0);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the dest.
	 */
	public Vertex getDest()
	{
		return dest;
	}
}


/**
 * Represents a vertex in the graph.
 */
class Vertex
{
	private String					name;		// Vertex name
	private List					adj;		// Adjacent vertices
	private double					dist;		// Cost
	private Vertex					prev;		// Previous vertex on shortest path
	private int						scratch;	// Extra variable used in algorithm
	private PriorityQueue.Position	pos;		// Used for dijkstra2 (Chapter 23)

	/**
	 * Creates a new Vertex object.
	 *
	 * @param nm TODO PARAM: DOCUMENT ME!
	 */
	public Vertex(String nm)
	{
		name = nm;
		adj = new LinkedList();
		reset();
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void reset()
	{
		dist = MazePath.INFINITY;
		prev = null;
		pos = null;
		scratch = 0;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the adj.
	 */
	public List getAdj()
	{
		return adj;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the dist.
	 */
	public double getDist()
	{
		return dist;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the pos.
	 */
	public PriorityQueue.Position getPos()
	{
		return pos;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the prev.
	 */
	public Vertex getPrev()
	{
		return prev;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the scratch.
	 */
	public int getScratch()
	{
		return scratch;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param dist The dist to set.
	 */
	public void setDist(double dist)
	{
		this.dist = dist;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param prev The prev to set.
	 */
	public void setPrev(Vertex prev)
	{
		this.prev = prev;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param adj The adj to set.
	 */
	public void setAdj(List adj)
	{
		this.adj = adj;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param pos The pos to set.
	 */
	public void setPos(PriorityQueue.Position pos)
	{
		this.pos = pos;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param scratch The scratch to set.
	 */
	public void setScratch(int scratch)
	{
		this.scratch = scratch;
	}
}


/**
 * Contains all the methods and classes to calculate a way out of a maze using
 * different algorithms.
 */
public class MazePath
{
	public static final double	INFINITY	= Double.MAX_VALUE;
	private Map					vertexMap	= new HashMap();	// Maps String to Vertex

	/**
	 * Add a new edge to the graph.
	 *
	 * @param sourceName TODO PARAM: DOCUMENT ME!
	 * @param destName TODO PARAM: DOCUMENT ME!
	 * @param cost TODO PARAM: DOCUMENT ME!
	 */
	public void addEdge(String sourceName, String destName, double cost)
	{
		Vertex v = getVertex(sourceName);
		Vertex w = getVertex(destName);
		v.getAdj().add(new Edge(w, cost));
	}

	/**
	 * Driver routine to handle unreachables and print total cost. It calls
	 * recursive routine to print shortest path to destNode after a shortest
	 * path algorithm has run.
	 *
	 * @param destName TODO PARAM: DOCUMENT ME!
	 *
	 * @throws NoSuchElementException TODO THROWS: DOCUMENT ME!
	 */
	public void printPath(String destName)
	{
		Vertex w = (Vertex) vertexMap.get(destName);

		if (w == null)
		{
			throw new NoSuchElementException("Destination vertex not found");
		}
		else if (w.getDist() == INFINITY)
		{
			Debug.printInfo(destName + " is unreachable");
		}
		else
		{
			Debug.printInfo("(Distance is: " + w.getDist() + ") ");
			printPath(w);
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param destName TODO PARAM: param description
	 */
	public void showPath(String destName)
	{
		Vertex dest = (Vertex) vertexMap.get(destName);

		if (dest != null)
		{
			showPath(dest);
		}
		else
		{
			Debug.printError("Start vertex not found!");
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param dest TODO PARAM: param description
	 */
	public void showPath(Vertex dest)
	{
		Cell theCell;
		Cell prevCell;
		Vertex thePrev;

		if (dest.getPrev() != null)
		{
			theCell = Cell.getCell(dest.getName());
			thePrev = dest.getPrev();
			prevCell = Cell.getCell(thePrev.getName());
			theCell.setPrevCell(prevCell.getIndex());
			showPath(dest.getPrev());
		}
	}

	/**
	 * If vertexName is not present, add it to vertexMap. In either case,
	 * return the Vertex.
	 *
	 * @param vertexName TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	private Vertex getVertex(String vertexName)
	{
		Vertex v = (Vertex) vertexMap.get(vertexName);

		if (v == null)
		{
			v = new Vertex(vertexName);
			vertexMap.put(vertexName, v);
		}

		return v;
	}

	/**
	 * Recursive routine to print shortest path to dest after running shortest
	 * path algorithm. The path is known to exist.
	 *
	 * @param dest TODO PARAM: DOCUMENT ME!
	 */
	private void printPath(Vertex dest)
	{
		if (dest.getPrev() != null)
		{
			printPath(dest.getPrev());
			Debug.printDebug(" to ");
		}

		Debug.printDebug(dest.getName());
	}

	/**
	 * Initializes the vertex output info prior to running any shortest path
	 * algorithm.
	 */
	private void clearAll()
	{
		for (Iterator itr = vertexMap.values().iterator(); itr.hasNext();)
		{
			((Vertex) itr.next()).reset();
		}
	}

	/**
	 * Single-source unweighted shortest-path algorithm.
	 *
	 * @param startName TODO PARAM: DOCUMENT ME!
	 *
	 * @throws NoSuchElementException TODO THROWS: DOCUMENT ME!
	 */
	public void unweighted(String startName)
	{
		clearAll();

		Vertex start = (Vertex) vertexMap.get(startName);

		if (start == null)
		{
			throw new NoSuchElementException("Start vertex not found");
		}

		LinkedList q = new LinkedList();
		q.addLast(start);
		start.setDist(0);

		while (!q.isEmpty())
		{
			Vertex v = (Vertex) q.removeFirst();

			for (Iterator itr = v.getAdj().iterator(); itr.hasNext();)
			{
				Edge e = (Edge) itr.next();
				Vertex w = e.getDest();

				if (w.getDist() == INFINITY)
				{
					w.setDist(v.getDist() + 1);
					w.setPrev(v);
					q.addLast(w);
				}
			}
		}
	}

	/**
	 * Single-source weighted shortest-path algorithm.
	 *
	 * @param startName TODO PARAM: DOCUMENT ME!
	 *
	 * @throws NoSuchElementException TODO THROWS: DOCUMENT ME!
	 * @throws GraphException TODO THROWS: DOCUMENT ME!
	 */
	public void dijkstra(String startName)
	{
		PriorityQueue pq = new BinaryHeap();
		Vertex start = (Vertex) vertexMap.get(startName);

		if (start == null)
		{
			throw new NoSuchElementException("Start vertex not found");
		}

		clearAll();
		pq.insert(new Path(start, 0));
		start.setDist(0);

		int nodesSeen = 0;

		while (!pq.isEmpty() && (nodesSeen < vertexMap.size()))
		{
			Path vrec = (Path) pq.deleteMin();
			Vertex v = vrec.getDest();

			if (v.getScratch() != 0)
			{
				// already processed v
				continue;
			}

			v.setScratch(1);
			nodesSeen++;

			for (Iterator itr = v.getAdj().iterator(); itr.hasNext();)
			{
				Edge e = (Edge) itr.next();
				Vertex w = e.getDest();
				double cvw = e.getCost();

				if (cvw < 0)
				{
					throw new GraphException("Graph has negative edges");
				}

				if (w.getDist() > (v.getDist() + cvw))
				{
					w.setDist(v.getDist() + cvw);
					w.setPrev(v);
					pq.insert(new Path(w, w.getDist()));
				}
			}
		}
	}

	/**
	 * Single-source weighted shortest-path algorithm using pairing heaps.
	 *
	 * @param startName TODO PARAM: DOCUMENT ME!
	 *
	 * @throws NoSuchElementException TODO THROWS: DOCUMENT ME!
	 * @throws GraphException TODO THROWS: DOCUMENT ME!
	 */
	public void dijkstra2(String startName)
	{
		PriorityQueue pq = new PairingHeap();

		Vertex start = (Vertex) vertexMap.get(startName);

		if (start == null)
		{
			throw new NoSuchElementException("Start vertex not found");
		}

		clearAll();
		start.setPos(pq.insert(new Path(start, 0)));
		start.setDist(0);

		while (!pq.isEmpty())
		{
			Path vrec = (Path) pq.deleteMin();
			Vertex v = vrec.getDest();

			for (Iterator itr = v.getAdj().iterator(); itr.hasNext();)
			{
				Edge e = (Edge) itr.next();
				Vertex w = e.getDest();
				double cvw = e.getCost();

				if (cvw < 0)
				{
					throw new GraphException("Graph has negative edges");
				}

				if (w.getDist() > (v.getDist() + cvw))
				{
					w.setDist(v.getDist() + cvw);
					w.setPrev(v);

					Path newVal = new Path(w, w.getDist());

					if (w.getPos() == null)
					{
						w.setPos(pq.insert(newVal));
					}
					else
					{
						pq.decreaseKey(w.getPos(), newVal);
					}
				}
			}
		}
	}

	/**
	 * Single-source negative-weighted shortest-path algorithm.
	 *
	 * @param startName TODO PARAM: DOCUMENT ME!
	 *
	 * @throws NoSuchElementException TODO THROWS: DOCUMENT ME!
	 * @throws GraphException TODO THROWS: DOCUMENT ME!
	 */
	public void negative(String startName)
	{
		clearAll();

		Vertex start = (Vertex) vertexMap.get(startName);

		if (start == null)
		{
			throw new NoSuchElementException("Start vertex not found");
		}

		LinkedList q = new LinkedList();
		q.addLast(start);
		start.setDist(0);
		start.setScratch(start.getScratch() + 1);

		while (!q.isEmpty())
		{
			Vertex v = (Vertex) q.removeFirst();

			if (v.getScratch() > (2 * vertexMap.size()))
			{
				throw new GraphException("Negative cycle detected");
			}

			v.setScratch(v.getScratch() + 1);

			for (Iterator itr = v.getAdj().iterator(); itr.hasNext();)
			{
				Edge e = (Edge) itr.next();
				Vertex w = e.getDest();
				double cvw = e.getCost();

				if (w.getDist() > (v.getDist() + cvw))
				{
					w.setDist(v.getDist() + cvw);
					w.setPrev(v);

					// Enqueue only if not already on the queue
					if ((w.getScratch() % 2) == 0)
					{
						w.setScratch(w.getScratch() + 1);
						q.addLast(w);
					}
				}
			}
		}
	}

	/**
	 * Single-source negative-weighted acyclic-graph shortest-path algorithm.
	 *
	 * @param startName TODO PARAM: DOCUMENT ME!
	 *
	 * @throws NoSuchElementException TODO THROWS: DOCUMENT ME!
	 * @throws GraphException TODO THROWS: DOCUMENT ME!
	 */
	public void acyclic(String startName)
	{
		Vertex start = (Vertex) vertexMap.get(startName);

		if (start == null)
		{
			throw new NoSuchElementException("Start vertex not found");
		}

		clearAll();

		LinkedList q = new LinkedList();
		start.setDist(0);

		// Compute the indegrees
		Collection vertexSet = vertexMap.values();

		for (Iterator vsitr = vertexSet.iterator(); vsitr.hasNext();)
		{
			Vertex v = (Vertex) vsitr.next();

			for (Iterator witr = v.getAdj().iterator(); witr.hasNext();)
			{
				Edge tmpEdge = (Edge) witr.next();
				tmpEdge.getDest().setScratch(tmpEdge.getDest().getScratch() + 1);
			}
		}

		// Enqueue vertices of indegree zero
		for (Iterator vsitr = vertexSet.iterator(); vsitr.hasNext();)
		{
			Vertex v = (Vertex) vsitr.next();

			if (v.getScratch() == 0)
			{
				q.addLast(v);
			}
		}

		int iterations;

		for (iterations = 0; !q.isEmpty(); iterations++)
		{
			Vertex v = (Vertex) q.removeFirst();

			for (Iterator itr = v.getAdj().iterator(); itr.hasNext();)
			{
				Edge e = (Edge) itr.next();
				Vertex w = e.getDest();
				double cvw = e.getCost();

				w.setScratch(w.getScratch() - 1);

				if (w.getScratch() == 0)
				{
					q.addLast(w);
				}

				if (v.getDist() == INFINITY)
				{
					continue;
				}

				if (w.getDist() > (v.getDist() + cvw))
				{
					w.setDist(v.getDist() + cvw);
					w.setPrev(v);
				}
			}
		}

		if (iterations != vertexMap.size())
		{
			throw new GraphException("Graph has a cycle!");
		}
	}

	/**
	 * Process a request; return false if end of file.
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public boolean processPath()
	{
		String startName = "C0";
		String destName = "C" + ((Maze.ROWS * Maze.COLUMNS) - 1);
		Debug.printDebug(Maze.ROWS + " " + Maze.COLUMNS + " " + destName);

		String alg = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try
		{
//			System.out.print("Enter start node:");
//			if ((startName= in.readLine()) == null)
//				return false;
//			System.out.print("Enter destination node:");
//			if ((destName= in.readLine()) == null)
//				return false;
			System.out.print("Enter algorithm (u, d, n, a ): ");
//			if ((alg= in.readLine()) == null)
//				return false;
			alg ="u";
			if (alg.equals("u"))
			{
				this.unweighted(startName);
			}
			else if (alg.equals("d"))
			{
				this.dijkstra(startName);
				this.printPath(destName);
				this.showPath(destName);
				this.dijkstra2(startName);
			}
			else if (alg.equals("n"))
			{
				this.negative(startName);
			}
			else if (alg.equals("a"))
			{
				this.acyclic(startName);
			}

			this.printPath(destName);
			this.showPath(destName);

			return false;
			} 
		
		catch (NoSuchElementException e)
		{
			System.err.println(e);
		}
		catch (GraphException e)
		{
			System.err.println(e);
		}
		catch (Exception e) {
			System.err.println(e);
	}

		return true;
	}
}
