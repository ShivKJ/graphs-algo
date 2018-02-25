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
		final String				representation;
		final Map<Vrtx, Edge<Vrtx>>	mapper;

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

	static class ABCGraph implements Graph<Vrtx, Edge<Vrtx>> {
		private final List<Vrtx>		vertices;
		private final List<Edge<Vrtx>>	edges;

		public ABCGraph() {
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
			return ofNullable(ofNullable(src.mapper.get(dst)).orElse(dst.mapper.get(src)));
		}

		@Override
		public void connect(Vrtx from, Vrtx to, double distance) {
			DirectionalEdge forward = new DirectionalEdge(from, to, distance);
			DirectionalEdge backwork = new DirectionalEdge(to, from, distance);

			from.connect(forward);
			to.connect(backwork);

			edges.add(forward);
			//			edges.add(backwork);// not adding backword edges

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
