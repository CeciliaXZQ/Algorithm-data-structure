import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
import se.kth.id1020.Edge;
import se.kth.id1020.Graph;
import se.kth.id1020.DataSource;
import se.kth.id1020.Vertex;

import java.util.*;

/**
 * Created by ceciliaX on 06/12/16.
 */
public class Paths {
    private static boolean[] marked;    // marked[v] = is there an s-v path?
    private static int count = 0;           // number of vertices connected to s
    private IndexMinPQ<Double> pq;
    private double[] distTo;      // distTo[v] = number of edges shortest s-v path
    private Edge[] edgeTo;

    public static void main(String[] args) {
        Graph g = DataSource.load();
        // work on g
       // System.out.println(g);

        System.out.println("How many subgraphs? " + count(g));

        System.out.println("ignore edge-weight? yes-1,no-0: ");

        if (StdIn.readInt() == 1) {
            Vertex[] pathResult = shortest(g, "Renyn", "Parses");
            System.out.println("Shortest path contains " + (pathResult.length - 1) + " jumps");
            System.out.println(Arrays.toString(pathResult));
        } else {
            int count = 0;
            Paths m = new Paths(g, 1006);
            if (m.hasPathTo(918, g)) {
                StdOut.printf("%d to %d (%.2f)  ", 1006, 918, m.distTo(918));
                for (Edge e : m.pathTo(918, g)) {
                    count++;
                    StdOut.print(e + "   ");
                }
                StdOut.println();
                StdOut.println();
                System.out.println("Shortest path contains " + count + " jumps");
            }
        }
    }


    public static boolean[] dfs(Graph G, int v, boolean[] marked) {
        marked[v] = true;
        count++;
        for (Edge w : G.adj(v)) {
            if (!marked[w.to]) {
                dfs(G, w.to, marked);

            }
        }
        return marked;
    }


    /**
     * Is there a path between the source vertex {@code s} and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    // public static boolean marked(int v) {
    // return marked[v];
    // }

    /**
     * Returns the number of vertices connected to the source vertex {@code s}.
     *
     * @return the number of vertices connected to the source vertex {@code s}
     */
    public static int count(Graph g) {
        boolean[] marked = new boolean[g.numberOfVertices()];
        int count = 0;

        for (int i = 0; i < marked.length; i++) {
            if (!marked[i]) {
                dfs(g, i, marked);
                count++;
            }
        }
        return count;
    }

    public static Vertex[] shortest(Graph g, String from, String to) {
        // Do a BFS, start at the 'from' node. Store all paths into pathTo.
        int[] edgeTo;     // last vertex on known path to this vertex
        Vertex vfrom = getVertex(g, from);
        Vertex vto = getVertex(g, to);

        boolean marked[] = new boolean[g.numberOfVertices()];
        int pathTo[] = new int[g.numberOfVertices()];

        Queue<Integer> queue = new LinkedList();
        queue.add(vfrom.id);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();

            for (Edge edge : g.adj(vertex)) {
                if (!marked[edge.to]) {
                    queue.add(edge.to);
                    marked[edge.to] = true;
                    pathTo[edge.to] = vertex;
                }

            }
        }
        // bfs(g, vfrom.id, marked);

        // Find the shortest path by following the path starting at to
        // and keep going until reach destination
        int pointer = vto.id;
        ArrayList<Integer> arr = new ArrayList();
        while (pointer != vfrom.id) {
            arr.add(pointer);
            pointer = pathTo[pointer];
        }
        arr.add(vfrom.id);

        // Reverse order
        Vertex[] result = new Vertex[arr.size()];
        for (int i = 0; i < arr.size(); i += 1) {
            result[result.length - i - 1] = getVertex(g, arr.get(i));
        }
        return result;
    }

    public Paths(Graph G, int s) {
        for (Edge e : G.edges()) {
            if (e.weight < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }

        distTo = new double[G.numberOfVertices()];
        edgeTo = new Edge[G.numberOfVertices()];

        for (int v = 0; v < G.numberOfVertices(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.numberOfVertices());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e : G.adj(v))
                relax(e);
        }
    }

    // relax edge e and update pq if changed
    private void relax(Edge e) {
        int v = e.from, w = e.to;
        if (distTo[w] > distTo[v] + e.weight) {
            distTo[w] = distTo[v] + e.weight;
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }

    private static Vertex getVertex(Graph g, String label) {
        for (Vertex v : g.vertices()) {
            if (v.label.equals(label)) {
                return v;
            }
        }
        return null;
    }

    private static Vertex getVertex(Graph g, int id) {
        for (Vertex v : g.vertices()) {
            if (v.id == id) {
                return v;
            }
        }
        return null;
    }

    /**
     * Returns the length of a shortest path from the source vertex {@code s} to vertex {@code v}.
     *
     * @param v the destination vertex
     * @return the length of a shortest path from the source vertex {@code s} to vertex {@code v};
     * {@code Double.POSITIVE_INFINITY} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public double distTo(int v) {
        return distTo[v];
    }

    public Iterable<Edge> pathTo(int v,Graph g) {
        //validateVertex(v,);
        if (!hasPathTo(v,g)) return null;
        Stack<Edge> path = new Stack<Edge>();
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.from]) {
            path.push(e);
        }
        return path;
    }

    /**
     * Is there a path between the source vertex {@code s} (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v,Graph g) {
   //     validateVertex(v,g);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }




}

