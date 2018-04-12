package com.ag.payment;//Requires the following libraries. Maven repositories given below
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import org.json.simple.JSONObject;

public class Main{
    private HttpServer server;
    public Main()    {
    try {
            //Define the port over which the listener will accept requests
            int port = 8080;

            server = HttpServer.create(new InetSocketAddress(port), 0);
            /*/
              Create the context (endpoint/URL) which will be triggered when the request
              hits the above specified port. This will resolve to a URL like
              'http://address:port/context'. E.g. the context below would
              resolve to 'http://127.0.0.1:80/confirm' on the local computer. Then
              the Handler will handle the request received via the given URL.
             
              You may create a separate context for every endpoint you need
            /*/
            server.createContext("/confirm", new ConfirmHandler());

            server.setExecutor(null);

            //start the server
            server.start();
            System.out.println("Server started");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /*/
      Class to handle incoming requests based on specified Contexts, you can
      create a class for each separate context/URL
     /*/

    public static void main(String args[]){
        Main main = new Main();
    }
}
