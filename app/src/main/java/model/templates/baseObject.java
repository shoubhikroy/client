package model.templates;

import java.io.Serializable;

public abstract class baseObject implements Serializable
{
    Integer id;

    static final long serialVersionUID = 42L;

    protected baseObject(Integer id)
    {
        this.id = id;
    }

    public baseObject()
    {

    }

    public Integer getId()
    {
        return id;
    }
}
