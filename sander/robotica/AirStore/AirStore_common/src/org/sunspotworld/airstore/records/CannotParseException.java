/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.records;

/**
 *
 * @author randy
 */
public class CannotParseException extends Exception {
    public CannotParseException(String message){
        super(message);
    }

    public CannotParseException() {
    }
}
