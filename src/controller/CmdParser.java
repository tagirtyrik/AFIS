/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.util.HashMap;
import java.util.ArrayList;
import exception.*;
/**
 *Предназначен для:
 * <li> синтаксичкский анализ строки ввода, формирование комманды и списка агрументов
 * <li> поиск ошибок в в строке ввода
 * <li> обеспечение работы с инструкциями для пользователя
 * @author Ксю
 */
public class CmdParser {
    private static final String splitterCommand=" ";//разделитель для аргументов комманды
    private static  String cmd;//комманда
    private static String[] arguments;//аргументы
    /**
     *  синтаксичкский анализ строки ввода, формирование комманды и списка агрументов
     * setHelp(String[] help) должен быть выполнен
     * @param input строка ввода
     */
    public static void parseInput(String input){
        String[] in =input.split(splitterCommand);
        cmd=in[0];
        arguments=new String[in.length-1];
        for(int i=0;i<in.length-1;i++){
            arguments[i]=in[i+1];
        }
    }
    /**
     * возвращает комманду
     * parseInput(String input) должен быть выполнен
     * @return комманда
     */
    public static String getCommand(){
        return cmd;
    }
    /**
     * возвращает аргументы
     * parseInput(String input) должен быть выполнен
     * @return аргументы
     */
    public static String[] getArguments(){
        return arguments;
    }
}
