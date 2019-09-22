package com.delfino.sgbus;

import com.delfino.sgbus.model.Bus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.repackaged.com.google.common.collect.ImmutableMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusController extends HttpServlet {

    private BusStopDb busStopDb = BusStopDb.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper();

    public BusController() {
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String stopId = req.getParameter("id");

        resp.setContentType("text/json");

        resp.getWriter().println(
            objectMapper.writeValueAsString(
                    ImmutableMap.of("services",
                    busStopDb.getBusTimes(stopId))));
    }
}