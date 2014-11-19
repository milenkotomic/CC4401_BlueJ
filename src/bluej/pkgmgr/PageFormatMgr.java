package bluej.pkgmgr;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;

import bluej.Config;

public class PageFormatMgr {
	
	private static PageFormat pageFormat = null;
	
	public void doPageSetup(){
		PrinterJob job = PrinterJob	.getPrinterJob();
		PageFormat pfmt = job.pageDialog(getPageFormat());
		setPageFormat(pfmt);
	}
		   
	public  PageFormat getPageFormat()
	{
		if (pageFormat == null) {
			pageFormat = PrinterJob.getPrinterJob().defaultPage();

		}
		//Important that this is set before the margins:
		int orientation = Config.getPropInteger("bluej.printer.paper.orientation", pageFormat.getOrientation());
		pageFormat.setOrientation(orientation);

		Paper paper = pageFormat.getPaper();
		int x = Config.getPropInteger("bluej.printer.paper.x", 72);
		int y = Config.getPropInteger("bluej.printer.paper.y", 72);
		int width = Config.getPropInteger("bluej.printer.paper.width", (int)paper.getWidth() - 72 - x);
		int height = Config.getPropInteger("bluej.printer.paper.height", (int)paper.getHeight() - 72 - y);
		paper.setImageableArea(x, y, width, height);
		//paper is a copy of pageFormat's paper, so we must use set again to make the changes:
		pageFormat.setPaper(paper);
		return pageFormat;
	}

	public void setPageFormat(PageFormat page)
	{
		pageFormat = page;
		// We must get the measurements from the paper (which ignores orientation)
		// rather than page format (which takes it into account) because ultimately
		// we will use paper.setImageableArea to load the dimensions again
		Paper paper = pageFormat.getPaper();
		double x = paper.getImageableX();
		double y = paper.getImageableY();
		double width = paper.getImageableWidth();
		double height = paper.getImageableHeight();
		//The sizes are in points, so saving them as an integer should be precise enough:
		Config.putPropInteger("bluej.printer.paper.x", (int)x);
		Config.putPropInteger("bluej.printer.paper.y", (int)y);
		Config.putPropInteger("bluej.printer.paper.width", (int)width);
		Config.putPropInteger("bluej.printer.paper.height", (int)height);
		int orientation = pageFormat.getOrientation();
		Config.putPropInteger("bluej.printer.paper.orientation", orientation);

	}
}
