package com.delfino.sgbus;

import com.delfino.sgbus.model.Bus;
import com.delfino.sgbus.util.ApiParserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class LtaDataController extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();
    private String url = "https://s3-ap-southeast-1.amazonaws.com/lta-eta-web-2/bus_arrival.baf3.js";

    public LtaDataController() {
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String action = req.getParameter("action");

        if (action.equals("cron")) {

            for (int i = 0; i < 3; i++) {
                Map<String, List<Bus>> timingsMap = ApiParserUtil.getInstance().parseLtaData(new URL(url));
                BusStopDb.getInstance().setBusTimes(timingsMap);
                System.out.println("Done fetching from lta server");
                try {
                    Thread.sleep(17000);
                } catch (InterruptedException e) {
                    throw new IOException(e.getMessage(), e);
                }
            }
            resp.getWriter().println("Done");
        } else if (action.equals("print")) {
            Map<String, List<Bus>> timingsMap = ApiParserUtil.getInstance().parseLtaData(new URL(url));
            BusStopDb.getInstance().setBusTimes(timingsMap);
            resp.setContentType("text/json");
            resp.getWriter().println(
                    objectMapper.writeValueAsString(timingsMap));
        }
    }
}