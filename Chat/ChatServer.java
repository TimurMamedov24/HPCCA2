package Chat;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Vector;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {
    String line = "---------------------------------------------\n";
    private Vector<Peer> peers;
    private static final long serialVersionUID = 1L;

    //Constructor
    public ChatServer() throws RemoteException {
        super();
        peers = new Vector<Peer>(10, 1);
    }

    public static void main(String[] args) {
        startRMIRegistry();
        String hostName = "localhost";
        String serviceName = "Chat";

        if(args.length == 2){
            hostName = args[0];
            serviceName = args[1];
        }

        try{
            ChatServerInterface hello = new ChatServer();
            Naming.rebind("rmi://" + hostName + "/" + serviceName, hello);
            System.out.println("Chat Server is running...");
        }
        catch(Exception e){
            System.out.println("Server had problems starting");
        }
    }


    public static void startRMIRegistry() {
        try{
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("Server is ready");
        }
        catch(RemoteException e) {
            e.printStackTrace();
        }
    }



    public String sayHello(String ClientName) throws RemoteException {
        System.out.println(ClientName + " sent a message");
        return "Hello " + ClientName + " from group chat server";
    }


    public void updateChat(String name, String nextPost) throws RemoteException {
        String message =  name + " : " + nextPost + "\n";
        sendToAll(message);
    }

    /**
     * Receive a new client remote reference
     */
    @Override
    public void IDentity(RemoteRef ref) throws RemoteException {
        //System.out.println("\n" + ref.remoteToString() + "\n");
        try{
            System.out.println(line + ref.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }//end passIDentity


    /**
     * Receive a new client and display details to the console
     * send on to register method
     */
    @Override
    public void registerListener(String[] details) throws RemoteException {
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println(details[0] + " has joined the chat session");
        System.out.println(details[0] + "'s hostname : " + details[1]);
        System.out.println(details[0] + "'sRMI service : " + details[2]);
        registerPeer(details);
    }



    private void registerPeer(String[] details){
        try{
            ChatClientInterface nextClient = ( ChatClientInterface ) Naming.lookup("rmi://" + details[1] + "/" + details[2]);

            peers.addElement(new Peer(details[0], nextClient));

            nextClient.messageFromServer("[Server] : Hello " + details[0] + " you are now free to chat.\n");

            sendToAll("[Server] : " + details[0] + " has joined the group.\n");

            updateUserList();
        }
        catch(RemoteException | MalformedURLException | NotBoundException e){
            e.printStackTrace();
        }
    }

    private void updateUserList() {
        String[] currentUsers = getUserList();
        for(Peer c : peers){
            try {
                c.getClient().updateUserList(currentUsers);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    private String[] getUserList(){
        // generate an array of current users
        String[] allUsers = new String[peers.size()];
        for(int i = 0; i< allUsers.length; i++){
            allUsers[i] = peers.elementAt(i).getName();
        }
        return allUsers;
    }


    public void sendToAll(String newMessage){
        for(Peer c : peers){
            try {
                c.getClient().messageFromServer(newMessage);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    public void leaveChat(String userName) throws RemoteException{

        for(Peer c : peers){
            if(c.getName().equals(userName)){
                System.out.println(line + userName + " has left the chat");
                System.out.println(new Date(System.currentTimeMillis()));
                peers.remove(c);
                break;
            }
        }
        if(!peers.isEmpty()){
            updateUserList();
        }
    }

}

