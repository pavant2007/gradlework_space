package com.ganesh.ws;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
 
public class ServerPasswordCallback implements CallbackHandler {
 
    public void handle(Callback[] callbacks) throws IOException, 
        UnsupportedCallbackException {
 
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
 
        
        pc.setPassword("samba123");
        /*if (pc.getIdentifier().equals("business")) {
            // set the password on the callback. This will be compared to the
            // password which was sent from the client.
            pc.setPassword("samba123");
        }*/
    }
 
}