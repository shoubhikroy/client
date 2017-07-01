package model.objects;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import model.templates.baseObject;

public class Setting extends baseObject implements Serializable
{

    static final long serialVersionUID = 42L;

    String key;
    String value;
    String value2;
    String value3;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getValue2()
    {
        return value2;
    }

    public void setValue2(String value2)
    {
        this.value2 = value2;
    }

    public String getValue3()
    {
        return value3;
    }

    public void setValue3(String value3)
    {
        this.value3 = value3;
    }

    public Setting(Integer id, String key, String value, String value2, String value3)
    {
        super(id);
        this.key = key;
        this.value = value;
        this.value2 = value2;
        this.value3 = value3;
    }

    public Setting()
    {
        super();
    }
}
