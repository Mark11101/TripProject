package com.mediasoft.course;

import com.mediasoft.course.authorization.Client;
import com.mediasoft.course.authorization.Server;
import com.mediasoft.course.places.*;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private ArrayList<String> list = new ArrayList<>();
    private int SizeOfList;
    private String FileName;
    public static String name;

    public void Info() throws SQLException, IOException, ClassNotFoundException {
        System.out.println("-------------------------------------------------------------------------------------------" +
                "------------------------------------------------------------------------------------------------------" +
                "------------------------------------------------------------------------------------------------------" +
                "-----------------------");
        System.out.println("\n Эта программа поможет вам найти и сохранить информацию о барах, ресторанах, парках,\n " +
                "торговых центрах, театрах и кинотеатрах города Ульяновск - названия, адреса, и рейтинги."
        );
        MenuText();
    }

    private void MenuText() throws SQLException, ClassNotFoundException, IOException {

        System.out.println("\n Какое место вас интересует?\n Введите нужный пункт меню с клавиатуры\n");
        System.out.println(" 1. Бары\n 2. Парки\n 3. Рестораны\n 4. Театры\n 5. Кинотеатры\n 6. Торговые центры\n");

        InputMenuNumber();
    }

    private void InputMenuNumber() throws SQLException, ClassNotFoundException, IOException {

        try {
            Scanner scnINT = new Scanner(System.in);
            System.out.print(" ");
            int item = scnINT.nextInt();

            if (item <=0 || item >= 7) {
                System.out.println("\n Пункт меню " + item + " не найден");
                System.out.println(" Введите число еще раз");
                InputMenuNumber();
            }

            switch (item) {
                case (1):
                    Bars.BarsList();
                    BDInfo("Бары");
                    break;
                case (2):
                    Parks.ParksList();
                    BDInfo("Парки");
                    break;
                case (3):
                    Rests.RestsList();
                    BDInfo("Рестораны");
                    break;
                case (4):
                    Theaters.TheatersList();
                    BDInfo("Театры");
                    break;
                case (5):
                    Cinemas.CinemasList();
                    BDInfo("Кинотеатры");
                    break;
                case (6):
                    TCenters.TCentersList();
                    BDInfo("Торговые центры");
                    break;
            } AddToList();
        } catch(InputMismatchException e) {
            System.out.println("\n Введите число");
            InputMenuNumber();
        }
    }

    private void AddToList() throws SQLException, ClassNotFoundException, IOException {

        System.out.println("\n Хотите добавить место в список? (Напишите да или нет)");
        Scanner scnSTR = new Scanner(System.in);
        System.out.print(" ");
        String choose = scnSTR.nextLine();

        if (choose.equals("да") || choose.equals("lf")) {
            System.out.println("\n Введите номер места");
            AddString();
            ListMenu();
        }
        else if (choose.equals("нет") || choose.equals("ytn"))
            ListMenu();
        else
            AddToList();

    }

    private void AddString() throws SQLException, ClassNotFoundException, IOException { //добавляет элемент в список

        System.out.print(" ");

        if (list.size() == SizeOfList) {
            System.out.println("\n Вы записали все возможные места");
            ListMenu();
        }

        try {
            Scanner scnINT = new Scanner(System.in);
            int i = scnINT.nextInt();
            String k = OutputBD.elements[i-1];
            list.add(k);
            if (Collections.frequency(list, k) >= 2) {
                System.out.println("\n Это место уже есть в списке");
                System.out.println(" Выберите другое");
                list.remove(k);
                AddString();
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\n Вы ввели несуществующий номер");
            System.out.println(" Попробуйте еще раз");
            AddString();
            return;
        } catch (InputMismatchException y) {
            System.out.println("\n Введите номер");
            AddString();
            return;
        }
        OutList();
    }

    private void OutList() {

        System.out.println("\n Список ваших избранных мест:");
        Header();
        for (String s : list) System.out.println(s);
    }

    private void ListMenu() throws IOException, SQLException, ClassNotFoundException{

        System.out.println("\n Выберите дальнешее действие:");
        System.out.println("\n 1. Перейти в главное меню\n 2. Вывести список на экран\n 3. Добавить место в список" +
                           "\n 4. Удалить место из списка\n 5. Узнать количество мест в списке\n 6. Вывести элемент списка по его номеру" +
                           "\n 7. Очистить список\n 8. Записать список в файл\n 9. Удалить файл" +
                           "\n 10. Посмотреть информацию о пользователе\n 11. Выйти из системы\n 12. Завершить работу программы\n"
        );

        try {
            Scanner scnINT = new Scanner(System.in);
            System.out.print(" ");
            int ListMenuitem = scnINT.nextInt();

            switch (ListMenuitem) {
                case (1):
                    MainMenu();
                    break;
                case (2):
                    EmptyList();
                    OutList();
                    ListMenu();
                    break;
                case (3):
                    System.out.println("\n Введите номер места");
                    AddString();
                    ListMenu();
                    break;
                case (4):
                    EmptyList();
                    System.out.println("\n Вы точно хотите удалить место из списка? (Напишите да или нет)");
                    DeletePlace();
                    OutList();
                    ListMenu();
                    break;
                case (5):
                    EmptyList();
                    System.out.println("\n Количество мест в списке: " + list.size());
                    ListMenu();
                    break;
                case (6):
                    EmptyList();
                    InputPlace();
                    ListMenu();
                    break;
                case (7):
                    EmptyList();
                    System.out.println("\n Вы точно хотите очистить список? (Напишите да или нет)");
                    DeleteList();
                    ListMenu();
                    break;
                case (8):
                    EmptyList();
                    CreateFile();
                    ListMenu();
                    break;
                case (9):
                    DeleteFile();
                    ListMenu();
                    break;
                case (10):
                    UserInformation();
                    ListMenu();
                case (11):
                    SystemOut();
                    break;
                case (12):
                    System.exit(0);
                default:
                    System.out.print("\n Введите число от 1 до 9\n");
                    ListMenu();
                    break;
            }
        } catch(InputMismatchException e) {
            System.out.print("\n Введите число\n");
            ListMenu();
        }
    }

    private void MainMenu() throws SQLException, IOException, ClassNotFoundException {

        if (!list.isEmpty()) {
            System.out.println("\n Вы точно хотите перейти в главное меню? (Все несохраненные данные будут утеряны)");
            System.out.println(" Напишите да или нет");
            MainMenuInput();
        }
        else MenuText();
    }

    private void MainMenuInput() throws IOException, SQLException, ClassNotFoundException {

        Scanner scnSTR = new Scanner(System.in);
        System.out.print(" ");
        String choose = scnSTR.nextLine();

        if (choose.equals("да") || choose.equals("lf")) {
            list.clear();
            MenuText();
        }

        else if (choose.equals("нет") || choose.equals("ytn"))
            ListMenu();

        else {
            System.out.println("\n Напишите да или нет");
            MainMenuInput();
        }
    }

    private void SystemOut() throws SQLException, IOException, ClassNotFoundException {

        if (!list.isEmpty()) {
            System.out.println("\n Вы точно хотите выйти из системы? (Все несохраненные данные будут утеряны)");
            System.out.println(" Напишите да или нет");
            SystemOutInput();
        }
        else {
            Client client = new Client();
            client.run();
        }
    }

    private void SystemOutInput() throws SQLException, IOException, ClassNotFoundException {

        Scanner scnSTR = new Scanner(System.in);
        System.out.print(" ");
        String choose = scnSTR.nextLine();

        if (choose.equals("да") || choose.equals("lf")) {
            list.clear();
            Client client = new Client();
            client.run();
        }

        else if (choose.equals("нет") || choose.equals("ytn"))
            ListMenu();

        else {
            System.out.println("\n Напишите да или нет");
            SystemOutInput();
        }
    }

    private void DeleteList() throws IOException, SQLException, ClassNotFoundException {

        Scanner scnSTR = new Scanner(System.in);
        System.out.print(" ");
        String choose = scnSTR.nextLine();

        if (choose.equals("да") || choose.equals("lf")) {
            list.clear();
        }

        else if (choose.equals("нет") || choose.equals("ytn"))
            ListMenu();

        else {
            System.out.println("\n Напишите да или нет");
            DeleteList();
        }
    }

    private void DeletePlace() throws IOException, SQLException, ClassNotFoundException {

        Scanner scnSTR = new Scanner(System.in);
        System.out.print(" ");
        String choosePlace = scnSTR.nextLine();

        if (choosePlace.equals("да") || choosePlace.equals("lf"))
            InputDeletePlace();

        else if (choosePlace.equals("нет") || choosePlace.equals("ytn"))
            ListMenu();

        else {
            System.out.println("\n Напишите да или нет");
            DeletePlace();
        }
    }

    private void InputDeletePlace() throws IOException, SQLException, ClassNotFoundException, NumberFormatException {

        OutList();
        System.out.println("\n Введите номер места, которое требуется удалить (для выхода в меню редактирования списка введите 0)");

        try {
            Scanner scnINT = new Scanner(System.in);
            System.out.print(" ");
            int delnum = scnINT.nextInt();

            if (delnum == 0) {
                ListMenu();
                return;
            }

            if (delnum > SizeOfList || delnum <= 0) {
                System.out.print("\n Вы ввели несуществующий номер");
                InputDeletePlace();
            }

            else {
                if (delnum < 10) {
                    for (int i = 0; i < list.size(); i++) {
                        String chr = list.get(i);
                        int chrINT1 = Character.getNumericValue(chr.charAt(1));
                        int chrINT2 = Character.getNumericValue(chr.charAt(2));

                        if (chrINT1 == delnum && !(chrINT2 >= 0)) {
                            list.remove(chr);
                            EmptyList();
                            InputDeletePlace();
                            return;
                        }
                    }

                    System.out.println("\n Элемент не найден");
                    InputDeletePlace();
                }

                else if (list.size() >= 10) {
                    for (int i = 10; i < list.size(); i++) {
                        String chr = list.get(i);
                        char chrINT1 = chr.charAt(1);
                        char chrINT2 = chr.charAt(2);

                        String chrINT12 = chrINT1 + "" + chrINT2;
                        int Num = Integer.parseInt(chrINT12);

                        if (Num == delnum) {
                            list.remove(chr);
                            EmptyList();
                            InputDeletePlace();
                            return;
                        }
                    }

                    System.out.println("\n Элемент не найден");
                }

                else {
                    for (int i = 0; i < list.size(); i++) {
                        String chr = list.get(i);
                        char chrINT1 = chr.charAt(1);
                        char chrINT2 = chr.charAt(2);

                        int Num1 = (int)chrINT2;

                        if (Num1 != 32) {
                            String chrINT12 = chrINT1 + "" + chrINT2;
                            int Num = Integer.parseInt(chrINT12);

                            if (Num == delnum) {
                                list.remove(chr);
                                EmptyList();
                                InputDeletePlace();
                                return;
                            }
                        }
                    } System.out.println("\n Элемент не найден");
                }
            }
        } catch(InputMismatchException e) {
            System.out.println("\n Неверный формат");
            InputDeletePlace();
        }
    }

    private void InputPlace() {

        System.out.println("\n Введите номер места, которое требуется вывести");

        try {
            Scanner scnINT = new Scanner(System.in);
            System.out.print(" ");
            int outnum = scnINT.nextInt();

            if (outnum > SizeOfList || outnum <= 0) {
                System.out.print("\n Вы ввели несуществующий номер");
                InputPlace();
            }

            else {
                if (outnum < 10) {
                    for (String chr : list) {
                        int chrINT1 = Character.getNumericValue(chr.charAt(1));
                        int chrINT2 = Character.getNumericValue(chr.charAt(2));

                        if (chrINT1 == outnum && !(chrINT2 >= 0)) {
                            Header();
                            System.out.println(chr);
                            return;
                        }
                    }
                }

                else if (list.size()>=10) {
                    for (int i = 10; i < list.size(); i++) {
                        String chr = list.get(i);
                        char chrINT1 = chr.charAt(1);
                        char chrINT2 = chr.charAt(2);

                        String chrINT12 = chrINT1 + "" + chrINT2;
                        int Num = Integer.valueOf(chrINT12);

                        if (Num == outnum) {
                            Header();
                            System.out.println(list.get(i));
                            return;
                        }
                    }
                }

                else {
                    for (String chr : list) {
                        char chrINT1 = chr.charAt(1);
                        char chrINT2 = chr.charAt(2);

                        int Num1 = (int) chrINT2;

                        if (Num1 != 32) {
                            String chrINT12 = chrINT1 + "" + chrINT2;
                            int Num = Integer.valueOf(chrINT12);

                            if (Num == outnum) {
                                Header();
                                System.out.println(chr);
                                return;
                            }
                        }
                    }
                }
                System.out.println("\n Элемент не найден");
                InputPlace();
            }
        } catch(InputMismatchException e) {
            InputPlace();
        }
    }

    private void CreateFile() throws SQLException, IOException, ClassNotFoundException {

        File folderName = new File("./Places/" + name);
        folderName.mkdir();

        File file = new File("Places/" + name + "/" + FileName + ".txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        else {
            System.out.println("\n Список " + FileName + " уже существует");
            System.out.println(" Переписать файл? (Введите да или нет)");
            CreateFileInput();
        }


        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write("\n № | Название               | Оценка  | Адрес                    ");
        bw.write("\n ----------------------------------------------------------------------------\n");

        for (String s : list) {
            bw.write(s + "\n");
        }

        bw.flush();
        bw.close();

        System.out.println("\n Файл " + FileName + " успешно записан в папку Places");
    }

    private void CreateFileInput() throws SQLException, IOException, ClassNotFoundException {

        Scanner scnSTR = new Scanner(System.in);
        System.out.print(" ");
        String choosePlace = scnSTR.nextLine();

        if (choosePlace.equals("да") || choosePlace.equals("lf"))
            System.out.print("");

        else if (choosePlace.equals("нет") || choosePlace.equals("ytn"))
            ListMenu();

        else {
            System.out.println("\n Напишите да или нет");
            CreateFileInput();
        }

    }

    private void DeleteFile() throws SQLException, IOException, ClassNotFoundException {

        System.out.println("\n Напишите имя файла, который нужно удалить");

        Scanner scnSTR = new Scanner(System.in);
        System.out.print(" ");
        String NameInput = scnSTR.nextLine();

        File file1 = new File("Places/" + name + "/" + NameInput + ".txt");

        if (!file1.exists()) {
            System.out.println("\n Файла не существует");
            ListMenu();
            return;
        }

        System.out.println("\n Вы точно хотите удалить файл? (Напишите да или нет)");
        DeleteFileInput(NameInput);
    }

    private void DeleteFileInput(String NameInput) throws IOException, SQLException, ClassNotFoundException {

        Scanner scnSTRdel = new Scanner(System.in);
        System.out.print(" ");
        String choose = scnSTRdel.nextLine();

        if (choose.equals("да") || choose.equals("lf")) {
            File filedel = new File("Places/" + name + "/" + NameInput + ".txt");
            filedel.delete();
            System.out.println("\n Файл удален");
        }

        else if (choose.equals("нет") || choose.equals("ytn"))
            ListMenu();

        else {
            System.out.println("\n Напишите да или нет");
            DeleteFileInput(NameInput);
        }
    }

    void Header() {
        System.out.println("\n № | Название               | Оценка  | Адрес                    ");
        System.out.println(" ----------------------------------------------------------------------------");
    }

    private void BDInfo(String BDName) {
        FileName = BDName;
        SizeOfList = OutputBD.y;
    }

    private void EmptyList() throws IOException, SQLException, ClassNotFoundException {
        if (list.size() == 0) {
            System.out.println("\n Список пуст\n");
            ListMenu();
        }
    }

    private void UserInformation() throws UnknownHostException {
        System.out.println("\n Логин: " + name);
        IPAddress();
        System.out.println(" Дата регистрации: " + Server.FileString[2]);
    }

    private void IPAddress() throws UnknownHostException {
        InetAddress localhost = InetAddress.getLocalHost();
        System.out.println(" Локальный IP адрес: " + (localhost.getHostAddress()).trim());

        String systemipaddress;
        try {
            URL url_name = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));
            systemipaddress = sc.readLine().trim();
        }
        catch (Exception e) {
            systemipaddress = "Ошибка";
        }
        System.out.println(" IP адрес в сети: " + systemipaddress);
    }
}
