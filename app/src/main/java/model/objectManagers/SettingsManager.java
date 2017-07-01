package model.objectManagers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import handler.dbHandler;
import model.objects.Setting;
import model.templates.objectManager;

public class SettingsManager extends objectManager
{
    private static SettingsManager instance = null;

    protected SettingsManager()
    {
        init();
    }

    private void init()
    {
        try
        {
            update();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static SettingsManager getInstance()
    {
        if (instance == null)
        {
            instance = new SettingsManager();
        }
        return instance;
    }

    public void process() throws IOException
    {
        dbHandler.write(new Setting(), userList);
    }

    public void update() throws IOException
    {
        userList = dbHandler.read(new Setting(), userList);
        if (userList == null)
        {
            userList = new HashMap<>();
        }
    }

    private HashMap<String, Setting> userList = new HashMap<>();

    public Setting createSetting(String key, String value1, String value2, String value3)
    {
        Setting p = new Setting(getNextId(), key, value1, value2, value3);
        userList.put(key, p);
        try
        {
            dbHandler.save();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return p;
    }

    public void deleteSetting(Setting u) throws IOException
    {
        userList.remove(u.getKey());
        dbHandler.save();
    }

    public ArrayList<Setting> getUserList()
    {
        ArrayList<Setting> beans =
                new ArrayList<Setting>(userList.values());
        return beans;
    }

    public Setting getSettingFromKey(String key)
    {
        return userList.get(key);
    }

}