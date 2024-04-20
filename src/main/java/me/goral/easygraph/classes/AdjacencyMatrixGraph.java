package me.goral.easygraph.classes;

import java.util.*;

public class AdjacencyMatrixGraph<V, E> extends Graph<V, E> {
    private final List<Vertex<V>> vertices;
    private Edge<E>[][] adjacencyMatrix;

    public AdjacencyMatrixGraph(boolean isDirected) {
        super(isDirected);
        vertices = new ArrayList<>();
        adjacencyMatrix = (Edge<E>[][]) new Edge[0][0];
    }

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertices;
    }

    @Override
    public int numEdges() {
        int count = 0;
        for (Edge<E>[] matrix : adjacencyMatrix) {
            for (Edge<E> eEdge : matrix) {
                if (eEdge != null) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public Iterable<Edge<E>> edges() {
        List<Edge<E>> result = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] != null && (i < j || isDirected())) {
                    result.add(adjacencyMatrix[i][j]);
                }
            }
        }
        return result;
    }

    @Override
    public boolean areAdjacent(Vertex<V> u, Vertex<V> v) {
        int indexU = vertices.indexOf(u);
        int indexV = vertices.indexOf(v);
        return adjacencyMatrix[indexU][indexV] != null || adjacencyMatrix[indexV][indexU] != null;
    }

    @Override
    public Optional<Edge<E>> getEdge(Vertex<V> u, Vertex<V> v) {
        int indexU = vertices.indexOf(u);
        int indexV = vertices.indexOf(v);
        return Optional.ofNullable(adjacencyMatrix[indexU][indexV]);
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) {
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (adjacencyMatrix[i][j] == e) {
                    return new Vertex[] {vertices.get(i), vertices.get(j)};
                }
            }
        }
        throw new IllegalArgumentException("Edge not in graph.");
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) {
        Vertex<V>[] endpoints = endVertices(e);
        if (endpoints[0].equals(v)) {
            return endpoints[1];
        } else if (endpoints[1].equals(v)) {
            return endpoints[0];
        }
        throw new IllegalArgumentException("Edge not incident to vertex.");
    }

    @Override
    public int outDegree(Vertex<V> v) {
        int indexV = vertices.indexOf(v);
        int count = 0;
        for (int j = 0; j < vertices.size(); j++) {
            if (adjacencyMatrix[indexV][j] != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int inDegree(Vertex<V> v) {
        int indexV = vertices.indexOf(v);
        int count = 0;
        for (int i = 0; i < vertices.size(); i++) {
            if (adjacencyMatrix[i][indexV] != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) {
        List<Edge<E>> result = new ArrayList<>();
        int indexV = vertices.indexOf(v);
        for (int j = 0; j < vertices.size(); j++) {
            if (adjacencyMatrix[indexV][j] != null) {
                result.add(adjacencyMatrix[indexV][j]);
            }
        }
        return result;
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) {
        List<Edge<E>> result = new ArrayList<>();
        int indexV = vertices.indexOf(v);
        for (int i = 0; i < vertices.size(); i++) {
            if (adjacencyMatrix[i][indexV] != null) {
                result.add(adjacencyMatrix[i][indexV]);
            }
        }
        return result;
    }

    @Override
    public Vertex<V> insertVertex(V x) {
        Vertex<V> newVertex = new Vertex<>(x);
        vertices.add(newVertex);
        int newSize = vertices.size();
        Edge<E>[][] newMatrix = (Edge<E>[][]) new Edge[newSize][newSize];

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, adjacencyMatrix.length);
        }
        adjacencyMatrix = newMatrix; // Update the adjacency matrix to the new larger matrix
        return newVertex;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E x) {
        int indexU = vertices.indexOf(u);
        int indexV = vertices.indexOf(v);
        if (indexU == -1 || indexV == -1)
            throw new IllegalArgumentException("One or more vertices not found");

        if (adjacencyMatrix[indexU][indexV] != null)
            throw new IllegalArgumentException("Edge already exists between the given vertices");

        Edge<E> newEdge = new Edge<>(x);
        adjacencyMatrix[indexU][indexV] = newEdge;
        if (!isDirected()) {
            adjacencyMatrix[indexV][indexU] = newEdge;
        }
        return newEdge;
    }

    @Override
    public void removeVertex(Vertex<V> v) {
        int indexV = vertices.indexOf(v);
        if (indexV == -1)
            throw new IllegalArgumentException("Vertex not found");

        vertices.remove(indexV);
        int newSize = vertices.size();
        Edge<E>[][] newMatrix = (Edge<E>[][]) new Edge[newSize][newSize];

        for (int i = 0, k = 0; i <= newSize; i++) {
            if (i == indexV) continue; // Skip the removed vertex
            for (int j = 0, l = 0; j <= newSize; j++) {
                if (j == indexV) continue; // Skip the removed vertex
                newMatrix[k][l] = adjacencyMatrix[i][j];
                l++;
            }
            k++;
        }
        adjacencyMatrix = newMatrix;
    }

    @Override
    public void removeEdge(Edge<E> e) {
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (adjacencyMatrix[i][j] == e) {
                    adjacencyMatrix[i][j] = null;
                    if (!isDirected()) {
                        adjacencyMatrix[j][i] = null;
                    }
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Edge not found in the graph");
    }
}
