
package se.martinstengard;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

public class Ping {
    /**
     * PingResponse contain result of called method.
     * This class will always be returned from all calls.
     */
    public class PingResponse{
        public String dns = "";
        public String ip = "";
        public String message = "";
        /**
         * success is true if supplied ip or dns address is reachable.
         */
        public Boolean succees = false;
        public Boolean hasError = false;
        public String errorMessage = "";
        
        @Override
        public String toString(){
            return "Dns: " + dns + "\n" +
            "Ip: " + ip + "\n" +
            "Message: " + message + "\n" +
            "Success: " + succees + "\n" +
            "Has Error: " + hasError + "\n" +
            "Error Message: " + errorMessage;
        }

    }
    
    public class UrlResponse{
        public Integer code = 0;
        public String message = "";
        public Boolean hasError = false;
        public String errorMessage = "";
        
        @Override
        public String toString(){
            return "Code: " + code + "\n" +
            "Message: " + message + "\n" +
            "Error Message: " + errorMessage;
        }
    }
    
    /**
     * Check if ip address is reachable. 
     * 
     * @param ipAddress ip address to test; for example 123.123.123.123.
     * @return PingResponse 
     */
    public PingResponse ip(String ipAddress){
        return testIpOrDns(ipAddress);
    }

    /**
     * Check if dns address is reachable. 
     * 
     * @param dnsAddress dns name to test, without protocol; for example java.sun.com.
     * @return PingResponse 
     */
    public PingResponse dns(String dnsAddress){
        return testIpOrDns(dnsAddress);
    }
   
    /**
     * Check if ip or dns address is reachable. 
     * Will always return a PingResponse object.
     * 
     * @param ipOrDns dns name to test, without protocol; for example java.sun.com.
     *                ip address to test; for example 123.123.123.123.
     * @return PingResponse 
     */
    private PingResponse testIpOrDns(String ipOrDns){
        PingResponse pingResponse = new PingResponse();

        try {
            InetAddress inet = InetAddress.getByName(ipOrDns);

            if(inet == null){
                pingResponse.errorMessage = ipOrDns + " could not be looked up.";
                pingResponse.hasError = true;
                return pingResponse;
            }

            pingResponse.dns = inet.getHostName();
            pingResponse.ip = inet.getHostAddress();
            
            if (inet.isReachable(5000)){
                pingResponse.message = ipOrDns + " is reachable.";
                pingResponse.succees = true;
            } else {
                pingResponse.message = ipOrDns + " is NOT reachable.";
            }

            return pingResponse;
            
        } catch (Exception e ) {
            pingResponse.errorMessage = "Unhandled error: " + e.getMessage();
            pingResponse.hasError = true;
            return pingResponse;
        }
    }

    /**
     * Performs a GET request for supplied url.
     * Will always return a UrlResponse object.
     * @param url web url to test; for example https://www.oracle.com/index.html.
     * @return UrlResponse
     */
    public UrlResponse url(String url){
        UrlResponse urlResponse = new UrlResponse();
        
        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            urlResponse.code = connection.getResponseCode();
            urlResponse.message = connection.getResponseMessage();
            
            return urlResponse;

        } catch (Exception e) {
            urlResponse.hasError = true;
            urlResponse.errorMessage = e.getMessage();
            return urlResponse;
        }
    }
    
    
}

 