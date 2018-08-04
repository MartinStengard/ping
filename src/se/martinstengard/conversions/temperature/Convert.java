package se.martinstengard.conversions.temperature;

import java.text.DecimalFormat;

public class Convert {
    public Response run(Request request){
        Response response = new Response(request.fromValue, request.fromScale, request.toScale);
        double valueFrom = 0;
        double valueTo = 0;
        DecimalFormat df = new DecimalFormat("#0.00");
        
        try{
            // If input is not supplied set default value to 0 (zero).
            if(response.fromValue == null || response.fromValue.isEmpty()){
                response.fromValue = "0";
            };
            
            // Remove any whitespaces, replace comma to point and remove
            // everything but digits, decimal point and minus.
            valueFrom = new Double(response.fromValue.trim().replace(",", ".").replaceAll("[^\\d-.]", ""));
            
        }catch(NumberFormatException e){
            // Should not be needed since we formatted user input above ... but just in case.
            System.out.println("Number format exception -> " + e.getMessage());
        }catch(Exception e){
            // Print any other exceptions.
            System.out.println("Unknown number exception -> " + e.getMessage());
        }
        
        // Concatenate conversion text "from->to" from the comboboxes.
        String conversionType = response.fromScale.toString().toLowerCase() + 
                        "->" + response.toScale.toString().toLowerCase();
        
        // Perform conversion depending on conversion type.
        if(conversionType.equals("celsius->fahrenheit")){
            valueTo = 32 + (valueFrom * 9 / 5);
            response.formula = "T(°F) = T(°C) × 9/5 + 32 ";
            response.calculation = df.format(valueFrom) + "°C × 9/5 + 32 = "+df.format(valueTo)+"°F";
        }else if(conversionType.equals("fahrenheit->celsius")){
            valueTo = ((valueFrom - 32) * 5) / 9;
            response.formula = "T(°C) = (T(°F) - 32) × 5/9";
            response.calculation = "("+df.format(valueFrom)+"°F - 32) × 5/9 = "+df.format(valueTo)+"°C";
        }

        // Set result to entity.
        response.fromValue = df.format(valueFrom);
        response.toValue = df.format(valueTo);
        
        return response;
    }
}
