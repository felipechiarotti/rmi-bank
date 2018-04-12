/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmibank;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmibank.code.client.Client;
import rmibank.gui.GUILogin;

/**
 *
 * @author a120111
 */
public class RunClient {
    public static void main(String[] args){
       GUILogin login = new GUILogin();
    }
}
