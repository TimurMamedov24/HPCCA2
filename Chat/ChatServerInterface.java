package Chat;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;

public interface ChatServerInterface extends Remote{
    public void updateChat(String user, String meessage)throws RemoteException;

    public void IDentity(RemoteRef ref)throws RemoteException;

    public void registerListener(String[] details)throws RemoteException;

    public void leaveChat(String userName)throws RemoteException;
}
