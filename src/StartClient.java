
import main.Client;

public class StartClient {
    private static String ip = "127.0.0.1";
    private static int port = 9876;

    
    /** 
     * @param args
     */
    public static void main(String[] args) {

        switch (args.length) {

            case 0:
                try {
                    System.out.println("Starting client with default values");
                    Client c1 = new Client(ip, port);
                    c1.queue();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Could not start client, check if server is running");
                }
                break;

            case 2:
                ip = args[0];
                port = Integer.parseInt(args[1]);
                if (port > 0) {
                    System.out.println("Connecting to server");
                    Client c2 = new Client(ip, port);       
                    System.out.println("Connection successful ! Game will start as soon as everyone is connected");
                    c2.queue();
                }
                break;

            default:
                System.out.println("Invalid number of arguments");
                System.out.println(
                        "Usage: java StartClient [ip] [port] (both optional, using default values if not provided)");
                System.exit(0);
        }

    }

}
