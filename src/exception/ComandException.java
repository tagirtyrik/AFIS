/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Ксю
 */
public class ComandException extends NullPointerException{
    public ComandException(){
        super();
    }
    public ComandException(String cmd){
        super(cmd);
    }
}
