package com.delfino.sgbus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusTime {

    public String time;
    public long duration_ms;
}
