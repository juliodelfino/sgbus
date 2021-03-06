package com.delfino.sgbus;

import com.delfino.sgbus.model.Bus;
import com.delfino.sgbus.util.ApiParserUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BusApiCrawler implements ServletContextListener {

    private ScheduledExecutorService executor;
    private String url = "https://s3-ap-southeast-1.amazonaws.com/lta-eta-web-2/bus_arrival.baf3.js";
    private ApiParserUtil parserUtil;

    public BusApiCrawler() throws IOException {
        parserUtil = ApiParserUtil.getInstance();
    }

    public void contextInitialized(ServletContextEvent event) {
        executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {

            try {
                Map<String, List<Bus>> timingsMap = parserUtil.parseLtaData(new URL(url));
                BusStopDb.getInstance().setBusTimes(timingsMap);
                System.out.println("Done fetching from lta server");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        executor.scheduleWithFixedDelay(task, 5,20, TimeUnit.SECONDS);
    }

    public void contextDestroyed(ServletContextEvent event) {
        executor.shutdown();
    }

}