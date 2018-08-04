/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.martinstengard.conversions.temperature;

public class Response {
    public String fromValue;
    public Scale fromScale;
    public String toValue;
    public Scale toScale;
    public String formula;
    public String calculation;

    public Response(String fromValue, Scale fromScale, Scale toScale){
        this.fromValue = fromValue;
        this.fromScale = fromScale;
        this.toScale = toScale;
    }
}