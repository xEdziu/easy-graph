package me.goral.easygraph.classes;

public class Vertex<V> {
    private V element; // The element stored by this vertex
    private String label; // The label of the vertex

    public Vertex(V element) {
        this.element = element;
    }

    public V getElement() {
        return element;
    }

    public void setElement(V element) {
        this.element = element;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}


