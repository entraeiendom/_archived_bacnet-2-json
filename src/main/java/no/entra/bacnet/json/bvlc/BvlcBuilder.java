package no.entra.bacnet.json.bvlc;

import no.entra.bacnet.Octet;

import static no.entra.bacnet.json.bvlc.Bvlc.findExpectdNumberOfOctetsInBvll;

public class BvlcBuilder {

    private final BvlcFunction function;
    private int fullMessageOctetLength;

    public BvlcBuilder(BvlcFunction function) {
        this.function = function;
    }

    public BvlcBuilder withMessageLength(Octet[] messageLength) {
        if (messageLength != null && messageLength.length == 2) {
            this.fullMessageOctetLength = findExpectdNumberOfOctetsInBvll(messageLength);
        }
        return this;
    }

    public Bvlc build() {
        Bvlc bvlc = new Bvlc(function, fullMessageOctetLength);
        return bvlc;
    }

}
