package com.mediasoft.course;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket socket = new ServerSocket(Globals.port);
            while (Globals.isRunning) {

                Socket client = socket.accept();
                Scanner line = new Scanner(client.getInputStream());
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                int i = 0;
                while (line.hasNext()) {
                    Globals.request[i] = line.next();
                    i++;
                }

                client.shutdownInput();

                if (Globals.request[1] == null || Globals.request[2] == null) {
                    out.write("error");
                }

                else if (Globals.request[2].equals("1")) {

                    File file1 = new File("Accounts/" + Globals.request[0] + ".txt");
                    if (!file1.exists()) {
                        out.write("error");
                    } else {
                        FileIN(file1);
                        if (Globals.FileString[0].equals(Globals.request[0]) &&
                            Globals.FileString[1].equals(Globals.request[1])) {
                            out.write("successIN");
                        } else {
                            out.write("error");
                        }
                    }
                } else if (Globals.request[2].equals("2")) {
                    FileUP(Globals.request[0]);
                    out.write("successUP");
                } else {
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

    public void FileIN(File file1) throws FileNotFoundException {

        FileReader fr = new FileReader(file1);
        Scanner scan = new Scanner(fr);

        int y=0;

        while (scan.hasNextLine()) {
            Globals.FileString[y] = scan.nextLine();
            y++;
        }
    }

    public void FileUP(String NameInput) throws IOException {
        File file = new File("Accounts/" + NameInput + ".txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(Globals.request[0] + "\n");
        bw.write(Globals.request[1] + "\n");
        bw.write(Globals.request[3] + "\n");

        bw.flush();
        bw.close();
    }

    public void close() {
        try {
            Globals.isRunning = false;
        } catch (Exception e) {e.printStackTrace();}
    }
}