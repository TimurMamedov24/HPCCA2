import java.rmi.*;
import java.rmi.registry.*;
public class RmiServer {
    public static void main(String args[])
    {
        try
        {

            RmiInterface obj = new RmiQuery();
            LocateRegistry.createRegistry(1900);
            Naming.rebind("rmi://localhost:1900"+
                    "/RmiCA",obj);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
