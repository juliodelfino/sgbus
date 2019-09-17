package com.delfino.sgbus;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class BusStopDbTest {


    public void test() throws IOException {

        BusStopDb db = new BusStopDb();
        Object value = db.getBusStop(14101);
        System.out.println(value);
    }

    @Test
    public void testGetNearbyStopsByCoordinates() throws IOException {

        BusStopDb db = new BusStopDb();
        db.initializeDb();
        List<Integer> stops = db.getNearbyBusStops(1.277046,103.809943);
        System.out.println(stops);
    }



    public void testNearby() throws IOException {

        BusStopDb db = new BusStopDb();
        db.initializeDb();

        System.out.println(db.nearby(db.getBusStop(14101), db.getBusStop(14109)));

        System.out.println(db.nearby(db.getBusStop(14101), db.getBusStop(14089)));
        System.out.println(db.nearby(db.getBusStop(14101), db.getBusStop(65539)));
        System.out.println(db.nearby(db.getBusStop(14121), db.getBusStop(14129)));
        db.initializeGraphDb();

        System.out.println(db.getBusStopInfo(14101));
    }


}
