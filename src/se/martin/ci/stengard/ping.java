/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.martin.ci.stengard;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

/**
 *
 * @author mst
 */
public class ping {
    
    public static String ip(String ip){
      try {
        InetAddress inet = InetAddress.getByName(ip);

        if (inet.isReachable(5000)){
          return ip + " is reachable.";
        } else {
          return ip + " NOT reachable.";
        }
      } catch ( IOException e ) {
        return "Exception: " + e.getMessage();
      }
    }
    
    public static String url(String url){
        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            return connection.getResponseCode() + " " + connection.getResponseMessage();

        } catch (IOException e) {
            return "Exception: " + e.getMessage();
        }
    }
    
}

 