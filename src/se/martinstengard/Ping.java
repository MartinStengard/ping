
package se.martinstengard;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Ping {

    private class PingResponse{
        public String dns = "";
        public String ip = "";
        public Boolean isReachable = false;
        public String errorMessage = "";
        
        @Override
        public String toString(){
            return "Dns: " + dns + "\n" +
            "Ip: " + ip + "\n" +
            "Is Reachable: " + isReachable + "\n" + 
            "Error Message: " + errorMessage;
        }

    }
    
    private class UrlResponse{
        public String url = "";
        public Integer httpStatusCode = 0;
        public String httpMessage = "";
        public String errorMessage = "";
        
        @Override
        public String toString(){
            return "Url: " + url + "\n" +
                    "Http Status Code: " + httpStatusCode + "\n" +
                    "Http Message: " + httpMessage + "\n" +
                    "Error Message: " + errorMessage;
        }
    }
    
    public class Response{
        public PingResponse pingResponse;
        public List<UrlResponse> urlResponses = new ArrayList<UrlResponse>();
        
        @Override
        public String toString(){
            String urlString = "";
            
            for(UrlResponse response : urlResponses){
                urlString += response.toString() + "\n\n";
            }
            
            return pingResponse.toString() + "\n\n" + urlString;
        }
    }
  
    /**
     * Check if ip or dns address is reachable. 
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
                return pingResponse;
            }

            pingResponse.dns = inet.getHostName();
            pingResponse.ip = inet.getHostAddress();
            
            if (inet.isReachable(1000)){
                pingResponse.isReachable = true;
            }

            return pingResponse;
            
        } catch (Exception e ) {
            pingResponse.errorMessage = "Unhandled error: " + e.getMessage();
            return pingResponse;
        }
    }

    /**
     * Performs a GET request for supplied url.
     * 
     * @param url web url to test; for example https://www.oracle.com/index.html.
     * @return List<UrlResponse> list with response for http and https.
     */
    private List<UrlResponse> testUrl(String url){
        List<UrlResponse> list = new ArrayList<UrlResponse>();
        
        for (String aString : new String[] {"http://", "https://"}) {
            UrlResponse response = new UrlResponse();

            try {
                URL siteURL = new URL(aString + url);
                HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                response.url = aString + url;
                response.httpStatusCode = connection.getResponseCode();
                response.httpMessage = connection.getResponseMessage();

            } catch (Exception e) {
                response.errorMessage = e.getMessage();
            }

            list.add(response);
        }
        
        return list;
    }
    
    /**
     * Test ping and return ip, http and https responses for supplied dns.
     * @param dns dns name to test, without protocol; for example java.sun.com.
     * @return Response Contain ip, http and https responses.
     */
    public Response test(String dns){
        // 1. Test as dns or ip.
        PingResponse pingResponse = testIpOrDns(dns);
        
        // 2. Test as url.
        List<UrlResponse> urlResponses = testUrl(dns);
        
        // 3. Add to one resultset.
        Response response = new Response();
        response.pingResponse = pingResponse;
        response.urlResponses = urlResponses;
        
        return response;
    }
}

 