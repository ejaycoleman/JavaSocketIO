import java.io.UnsupportedEncodingException;

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
                    runServer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        ts.start();

    }
    public static void runServer() throws InterruptedException, UnsupportedEncodingException {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(PORT);
        server = new SocketIOServer(config);
        server.addEventListener("toServer", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
            		System.out.println("recieved");
                client.sendEvent("toClient", "server received " + data);
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