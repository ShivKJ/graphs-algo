package algo.graphs.coloring;

import static com.google.common.collect.Sets.difference;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.Vertex;

public class WelshAndPowell {
	private final Graph<Vertex, Edge<Vertex>> graph;
	private final Set<Integer>                colors;

	public WelshAndPowell(Graph<Vertex, Edge<Vertex>> graph) {
		this.graph = graph;
		this.colors = new HashSet<>();
	}

	public int color() {
		if (graph.isEmpty())
			return 0;

		List<Vertex> vertices = new ArrayList<>(graph.vertices());

		vertices.sort(comparingInt(this::adjacentVertices).reversed());

		for (Vertex vertex : vertices)// processing those vertices first which has more adjacent vertices
			color(vertex);

		return colors.size();

	}

	private int adjacentVertices(Vertex v) {
		return graph.adjacentEdges(v).size();
	}

	private void color(Vertex src) {
		Set<Integer> adjacentCol = graph.adjacentVertices(src)
		                                .stream()
		                                .map(Vertex::userData)
		                                .filter(Objects::nonNull)
		                                .map(Integer.class::cast)
		                                .collect(toSet());

		Optional<Integer> minColor = difference(colors, adjacentCol).stream().min(naturalOrder());

		int color;

		if (minColor.isPresent())
			color = minColor.get();
		else {
			color = colors.size();// creating new color
			colors.add(color);
		}

		src.setUserData(color);
	}

}
