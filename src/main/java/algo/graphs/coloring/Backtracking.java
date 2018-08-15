package algo.graphs.coloring;

import static com.google.common.collect.Sets.difference;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;

import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.Vertex;

public class Backtracking {
	private final Graph<Vertex, Edge<Vertex>>	graph;
	private final Set<Integer>					colors;

	private Backtracking(Graph<Vertex, Edge<Vertex>> graph, int maxColor) {
		this.graph = graph;
		this.colors = IntStream.range(0, maxColor)
		                       .boxed()
		                       .collect(toSet());
	}

	public static boolean hasSolution(Graph<Vertex, Edge<Vertex>> graph, int maxColor) {
		return !graph.isEmpty() && new Backtracking(graph, maxColor).solve(graph.vertices().iterator().next());
	}

	private boolean solve(Vertex src) {
		Set<Integer> remainingColor = difference(colors, graph.adjacentVertices(src)
		                                                      .stream()
		                                                      .map(Vertex::userData)
		                                                      .filter(Objects::nonNull)
		                                                      .map(Integer.class::cast)
		                                                      .collect(toSet()));
		if (!remainingColor.isEmpty())
			for (Integer color : remainingColor) {
				src.setUserData(color);
				boolean isSafe = true;

				for (Vertex v : graph.adjacentVertices(src))
					if (isNull(v.userData()))
						if (!solve(v)) {
							v.setUserData(null);
							isSafe = false;
							break;
						}

				if (isSafe)
					return true;

			}

		return false;
	}
}
