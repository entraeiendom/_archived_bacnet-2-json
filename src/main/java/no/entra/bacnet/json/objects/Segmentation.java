package no.entra.bacnet.json.objects;

import no.entra.bacnet.Octet;

public enum Segmentation {
    SegmentedBoth("00"),
    SegmentedTransmit("01"),
    SegmentedReceive("02"),
    NoSegmentation("03");

    private String segmentationHex;

    public static Segmentation fromSegmentationHex(String hexString) {
        for (Segmentation type : values()) {
            if (type.getSegmentationHex().equals(hexString)) {
                return type;
            }
        }
        return null;
    }


    public static Segmentation fromOctet(Octet segmentationOctet) throws NumberFormatException {
        if (segmentationOctet == null) {
            return null;
        }
        return fromSegmentationHex(segmentationOctet.toString());
    }


    public String getSegmentationHex() {
        return segmentationHex;
    }

    private Segmentation(String hexString) {
        this.segmentationHex = hexString;
    }
}
