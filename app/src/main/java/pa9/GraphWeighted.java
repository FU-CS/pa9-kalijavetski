package pa9;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class GraphWeighted {
    private LinkedList<LinkedList<Edge>> graph;

    public GraphWeighted(int n){
        this.graph = new LinkedList<>();
        for (int i=0; i<n; i++){
            graph.add(new LinkedList<Edge>());
        }
    }

    public void addWeightedEdge(int v, int w, int weight){
        Edge edge1 = new Edge(v,w,weight);
        // Edge edge2 = new Edge(w,v,weight);
        this.graph.get(v).add(edge1);
        // this.graph.get(w).add(edge2);
    }

    private static class Edge implements Comparable<Edge>{
        int source;
        int destination;
        int weight;
    
        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    
        // Compare edges based on their weight
        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    public int[] shortestPath(int v){
        int[] distances = new int[this.graph.size()];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[v] = 0;
        int vertices = this.graph.size();
        if (vertices == 0){
            int[] list = {};
            return list;
        }
        for (int i=0; i<vertices-1 ; i++){
            for (int j=0; j<vertices; j++){
                for (Edge edge:graph.get(j)){
                    int dest = edge.destination;
                    int weight = edge.weight;
                    if (distances[j] != Integer.MAX_VALUE && distances[j]+weight < distances[dest] ){
                        distances[dest] = distances[j] + weight;
                    }
                }
            }
        }
        return distances;
    }

    public boolean hasNegativeCycle(){
        boolean[] visited = new boolean[this.graph.size()];
        Arrays.fill(visited,false);
        boolean[] recurse_stack = new boolean[this.graph.size()];
        Arrays.fill(recurse_stack,false);
        int nodes = this.graph.size();
        if (this.graph.size()==0)
            return false;
        for (int i=0; i<nodes; i++){
            if (!visited[i] && cycleHelper(i, visited, recurse_stack)){
                return true;
            }
        }
        return false;
    }

    private boolean cycleHelper(int curr, boolean[] visited, boolean[] recurse_stack){
        if (recurse_stack[curr]==true){
            return true;
        }
        if (visited[curr] == true){
            return false;
        }
        visited[curr] = true;
        recurse_stack[curr] = true;
        LinkedList<Edge> neighbors = this.graph.get(curr);
        for (int i=0; i<neighbors.size(); i++){
            int neighbor = neighbors.get(i).destination;
            if (cycleHelper(neighbor,visited, recurse_stack))
                return true;
            }
        recurse_stack[curr] = false;
        return false;
    }


    public int[] minimumSpanningTreePrim(){
        int[] distances = new int[this.graph.size()];
        Arrays.fill(distances, Integer.MAX_VALUE);
        int[] tree = new int[this.graph.size()];
        Arrays.fill(tree, -1);
        boolean[] visited = new boolean[this.graph.size()];
        Arrays.fill(visited, false);
        int v = this.graph.get(0).get(0).source;
        distances[v] = 0;
        tree[v] = v;

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.add(new Edge(0,0,0));

        while (queue.isEmpty() != true){
            Edge curr_edge = queue.poll();
            int current = curr_edge.destination;
            if (visited[current] != true){
                visited[current] = true;
                LinkedList<Edge> neighbors = this.graph.get(current);
                for (int i=0; i<neighbors.size(); i++){
                    int neighbor = neighbors.get(i).destination;
                    int weight = neighbors.get(i).weight;
                    if (visited[neighbor]!= true && weight<distances[neighbor]){
                        distances[neighbor] = weight;
                        tree[neighbor] = current;
                        queue.add(new Edge(current, neighbor, distances[neighbor]));
                    }

                }
            }
        }
        return tree;
    }

    public int[] minimumSpanningTree(){
        int[] tree = new int[this.graph.size()];
        int vertices = this.graph.size();
        boolean[] visited = new boolean[this.graph.size()];
        Arrays.fill(visited, false);
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        for (int i=0; i<vertices; i++){
            queue.add(new Edge(this.graph.get(i).get(0).source,this.graph.get(i).get(0).destination,this.graph.get(i).get(0).weight));
        }
        for (int i=0; i<vertices; i++){
            if (visited[i] != true){
                visited[i] = true;
                tree[i] = queue.poll().source;
            }
        }
        return tree;

    }

}
