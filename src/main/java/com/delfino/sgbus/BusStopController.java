package com.delfino.sgbus;

import com.delfino.sgbus.model.BusStop2;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class BusStopController extends HttpServlet {

    private BusStopDb busStopDb = BusStopDb.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String strLat = req.getParameter("lat");
        String strLong = req.getParameter("long");
        double latitude = Double.parseDouble(strLat);
        double longitude = Double.parseDouble(strLong);

        resp.setContentType("text/json");

        Object response = null;
        if (StringUtils.isEmpty(req.getParameter("minify"))) {
            response = busStopDb.getNearbyBusStops(latitude, longitude);
        } else {
            response = busStopDb.getNearbyBusStops(latitude, longitude).stream()
                    .map(BusStop2::convert).collect(Collectors.toList());
        }
        resp.getWriter().println(
            objectMapper.writeValueAsString(response));
    }
}