package Commands;

import Collection.Worker;
import DataBase.SQLreader;

import java.io.IOException;
import java.time.LocalDateTime;

/***
 * Класс, реализующий команду info
 */

public class Info extends Comands{
    LocalDateTime minDate;
    public byte[] bb;

    public Info() {
        name = "info";
        commandsNames.add(name);
    }

    public void info(SQLreader reader) throws IOException {
        String answer;
        LocalDateTime ldt = reader.workers.element().getStartDate();
        for (Worker worker : reader.workers) {
            if (worker.getStartDate().getYear() < ldt.getYear())
                minDate = worker.getStartDate();
            if (worker.getStartDate().getYear() == ldt.getYear()) {
                if (worker.getStartDate().getDayOfYear() < ldt.getDayOfYear())
                    minDate = worker.getStartDate();
                if (worker.getStartDate().getDayOfYear() == ldt.getDayOfYear()) {
                    if (worker.getStartDate().getHour() < ldt.getHour())
                        minDate = worker.getStartDate();
                    if (worker.getStartDate().getHour() == ldt.getHour()) {
                        if (worker.getStartDate().getMinute() <= ldt.getMinute()) {
                            minDate = worker.getStartDate();
                        }
                    }
                }
            }
        }
        System.out.println("Тип коллекции: " + reader.workers.getClass().toString() + "\nРазмер коллекции: " + reader.workers.size()
                + "\nДата инициализации: " + minDate);
        answer = ("Тип коллекции: " + reader.workers.getClass().toString() + "\nРазмер коллекции: " + reader.workers.size()
                + "\nДата инициализации: " + minDate);
        bb = answer.getBytes();
    }
}
