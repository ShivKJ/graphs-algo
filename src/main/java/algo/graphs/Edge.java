package algo.graphs;

import static java.lang.Double.compare;
import static java.util.Objects.hash;

public abstract class Edge<T extends Vertex> implements Comparable<Edge<T>> {
	private final T src, dst;

	public Edge(T src, T dst) {
		this.src = src;
		this.dst = dst;
	}

	/**
	 * @return weight of edge
	 */
	public abstract double distance();

	public T getSrc() {
		return src;
	}

	public T getDst() {
		return dst;
	}

	/**
	 * @param o
	 * @return comparing the other edge with this edge with respect to weight(distance between two vertices of this edge) of edge
	 */
	@Override
	public int compareTo(Edge<T> o) {
		return compare(distance(), o.distance());
	}

	@Override
	public String toString() {
		return src + "->" + dst;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Edge<?>) {
			Edge<?> edge = (Edge<?>) obj;
			return src.equals(edge.src) && dst.equals(edge.dst);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return hash(src, dst);
	}

}
