package sample.View.Graphic;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketPackage {
    private static SocketPackage socketPackage;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    static {
        try {
            socketPackage = new SocketPackage(7755);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static SocketPackage getInstance(){
        return socketPackage;
    }

    public SocketPackage(int port) throws IOException {
        socket = new Socket("localhost",7755);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public String getResponse(JSONObject request) throws IOException {
        return  getResponse(request.toString());
    }

    public String getResponse(String request) throws IOException {
        dataOutputStream.writeUTF(request);
        dataOutputStream.flush();
        return dataInputStream.readUTF();
    }

}
