package se.martinstengard.conversions.temperature;

import java.text.DecimalFormat;

public class Convert {
    public Response run(Request request){
        Response r = new Response(request.fromValue, request.fromScale, request.toScale);
        double valueFrom = 0;
        double valueTo = 0;
        DecimalFormat df = new DecimalFormat("#0.00");
        
        try{
            // If input is not supplied set default value to 0 (zero).
            if(r.fromValue == null || r.fromValue.isEmpty()){
                r.fromValue = "0";
            };
            
            // Remove any whitespaces, replace comma to point and remove
            // everything but digits, decimal point and minus.
            valueFrom = new Double(r.fromValue.trim().replace(",", ".").replaceAll("[^\\d-.]", ""));
            r.fromValue = df.format(valueFrom);
            
        }catch(NumberFormatException e){
            // Should not be needed since we formatted user input above ... but just in case.
            System.out.println("Number format exception -> " + e.getMessage());
        }catch(Exception e){
            // Print any other exceptions.
            System.out.println("Unknown number exception -> " + e.getMessage());
        }
        
        // Concatenate conversion text "from->to" from the comboboxes.
        String conversionType = r.fromScale.toString().toLowerCase() + 
                        "->" + r.toScale.toString().toLowerCase();
        
        // Perform conversion depending on conversion type.
        switch(conversionType){
            case "celsius->fahrenheit":
                r.toValue = df.format(valueFrom * 9 / 5 + 32);
                r.formula = "T(°F) = T(°C) × 9/5 + 32";
                r.calculation = r.fromValue + "°C × 9/5 + 32 = " + r.toValue + "°F";
                break;
            case "celsius->kelvin":
                r.toValue = df.format(valueFrom + 273.15);
                r.formula = "T(K) = T(°C) + 273.15";
                r.calculation = r.fromValue + "°C + 273.15 = " + r.toValue + "K";
                break;
            case "celsius->rankine":
                r.toValue = df.format((valueFrom + 273.15) * 9/5);
                r.formula = "T(°R) = (T(°C) + 273.15) × 9/5";
                r.calculation = "(" + r.fromValue + "°C + 273.15) × 9/5 = " + r.toValue + "°R";
                break;
            case "fahrenheit->celsius":
                r.toValue = df.format((valueFrom - 32) * 5/9);
                r.formula = "T(°C) = (T(°F) - 32) × 5/9";
                r.calculation = "(" + r.fromValue + "°F - 32) × 5/9 = " + r.toValue + "°C";
                break;
            case "fahrenheit->kelvin":
                r.toValue = df.format((valueFrom - 459.67) * 5/9);
                r.formula = "T(K) = (T(°F) + 459.67) × 5/9";
                r.calculation = "(" + r.fromValue + "°F + 459.67) × 5/9 = " + r.toValue + "K";
                break;
            case "fahrenheit->rankine":
                r.toValue = df.format(valueFrom + 459.67);
                r.formula = "T(°R) = T(°F) + 459.67";
                r.calculation = r.fromValue + "°F + 459.67 = " + r.toValue + "°R";
                break;
            case "kelvin->celsius":
                r.toValue = df.format(valueFrom - 273.15);
                r.formula = "T(°C) = T(K) - 273.15";
                r.calculation = r.fromValue + "K - 273.15 = " + r.toValue + "°C";
                break;
            case "kelvin->fahrenheit":
                r.toValue = df.format(valueFrom * 9/5 - 459.67);
                r.formula = "T(°F) = T(K) × 9/5 - 459.67";
                r.calculation = r.fromValue + "K × 9/5 - 459.67 = " + r.toValue + "°F";
                break;
            case "kelvin->rankine":
                r.toValue = df.format(valueFrom + 9/5);
                r.formula = "T(°R) = T(K) × 9/5";
                r.calculation = r.fromValue + "K × 9/5 = " + r.toValue + "°R";
                break;
            case "rankine->celsius":
                r.toValue = df.format((valueFrom - 491.67) * 5/9);
                r.formula = "T(°C) = (T(°R) - 491.67) × 5/9";
                r.calculation = "(" + r.fromValue + "°R - 491.67) × 5/9 = " + r.toValue + "°C";
                break;
            case "rankine->fahrenheit":
                r.toValue = df.format(valueFrom - 459.67);
                r.formula = "T(°F) = T(°R) - 459.67";
                r.calculation = r.fromValue + "°R - 459.67 = " + r.toValue + "°F";
                break;
            case "rankine->kelvin":
                r.toValue = df.format(valueFrom + 5/9);
                r.formula = "T(K) = T(°R) × 5/9";
                r.calculation = r.fromValue + "°R × 5/9 = " + r.toValue + "K";
                break;
        }
        
        return r;
    }
}
