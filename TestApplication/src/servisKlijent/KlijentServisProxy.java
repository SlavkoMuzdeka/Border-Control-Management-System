package servisKlijent;

public class KlijentServisProxy implements servisKlijent.KlijentServis {
  private String _endpoint = null;
  private servisKlijent.KlijentServis klijentServis = null;
  
  public KlijentServisProxy() {
    _initKlijentServisProxy();
  }
  
  public KlijentServisProxy(String endpoint) {
    _endpoint = endpoint;
    _initKlijentServisProxy();
  }
  
  private void _initKlijentServisProxy() {
    try {
      klijentServis = (new servisKlijent.KlijentServisServiceLocator()).getKlijentServis();
      if (klijentServis != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)klijentServis)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)klijentServis)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (klijentServis != null)
      ((javax.xml.rpc.Stub)klijentServis)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public servisKlijent.KlijentServis getKlijentServis() {
    if (klijentServis == null)
      _initKlijentServisProxy();
    return klijentServis;
  }
  
  public void slanjeNaPosrednika(java.lang.String naziv, int temp) throws java.rmi.RemoteException{
    if (klijentServis == null)
      _initKlijentServisProxy();
    klijentServis.slanjeNaPosrednika(naziv, temp);
  }
  
  public int isDokumentSmjesten(int idAplikacije) throws java.rmi.RemoteException{
    if (klijentServis == null)
      _initKlijentServisProxy();
    return klijentServis.isDokumentSmjesten(idAplikacije);
  }
  
  public int isDokumentPoslat(int idTestneAplikacije, byte[] data, java.lang.String nazivTerminala, java.lang.String tipPrelaza, int idPrelaza, java.lang.String nazivZipFajla) throws java.rmi.RemoteException{
    if (klijentServis == null)
      _initKlijentServisProxy();
    return klijentServis.isDokumentPoslat(idTestneAplikacije, data, nazivTerminala, tipPrelaza, idPrelaza, nazivZipFajla);
  }
  
  public int naPotjernici(int idAplikacije) throws java.rmi.RemoteException{
    if (klijentServis == null)
      _initKlijentServisProxy();
    return klijentServis.naPotjernici(idAplikacije);
  }
  
  public boolean postojiTerminal(java.lang.String nazivTerminala) throws java.rmi.RemoteException{
    if (klijentServis == null)
      _initKlijentServisProxy();
    return klijentServis.postojiTerminal(nazivTerminala);
  }
  
  public boolean aktivanPrelaz(java.lang.String vrstaPrelaza, int idPrelaza, java.lang.String nazivTerminala) throws java.rmi.RemoteException{
    if (klijentServis == null)
      _initKlijentServisProxy();
    return klijentServis.aktivanPrelaz(vrstaPrelaza, idPrelaza, nazivTerminala);
  }
  
  public void provjeriIdSaTestneAplikacije(int idOsobe, int idTestneAplikacije, java.lang.String nazivTerminala, java.lang.String tipPrelaza, int idPrelaza) throws java.rmi.RemoteException{
    if (klijentServis == null)
      _initKlijentServisProxy();
    klijentServis.provjeriIdSaTestneAplikacije(idOsobe, idTestneAplikacije, nazivTerminala, tipPrelaza, idPrelaza);
  }
  
  public void posaljiSaPolicijskeKontrole(int idOsobe, int temp) throws java.rmi.RemoteException{
    if (klijentServis == null)
      _initKlijentServisProxy();
    klijentServis.posaljiSaPolicijskeKontrole(idOsobe, temp);
  }
  
  
}