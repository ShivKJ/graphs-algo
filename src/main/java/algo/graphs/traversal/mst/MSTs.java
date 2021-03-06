package algo.graphs.traversal.mst;

import static algo.graphs.traversal.Utils.labelAsNew;
import static algo.graphs.traversal.Utils.traversalVertexToPQNode;
import static algo.graphs.traversal.VertexTraversalCode.DONE;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.util.Comparator.comparingDouble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.traversal.TraversalVertex;
import algo.heap.AdaptablePriorityQueue;
import algo.heap.ArrayPriorityQueue;
import algo.heap.IndexedPNode;

/**
 * Minimum spanning tree(MST) is a tree, such that sum of edges present is minimum,
 * and if two vertices were connected earlier then they remain connected.
 *  
 * @author shiv
 *
 */
public final class MSTs {

	private MSTs() {}

	/**
	 * This methods finds minimum spanning tree(MST) using Kruskal algorithm.
	 * This is achieved via greedy method.
	 * 
	 * First we sort edges in increasing order of edge-weight.
	 * Then for each edge which connects two vertices having different parent, merging children of parents.
	 * 
	 * @param graph
	 * @return
	 */

	public static <T extends TraversalVertex, E extends Edge<T>> Graph<T, E> kruskal(Graph<T, E> graph) {
		Collection<T> vertices = graph.vertices();
		labelAsNew(vertices);

		vertices.forEach(MSTs::initialize);

		Queue<E> edges = new PriorityQueue<>(comparingDouble(E::distance));
		edges.addAll(graph.edges());

		int maxEdges = (vertices.size() - 1);// undirected graphs will have (V-1) number of edges for MST

		Collection<E> mstEdges = new ArrayList<>(maxEdges);

		while (!edges.isEmpty()) {
			E w = edges.poll();
			T src = w.getSrc(), dst = w.getDst();
			// using union-find method
			TraversalVertex srcParent = parent(src), dstParent = parent(dst);

			if (srcParent != dstParent) {
				merge(srcParent, dstParent);

				mstEdges.add(w);
				mstEdges.add(graph.edge(dst, src).get());

				if (mstEdges.size() == maxEdges)
					break;
			}

		}

		return new MSTGraph<>(vertices, mstEdges);
	}

	/**
	 * initializing vertex for union-find method and setting each vertex to its parent
	 * @param vertex
	 */
	private static void initialize(TraversalVertex vertex) {
		vertex.setParent(vertex);
		vertex.setUserData(0);// storing rank in user data
	}

	/**
	 * 
	 * @param vertex
	 * @return root of this vertex which is found by such ancestor of this vertex 
	 * which is its own parent.
	 */
	private static TraversalVertex parent(TraversalVertex vertex) {
		if (vertex.parent() != vertex) {
			Queue<TraversalVertex> queue = new LinkedList<>();

			do {
				queue.add(vertex);
				vertex = vertex.parent();
			} while (vertex != vertex.parent());

			for (TraversalVertex v : queue)
				v.setParent(vertex);

		}

		return vertex;

	}

	private static void merge(TraversalVertex u, TraversalVertex v) {
		Integer uRank = u.userData(), vRank = v.userData();

		if (uRank > vRank)
			v.setParent(u);
		else {
			u.setParent(v);

			if (uRank == vRank)
				v.setUserData(vRank + 1);
		}
	}

	/**
	 * Implementing MST using Prim algorithm.
	 * This uses Adaptable priority queue. 
	 * 
	 * We start with a vertex. Setting its patent to null.
	 * Now we poll vertex from priority queue and update adjacent 
	 * vertices if they in priority queue and has high distance stored.
	 * 
	 * @param graph
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public static <V extends TraversalVertex, W extends Edge<V>> Graph<V, W> prim(Graph<V, W> graph) {
		Collection<V> vertices = graph.vertices();

		labelAsNew(vertices);

		Map<V, IndexedPNode<V, Double>> vToPQNode = traversalVertexToPQNode(graph.vertices(), POSITIVE_INFINITY);

		List<IndexedPNode<V, Double>> vs = new ArrayList<>(vToPQNode.values());

		IndexedPNode<V, Double> src = vs.get(0);

		src.setPriority(NEGATIVE_INFINITY);
		src.getData().setParent(null);

		// adding all vertices to priority queue
		AdaptablePriorityQueue<IndexedPNode<V, Double>> priorityQueue = new ArrayPriorityQueue<>(vs, Double::compareTo);

		Collection<W> edges = new ArrayList<>(2 * (vs.size() - 1));

		while (!priorityQueue.isEmpty()) {
			IndexedPNode<V, Double> uNode = priorityQueue.poll();

			V u = uNode.getData();
			u.code(DONE);// vertex has been explored.

			if (u.parent() != null) {
				edges.add(graph.edge((V) u.parent(), u).get());
				edges.add(graph.edge(u, (V) u.parent()).get());
			}

			for (V v : graph.adjacentVertices(u)) {
				IndexedPNode<V, Double> vNode = vToPQNode.get(v);

				if (v.code() != DONE) {
					Double cost = graph.distance(u, v);

					if (cost.compareTo(vNode.getPriority()) < 0) {
						v.setParent(u);
						priorityQueue.updatePriority(vNode, cost);
					}
				}
			}
		}

		return new MSTGraph<>(vertices, edges);
	}

}
