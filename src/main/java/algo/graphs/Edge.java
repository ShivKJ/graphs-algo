package algo.graphs;

import static java.lang.Double.compare;

import java.util.Objects;

public abstract class Edge<T extends Vertex> implements Comparable<Edge<T>> {
	private final static double	TOL	= 1e-7;
	private final T				src , dst;

	public Edge(T src, T dst) {
		this.src = src;
		this.dst = dst;
	}

	public abstract double distance();

	public T getSrc() {
		return src;
	}

	public T getDst() {
		return dst;
	}

	@Override
	public int compareTo(Edge<T> o) {
		return compare(distance(), o.distance());
	}
	@Override
	public String toString() {
		
		return src+"->"+dst;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Edge<?>) {
			Edge<?> edge = (Edge<?>) obj;
			return Math.abs(distance() - edge.distance()) <= TOL && src.equals(edge.src) && dst.equals(edge.dst);
		}

		return false;
	}

	@Override
	public int hashCode() {

		return Objects.hash(src, dst);
	}

}
