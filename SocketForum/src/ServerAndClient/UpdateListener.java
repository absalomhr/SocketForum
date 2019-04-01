package ServerAndClient;

import GUIs.ClientMainGUI;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author Absalom Herrera
 */
public class UpdateListener implements Runnable {

    private ClientMainGUI clientGUI;
    private int listenerPort;

    public UpdateListener(ClientMainGUI clientGUI, int listenerPort) {
        this.clientGUI = clientGUI;
        this.listenerPort = listenerPort;
    }

    @Override
    public void run() {
        for (;;) {
            try {
                // Port for sending
                MulticastSocket listen = new MulticastSocket(listenerPort);
                
                // Group IP
                listen.joinGroup(InetAddress.getByName("230.0.0.1"));
                
                // Option
                byte[] data = new byte[1];
                
                // We receive the option
                DatagramPacket dgp = new DatagramPacket(data, data.length);
                System.out.println("HEARD FROM: " + listenerPort);
                listen.receive(dgp);
                
                data = dgp.getData();
                if (data [0] == 0){
                    clientGUI.seek();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

}
