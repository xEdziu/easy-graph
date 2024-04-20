package me.goral.easygraph.classes;

public class Edge<E> {
    private E element; // The element stored by this edge

    public Edge(E element) {
        this.element = element;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }
}
