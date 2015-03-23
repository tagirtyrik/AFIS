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
 * @author GeneraL
 */
public class CmdParser {
    private static final String splitterHelp="::";//разделитель для справки
    private static final String splitterCommand=" ";//разделитель для аргументов комманды
    private static HashMap <String,HashMap <String, String> > help;//справка храниться здесь
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
            if(!help.containsKey(cmd))
                throw new CommandNotFoundException(cmd);
        arguments=new String[in.length-1];
        for(int i=0;i<in.length-1;i++){
            arguments[i]=in[i+1];
        }
        if(getHelpArguments(cmd).length!=arguments.length && !cmd.equals("help"))throw new InvalidArgumentsException(cmd,getHelp(cmd));
        
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
    }/**
     * Возвращает аргумент под номером
     * @param number номер аргумента
     * @return аргумент
     */
    public static String getArgument(int number){
        if(number>=arguments.length || number<0)throw new InvalidArgumentsException(cmd,getHelp(cmd));
        return arguments[number];
    }
    /**
     * анализ и установка инструкций для синтаксического анализа комманд
     * @param help инструкции
     */
    public static void setHelp(String[] help){
        CmdParser.help=new HashMap();
        for(int i=0;i<help.length;i++){
            HashMap <String, String> cmdInput=new HashMap();
            
            String[] cmdDescriptionExample=help[i].split(splitterHelp);//первичное деление строки помощи
            String[] cmdArguments=cmdDescriptionExample[0].split(splitterCommand);
            String cmd=cmdArguments[0];
            if(cmdArguments.length>1){
                String args=new String();
                    for(int j=1;j<cmdArguments.length;j++){
                        args+=cmdArguments[j]+" ";
                    }
               cmdInput.put("Args", args);//добавляем список аргументов
            }
            if(cmdDescriptionExample.length>1){//проверяем, есть ли что-то после описания синтаксиса команды
                cmdInput.put("Description", cmdDescriptionExample[1]);
                if(cmdDescriptionExample.length>2){//проверяем, есть ли пример
                    cmdInput.put("Example", cmdDescriptionExample[2]);
                }
            }
            
            CmdParser.help.put(
                    cmd, 
                    cmdInput);
        }
    }
    /**
     * возвращает справку по комманде
     * @param cmd - комманда
     * @return инструкции в читаемом виде
     */
    public static String getHelp(String cmd){
        
        if(!help.containsKey(cmd))throw new CommandNotFoundException(cmd);
        
        StringBuffer formattedHelp=new StringBuffer();
        if (help.get(cmd).containsKey("Args")){
            formattedHelp.append("\tПараметры:");
            formattedHelp.append(help.get(cmd).get("Args"));
            formattedHelp.append("\n");
        }
        if (help.get(cmd).containsKey("Description")){
            formattedHelp.append( "\tОписание:");
            formattedHelp.append(help.get(cmd).get("Description"));
            formattedHelp.append("\n");
        }
        if(help.get(cmd).containsKey("Example")){
            formattedHelp.append("\tПример:");
            formattedHelp.append(help.get(cmd).get("Example"));
            formattedHelp.append("\n");
        }
        return new String (formattedHelp);
    }
    /**
     * Возвращает список названий параметров, требуемых для работы комманды
     * @param command комманда
     * @return список названий параметров
     */
     public static String[] getHelpArguments(String command){
         
         if(!help.containsKey(command))throw new CommandNotFoundException(cmd);
         if(!help.get(command).containsKey("Args"))return new String[0];
         return help.get(command).get("Args").split(splitterCommand);
         
     }
     /**
      * Возвращает полную справку по системе в форматированном виде
      * @return справка по системе
      */
       public static String getHelp(){
            ArrayList<String> keys=new ArrayList(help.keySet());
            ArrayList<HashMap <String, String> > values=new ArrayList(help.values()); 
            StringBuffer formattedHelp=new StringBuffer();
        for(int i=0;i<help.size();i++){
            formattedHelp.append(keys.get(i));
            formattedHelp.append("\n");
            if (values.get(i).containsKey("Args")){
                formattedHelp.append("\tПараметры: ");
                formattedHelp.append(values.get(i).get("Args"));
                formattedHelp.append("\n");
            }
            if (values.get(i).containsKey("Description")){
                formattedHelp.append("\tОписание: ");
                formattedHelp.append(values.get(i).get("Description"));
                formattedHelp.append("\n");
            }
            if(values.get(i).containsKey("Example")){
                formattedHelp.append("\tПример: ");
                formattedHelp.append(values.get(i).get("Example"));
                formattedHelp.append("\n");
            }
            
        }
        return new String (formattedHelp);
    }
}
