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

public final class Backtracking {
	private final Graph<Vertex, Edge<Vertex>> graph;// vertices of the graph will be used to hold color in the user data
	private final Set<Integer>                colors;

	/**
	 * coloring the graph with max "maxColor" colors
	 * @param graph
	 * @param maxColor
	 */
	private Backtracking(Graph<Vertex, Edge<Vertex>> graph, int maxColor) {
		this.graph = graph;
		this.colors = IntStream.range(0, maxColor)
		                       .boxed()
		                       .collect(toSet());
	}

	/**
	 * 
	 * @param graph
	 * @param maxColor
	 * @return true if graph is either empty or it can be colored with max of "maxColor" many colors
	 */
	public static boolean hasSolution(Graph<Vertex, Edge<Vertex>> graph, int maxColor) {
		return graph.isEmpty() || new Backtracking(graph, maxColor).solve(graph.vertices().iterator().next());
	}

	/**
	 * In the backtracking algorithm, we pick an uncolored vertex. Then adjacent vertices colors are checked.
	 * If there is a color which is not in the neighboring vertices then "src" vertex gets the color.
	 * 
	 * After coloring the src vertex, neighbor uncolored vertices are checked and method backtracks if n
	 * @param src uncolored vertex
	 * @return true if uncolored vertices connected through "src" vertex can be colored
	 */
	private boolean solve(Vertex src) {
		Set<Integer> availableColors = difference(colors, graph.adjacentVertices(src)
		                                                       .stream()
		                                                       .map(Vertex::userData)
		                                                       .filter(Objects::nonNull)
		                                                       .map(Integer.class::cast)
		                                                       .collect(toSet()));
		if (!availableColors.isEmpty())
			for (Integer color : availableColors) {
				src.setUserData(color);
				boolean isSafe = true;

				for (Vertex v : graph.adjacentVertices(src))
					if (isNull(v.userData()))
						if (!solve(v)) {// if remaining colors are not sufficient then trying next color
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
