package service;

public class TerminalServisProxy implements service.TerminalServis {
  private String _endpoint = null;
  private service.TerminalServis terminalServis = null;
  
  public TerminalServisProxy() {
    _initTerminalServisProxy();
  }
  
  public TerminalServisProxy(String endpoint) {
    _endpoint = endpoint;
    _initTerminalServisProxy();
  }
  
  private void _initTerminalServisProxy() {
    try {
      terminalServis = (new service.TerminalServisServiceLocator()).getTerminalServis();
      if (terminalServis != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)terminalServis)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)terminalServis)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (terminalServis != null)
      ((javax.xml.rpc.Stub)terminalServis)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public service.TerminalServis getTerminalServis() {
    if (terminalServis == null)
      _initTerminalServisProxy();
    return terminalServis;
  }
  
  public void loadConfig() throws java.rmi.RemoteException{
    if (terminalServis == null)
      _initTerminalServisProxy();
    terminalServis.loadConfig();
  }
  
  public boolean kreirajTerminal(java.lang.String naziv, int brojUlaza, int brojIzlaza) throws java.rmi.RemoteException{
    if (terminalServis == null)
      _initTerminalServisProxy();
    return terminalServis.kreirajTerminal(naziv, brojUlaza, brojIzlaza);
  }
  
  public boolean obrisiTerminal(java.lang.String nazivTerminala) throws java.rmi.RemoteException{
    if (terminalServis == null)
      _initTerminalServisProxy();
    return terminalServis.obrisiTerminal(nazivTerminala);
  }
  
  public model.Terminal dohvatiTerminal(java.lang.String nazivTerminala) throws java.rmi.RemoteException{
    if (terminalServis == null)
      _initTerminalServisProxy();
    return terminalServis.dohvatiTerminal(nazivTerminala);
  }
  
  public boolean provjeraOsobe1(java.lang.String nazivTerminala, int idUlaza, int idOsobe) throws java.rmi.RemoteException{
    if (terminalServis == null)
      _initTerminalServisProxy();
    return terminalServis.provjeraOsobe1(nazivTerminala, idUlaza, idOsobe);
  }
  
  public boolean setSlobodan(boolean t, java.lang.String nazivTerminala, int idUI, java.lang.String ulazIzlaz, java.lang.String tipUlazIzlaz) throws java.rmi.RemoteException{
    if (terminalServis == null)
      _initTerminalServisProxy();
    return terminalServis.setSlobodan(t, nazivTerminala, idUI, ulazIzlaz, tipUlazIzlaz);
  }
  
  public boolean azurirajTerminal(java.lang.String nazivTerminala, int brojUlaza, int brojIzlaza) throws java.rmi.RemoteException{
    if (terminalServis == null)
      _initTerminalServisProxy();
    return terminalServis.azurirajTerminal(nazivTerminala, brojUlaza, brojIzlaza);
  }
  
  public boolean provjeraOsobe2(java.lang.String nazivTerminala, int idIzlaza, int idOsobe) throws java.rmi.RemoteException{
    if (terminalServis == null)
      _initTerminalServisProxy();
    return terminalServis.provjeraOsobe2(nazivTerminala, idIzlaza, idOsobe);
  }
  
  public void blokirajTerminal(boolean t, java.lang.String nazivTerminala) throws java.rmi.RemoteException{
    if (terminalServis == null)
      _initTerminalServisProxy();
    terminalServis.blokirajTerminal(t, nazivTerminala);
  }
  
  public boolean isObradjenDokument2(java.lang.String nazivTerminala, int idIzlaza, byte[] data, java.lang.String zipFajl) throws java.rmi.RemoteException{
    if (terminalServis == null)
      _initTerminalServisProxy();
    return terminalServis.isObradjenDokument2(nazivTerminala, idIzlaza, data, zipFajl);
  }
  
  public java.lang.String dohvatiProvjerenePutnike() throws java.rmi.RemoteException{
    if (terminalServis == null)
      _initTerminalServisProxy();
    return terminalServis.dohvatiProvjerenePutnike();
  }
  
  public boolean isObradjenDokument1(java.lang.String nazivTerminala, int idUlaza, byte[] data, java.lang.String zipFajl) throws java.rmi.RemoteException{
    if (terminalServis == null)
      _initTerminalServisProxy();
    return terminalServis.isObradjenDokument1(nazivTerminala, idUlaza, data, zipFajl);
  }
  
  public void dodajProvjerenogPutnika(int idPutnika) throws java.rmi.RemoteException{
    if (terminalServis == null)
      _initTerminalServisProxy();
    terminalServis.dodajProvjerenogPutnika(idPutnika);
  }
  
  
}