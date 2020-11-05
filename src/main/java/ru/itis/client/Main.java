package ru.itis.client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import ru.itis.services.Cryptor;

import java.math.BigInteger;
import java.util.Scanner;

class Main {
    @Parameter(names={"--port"}, required = true)
    private int port;
    @Parameter(names={"--ip"}, required = true)
    private String ip;
    private Cryptor cryptor;

    public static void main(String ... argv) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(argv);
        main.run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your public key:");
        BigInteger myPublicKey = new BigInteger(scanner.nextLine());
        System.out.println("Friend's public key:");
        BigInteger publicKey = new BigInteger(scanner.nextLine());
        cryptor = new Cryptor(2048, publicKey, myPublicKey);
        SocketClient client = new SocketClient(cryptor);
        client.startConnection(ip, port);
        while (true) {
            String message = scanner.nextLine();
            BigInteger bigIntegerMessage = new BigInteger(message.getBytes());
            client.sendMessage(cryptor.encrypt(bigIntegerMessage).toString());
        }
    }
}