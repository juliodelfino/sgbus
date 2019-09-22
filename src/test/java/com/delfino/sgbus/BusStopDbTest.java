package com.delfino.sgbus;

import com.delfino.sgbus.model.Bus;
import com.delfino.sgbus.model.BusStop;
import com.delfino.sgbus.util.ApiParserUtil;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BusStopDbTest {


    public void test() throws IOException {

        BusStopDb db = BusStopDb.getInstance();
        Object value = db.getBusStop(14101);
        System.out.println(value);
    }

    public void testGetNearbyStopsByCoordinates() throws IOException {

        BusStopDb db = BusStopDb.getInstance();
        db.initializeDb();
        List<BusStop> stops = db.getNearbyBusStops(1.277046,103.809943);
        System.out.println(stops);
    }



    public void testNearby() throws IOException {

        BusStopDb db = BusStopDb.getInstance();
        db.initializeDb();

        System.out.println(db.nearby(db.getBusStop(14101), db.getBusStop(14109)));

        System.out.println(db.nearby(db.getBusStop(14101), db.getBusStop(14089)));
        System.out.println(db.nearby(db.getBusStop(14101), db.getBusStop(65539)));
        System.out.println(db.nearby(db.getBusStop(14121), db.getBusStop(14129)));
        db.initializeGraphDb();

        System.out.println(db.getBusStopInfo(14101));
    }

    public void testParseLtaData() throws IOException {

        URL url = new URL("https://s3-ap-southeast-1.amazonaws.com/lta-eta-web-2/bus_arrival.baf3.js");
        URLConnection conn = url.openConnection();
        String response;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            response = reader.lines().collect(Collectors.joining("\n"));
        }

        System.out.println(response);
    }

    @Test
    public void testParseLtaData2() throws IOException {

        ApiParserUtil util = ApiParserUtil.getInstance();

        URL url = new URL("https://s3-ap-southeast-1.amazonaws.com/lta-eta-web-2/bus_arrival.baf3.js");
        Map<String, List<Bus>> timingsMap = util.parseLtaData(url);
        System.out.println(timingsMap.get("01012"));
        System.out.println(timingsMap.get("14331"));
    }



}
