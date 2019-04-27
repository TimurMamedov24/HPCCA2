package Chat;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {
    private static final long serialVersionUID = 7468891722773409712L;
    private String hostName = "localhost";
    private String serviceName = "Chat";
    private String clientServiceName;
    private String name;
    protected ChatServerInterface serverIF;
    protected boolean connectionProblem = false;



    public ChatClient(String userName) throws RemoteException {
        super();
        this.name = userName;
        this.clientServiceName = "Client_" + userName;
    }



    public void startClient() throws RemoteException {
        String[] details = {name, hostName, clientServiceName};

        try {
            Naming.rebind("rmi://" + hostName + "/" + clientServiceName, this);
            serverIF = ( ChatServerInterface ) Naming.lookup("rmi://" + hostName + "/" + serviceName);
        }
        catch (ConnectException | NotBoundException | MalformedURLException e) {
            connectionProblem = true;
            e.printStackTrace();
        }
        if(!connectionProblem){
            registerWithServer(details);
        }
        System.out.println("Client Listen RMI Server is running...\n");
    }


    public void registerWithServer(String[] details) {
        try{
            serverIF.IDentity(this.ref);
            serverIF.registerListener(details);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void messageFromServer(String message) throws RemoteException {
        System.out.println( message );
    }

    @Override
    public void updateUserList(String[] currentUsers) throws RemoteException {

        if(currentUsers.length < 2){
            for (int i=0; i < currentUsers.length; i++){
                System.out.println(currentUsers[i]);
            }
        }

    }

    public static void main(String[] args){
        String name = args[0];
        try {
            ChatClient chatClient = new ChatClient( name);
            chatClient.startClient();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
