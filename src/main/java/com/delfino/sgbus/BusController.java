package com.delfino.sgbus;

import com.delfino.sgbus.model.Bus2;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

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

        Object services = StringUtils.isEmpty(req.getParameter("minify")) ?
                busStopDb.getBusTimes(stopId) :
                busStopDb.getBusTimes(stopId).stream()
                        .map(Bus2::convert).collect(Collectors.toList());
        resp.getWriter().println(
                objectMapper.writeValueAsString(
                        Pair.of("services", services)));
    }
}