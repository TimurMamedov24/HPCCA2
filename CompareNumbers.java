import java.rmi.*;
public class CompareNumbers {
    public static void main(String args[])
    {
        try
        {

            RmiInterface access =
                    (RmiInterface) Naming.lookup("rmi://localhost:1900"+
                            "/RmiCA");
            int answer = access.find_bigger(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            System.out.println("Comparing two numbers " + args[0] + " and " + args[1] + "\n" +
            "Bigger number is : " + answer);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
