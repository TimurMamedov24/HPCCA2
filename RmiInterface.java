import java.rmi.*;
public interface RmiInterface extends Remote {
    public int find_bigger(int first, int second) throws RemoteException;

    public String system_time() throws RemoteException;
}
