/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author phill
 */
public class MissingInputException extends Exception{

    public MissingInputException(String string) {
        super(string);
    }
    
}
