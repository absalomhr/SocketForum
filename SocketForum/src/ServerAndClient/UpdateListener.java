package ServerAndClient;

import GUIs.ClientMainGUI2;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author Absalom Herrera
 */
public class UpdateListener implements Runnable {

    private ClientMainGUI2 clientGUI;
    private int listenerPort;

    public UpdateListener(ClientMainGUI2 clientGUI, int listenerPort) {
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
                System.out.println("escuchando desde: " + listenerPort);
                listen.receive(dgp);
                
                data = dgp.getData();
                if (data [0] == 0){
                    clientGUI.getAllPost();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

}
