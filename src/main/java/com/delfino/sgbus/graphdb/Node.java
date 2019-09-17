package com.delfino.sgbus.graphdb;

import java.util.*;

public class Node<T> {

    public List<Edge> edges = new ArrayList();
    public T value;

    public Node(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }


}
