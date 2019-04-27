import java.rmi.*;
import java.rmi.server.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class RmiQuery  extends UnicastRemoteObject implements RmiInterface{
    RmiQuery() throws RemoteException
    {
        super();
    }

    public int find_bigger(int first, int second) throws RemoteException
    {
        if (first >= second){
            return first;
        } else{
            return second;
        }
    }

    public String system_time() throws RemoteException
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
