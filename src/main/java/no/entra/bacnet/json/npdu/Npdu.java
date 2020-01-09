package no.entra.bacnet.json.npdu;

import no.entra.bacnet.Octet;
import no.entra.bacnet.json.utils.HexUtils;

import java.util.Arrays;

public class Npdu {
    public static final Octet version = Octet.fromHexString("01");
    private Octet control;
    private Octet[] sourceNetworkAddress;
    private Octet[] sourceMacLayerAddress;
    private Octet[] destinationNetworkAddress;
    private Octet destinationMacLayerAddress;
    private Octet hopCount;
    private boolean expectingResponse = false;

    public Octet getVersion() {
        return version;
    }

    public Octet getControl() {
        return control;
    }

    public void setControl(Octet controlOctet) {
        this.control = controlOctet;
    }

    public Octet[] getSourceNetworkAddress() {
        return sourceNetworkAddress;
    }

    public void setSourceNetworkAddress(Octet[] sourceNetworkAddress) {
        this.sourceNetworkAddress = sourceNetworkAddress;
    }

    public Octet[] getSourceMacLayerAddress() {
        return sourceMacLayerAddress;
    }

    public void setSourceMacLayerAddress(Octet[] sourceMacLayerAddress) {
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

    public boolean isSourceAvailable() {
        boolean sourceIsAvailable = false;
        char lowerControl = control.getSecondNibble();
        sourceIsAvailable = HexUtils.isBitSet(lowerControl, 3);
        return sourceIsAvailable;
    }

    public boolean isDestinationAvailable() {
        boolean destinationIsAvailable = false;
        char higerControl = control.getFirstNibble();
        destinationIsAvailable = HexUtils.isBitSet(higerControl, 1);
        return destinationIsAvailable;
    }

    public boolean isExpectingResponse() {
        return expectingResponse;
    }
    public void expectingResponse(boolean expectingResponse) {
        this.expectingResponse = expectingResponse;
    }

    public boolean getExpectingResponse() {
        return expectingResponse;
    }
}
