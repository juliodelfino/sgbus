package com.delfino.sgbus.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BusTime {

    public Long duration_ms;

    public String toString() {
        return duration_ms + "";
    }
}
