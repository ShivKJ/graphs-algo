package algo.graphs.traversal.mst;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.Vertex;

class MSTGraph<V extends Vertex, W extends Edge<V>> implements Graph<V, W> {
	private final Collection<V>	vertices;
	private final Collection<W>	edges;

	private final Map<V, Map<V, W>> edgeMapper;

	MSTGraph(Collection<V> vertices, Collection<W> edges) {
		this.vertices = vertices;
		this.edges = edges;

		this.edgeMapper = edges.stream().collect(groupingBy(W::getSrc, toMap(W::getDst, identity())));
	}

	@Override
	public Collection<V> vertices() {

		return vertices;
	}

	@Override
	public Collection<W> edges() {

		return edges;
	}

	@Override
	public Optional<W> edge(V src, V dst) {
		return ofNullable(edgeMapper.getOrDefault(src, emptyMap()).get(dst));
	}

	@Override
	public Collection<V> adjacentVertices(V vertex) {
		return unmodifiableCollection(edgeMapper.getOrDefault(vertex, emptyMap()).keySet());
	}

	@Override
	public Collection<W> adjacentEdges(V vertex) {
		return unmodifiableCollection(edgeMapper.getOrDefault(vertex, emptyMap()).values());
	}

	@Override
	public void connect(V from, V to, double distance) {
		throw new UnsupportedOperationException();
	}

}
