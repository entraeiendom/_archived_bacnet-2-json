package no.entra.bacnet.json.npdu;

import no.entra.bacnet.Octet;

public class NpduBuilder {

    private final Octet version;
    private final Octet control;
    private Octet[] destinationNetworkAddress;
    private Octet destinationMacLayerAddress;
    private Octet hopCount;

    public NpduBuilder(Octet control) {
        this.version = Octet.fromHexString("01");
        this.control = control;
    }

    public NpduBuilder withDestinationNetworkAddress(Octet[] destinationNetworkAddress) {
        this.destinationNetworkAddress = destinationNetworkAddress;
        return this;
    }
    public NpduBuilder withDestinationMacLayerAddress(Octet destinationMacLayerAddress) {
        this.destinationMacLayerAddress = destinationMacLayerAddress;
        return this;
    }
    public NpduBuilder withHopCount(Octet hopCount) {
        this.hopCount = hopCount;
        return this;
    }

    public Npdu build() {
        Npdu npdu = new Npdu();
        npdu.setControl(control);

        return npdu;
    }

}
