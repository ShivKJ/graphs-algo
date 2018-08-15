package algo.graphs.coloring;

import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Sets.difference;
import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.Vertex;

public class WelshAndPowell {
	private final Graph<Vertex, Edge<Vertex>>	graph;
	private final Set<Integer>					colors;

	public WelshAndPowell(Graph<Vertex, Edge<Vertex>> graph) {
		this.graph = graph;
		this.colors = new HashSet<>();
	}

	public int color() {
		if (graph.isEmpty())
			return 0;

		List<Vertex> vertices = new ArrayList<>(graph.vertices());
		vertices.sort(reverseOrder());

		for (Vertex vertex : vertices)
			color(vertex);

		return colors.size();

	}

	private void color(Vertex src) {

		if (src.userData() != null)
			return;

		Set<Integer> adjacentCol = graph.adjacentVertices(src)
		                                .stream()
		                                .map(Vertex::userData)
		                                .filter(notNull())
		                                .map(Integer.class::cast)
		                                .collect(toCollection(HashSet<Integer>::new));

		Set<Integer> diff = difference(colors, adjacentCol);

		int color = 0;

		if (diff.isEmpty()) {
			color = colors.size() + 1;
			colors.add(color);
		} else
			color = diff.iterator().next();

		src.setUserData(color);
	}

}
