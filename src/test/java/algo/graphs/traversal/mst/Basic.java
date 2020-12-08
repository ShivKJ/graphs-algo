package algo.graphs.traversal.mst;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Optional.ofNullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.traversal.TraversalVertex;

public class Basic {
	static class Vrtx extends TraversalVertex {
		final String                representation;
		final Map<Vrtx, Edge<Vrtx>> mapper;

		public Vrtx(String representation) {
			this.representation = representation;
			this.mapper = new HashMap<>();
		}

		public void connect(Edge<Vrtx> edge) {
			mapper.put(edge.getDst(), edge);
		}

		@Override
		public String toString() {
			return representation;
		}

		@Override
		public int hashCode() {

			return representation.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof Vrtx && ((Vrtx) obj).representation.equals(representation);
		}
	}

	static class DirectionalEdge extends Edge<Vrtx> {
		final double distance;

		public DirectionalEdge(Vrtx src, Vrtx dst, double distance) {
			super(src, dst);
			this.distance = distance;

		}

		@Override
		public double distance() {

			return distance;
		}

	}

	static class UndirectedGraph extends DirectedGraph {

		@Override
		public void connect(Vrtx from, Vrtx to, double distance) {
			super.connect(from, to, distance);
			to.connect(new DirectionalEdge(to, from, distance));
		}

		@Override
		public Optional<Edge<Vrtx>> edge(Vrtx src, Vrtx dst) {
			return ofNullable(super.edge(src, dst).orElse(dst.mapper.get(src)));
		}

	}

	static class DirectedGraph implements Graph<Vrtx, Edge<Vrtx>> {
		protected final List<Vrtx>       vertices;
		protected final List<Edge<Vrtx>> edges;

		public DirectedGraph() {
			this.vertices = new LinkedList<>();
			this.edges = new LinkedList<>();
		}

		@Override
		public Collection<Vrtx> vertices() {

			return unmodifiableCollection(vertices);
		}

		@Override
		public Collection<Edge<Vrtx>> edges() {
			return unmodifiableCollection(edges);
		}

		@Override
		public Optional<Edge<Vrtx>> edge(Vrtx src, Vrtx dst) {
			return ofNullable(src.mapper.get(dst));
		}

		@Override
		public void connect(Vrtx from, Vrtx to, double distance) {
			DirectionalEdge forward = new DirectionalEdge(from, to, distance);
			from.connect(forward);
			edges.add(forward);

		}

		@Override
		public Collection<Vrtx> adjacentVertices(Vrtx vertex) {
			return unmodifiableCollection(vertex.mapper.keySet());
		}

		@Override
		public void addVertex(Vrtx vertex) {
			vertices.add(vertex);
		}

		@Override
		public Collection<Edge<Vrtx>> adjacentEdges(Vrtx vertex) {
			return unmodifiableCollection(vertex.mapper.values());
		}

	}
}
