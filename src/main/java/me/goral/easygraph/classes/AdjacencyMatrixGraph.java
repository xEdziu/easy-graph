package me.goral.easygraph.classes;

public class AdjacencyMatrixGraph extends Graph {
    private boolean[][] adjMatrix;

    public AdjacencyMatrixGraph(int numVertices) {
        super();
        this.numVertices = numVertices;
        adjMatrix = new boolean[numVertices][numVertices];
    }

    public void addEdge(int v, int w) {
        if (v >= numVertices || w >= numVertices || v < 0 || w < 0) {
            throw new IllegalArgumentException("Vertex index out of bound");
        }
        if (!adjMatrix[v][w]) {  // Only add edge if it does not already exist
            adjMatrix[v][w] = true;
            adjMatrix[w][v] = true;
            numEdges++;
        }
    }


    public boolean isAdjacent(int v, int w) {
        return adjMatrix[v][w];
    }

    public void removeEdge(int v, int w) {
        if (v >= numVertices || w >= numVertices || v < 0 || w < 0) {
            throw new IllegalArgumentException("Vertex index out of bound");
        }
        if (adjMatrix[v][w]) {  // Only remove edge if it exists
            adjMatrix[v][w] = false;
            adjMatrix[w][v] = false;
            numEdges--;
        }
    }


    public void addVertex() {
        boolean[][] newAdjMatrix = new boolean[numVertices + 1][numVertices + 1];
        for (int i = 0; i < numVertices; i++) {
            System.arraycopy(adjMatrix[i], 0, newAdjMatrix[i], 0, numVertices);
        }
        adjMatrix = newAdjMatrix;
        numVertices++;
    }

    public void removeVertex(int v) {
        boolean[][] newAdjMatrix = new boolean[numVertices - 1][numVertices - 1];
        for (int i = 0; i < numVertices; i++) {
            if (i == v) {
                continue;
            }
            for (int j = 0; j < numVertices; j++) {
                if (j == v) {
                    continue;
                }
                newAdjMatrix[i < v ? i : i - 1][j < v ? j : j - 1] = adjMatrix[i][j];
            }
        }
        adjMatrix = newAdjMatrix;
        numVertices--;
    }
}

