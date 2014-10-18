package bluej.pkgmgr;

import javax.swing.JSplitPane;

import bluej.Config;
import bluej.debugmgr.texteval.TextEvalArea;

public class TextEvaluatorMgr {
	private boolean showingTextEvaluator = false;
	private TextEvalArea textEvaluator;
		
	/**
     * Tell whether we are currently showing the text evaluation pane.
     * 
     * @return true if the text eval pane is visible.
     */
    public boolean isTextEvalVisible()
    {
        return showingTextEvaluator;
    }

    /**
     * Show or hide the text evaluation component.
     */
    public void showHideTextEval(boolean show)
    {
        if (showingTextEvaluator == show) // already showing the right thing?
            return;

        if (show) {
            addTextEvaluatorPane();
            textEvaluator.requestFocus();
        }
        else {
            removeTextEvaluatorPane();
            editor.requestFocus();
        }
        pack();
        showingTextEvaluator = show;
    }

    /**
     * Remove the text evaluation pane from the frame.
     */
    private void removeTextEvaluatorPane()
    {
        textEvaluator.setPreferredSize(textEvaluator.getSize()); // memorize
                                                                 // current
                                                                 // sizes
        //classScroller.setPreferredSize(classScroller.getSize());
        //splitPane.setBottomComponent(objbench);
        showingTextEvaluator = false;
    }
    
    /**
     * Clear the text evaluation component (if it exists).
     */
    public void clearTextEval()
    {
        if (textEvaluator != null) {
            textEvaluator.clear();
        }
    }
    
    /**
     * Updates the background of the text evaluation component (if it exists),
     * when a project is opened/closed
     */
    public void updateTextEvalBackground(boolean emptyFrame)
    {
        if (textEvaluator != null) {
            textEvaluator.updateBackground(emptyFrame);
        }
    }

    /**
     * Add the text evaluation pane in the lower area of the frame.
     */
    private void addTextEvaluatorPane()
    {
        classScroller.setPreferredSize(classScroller.getSize()); // memorize
                                                                 // current size
        if (textEvaluator == null) {
            textEvaluator = new TextEvalArea(this, pkgMgrFont);
            objectBenchSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, objbench, textEvaluator);
            objectBenchSplitPane.setBorder(null);
            objectBenchSplitPane.setResizeWeight(1.0);
            objectBenchSplitPane.setDividerSize(5);
            if (!Config.isRaspberryPi()) objectBenchSplitPane.setOpaque(false);
            itemsToDisable.add(textEvaluator);
            addCtrlTabShortcut(textEvaluator.getFocusableComponent());
        }
        else {
            objectBenchSplitPane.setLeftComponent(objbench);
        }
        splitPane.setBottomComponent(objectBenchSplitPane);
        showingTextEvaluator = true;
    }

    
    
}
