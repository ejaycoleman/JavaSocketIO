import java.io.UnsupportedEncodingException;

import org.apache.log4j.BasicConfigurator;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

public class server {
	// define constant called port, used to connect to the server
	static final int PORT = 5001;
	
	// instantiate SocketIOServer object as server
    static SocketIOServer server;
    
    
    public static void main(String[] args) throws InterruptedException {
    		// Not really sure about this, just recommended by StackOverflow
    		BasicConfigurator.configure();
    		
    		// Run this code in a new thread
        Thread ts = new Thread(
        		
        		new Runnable() {
        		// When the thread is started, this method is run
            @Override
            public void run() {
            		
            		// Try executing the runServer function
                try {
                    runServer();
                // If the error "InterruptedException" is thrown, print the stack trace
                } catch (InterruptedException e) {
                    e.printStackTrace();
                // If the error "UnsupportedEncodingException" is thrown, print the stack trace
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        
        // Begin executing the previously defined thread
        ts.start();

    }
    
    public static void runServer() throws InterruptedException, UnsupportedEncodingException {
    		// the Socket server requires a configuration object, so instantiate one here called config
        Configuration config = new Configuration();
        
        // specify the HostName of the server
        config.setHostname("localhost");
        
        // specify the Port of the server
        config.setPort(PORT);
        
        // instantiate a socket object, based on the config object just created
        server = new SocketIOServer(config);
        
        // tell the server object to listen in on the event "toServer"
        server.addEventListener("toServer", String.class, new DataListener<String>() {
        		// if the server receives this event...
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
            		// print received 
            		System.out.println("received");
            		// Emit the event "toClient", and send "server received " and the data about the previous event
                client.sendEvent("toClient", "server received " + data);
            }
        });
        
        // start the server
        server.start();
        
        // ten seconds later...
        Thread.sleep(10000);
        
        // stop the server
        server.stop();
    }
}