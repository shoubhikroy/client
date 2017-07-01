package model.templates;

import java.io.IOException;

public abstract class objectManager
{
    public static Integer getId()
    {
        return id;
    }

    public static void setId(Integer id)
    {
        objectManager.id = id;
    }

    static Integer id = (int) (Math.random() * 100);
    ;

    public Integer getNextId()
    {
        return ++id;
    }

    public abstract void process() throws IOException;

    public abstract void update() throws IOException, ClassNotFoundException;

}
