package algo.graphs.traversal;

import static java.util.Collections.asLifoQueue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import algo.graphs.Graph;
import algo.graphs.Vertex;

public class TopologicalSorting<T extends Vertex> {
	/* 
	 * It is simply DFS, just that we need to maintain the order of visit.
	 */

	private final Graph<T, ?>	graph;
	private final Set<T>		visitedNodes;
	private final Queue<T>		outputStack;

	@SuppressWarnings("unchecked")
	public TopologicalSorting(Graph<? extends Vertex, ?> graph) {
		this.graph = (Graph<T, ?>) graph;
		this.visitedNodes = new HashSet<>();
		this.outputStack = asLifoQueue(new LinkedList<>());// stack implementation
	}

	public List<T> sort() {
		for (T vertex : graph.vertices())
			if (!visitedNodes.contains(vertex))
				processVertex(vertex);

		return new ArrayList<>(outputStack);
	}

	private void processVertex(T sourceVertex) {
		visitedNodes.add(sourceVertex);

		for (T adjacentVertex : graph.adjacentVertices(sourceVertex))
			if (!visitedNodes.contains(adjacentVertex))
				processVertex(adjacentVertex);

		outputStack.offer(sourceVertex);// source vertex is added at top.
	}
}
