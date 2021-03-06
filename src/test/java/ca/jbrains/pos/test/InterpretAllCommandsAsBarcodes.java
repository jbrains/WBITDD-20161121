package ca.jbrains.pos.test;

public class InterpretAllCommandsAsBarcodes implements InterpretCommand {
    private BarcodeScannedListener barcodeScannedListener;

    public InterpretAllCommandsAsBarcodes(BarcodeScannedListener barcodeScannedListener) {
        this.barcodeScannedListener = barcodeScannedListener;
    }

    @Override
    public void interpretCommand(String commandText) {
        barcodeScannedListener.onBarcode(commandText);
    }
}