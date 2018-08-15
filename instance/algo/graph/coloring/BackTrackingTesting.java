package algo.graph.coloring;

import static java.lang.Integer.parseInt;
import static java.nio.file.Paths.get;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.Vertex;
import algo.graphs.coloring.Backtracking;

public class BackTrackingTesting {
	public static void main(String[] args) {
		if (args.length == 0)
			args = new String[] { "data/coloring/gc_50_3", "6" };

		Graph<Vertex, Edge<Vertex>> graph = new GraphImpl(get(args[0]));

		System.out.println(Backtracking.hasSolution(graph, parseInt(args[1])));
	}
}
