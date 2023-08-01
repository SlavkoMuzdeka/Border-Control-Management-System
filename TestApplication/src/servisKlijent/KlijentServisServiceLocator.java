/**
 * KlijentServisServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servisKlijent;

public class KlijentServisServiceLocator extends org.apache.axis.client.Service implements servisKlijent.KlijentServisService {

    public KlijentServisServiceLocator() {
    }


    public KlijentServisServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public KlijentServisServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for KlijentServis
    private java.lang.String KlijentServis_address = "http://localhost:8080/TestServerKlijent/services/KlijentServis";

    public java.lang.String getKlijentServisAddress() {
        return KlijentServis_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String KlijentServisWSDDServiceName = "KlijentServis";

    public java.lang.String getKlijentServisWSDDServiceName() {
        return KlijentServisWSDDServiceName;
    }

    public void setKlijentServisWSDDServiceName(java.lang.String name) {
        KlijentServisWSDDServiceName = name;
    }

    public servisKlijent.KlijentServis getKlijentServis() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(KlijentServis_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getKlijentServis(endpoint);
    }

    public servisKlijent.KlijentServis getKlijentServis(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            servisKlijent.KlijentServisSoapBindingStub _stub = new servisKlijent.KlijentServisSoapBindingStub(portAddress, this);
            _stub.setPortName(getKlijentServisWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setKlijentServisEndpointAddress(java.lang.String address) {
        KlijentServis_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (servisKlijent.KlijentServis.class.isAssignableFrom(serviceEndpointInterface)) {
                servisKlijent.KlijentServisSoapBindingStub _stub = new servisKlijent.KlijentServisSoapBindingStub(new java.net.URL(KlijentServis_address), this);
                _stub.setPortName(getKlijentServisWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("KlijentServis".equals(inputPortName)) {
            return getKlijentServis();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://servisKlijent", "KlijentServisService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://servisKlijent", "KlijentServis"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("KlijentServis".equals(portName)) {
            setKlijentServisEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
