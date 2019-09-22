package com.delfino.sgbus.util;

import com.delfino.sgbus.model.Bus;
import com.delfino.sgbus.model.BusTime;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ApiParserUtil {

    private Map<Character, String> CM;

    private static ApiParserUtil instance;
    public static ApiParserUtil getInstance() {
        if (instance == null) {
            instance = new ApiParserUtil();
        }
        return instance;
    }

    private ApiParserUtil() {
        CM = generateTimestampCharMap();
    }


    private Map generateTimestampCharMap() {

        Map<Character, String> charMap = new LinkedHashMap();
        for (char c = '0'; c <= '9'; c++) {
            charMap.put(c, String.format("%02d", ((int) c) - 48));
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            charMap.put(c, String.format("%02d", ((int) c) - 55));
        }
        for (char c = 'a'; c <= 'x'; c++) {
            charMap.put(c, String.format("%02d", ((int) c) - 61));
        }
        return charMap;
    }

    private List<BusTime> extractBusTimes(String encryptedTimes) {

        long now = System.currentTimeMillis();
        List<BusTime> busTimes = new ArrayList<>();
        for (int i = 0; i < encryptedTimes.length() - 10; i = i + 10) {
            String token = encryptedTimes.substring(i, i + 10);
            BusTime busTime = new BusTime();
            if (token.indexOf('-') < 0) {
                busTime.duration_ms = DatatypeConverter
                        .parseDateTime(decrypt(token)).getTimeInMillis() - now;
            }
            busTimes.add(busTime);
        }
        return busTimes;
    }

    private String decrypt(String text) {

        return "20" + CM.get(text.charAt(0)) + "-" + CM.get(text.charAt(1)) + "-" + CM.get(text.charAt(2)) + "T"
                + CM.get(text.charAt(3)) + ":" + CM.get(text.charAt(4)) + ":" + CM.get(text.charAt(5)) + "Z";
    }

    public Map<String,List<Bus>> parseLtaData(URL url) throws IOException {

        Map<String, List<Bus>> timingsMap = new HashMap();
        try (Scanner scanner = new Scanner(url.openStream(),
                StandardCharsets.UTF_8.toString()))
        {
            scanner.useDelimiter("\\$"); //bus stops are delimited by '$' sign
            while(scanner.hasNext()) {
                String busStop = scanner.next();
                String bsId = busStop.substring(0, busStop.indexOf('|'));
                if (bsId.length() > 5) {
                    bsId = bsId.substring(bsId.length() - 5, bsId.length());
                }
                List<Bus> buses = new ArrayList<>();
                for (String busInfo : busStop.substring(busStop.indexOf('|') + 1).split(";")) {
                    Bus bus = new Bus();
                    String[] tokens = busInfo.split(":");
                    bus.no = tokens[0];
                    List<BusTime> busTimes = extractBusTimes(tokens[1]);
                    bus.next = busTimes.get(0);
                    bus.next2 = busTimes.get(1);
                    bus.next3 = busTimes.get(2);
                    buses.add(bus);
                }
                timingsMap.put(bsId, buses);
            }
        }
        return timingsMap;
    }
}
