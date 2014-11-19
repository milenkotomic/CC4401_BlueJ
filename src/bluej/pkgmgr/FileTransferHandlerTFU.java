package bluej.pkgmgr;

import bluej.utility.Debug;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * The TransferHandler handles drop events (the tail end of Drag-and-Drop).
 * This specific TransferHandler receives file drops (drops of files).
 * 
 * It is used for the package editor (the main diagram) so that is can receive
 * Java source files via drag-and-drop.
 * 
 * @author mik
 * @version 1.0
 */
public class FileTransferHandlerTFU extends TransferHandler 
{
    private DataFlavor fileFlavour;
    private TabbedFrameUnit pmf;
    
    /**
     * Create a new FileTransferHandler for a specific PackageMgrFrame.
     */
    public FileTransferHandlerTFU(TabbedFrameUnit pmf)
    {
        fileFlavour = DataFlavor.javaFileListFlavor;
        this.pmf = pmf;
    }
    
    /**
     * importData - called when a drop event is received. See whether we
     * can import the dropped item, and if so, handle it.
     * 
     * @param c The component that drop occured on.
     * @param t The item being dropped.
     * @return true iff we can import the item
     */
    @SuppressWarnings("unchecked")
    public boolean importData(JComponent c, Transferable t) 
    {
        try {
            if (!canImport(c, t.getTransferDataFlavors())) {
                return false;
            }

            List<File> files = (List<File>) t.getTransferData(fileFlavour);
            pmf.addFiles(files);
        } catch (UnsupportedFlavorException ex) {
            Debug.reportError("Cannot handle D&D transfer");
        } catch (IOException ex) {
            Debug.reportError("I/O exception during D&D import attempt");
        }
        return true;
    }
    
    /**
     * Check whether we can import the given data flavours into this component.
     * This will be true if the data items are files.
     */
    public boolean canImport(JComponent c, DataFlavor[] flavours)
    {
        return hasFileFlavor(flavours);
    }

    /**
     * Check whether the data can be received as a file.
     */
    private boolean hasFileFlavor(DataFlavor[] flavours)
    {
        for (int i = 0; i < flavours.length; i++) {
            if (fileFlavour.equals(flavours[i])) {
                return true;
            }
        }
        return false;
    }

}
