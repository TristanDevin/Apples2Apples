
import main.Server;

public class StartServer {
    private final static String IP = "localhost";
    private final static int PORT = 9876;

    
    /** 
     * @param args
     */
    public static void main(String[] args) {

        int humans = 1;
        int players = 4;

        // Defaults file paths, could be changed in future implementations
        String RedAppleFile = "../redApples.txt";
        String GreenAppleFile = "../greenApple.txt";

        switch (args.length) {
            case 0:

                System.out.println("No arguments provided, starting server for 1 human and 3 bots");
                Server s1 = new Server(IP, PORT, players, humans, RedAppleFile, GreenAppleFile);
                s1.queue(humans);

                break;

            case 2:
                players = Integer.parseInt(args[0]);
                humans = Integer.parseInt(args[1]);
                if (humans > 0 && players > 0 && players >= humans) {
                    System.out.println(
                            "Starting server for " + humans + " human(s) and " + (players - humans) + " bot(s)");

                    Server s2 = new Server(IP, PORT, players, humans, RedAppleFile, GreenAppleFile);
                    s2.queue(humans);
                } else {
                    System.out.println(
                            "Invalid argument(s), Usage: java StartServer [Nb of players] [Nb of human players]");
                }
                break;

            default:
                System.out.println("Too many arguments");
                System.exit(0);
                break;
        }

    }

}
