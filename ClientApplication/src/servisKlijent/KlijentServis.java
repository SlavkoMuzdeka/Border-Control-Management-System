/**
 * KlijentServis.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servisKlijent;

public interface KlijentServis extends java.rmi.Remote {
    public void slanjeNaPosrednika(java.lang.String naziv, int temp) throws java.rmi.RemoteException;
    public int isDokumentSmjesten(int idAplikacije) throws java.rmi.RemoteException;
    public int isDokumentPoslat(int idTestneAplikacije, byte[] data, java.lang.String nazivTerminala, java.lang.String tipPrelaza, int idPrelaza, java.lang.String nazivZipFajla) throws java.rmi.RemoteException;
    public int naPotjernici(int idAplikacije) throws java.rmi.RemoteException;
    public boolean postojiTerminal(java.lang.String nazivTerminala) throws java.rmi.RemoteException;
    public boolean aktivanPrelaz(java.lang.String vrstaPrelaza, int idPrelaza, java.lang.String nazivTerminala) throws java.rmi.RemoteException;
    public void provjeriIdSaTestneAplikacije(int idOsobe, int idTestneAplikacije, java.lang.String nazivTerminala, java.lang.String tipPrelaza, int idPrelaza) throws java.rmi.RemoteException;
    public void posaljiSaPolicijskeKontrole(int idOsobe, int temp) throws java.rmi.RemoteException;
}
