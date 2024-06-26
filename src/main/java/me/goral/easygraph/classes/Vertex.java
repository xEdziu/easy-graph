package me.goral.easygraph.classes;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<?> vertex = (Vertex<?>) o;
        return Objects.equals(element, vertex.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element);
    }
}
