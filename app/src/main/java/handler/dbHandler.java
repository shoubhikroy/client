package handler;

import java.io.*;
import java.util.HashMap;

import c.c.MainActivity;
import model.objectManagers.SettingsManager;
import model.objects.Setting;

public class dbHandler
{
    public static void write(Setting Setting, HashMap<String, Setting> userList) throws IOException
    {
        File yourFile = new File(MainActivity.getAppContext().getFilesDir().getPath().toString() + "Setting.dat");
        if (!yourFile.exists())
        {
            yourFile.createNewFile();
        }

        FileOutputStream outStream = new FileOutputStream(yourFile, false);
        write(userList, outStream);
    }

    public static void save() throws IOException
    {
        SettingsManager.getInstance().process();
    }

    /**
     * Calls the manager singletons' process methods to invoke reading from disk
     */
    public static void load() throws IOException, ClassNotFoundException
    {
        SettingsManager.getInstance().update();
    }

    /**
     * reads from file Setting db structure on disk
     *
     * @param Setting  class object identifier
     * @param userList hash map containing users indexed by unique id
     * @return userList hash map containing users indexed by unique id
     */
    public static HashMap<String, Setting> read(Setting Setting, HashMap<String, Setting> userList) throws IOException
    {
        userList = null;
        File f = new File(MainActivity.getAppContext().getFilesDir().getPath().toString() + "Setting.dat");
        if (f.isFile() && f.canRead())
        {
            FileInputStream fis = new FileInputStream(MainActivity.getAppContext().getFilesDir().getPath().toString() + "Setting.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            try
            {
                userList = (HashMap) ois.readObject();
            } catch (ClassNotFoundException e)
            {
                userList = null;
            }
            ois.close();
            fis.close();
        }
        return userList;
    }

    /**
     * serialize
     */
    public static void write(Object o, FileOutputStream outStream) throws IOException
    {
        ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
        objectOutputFile.writeObject(o);
        outStream.close();
        objectOutputFile.close();
    }

    public static Integer processId(String s) throws IOException
    {
        Integer i = (int) (Math.random() * 100);
        ;
        File f = new File(MainActivity.getAppContext().getFilesDir().getPath().toString() + s);
        if (f.isFile() && f.canRead())
        {
            FileInputStream fis = new FileInputStream(MainActivity.getAppContext().getFilesDir().getPath().toString() + s);
            ObjectInputStream ois = new ObjectInputStream(fis);

            try
            {
                i = (Integer) ois.readObject();
            } catch (ClassNotFoundException e)
            {
                i = (int) (Math.random() * 100);
                ;
            }
            ois.close();
            fis.close();
        }
        return i;
    }
}
