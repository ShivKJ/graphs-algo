package algo.graphs;

import static java.lang.Double.POSITIVE_INFINITY;

import java.util.Collection;
import java.util.Optional;

public interface Graph<V extends Vertex, W extends Edge<? extends V>> {
	Collection<V> vertices();

	Collection<W> edges();

	/**
	 * Graph can not have more than one edge between two vertices
	 * @param src
	 * @param dst
	 * @return optional object containing the edge from src to dst
	 */
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

	void connect(V from, V to, double distance);

	Collection<V> adjacentVertices(V vertex);

	Collection<W> adjacentEdges(V vertex);

	default boolean isEmpty() {
		return vertices().isEmpty();
	}

	/**
	 * setting user data stored to each vertex to null
	 */
	default void resetUsertData() {
		vertices().forEach(x -> x.setUserData(null));
	}

}
