package no.entra.bacnet.json.npdu;

import no.entra.bacnet.Octet;

public class Npdu {
    public static final Octet version = Octet.fromHexString("01");
    private Octet control;
    private Octet[] destinationNetworkAddress;
    private Octet destinationMacLayerAddress;
    private Octet hopCount;

    public Octet getVersion() {
        return version;
    }

    public Octet getControl() {
        return control;
    }

    public void setControl(Octet control) {
        this.control = control;
    }

    public Octet[] getDestinationNetworkAddress() {
        return destinationNetworkAddress;
    }

    public void setDestinationNetworkAddress(Octet[] destinationNetworkAddress) {
        this.destinationNetworkAddress = destinationNetworkAddress;
    }

    public Octet getDestinationMacLayerAddress() {
        return destinationMacLayerAddress;
    }

    public void setDestinationMacLayerAddress(Octet destinationMacLayerAddress) {
        this.destinationMacLayerAddress = destinationMacLayerAddress;
    }

    public Octet getHopCount() {
        return hopCount;
    }

    public void setHopCount(Octet hopCount) {
        this.hopCount = hopCount;
    }
}
