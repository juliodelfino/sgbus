package com.delfino.sgbus;

import com.delfino.sgbus.model.Bus;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    private BusStopDb busStopDb = new BusStopDb();
    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<String,BusesResponse> responses = new HashMap<>();

    public BusController() throws IOException {

        for (String file: Arrays.asList("buses_54009.json", "buses_08041.json")) {

            InputStream in = getClass().getClassLoader()
                    .getResourceAsStream(file);

            ObjectMapper mapper = new ObjectMapper();

            responses.put(file, mapper.readValue(in, BusesResponse.class));
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String stopId = req.getParameter("id");

        resp.setContentType("text/json");

        resp.getWriter().println(
            objectMapper.writeValueAsString(responses.get("buses_" + stopId + ".json")));
    }
}

class BusesResponse {

    public List<Bus> services;

}