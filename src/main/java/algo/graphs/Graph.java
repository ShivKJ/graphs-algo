package algo.graphs;

import static java.lang.Double.POSITIVE_INFINITY;

import java.util.Collection;
import java.util.Optional;

public interface Graph<V extends Vertex, W extends Edge<? extends V>> {
	Collection<V> vertices();

	Collection<W> edges();

	Optional<W> edge(V src, V dst);

	/**
	 * Returns distance between two vertices which is edge length.
	 * If no edge exists between the vertices then returns Double.POSITIVE_INFINITY. 
	 * @param src
	 * @param dst
	 * @return
	 */
	default double distance(V src, V dst) {
		return edge(src, dst).map(W::distance).orElse(POSITIVE_INFINITY);
	}

	default void addVertex(V vertex) {
		vertices().add(vertex);
	}

	void connect(V from, V to);

	Collection<V> adjacentVertices(V vertex);

	Collection<W> adjacentEdges(V vertex);

}
