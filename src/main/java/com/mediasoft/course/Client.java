package com.mediasoft.course;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;


public class Client implements Runnable {

    static Server s = new Server();
    static Thread th = new Thread(s);
    static Client cn = new Client();

    public static void stream() {
        th.start();
        Thread th1 = new Thread(cn);
        th1.start();
    }

    @Override
    public void run() {

        System.out.println("\n Выберите нужный пункт:");
        System.out.println("\n 1. Войти в систему");
        System.out.println(" 2. Зарегестрироваться\n");


        try {
            Scanner scnSTR = new Scanner(System.in);
            System.out.print(" ");
            String point = scnSTR.nextLine();

            if (!point.equals("1") && !point.equals("2")) {
                System.out.println("\n Номер команды не найден");
                run();
            }

            if (point.equals("2")) {
                System.out.println("\n Для регистрации введите логин и пароль:");
            }

            while (Globals.isRunning) {
                Socket socket = new Socket(Globals.host, Globals.port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                System.out.print("\n Логин: ");
                Globals.name = scnSTR.nextLine();
                System.out.print(" Пароль: ");
                String password = scnSTR.nextLine();

                out.write(Globals.name + "\n");
                out.flush();

                out.write(password + "\n");
                out.flush();

                out.write(point + "\n");
                out.flush();

                LocalDate now = LocalDate.now();
                out.write(now + "\n");
                out.flush();

                socket.shutdownOutput();

                String answer = in.readLine();
                if (answer.equals("successIN")) {
                    System.out.println("\n Вы успешно авторизовались");
                    Menu m = new Menu();
                    m.Info();
                    close();
                } else if (answer.equals("successUP")) {
                    System.out.println("\n Вы успешно зарегестрировались");
                    run();
                } else {
                    System.out.println("\n Неверный логин или пароль");
                    run();
                }
            }

        } catch (UnknownHostException uhe) {
            System.out.println("Host " + Globals.host + " is unknown");
        } catch (IOException ioe) {
            System.out.println("Unable to connect with the host " + Globals.host);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void close() {
        try {
            Globals.isRunning = false;
        } catch (Exception e) {e.printStackTrace();}
    }
}
