package me.goral.easygraph.classes;

import java.util.Optional;

public abstract class Graph<V, E> {
    private final boolean isDirected; // Determines if the graph is directed

    public Graph(boolean isDirected) {
        this.isDirected = isDirected;
    }

    /**
     * Returns the number of vertices of the graph
     * @return the number of vertices of the graph
     */
    public abstract int numVertices();

    /**
     * Returns an iteration of all the vertices of the graph
     * @return an iteration of all the vertices of the graph
     */
    public abstract Iterable<Vertex<V>> vertices();

    /**
     * Returns the number of edges of the graph
     * @return the number of edges of the graph
     */
    public abstract int numEdges();

    /**
     * Returns an iteration of all the edges of the graph
     * @return an iteration of all the edges of the graph
     */
    public abstract Iterable<Edge<E>> edges();

    /**
     * Checks if two vertices are adjacent
     * @param u vertex u
     * @param v vertex v
     * @return true if u and v are adjacent, false otherwise
     */
    public abstract boolean areAdjacent(Vertex<V> u, Vertex<V> v);

    /**
     * Returns the edge from vertex u to vertex v, if one exists;
     * otherwise return null. For an undirected graph, there is no
     * difference between getEdge(u, v) and getEdge(v, u)
     * @param u vertex u
     * @param v vertex v
     * @return the edge between u and v, if one exists; otherwise null
     */
    public abstract Optional<Edge<E>> getEdge(Vertex<V> u, Vertex<V> v);

    /**
     * Returns an array containing the two endpoint vertices of
     * edge e. If the graph is directed, the first vertex is the origin
     * and the second is the destination
     * @param e edge e
     * @return an array containing the two endpoint vertices of edge e
     */
    public abstract Vertex<V>[] endVertices(Edge<E> e);

    /**
     * For edge e incident to vertex v, returns the other vertex of
     * the edge; an error occurs if e is not incident to v
     * @param v vertex v
     * @param e edge e
     * @return the other vertex of the edge
     */
    public abstract Vertex<V> opposite(Vertex<V> v, Edge<E> e);

    /**
     * Returns the number of outgoing edges from vertex v
     * @param v vertex v
     * @return the number of outgoing edges from vertex v
     */
    public abstract int outDegree(Vertex<V> v);

    /**
     * Returns the number of incoming edges to vertex v. For
     * an undirected graph, this returns the same value as does
     * outDegree(v)
     * @param v vertex v
     * @return the number of incoming edges to vertex v
     */
    public abstract int inDegree(Vertex<V> v);

    /**
     * Returns an iteration of all outgoing edges from vertex v
     * @param v vertex v
     * @return an iteration of all outgoing edges from vertex v
     */
    public abstract Iterable<Edge<E>> outgoingEdges(Vertex<V> v);

    /**
     * Returns an iteration of all incoming edges to vertex v. For
     * an undirected graph, this returns the same collection as
     * does outgoingEdges(v)
     * @param v vertex v
     * @return an iteration of all incoming edges to vertex v
     */
    public abstract Iterable<Edge<E>> incomingEdges(Vertex<V> v);

    /**
     * Creates and returns a new Vertex storing element x
     * @param x element x
     * @return a new Vertex storing element x
     */
    public abstract Vertex<V> insertVertex(V x);

    /**
     * Creates and returns a new Edge from vertex u to vertex v,
     * storing element x; an error occurs if there already exists an
     * edge from u to v
     * @param u vertex u
     * @param v vertex v
     * @param x element x
     * @return a new Edge from vertex u to vertex v, storing element x
     */
    public abstract Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E x);

    /**
     * Removes vertex v and all its incident edges from the graph
     * @param v vertex v
     */
    public abstract void removeVertex(Vertex<V> v);

    /**
     * Removes edge e from the graph
     * @param e edge e
     */
    public abstract void removeEdge(Edge<E> e);

    /**
     * Checks if the graph is directed
     * @return true if the graph is directed, false otherwise
     */
    public boolean isDirected() {
        return this.isDirected;
    }

    /**
     * Prints the graph
     */
    public abstract void printGraph();
}
