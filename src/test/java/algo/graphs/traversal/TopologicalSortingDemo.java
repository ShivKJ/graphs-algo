package algo.graphs.traversal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import algo.graphs.Edge;
import algo.graphs.Graph;

public class TopologicalSortingDemo {
	public static void main(String[] args) {

		Graph<HashableVertex, ?> graph = new HashableGraph<>();
		HashableVertex a = new HashableVertex("a");
		HashableVertex b = new HashableVertex("b");
		HashableVertex c = new HashableVertex("c");
		HashableVertex d = new HashableVertex("d");
		HashableVertex e = new HashableVertex("e");
		HashableVertex f = new HashableVertex("f");
		HashableVertex g = new HashableVertex("g");

		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		graph.addVertex(g);

		graph.connect(a, b, 1);
		graph.connect(a, c, 1);
		graph.connect(b, c, 1);
		System.out.println(new TopologicalSorting<>(graph).sort());
	}
}

class HashableGraph<E> implements Graph<HashableVertex, Edge<HashableVertex>> {
	private final Collection<HashableVertex> vertices;

	public HashableGraph() {
		this.vertices = new HashSet<>();
	}

	@Override
	public Collection<HashableVertex> vertices() {
		return vertices;
	}

	@Override
	public Collection<Edge<HashableVertex>> edges() {

		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<Edge<HashableVertex>> edge(HashableVertex src, HashableVertex dst) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void connect(HashableVertex from, HashableVertex to, double dis) {
		from.adjacentVertices().add(to);
	}

	@Override
	public Collection<HashableVertex> adjacentVertices(HashableVertex vertex) {
		return vertex.adjacentVertices();
	}

	@Override
	public Collection<Edge<HashableVertex>> adjacentEdges(HashableVertex vertex) {
		throw new UnsupportedOperationException();
	}

}

class HashableVertex extends TraversalVertex {
	private static int					indexer	= 0;
	private final Set<HashableVertex>	adja;
	private Object						data;
	private final int					I;

	public HashableVertex(Object data) {
		this.data = data;
		this.adja = new HashSet<>();
		this.I = indexer++;
	}

	@Override
	public int hashCode() {

		return I;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof HashableVertex && ((HashableVertex) obj).data.equals(data);
	}

	@Override
	public String toString() {

		return data.toString();
	}

	public Collection<HashableVertex> adjacentVertices() {

		return adja;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T userData() {
		return (T) data;
	}

	@Override
	public <T> void setUserData(T data) {
		throw new UnsupportedOperationException();
	}

}
