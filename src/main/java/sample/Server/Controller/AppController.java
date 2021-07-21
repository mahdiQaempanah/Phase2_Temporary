package sample.Controller;

import com.google.gson.Gson;
import org.json.JSONObject;
import sample.Model.ApiMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class AppController {
    API api = new API();
    ServerSocket serverSocket = new ServerSocket(7755);

    public AppController() throws IOException {
    }


    public void run() throws Exception{
        while (true){
            Socket socket = serverSocket.accept();

            new Thread(() -> {
                try {
                    String userHash = null;
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                    while (true){
                        try {
                            String input = dataInputStream.readUTF();
                            if (input.equals(""))
                                break;
                            if(userHash != null){
                                input = new JSONObject(input).put("userHash",userHash).toString();
                            }
                            JSONObject result = api.run(input);
                            userHash = checkLoginLogout(input,result,userHash);
                            dataOutputStream.writeUTF(result.toString());
                            dataOutputStream.flush();
                        }catch (SocketException socketException){
                            break;
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                    if(userHash != null)
                        api.programController.logout(userHash);//Todo: reset
                    dataInputStream.close();
                    socket.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }).start();
        }
    }

    private String checkLoginLogout(String input, JSONObject result, String userHash) {
        String command = new JSONObject(input).getString("command");
        if(command.equals("logout")){
            if(new Gson().fromJson(String.valueOf(result), ApiMessage.class).getType().equals(ApiMessage.successful))
                return null;
        }
        if(command.equals("login_user")){
            ApiMessage message = new Gson().fromJson(String.valueOf(result), ApiMessage.class);
            if(message.getType().equals(ApiMessage.successful))
                return new JSONObject(message.getMessage()).getString("userHash");
        }
        return userHash;
    }
}
