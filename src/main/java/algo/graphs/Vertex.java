package algo.graphs;

public interface Vertex {

	<T> T userData();

	<T> void setUserData(T data);

	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);

	@Override
	String toString();

}
