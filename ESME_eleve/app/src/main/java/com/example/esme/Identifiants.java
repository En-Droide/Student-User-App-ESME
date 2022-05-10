package com.example.esme;

import java.io.Serializable;

public class Identifiants implements Serializable {
    public String ndc,mdpenc;
    public Identifiants(String ndc,String mdp) {
        this.ndc=ndc;
        this.mdpenc=encrypt(mdp);
    }
    public String decrypt(String mdp){
        String mdpDec="";
        char[] mdpDecTemp = mdp.toCharArray();
        for(int i=0;i<mdp.length();i++){
            mdpDecTemp[i] = (char)((int)mdpDecTemp[i]+i);
        }
        mdpDec=new String(mdpDecTemp);

        return mdpDec;
    }
    public String encrypt(String mdp){
        String mdpEnc="";
        char[] mdpEncTemp = mdp.toCharArray();
        for(int i=0;i<mdp.length();i++){
            mdpEncTemp[i] = (char)((int)mdpEncTemp[i]-i);
        }
        mdpEnc=new String(mdpEncTemp);

        return mdpEnc;
    }
}
