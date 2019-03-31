package com.mediasoft.course.authorization;

import com.mediasoft.course.Globals;
import com.mediasoft.course.Menu;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;


public class Client implements Runnable {

    private static Server s = new Server();
    private static Thread th = new Thread(s);
    private static Client cn = new Client();
    boolean isRunning = true;

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

            while (isRunning) {
                Socket socket = new Socket(Globals.host, Globals.port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                System.out.print("\n Логин: ");
                Menu.name = scnSTR.nextLine();
                System.out.print(" Пароль: ");
                String password = scnSTR.nextLine();

                out.write(Menu.name + "\n");
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
                switch (answer) {
                    case "successIN":
                        System.out.println("\n Вы успешно авторизовались");
                        Menu m = new Menu();
                        m.Info();
                        close();
                        break;
                    case "successUP":
                        System.out.println("\n Вы успешно зарегестрировались");
                        run();
                        break;
                    case "errorLogin":
                        System.out.println("\n Пользователь с таким именем уже существует");
                        run();
                        break;
                    case "errorNull":
                        System.out.println("\n Вы не заполнили одно из полей");
                        run();
                        break;
                    default:
                        System.out.println("\n Неверный логин или пароль");
                        run();
                        break;
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

    private void close() {
        try {
            isRunning = false;
        } catch (Exception e) {e.printStackTrace();}
    }
}
