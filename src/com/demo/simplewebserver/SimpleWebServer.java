package com.demo.simplewebserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Visit https://hightechdistrict.com for more articles, videos, interactive practice tests and AI Assistant features.
 * @author https://hightechdistrict.com
 *
 */
public class SimpleWebServer {

	public static void main(String[] args) {
	
		/**
		 * Listen on port 5000 and IP address 127.0.0.1 or reserved domain name localhost
		 */
		try (ServerSocket serverSocket = new ServerSocket(5000)) {
			
			
			System.out.println("Web Server accepted client connection.");
			System.out.println("-------------");
			
			while (true) {
				
				Socket clientSocket = serverSocket.accept();
				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
				
				//Get client request message
				String clientInput = input.readLine();
				System.out.println("Request String >>  "+ clientInput);
				String path = extractPath(clientInput);
				System.out.println("Client query >>  "+ path);
				System.out.println("-------------");
				//build response message
				String httpResponse = buildMessage(path);
				 
				//Send out response message to client
				 OutputStream output = clientSocket.getOutputStream();
		            output.write(httpResponse.getBytes());
		            output.flush();
				 
				//Close the connection
				clientSocket.close();
			}
		
		} catch (IOException e) {
			System.out.println("Server exception" + e.getMessage());
		}
		
		
	}
	
	
	/**
	 * This method is used to build response message.
	 * Please ask our AI Assistant more about one of the response headers: Access-Control-Allow-Origin: *
	 * 
	 * @param queryString Example value of queryString is: /toyota
	 * @return httpResponse that include Response header messages and response message.
	 */
	private static String buildMessage(String queryString) {
		
		String responseMessage = "";
		
		if (queryString.equalsIgnoreCase("/toyota")) {
			responseMessage = "Toyota price starting from 200,000";
		} else if (queryString.equalsIgnoreCase("/ferrari")) {
			responseMessage = "Ferrari price starting from 400,000";
		} else {
			responseMessage = "Sorry, we can only provide prices for Toyota or Ferrari. Thanks.";
		}
		
		 String httpResponse = "HTTP/1.1 200 OK\r\n" +
                 "Content-Type: text\r\n" +
                 "Connection: close\r\n" +
                 "Access-Control-Allow-Origin: *\r\n" +
                 "\r\n" +
                 "Server Response: "+responseMessage;
		 return httpResponse;
	}
	
	/**
	 * This method is used to extract client query.
	 * 
	 * @param requestLine   Example value of requestLine is: GET /toyota HTTP/1.1
	 * @return   Return only client query. For example: /toyota
	 */
	 private static String extractPath(String requestLine) {
	        if (requestLine != null && !requestLine.isEmpty()) {
	            // Split the request line by spaces
	            String[] parts = requestLine.split(" ");
	            if (parts.length >= 2) {
	                return parts[1]; // The path is the second part
	            }
	        }
	        return "";
	    }

}
