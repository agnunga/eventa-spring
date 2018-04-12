package com.ag.payment;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ConfirmHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange he) throws IOException{
           /* /
              handle the request which comes through the '/confirm' context
             /*/
            System.out.println("Request received");

            /*/
              Buffer and store the response in a string
             /*/
            BufferedReader br = new BufferedReader(new InputStreamReader(he.getRequestBody(), "UTF-8"));
            String line = "";
            StringBuilder buffer = new StringBuilder();
            while((line = br.readLine()) != null)
            {
                buffer.append(line);
            }

            /*/
              Once buffered, you can perform any other processing
              you need on the buffered response e.g. print out the response...
             /*/
            System.out.println("Res: " + buffer.toString());

            /*/
              Prepare the response, assuming no errors have occurred. Any response
              other than a 0 (zero) for the 'ResultCode' during Validation means an
              error occurred and the transaction is cancelled
             /*/
            JSONObject obj = new JSONObject();
            obj.put("ResultCode", 0);
            obj.put("ResultDesc", "The service was accepted successfully");
            obj.put("ThirdPartyTransID", "1234567890");

           /* /
              Respond to the server appropriately
             /*/
            String res = obj.toJSONString();
            he.sendResponseHeaders(200, res.length());
            OutputStream os = he.getResponseBody();
            os.write(res.getBytes("UTF-8"));
            os.close();
        }
    }
