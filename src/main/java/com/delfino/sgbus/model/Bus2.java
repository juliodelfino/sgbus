package com.delfino.sgbus.model;

public class Bus2 {

    public String no;
    public Long n1;
    public Long n2;
    public Long n3;

    @Override
    public String toString() {
        return "Bus{" +
                "no='" + no + '\'' +
                ", next=" + n1 +
                ", next2=" + n2 +
                ", next3=" + n3 +
                '}';
    }

    public static Bus2 convert(Bus bus) {

        Bus2 bus2 = new Bus2();
        bus2.no = bus.no;
        bus2.n1 = bus.next.duration_ms != null ? bus.next.duration_ms/60000 : null;
        bus2.n2 = bus.next2.duration_ms != null ? bus.next2.duration_ms/60000 : null;
        bus2.n3 = bus.next3.duration_ms != null ? bus.next3.duration_ms/60000 : null;
        return bus2;
    }
}
