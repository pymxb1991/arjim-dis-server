package com.arjim.server.test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class IPTest {
    public static void main(String[] args) {

    }
    public static String getHostAddress() throws Exception {
        Enumeration netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    InetAddress ip = (InetAddress) ips.nextElement();
                    if (ip.isSiteLocalAddress()) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
        }
        return InetAddress.getLocalHost().getHostAddress();
    }
}
