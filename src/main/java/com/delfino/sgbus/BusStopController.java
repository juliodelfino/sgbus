package com.delfino.sgbus;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BusStopController extends HttpServlet {

    private BusStopDb busStopDb = new BusStopDb();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String strLat = req.getParameter("lat");
        String strLong = req.getParameter("long");
        double latitude = Double.parseDouble(strLat);
        double longitude = Double.parseDouble(strLong);

        resp.setContentType("text/json");

        resp.getWriter().println(busStopDb.getNearbyBusStops(latitude, longitude));
    }
}