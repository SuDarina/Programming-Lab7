import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class FormDate {
    LocalDate ld;
    Scanner scr = new Scanner(System.in);
    LocalTime lt;
    LocalDateTime ldt;
    public void countDate(){
        try {
            System.out.println("Введите год:");
            int year = Integer.parseInt(scr.nextLine());
            System.out.println("Введите номер месяца");
            int month = Integer.parseInt(scr.nextLine());
            System.out.println("Введите день:");
            int day = Integer.parseInt(scr.nextLine());
            ld = LocalDate.of(year, month, day);
        }catch (DateTimeException e){
            System.out.println("Неправильный формат ввода даты, повторите ввод");
            countDate();
        }catch (NumberFormatException e){
            System.out.println("Вы ввели не число, повторите ввод даты");
            countDate();
        }
    }

    public void countTime(){
        try {
            System.out.println("Введите время:" + "\nЧасы:");
            int hour = Integer.parseInt(scr.nextLine());
            System.out.println("Минуты:");
            int minute = Integer.parseInt(scr.nextLine());
            lt = LocalTime.of(hour, minute);
        }catch (DateTimeException e){
            System.out.println("Неправильный формат ввода времени, повторите ввод");
            countTime();
        }catch (NumberFormatException e){
            System.out.println("Вы ввели не число, повторите ввод времени");
            countTime();
        }
    }
    public void formDateTime(){
        ldt = LocalDateTime.of(ld, lt);
    }
}
