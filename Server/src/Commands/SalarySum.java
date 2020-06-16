package Commands;


import DataBase.SQLreader;

/***
 * Класс, реализующий команду sum_of_salary
 */

public class SalarySum extends Comands {
    public byte[] bb;
    public SalarySum(SQLreader reader){
        name = "sum_of_salary";
        this.reader = reader;
        commandsNames.add(name);
    }
    long sum = 0;
    SQLreader reader;
    public void salarySum()  {
        StringBuilder answer = new StringBuilder();
        sum = reader.workers.stream().map(x -> x.getSalary()).reduce((s1, s2) -> s1+s2).orElse((long) 0);

        System.out.println("Сумма salary всех worker'ов: "+sum);
        answer.append("Сумма salary всех worker'ов: ");
        answer.append(sum);
        answer.append("\n");
        bb = String.valueOf(answer).getBytes();

    }
}
