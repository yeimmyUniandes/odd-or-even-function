package com.microsoft.azure;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    @FunctionName("HttpTrigger-Java")
    public HttpResponseMessage run(@HttpTrigger(name = "req", methods = { HttpMethod.GET,
            HttpMethod.POST }, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");
        String numberQueryValue = request.getQueryParameters().get("number");
        try {
            int number = Integer.parseInt(numberQueryValue);
            String response = String.format("El número %d es %s.", number, number % 2 == 0 ? "Par" : "Impar");

            return request.createResponseBuilder(HttpStatus.OK).body(response).build();
        } catch (NumberFormatException nfe) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Unable to parse " + numberQueryValue)
                    .build();
        }
    }
}
