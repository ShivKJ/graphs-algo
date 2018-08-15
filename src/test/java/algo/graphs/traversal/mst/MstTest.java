package algo.graphs.traversal.mst;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.traversal.mst.Basic.Vrtx;

public class MstTest {

    private static final double TOL    = 1e-7;
    private static final Random RANDOM = new Random(10L);

    private static void populate(Graph<Vrtx, Edge<Vrtx>> graph, int vs) {
        List<Vrtx> vertices = IntStream.range(0, vs)
                                       .mapToObj(String::valueOf)
                                       .map(Vrtx::new)
                                       .collect(toList());

        vertices.forEach(graph::addVertex);
        int max = 100 , MAX = 1000 , min = 20;
        for (int i = 0; i < vs; i++)
            for (int j = i + 1; j < vs; j++)
                if (i != j)
                    if (RANDOM.nextBoolean() && RANDOM.nextBoolean())
                        graph.connect(vertices.get(i), vertices.get(j), RANDOM.nextInt(MAX) + 1 + RANDOM.nextInt(min));
                    else
                        graph.connect(vertices.get(i), vertices.get(j), RANDOM.nextInt(max) + 1 + RANDOM.nextInt(min));

    }

    private static double weight(Graph<Vrtx, Edge<Vrtx>> graph) {
        return graph.edges().stream().mapToDouble(Edge::distance).sum() / 2;
    }

    @Test(timeout = 3_000)
    public void test() {
        for (int i = 0; i < 20; i++) {
            int size = 10 + RANDOM.nextInt(500);

            Graph<Vrtx, Edge<Vrtx>> graph = new Basic.ABCGraph();

            populate(graph, size);

            assertEquals(weight(MSTs.kruskal(graph)), weight(MSTs.prim(graph)), TOL);
        }
    }

}
