package algo.graphs.traversal;

import algo.graphs.Vertex;

public class TraversalVertex implements Vertex {
	private TraversalVertex     parent;// vertex which was visited just before this vertex (one of the neighboring vertices)
	private Object              data;// stored data in this vertex
	private VertexTraversalCode code;// defines the status of vertex in traversal

	public TraversalVertex() {
		this.parent = null;
	}

	public void setParent(TraversalVertex parent) {
		this.parent = parent;
	}

	public TraversalVertex parent() {
		return parent;
	}

	@Override
	public <T> void setUserData(T data) {
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T userData() {
		return (T) data;
	}

	public VertexTraversalCode code() {
		return code;
	}

	public void code(VertexTraversalCode code) {
		this.code = code;
	}

	@Override
	public boolean equals(Object obj) {
		throw new UnsupportedOperationException("equals method has not been implemented ");
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException("hasCode method has not been implemented ");
	}
}
