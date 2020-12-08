package algo.graph.coloring;

import static java.lang.System.out;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.joining;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.Vertex;
import algo.graphs.coloring.Greedy;
import algo.graphs.coloring.WelshAndPowell;

public class Test {

	public static void main(String[] args) {
		if (args.length == 0)
			args = new String[] { "data/coloring/gc_250_9", "1" };

		Graph<Vertex, Edge<Vertex>> graph = new GraphImpl(get(args[0]));

		switch (args[1]) {
		case "0":
			out.println(new Greedy(graph).color() + " " + 0);
			break;
		case "1":
			out.println(new WelshAndPowell(graph).color() + " " + 0);
			break;
		}

		out.println(graph.vertices().stream().map(Vertex::userData).map(String::valueOf).collect(joining(" ")));

	}

}
