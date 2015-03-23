/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.view;

import client.*;
import java.util.ArrayList;

/**
 *
 * @author Ксюша
 */
public class CommandClass
{
    static String command;
   // static String[] info;
    static ArrayList <?> info;
    static String someInfo;
    static boolean flag = false;
    
    public static String getCommand() throws InterruptedException
    {
        while(!flag)
        {
            Thread.sleep(100);
        }
        Thread.sleep(100);
        flag = false;
        return command;
    }
    
    public static ArrayList<?> getInformation() throws InterruptedException
    {
        Thread.sleep(500);
        return info;
    }
    public static String getSomeInformation() throws InterruptedException
    {
        Thread.sleep(500);
        return someInfo;
    }
    public static void setSomeInformation(String someInfo) throws InterruptedException
    {
        CommandClass.someInfo = someInfo;
    }
    public static void setInformation(ArrayList<?> info)
    {
        CommandClass.info = info;
    }
    
    public static void setCommand(String command)
    {
        flag = true;
        CommandClass.command = command;

    }
    public static Integer[] getList() throws InterruptedException
    {
        int  size = Integer.valueOf(CommandClass.getSomeInformation());
        Integer[] list = new Integer[size];
        for(int i=0; i<size; i++)
        {
            list[i] = i+1;
        }
        return list;
    }
    public static Integer[] getListAddCount() throws InterruptedException
    {
        int  size = Integer.valueOf(CommandClass.getSomeInformation());
        size++;
        Integer[] list = new Integer[size];
        for(int i=0; i<size; i++)
        {
            list[i] = i+1;
        }
        return list;
    }
       public static Integer[] getListDelCount() throws InterruptedException
    {
        int  size = Integer.valueOf(CommandClass.getSomeInformation());
        size--;
        Integer[] list = new Integer[size];
        for(int i=0; i<size; i++)
        {
            list[i] = i+1;
        }
        return list;
    }
     
}
