package com.delfino.sgbus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class BusStop2 {

    public int id;
    public String name;

    public static BusStop2 convert(BusStop bs) {

        BusStop2 bs2 = new BusStop2();
        bs2.id = bs.id;
        bs2.name = bs.name;
        return bs2;
    }
}
