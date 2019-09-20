package com.delfino.sgbus;

import com.delfino.sgbus.graphdb.Edge;
import com.delfino.sgbus.graphdb.Node;
import com.delfino.sgbus.model.BusStop;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BusStopDb {

    private static final double RADIUS = 0.003;

    private Map<Integer, BusStop> busStopMap;
    private Map<Integer, Node<BusStop>> graphDb;

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#####");
    private int counter = 0;

    public void initializeDb() throws IOException {

        InputStream in = getClass().getClassLoader()
                .getResourceAsStream("busstops.json");

        ObjectMapper mapper = new ObjectMapper();
        JavaType type =
                mapper.getTypeFactory().constructMapLikeType(Map.class, Integer.class, BusStop.class);

        busStopMap = mapper.readValue(in, type);
        System.out.println(busStopMap.values().stream()
            .max((b1, b2) -> Integer.compare(b1.name.length(), b2.name.length())).get().name);
        busStopMap.entrySet().forEach(e -> e.getValue().id = e.getKey());
    }

    public Map<Integer, BusStop> getBusStopMap() throws IOException {
        if (busStopMap == null) {
            initializeDb();
      //      initializeGraphDb();
        }
        return busStopMap;
    }


    public BusStop getBusStop(int stopId) throws IOException {

        return getBusStopMap().get(stopId);
    }

    public Node getBusStopInfo(int stopId) {

        return graphDb.get(stopId);
    }

    public void initializeGraphDb() {

        graphDb = busStopMap.entrySet().stream()
                .collect(Collectors.toMap(n -> n.getKey(), n -> new Node(n.getValue())));

        graphDb.values().stream().forEach(n -> generateEdgesForNearbyStops(n, graphDb.values()));
    }

    private void generateEdgesForNearbyStops(Node<BusStop> currentNode, Collection<Node<BusStop>> nodes) {

        List<Node<BusStop>> nearbyStops = nodes.stream()
                .filter(n -> !n.equals(currentNode))
                .filter(n -> nearby(n.value, currentNode.value))
                .collect(Collectors.toList());
        nearbyStops.stream().forEach(n -> {
            Edge edge = new Edge(currentNode, n);
            edge.properties.put("distance", getHypotenuse(n.value, currentNode.value));
            currentNode.edges.add(edge);
        });
        Collections.sort(currentNode.edges,
                (e1, e2) -> Double.compare((double)e1.properties.get("distance"),
                        (double)e2.properties.get("distance")));
        ++counter;
        if (counter % 500 == 0)
            System.out.println(++counter + " nodes completed");
    }

    private double getHypotenuse(BusStop b1, BusStop b2) {

        double ac = Math.abs(b2.lng - b1.lng);
        double cb = Math.abs(b2.lat - b1.lat);
        double hyp = Math.hypot(ac, cb);
        return hyp;
    }

    public boolean nearby(BusStop b1, BusStop b2) {

        double latDiff = Math.abs(b1.lat - b2.lat);
        double lngDiff = Math.abs(b1.lng - b2.lng);
        double hyp = getHypotenuse(b1, b2);

        return latDiff < RADIUS
                && lngDiff < RADIUS
                && hyp < RADIUS;
    }

    public List<BusStop> getNearbyBusStops(double lat, double lng) throws IOException {

        BusStop currentBs = new BusStop();
        currentBs.lat = lat;
        currentBs.lng = lng;
        return getBusStopMap().values().stream().filter(b -> nearby(b, currentBs))
                .map(b -> Pair.of(b, getHypotenuse(currentBs, b)))
                .sorted((p1, p2) -> p1.getRight().compareTo(p2.getRight()))
                .map(p1 -> p1.getLeft())
                .collect(Collectors.toList());
    }
}
