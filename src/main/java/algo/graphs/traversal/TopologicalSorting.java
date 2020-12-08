package algo.graphs.traversal;

import static algo.graphs.traversal.Utils.labelAsNew;
import static algo.graphs.traversal.VertexTraversalCode.DONE;
import static java.util.Collections.asLifoQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import algo.graphs.Graph;

public class TopologicalSorting<T extends TraversalVertex> {
	/* 
	 * It is simply DFS, just that we need to maintain the order of visit.
	 */

	private final Graph<T, ?> graph;
	private final Queue<T>    outputStack;

	@SuppressWarnings("unchecked")
	public TopologicalSorting(Graph<? extends TraversalVertex, ?> graph) {
		this.graph = (Graph<T, ?>) graph;
		this.outputStack = asLifoQueue(new LinkedList<>());// stack implementation
	}

	public List<T> sort() {
		Collection<T> vertices = graph.vertices();
		labelAsNew(vertices);

		for (T vertex : graph.vertices())
			if (vertex.code() != DONE)
				processVertex(vertex);

		return new ArrayList<>(outputStack);
	}

	private void processVertex(T sourceVertex) {
		sourceVertex.code(DONE);

		for (T adjacentVertex : graph.adjacentVertices(sourceVertex))
			if (adjacentVertex.code() != DONE)
				processVertex(adjacentVertex);

		outputStack.offer(sourceVertex);// source vertex is added at top.
	}
}
