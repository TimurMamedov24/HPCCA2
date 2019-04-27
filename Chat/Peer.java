package Chat;

public class Peer {
    public String name;
    public ChatClientInterface client;


    public Peer(String name, ChatClientInterface client){
        this.name = name;
        this.client = client;
    }

    public String getName(){
        return name;
    }
    public ChatClientInterface getClient(){
        return client;
    }


}

