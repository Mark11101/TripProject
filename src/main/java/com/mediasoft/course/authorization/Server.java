package com.mediasoft.course.authorization;

import com.mediasoft.course.Globals;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable {
    private Client client = new Client();
    private String[] request = new String[4];
    public static String[] FileString = new String[3];

    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(Globals.port);
            while (client.isRunning) {

                Socket client = socket.accept();
                Scanner line = new Scanner(client.getInputStream());
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                int i = 0;
                while (line.hasNext()) {
                    request[i] = line.next();
                    i++;
                }

                client.shutdownInput();

                if (request[1] == null || request[2] == null) {
                    out.write("errorNull");
                }

                else if (request[2].equals("1")) {

                    File file1 = new File("Accounts/" + request[0] + ".txt");
                    if (!file1.exists()) {
                        out.write("error");
                    }
                    else {
                        FileIN(file1);
                        if (FileString[0].equals(request[0]) &&
                            FileString[1].equals(request[1])) {
                            out.write("successIN");
                        } else {
                            out.write("error");
                        }
                    }
                }
                else if (request[2].equals("2")) {

                    File file1 = new File("Accounts/" + request[0] + ".txt");
                    if (file1.exists()) {
                        out.write("errorLogin");
                    }
                    else {
                        FileUP(request[0]);
                        out.write("successUP");
                    }
                }
                else {
                    out.write("error");
                }
                out.flush();
                client.shutdownOutput();

            }
        }
        catch (IOException ioe) {
            System.out.println("Unable to start server in local host");
        }
        finally {
            close();
        }
    }

    private void FileIN(File file1) throws FileNotFoundException {

        FileReader fr = new FileReader(file1);
        Scanner scan = new Scanner(fr);

        int y=0;

        while (scan.hasNextLine()) {
            FileString[y] = scan.nextLine();
            y++;
        }
    }

    private void FileUP(String NameInput) throws IOException {
        File file = new File("Accounts/" + NameInput + ".txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(request[0] + "\n");
        bw.write(request[1] + "\n");
        bw.write(request[3] + "\n");

        bw.flush();
        bw.close();
    }

    private void close() {
        try {
            client.isRunning = false;
        } catch (Exception e) {e.printStackTrace();}
    }
}