package me.goral.easygraph.classes;

public abstract class Graph {
    protected int numVertices; // liczba wierzchołków
    protected int numEdges; // liczba krawędzi

    public Graph() {
        numVertices = 0;
        numEdges = 0;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public abstract void addVertex();
    public abstract void addEdge(int v, int w);
    public abstract void removeVertex(int v);
    public abstract void removeEdge(int v, int w);
    public abstract boolean isAdjacent(int v, int w);
}
