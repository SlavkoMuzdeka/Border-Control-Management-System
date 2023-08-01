/**
 * TerminalServis.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package service;

public interface TerminalServis extends java.rmi.Remote {
    public void loadConfig() throws java.rmi.RemoteException;
    public boolean kreirajTerminal(java.lang.String naziv, int brojUlaza, int brojIzlaza) throws java.rmi.RemoteException;
    public boolean obrisiTerminal(java.lang.String nazivTerminala) throws java.rmi.RemoteException;
    public model.Terminal dohvatiTerminal(java.lang.String nazivTerminala) throws java.rmi.RemoteException;
    public boolean provjeraOsobe1(java.lang.String nazivTerminala, int idUlaza, int idOsobe) throws java.rmi.RemoteException;
    public boolean setSlobodan(boolean t, java.lang.String nazivTerminala, int idUI, java.lang.String ulazIzlaz, java.lang.String tipUlazIzlaz) throws java.rmi.RemoteException;
    public boolean azurirajTerminal(java.lang.String nazivTerminala, int brojUlaza, int brojIzlaza) throws java.rmi.RemoteException;
    public boolean provjeraOsobe2(java.lang.String nazivTerminala, int idIzlaza, int idOsobe) throws java.rmi.RemoteException;
    public void blokirajTerminal(boolean t, java.lang.String nazivTerminala) throws java.rmi.RemoteException;
    public boolean isObradjenDokument2(java.lang.String nazivTerminala, int idIzlaza, byte[] data, java.lang.String zipFajl) throws java.rmi.RemoteException;
    public java.lang.String dohvatiProvjerenePutnike() throws java.rmi.RemoteException;
    public boolean isObradjenDokument1(java.lang.String nazivTerminala, int idUlaza, byte[] data, java.lang.String zipFajl) throws java.rmi.RemoteException;
    public void dodajProvjerenogPutnika(int idPutnika) throws java.rmi.RemoteException;
}
