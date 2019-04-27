import java.rmi.*;
public class SystemTime {
    public static void main(String args[])
    {
        try
        {

            RmiInterface access =
                    (RmiInterface) Naming.lookup("rmi://localhost:1900"+
                            "/RmiCA");
            String answer = access.system_time();
            System.out.println("Current system time on the server: " + answer);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
