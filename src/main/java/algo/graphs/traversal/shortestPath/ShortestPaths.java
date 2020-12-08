package algo.graphs.traversal.shortestPath;

import static algo.graphs.traversal.Utils.traversalVertexToPQNode;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import algo.graphs.Graph;
import algo.graphs.traversal.TraversalVertex;
import algo.heap.AdaptablePriorityQueue;
import algo.heap.ArrayPriorityQueue;
import algo.heap.IndexedPNode;

public final class ShortestPaths {
	private ShortestPaths() {}

	/**
	 * Checks if going from u -> v is better, in which case, sets v's parent to u and
	 * updates distance(inferred as priority) from "src" to v.
	 * src_v -> src_u (uData) + u_v (w)
	 *   
	 * @param u
	 * @param v
	 * @param w
	 */
	private static <PQ extends IndexedPNode<? extends TraversalVertex, Double>, T, P extends Comparable<P>> void relax(PQ u, PQ v,
	                                                                                                                   double w,
	                                                                                                                   AdaptablePriorityQueue<PQ> pq) {
		Double uData = u.getPriority(), vData = v.getPriority();

		if (vData > uData + w) {
			pq.updatePriority(v, uData + w);
			v.getData().setParent(u.getData());
		}
	}

	/**
	 * Given a graph, finds shortest path between two point.
	 * The method uses Adaptable Priority Queue and relaxation 
	 * criteria to update distance from source to a point.
	 * 
	 * If there does not exists any path between src and dst then
	 * returns empty list.
	 * 
	 * @param graph
	 * @param src
	 * @param dst
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends TraversalVertex> List<T> dijkstra(Graph<T, ?> graph, T src, T dst) {
		Map<T, IndexedPNode<T, Double>> vertexToPQNode = traversalVertexToPQNode(graph.vertices(), POSITIVE_INFINITY);
		IndexedPNode<T, Double> srcNode = vertexToPQNode.get(src);

		src.setParent(null);
		srcNode.setPriority(0);// so that it is popped first.

		AdaptablePriorityQueue<IndexedPNode<T, Double>> pq = new ArrayPriorityQueue<>(vertexToPQNode.values(), Double::compare);

		boolean reachableToDst = false;

		do {
			IndexedPNode<T, Double> uNode = pq.poll();
			T u = uNode.getData();

			for (T v : graph.adjacentVertices(u)) {
				relax(uNode, vertexToPQNode.get(v), graph.distance(u, v), pq);
				// we update distance from u -> v and set v's parent to u which will be used to find path.
				if (v == dst)
					reachableToDst = true;
			}

		} while (!pq.isEmpty());

		if (reachableToDst) {
			List<T> path = new ArrayList<>();

			do
				path.add(dst);
			while ((dst = (T) dst.parent()) != null);

			return path;
		} else
			return emptyList();
	}
}
