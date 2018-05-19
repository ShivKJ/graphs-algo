package algo.graph.coloring;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.Vertex;

public class GraphImpl implements Graph<Vertex, Edge<Vertex>> {
	private VertexImpl[] vertices;

	public GraphImpl(Path file) {
		parse(file);
	}

	private void parse(Path path) {

		try (BufferedReader reader = Files.newBufferedReader(path)) {
			this.vertices = IntStream.range(0, parseInt(reader.readLine().split(" ")[0]))
			                         .mapToObj(VertexImpl::new)
			                         .toArray(VertexImpl[]::new);

			reader.lines().forEach(this::connect);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Collection<Vertex> vertices() {

		return asList(vertices);
	}

	@Override
	public boolean isEmpty() {

		return vertices.length == 0;
	}

	@Override
	public void connect(Vertex from, Vertex to, double distance) {
		if (from != to) {
			((VertexImpl) from).adj.add(to);
			((VertexImpl) to).adj.add(from);
		}
	}

	private void connect(String edge) {
		String[] split = edge.split(" ");
		connect(vertices[parseInt(split[0])], vertices[parseInt(split[1])], 1);
	}

	//-----------------------------------------------------------------------
	@Override
	public Collection<Edge<Vertex>> edges() {
		throw new UnsupportedOperationException();

	}

	@Override
	public Optional<Edge<Vertex>> edge(Vertex src, Vertex dst) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Vertex> adjacentVertices(Vertex vertex) {

		return ((VertexImpl) vertex).adj;
	}

	@Override
	public Collection<Edge<Vertex>> adjacentEdges(Vertex vertex) {
		throw new UnsupportedOperationException();
	}

	//-----------------------------------------------------------------------

	 static class VertexImpl implements Vertex, Comparable<VertexImpl> {
		private final int			index;
		private Object				userData;
		 final Set<Vertex>	adj;

		VertexImpl(int index) {
			this.index = index;
			this.userData = null;
			this.adj = new HashSet<>();
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T userData() {

			return (T) userData;
		}

		@Override
		public <T> void setUserData(T data) {
			this.userData = data;
		}

		@Override
		public String toString() {

			return index + " : " + userData + " : " + adj.size();
		}

		@Override
		public int compareTo(VertexImpl o) {

			return adj.size() - o.adj.size();
		}

		@Override
		public int hashCode() {

			return index;
		}

		@Override
		public boolean equals(Object obj) {

			return obj instanceof VertexImpl && ((VertexImpl) obj).index == index;
		}

	}
}
