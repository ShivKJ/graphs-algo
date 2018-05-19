package algo.graphs.traversal;

import static algo.graphs.traversal.VertexTraversalCode.NEW;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.Map;

import algo.heap.IndexedPNode;
import algo.heap.IndexedPNodeImpl;

public final class Utils {
	private Utils() {}

	public static <T extends TraversalVertex, P extends Comparable<P>> Map<T, IndexedPNode<T, P>> traversalVertexToPQNode(Collection<T> vertices,
	    P defaultVal) {
		return vertices.stream()
		               .collect(toMap(identity(), e -> new IndexedPNodeImpl<>(e, defaultVal)));
	}

	public static void labelAsNew(Collection<? extends TraversalVertex> vertices) {
		for (TraversalVertex traversalVertex : vertices) {
			traversalVertex.code(NEW);
			traversalVertex.setParent(null);
		}

	}
}
