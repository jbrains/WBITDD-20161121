package ca.jbrains.pos.test;

public class InterpretCommands {
    private BarcodeScannedListener barcodeScannedListener;

    public InterpretCommands(BarcodeScannedListener barcodeScannedListener) {
        this.barcodeScannedListener = barcodeScannedListener;
    }

    public void interpretCommand(String commandText) {
        barcodeScannedListener.onBarcode(commandText);
    }
}