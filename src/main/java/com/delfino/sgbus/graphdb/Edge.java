package com.delfino.sgbus.graphdb;

import com.delfino.sgbus.model.BusStop;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Edge {

    public Node local;
    public Node remote;

    public Map properties = new HashMap();

    public Edge(Node<BusStop> local, Node<BusStop> remote) {
        this.local = local;
        this.remote = remote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(local, edge.local) &&
                Objects.equals(remote, edge.remote);
    }

    @Override
    public int hashCode() {

        return Objects.hash(local, remote);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "local=" + local.value +
                ", remote=" + remote.value +
                ", properties=" + properties +
                '}';
    }
}
