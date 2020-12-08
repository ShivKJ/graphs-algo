package algo.graphs.coloring;

import static com.google.common.collect.Sets.difference;
import static java.util.stream.Collectors.toCollection;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.Vertex;

public class Greedy {
	private final Graph<Vertex, Edge<Vertex>> graph;
	private final Set<Integer>                colors;

	public Greedy(Graph<Vertex, Edge<Vertex>> graph) {
		this.graph = graph;
		this.colors = new HashSet<>();
	}

	/**
	 * @return number of colors required to color vertices of graph such that no adjacent vertices gets same color. 
	 * Graph should have only one component.
	 * @implNote this is not the minimum number of colors needed
	 */
	public int color() {
		if (graph.isEmpty())
			return 0;

		color(graph.vertices().iterator().next());

		return colors.size();
	}

	private void color(Vertex src) {
		if (src.userData() != null)
			return;

		Set<Integer> adjacentCol = graph.adjacentVertices(src)
		                                .stream()
		                                .map(Vertex::userData)
		                                .filter(Objects::nonNull)
		                                .map(Integer.class::cast)
		                                .collect(toCollection(HashSet<Integer>::new));

		Set<Integer> diff = difference(colors, adjacentCol);

		int color;

		if (diff.isEmpty()) {
			color = colors.size() + 1;
			colors.add(color);
		} else
			color = diff.iterator().next();

		src.setUserData(color);

		for (Vertex adj : graph.adjacentVertices(src))
			color(adj);

	}

}
