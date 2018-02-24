package algo.graphs;

public interface Vertex {

	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);

	@Override
	String toString();

	<T> T userData();

	<T> void setUserData(T data);

}
