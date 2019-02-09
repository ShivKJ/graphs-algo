package algo.graphs.coloring;

import static java.util.Arrays.sort;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingInt;

import java.util.LinkedList;
import java.util.List;

import algo.graphs.Edge;
import algo.graphs.Vertex;

public class Genetics {
    Vertex[]       vertices;
    Edge<Vertex>[] adjacency;

    public Genetics(Vertex[] vertices, Edge<Vertex>[] adjacency, int initialColors) {
        this.vertices = vertices;
        this.adjacency = adjacency;

        @SuppressWarnings("unchecked")
        List<Vertex>[] groups = new List[initialColors];

        for (int i = 0; i < initialColors; i++)
            groups[i] = new LinkedList<>();

        for (Vertex v : vertices)
            groups[(int) v.userData()].add(v);

        sort(groups, reverseOrder(comparingInt(List::size)));
        
    }

}
