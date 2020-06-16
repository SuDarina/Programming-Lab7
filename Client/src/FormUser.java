import Collection.User;

import java.util.Scanner;

public class FormUser {
    public User user = new User();
    public void formUser(){
        Scanner scr = new Scanner(System.in);
        System.out.println("Введите логин: ");
        String login = scr.nextLine();
        user.setLogin(login);
        System.out.println("Введите пароль: ");
        String password = scr.nextLine();
        user.setPassword(password);
        user.setPassword(user.MD5Hash());
        user.setOnline(false);
    }
}
