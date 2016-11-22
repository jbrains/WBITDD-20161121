package ca.jbrains.pos.test;

public class InterpretAllCommandsAsBarcodes {
    private BarcodeScannedListener barcodeScannedListener;

    public InterpretAllCommandsAsBarcodes(BarcodeScannedListener barcodeScannedListener) {
        this.barcodeScannedListener = barcodeScannedListener;
    }

    public void interpretCommand(String commandText) {
        barcodeScannedListener.onBarcode(commandText);
    }
}