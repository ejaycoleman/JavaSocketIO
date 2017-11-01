import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.apache.log4j.BasicConfigurator;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

public class server {
	static final int PORT = 5001;
    static SocketIOServer server;
    public static void main(String[] args) throws InterruptedException {
    	BasicConfigurator.configure();
        Thread ts = new Thread(
        		new Runnable() {
            @Override
            public void run() {
                try {
                    server();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        ts.start();

    }
    public static void server() throws InterruptedException, UnsupportedEncodingException {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(PORT);
        server = new SocketIOServer(config);
        server.addEventListener("toServer", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                client.sendEvent("toClient", "server recieved " + data);
            }
        });
        server.addEventListener("message", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                //client.sendEvent("toClient", "message from server " + data);
            }
        });
        server.start();
        Thread.sleep(10000);
        server.stop();
    }
}




/*


import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;

public class server {
	public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", null, null {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                // broadcast messages to all clients
                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }
}


*/