package com.delfino.sgbus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class BusStop {

    public int id;

    public Double lat;

    public Double lng;

    public String name;

    @JsonIgnore
    public boolean left;

    @Override
    public String toString() {
        return "BusStop{" +
                "id=" + id +
                ", lat=" + lat +
                ", lng=" + lng +
                ", name='" + name + '\'' +
                ", left=" + left +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusStop busStop = (BusStop) o;
        return id == busStop.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
