package com.delfino.sgbus;

import com.delfino.sgbus.model.Bus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.repackaged.com.google.common.collect.ImmutableMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BusMixedController extends HttpServlet {

    private BusStopDb busStopDb = BusStopDb.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper();

    public BusMixedController() {
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Map<String, Bus> busTimingsMap = new HashMap();
        String ids = req.getParameter("ids");
        Stream.of(ids.split(",")).forEach(id -> {
            String[] token = id.split("_");
            Bus bus = busStopDb.getBusTimes(token[0]).stream()
                    .filter(b -> b.no.equals(token[1])).findFirst().orElse(new Bus());
            busTimingsMap.put(id, bus);
        });

        resp.setContentType("text/json");

        resp.getWriter().println(
            objectMapper.writeValueAsString(busTimingsMap));
    }
}