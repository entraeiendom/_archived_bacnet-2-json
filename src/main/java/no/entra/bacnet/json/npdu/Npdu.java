package no.entra.bacnet.json.npdu;

import no.entra.bacnet.Octet;

import java.util.Arrays;

public class Npdu {
    public static final Octet version = Octet.fromHexString("01");
    private NpduControl control;
    private Octet[] sourceNetworkAddress;
    private Octet sourceMacLayerAddress;
    private Octet[] destinationNetworkAddress;
    private Octet destinationMacLayerAddress;
    private Octet hopCount;

    public Octet getVersion() {
        return version;
    }

    public NpduControl getControl() {

        return control;
    }

    public void setControl(Octet controlOctet) {
        this.control = NpduControl.fromOctet(controlOctet);
    }

    public void setControl(NpduControl control) {
        this.control = control;
    }

    public Octet[] getSourceNetworkAddress() {
        return sourceNetworkAddress;
    }

    public void setSourceNetworkAddress(Octet[] sourceNetworkAddress) {
        this.sourceNetworkAddress = sourceNetworkAddress;
    }

    public Octet getSourceMacLayerAddress() {
        return sourceMacLayerAddress;
    }

    public void setSourceMacLayerAddress(Octet sourceMacLayerAddress) {
        this.sourceMacLayerAddress = sourceMacLayerAddress;
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

    @Override
    public String toString() {
        return "Npdu{" +
                "control=" + control +
                ", sourceNetworkAddress=" + Arrays.toString(sourceNetworkAddress) +
                ", sourceMacLayerAddress=" + sourceMacLayerAddress +
                ", destinationNetworkAddress=" + Arrays.toString(destinationNetworkAddress) +
                ", destinationMacLayerAddress=" + destinationMacLayerAddress +
                ", hopCount=" + hopCount +
                '}';
    }
}
