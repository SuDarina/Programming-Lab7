package Collection;

import Exceptions.FieldException;

import java.io.*;

public class Person implements Serializable {
    private float height; //Значение поля должно быть больше 0
    private String passportID; //Длина строки должна быть не меньше 9, Строка не может быть пустой, Длина строки не должна быть больше 41, Поле не может быть null}

    public Person() {

    }

    public void setHeight(float height) throws FieldException {
        this.height = height;
        if (height <= 0)
            throw new FieldException("Exception in field : person (height)");
    }

    public void setPassportID(String passportID) throws FieldException {
        this.passportID = passportID;
        if (passportID.length() < 9 | passportID.length() > 41)
            throw new FieldException("Exception in field : person (passportID)");
    }

    public float getHeight() {
        return height;
    }

    public String getPassportID() {
        return passportID;
    }

    @Override
    public String toString() {
        return "[height:"+height+", passportID:"+passportID+"]";
    }

}
