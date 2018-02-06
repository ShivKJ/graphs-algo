package algo.graphs.traversal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.Vertex;

public class TopologicalSortingDemo {
	public static void main(String[] args) {

		Graph<HashableVertex<String>, ?> graph = new HashableGraph<>();
		HashableVertex<String> a = new HashableVertex<>("a");
		HashableVertex<String> b = new HashableVertex<>("b");
		HashableVertex<String> c = new HashableVertex<>("c");
		HashableVertex<String> d = new HashableVertex<>("d");
		HashableVertex<String> e = new HashableVertex<>("e");
		HashableVertex<String> f = new HashableVertex<>("f");
		HashableVertex<String> g = new HashableVertex<>("g");

		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		graph.addVertex(g);

		graph.connect(a, b);
		graph.connect(a, c);
		graph.connect(b, c);
		System.out.println(new TopologicalSorting<>(graph).sort());
	}
}

class HashableGraph<E> implements Graph<HashableVertex<E>, Edge<HashableVertex<E>>> {
	private final Collection<HashableVertex<E>> vertices;

	public HashableGraph() {
		this.vertices = new HashSet<>();
	}

	@Override
	public Collection<HashableVertex<E>> vertices() {
		return vertices;
	}

	@Override
	public Collection<Edge<HashableVertex<E>>> edges() {

		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<Edge<HashableVertex<E>>> edge(HashableVertex<E> src, HashableVertex<E> dst) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void connect(HashableVertex<E> from, HashableVertex<E> to) {
		from.adjacentVertices().add(to);
	}

	@Override
	public Collection<HashableVertex<E>> adjacentVertices(HashableVertex<E> vertex) {
		return vertex.adjacentVertices();
	}

	@Override
	public Collection<Edge<HashableVertex<E>>> adjacentEdges(HashableVertex<E> vertex) {
		throw new UnsupportedOperationException();
	}

}

class HashableVertex<T> implements Vertex {
	static int								indexer	= 0;
	private final Set<HashableVertex<T>>	adja;
	private final T							data;
	private final int						I;

	public HashableVertex(T data) {
		this.data = data;
		this.adja = new HashSet<>();
		this.I = indexer++;
	}

	@Override
	public int hashCode() {

		return I;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		return obj instanceof HashableVertex && ((HashableVertex<T>) obj).data.equals(data);
	}

	@Override
	public String toString() {

		return data.toString();
	}

	public Collection<HashableVertex<T>> adjacentVertices() {

		return adja;
	}

	public T getData() {

		return data;
	}

	@Override
	public int uid() {

		return I;
	}

}
