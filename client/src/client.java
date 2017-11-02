
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;



public class client {
	// instantiate Socket object
	static private Socket socket;
	// define constant called port, used to connect to the server
	static private final int PORT = 5001;
	
	public static void main(final String[] args) throws URISyntaxException, InterruptedException
	   {
			// Assign URL for socket object
			socket = IO.socket("http://localhost:" + PORT);
			
			// Assign an event to the socket object, specifically one called on socket connect
	        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
	        	
	        		// When the client has connected to the server, emit "toServer" with the data "connected"
	            @Override
	            public void call(Object... objects) {
	                socket.emit("toServer", "connected");
	                //socket.send("test");
	            }
	        });
	        // Assign the event toClient to the socket object
	        socket.on("toClient", new Emitter.Listener() {
	        		// print to the console when the client receives toClient
	        		// "Client received" and all the data sent with the event
	            @Override
	            public void call(Object... args) {
	                System.out.println("Client received : " + args[0]);
	
	            }
	        });
	        
	        // Get the socket object to connect to the server
	        socket.connect();
	        
	        // While the socket hasn't connected, wait 50ms
	        while (!socket.connected())
	            Thread.sleep(50);
	        
	        // Wait ten seconds before disconnecting 
	        Thread.sleep(10000);
	        socket.disconnect();
	   }
}