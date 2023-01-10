import javax.sound.sampled.Port;

import main.Server;

public class StartServer {
    public final static String IP = "localhost";
    public final static int PORT = 9876;

    public static void main(String[] args) {

        int humans = 1;
        int players = 4;

        // Defaults file paths, could be changed in future implementations
        String RedAppleFile = "../redApples.txt";
        String GreenAppleFile = "../greenApple.txt";

        switch (args.length) {
            case 0:

                System.out.println("No arguments provided, starting server for 1 human and 3 bots");
                start(IP, PORT, players, humans, RedAppleFile, GreenAppleFile);

                break;

            case 2:
                players = Integer.parseInt(args[0]);
                humans = Integer.parseInt(args[1]);
                if (humans > 0 && players > 0 && players >= humans) {
                    System.out.println(
                            "Starting server for " + humans + " human(s) and " + (players - humans) + " bot(s)");

                    // Server s2 = new Server(IP, PORT, players, humans, RedAppleFile,
                    // GreenAppleFile);
                    start(IP, PORT, players, humans, RedAppleFile, GreenAppleFile);
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

    public static void start(String IP, int port, int players, int humans, String RedAppleFile, String GreenAppleFile) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Server s1 = new Server(IP, port, players, humans, RedAppleFile, GreenAppleFile);
                s1.queue(humans);

            }
        }).start();
    }
}
