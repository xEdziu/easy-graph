package me.goral.easygraph.classes;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyListGraph extends Graph {
    private List<List<Integer>> adjList;

    public AdjacencyListGraph() {
        super();
        adjList = new ArrayList<>();
    }

    public void addVertex() {
        adjList.add(new ArrayList<>());
        numVertices++;
    }

    public void addEdge(int v, int w) {
        if (v >= numVertices || w >= numVertices || v < 0 || w < 0) {
            throw new IllegalArgumentException("Vertex index out of bound");
        }
        adjList.get(v).add(w);
        adjList.get(w).add(v);
        numEdges++;
    }


    public boolean isAdjacent(int v, int w) {
        return adjList.get(v).contains(w);
    }

    public void removeVertex(int v) {
        if (v >= numVertices || v < 0) {
            throw new IllegalArgumentException("Vertex index out of bound");
        }
        adjList.set(v, null); // Set to null rather than removing to keep index stable
        for (List<Integer> neighbors : adjList) {
            if (neighbors != null) {
                neighbors.remove(Integer.valueOf(v));
            }
        }
        numVertices--;
    }


    public void removeEdge(int v, int w) {
        adjList.get(v).remove(w);
        adjList.get(w).remove(v);
        numEdges--;
    }
}
