
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;



public class client {
	static private Socket socket;
	static private final int PORT = 5001;
	
	
	public static void main(final String[] args) throws URISyntaxException, InterruptedException
	   {
			socket = IO.socket("http://localhost:" + PORT);
	        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
	            @Override
	            public void call(Object... objects) {
	                socket.emit("toServer", "connected");
	                socket.send("test");
	            }
	        });
	        socket.on("toClient", new Emitter.Listener() {
	            @Override
	            public void call(Object... args) {
	                System.out.println("Client recievd : " + args[0]);
	
	            }
	        });
	        socket.connect();
	        while (!socket.connected())
	            Thread.sleep(50);
	        socket.send("toServer");
	        Thread.sleep(10000);
	        socket.disconnect();
	   }
}