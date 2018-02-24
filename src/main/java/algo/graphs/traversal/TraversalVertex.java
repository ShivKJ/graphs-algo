package algo.graphs.traversal;

import algo.graphs.Vertex;

public class TraversalVertex implements Vertex {
	private TraversalVertex		parent;
	private Object				data;
	private VertexTraversalCode	code;

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

}
