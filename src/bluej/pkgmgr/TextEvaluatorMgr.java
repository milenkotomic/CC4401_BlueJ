package bluej.pkgmgr;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import bluej.Config;
import bluej.debugmgr.objectbench.ObjectBenchTFU;
import bluej.debugmgr.texteval.TextEvalArea;
import bluej.debugmgr.texteval.TextEvalAreaTFU;

public class TextEvaluatorMgr {
	private boolean showingTextEvaluator = false;
	private TextEvalAreaTFU textEvaluator;
	private ObjectBenchTFU objbench;
		
	public TextEvaluatorMgr(TextEvalAreaTFU text, ObjectBenchTFU objbench){
		 textEvaluator = text;
		 this.objbench = objbench;
	}
	
	/**
     * Tell whether we are currently showing the text evaluation pane.
     * 
     * @return true if the text eval pane is visible.
     */
    public boolean isTextEvalVisible()
    {
        return showingTextEvaluator;
    }

    public TextEvalAreaTFU getTextEval(){
    	return textEvaluator;
    }
    
    public JComponent getFocus(){
    	return textEvaluator.getFocusableComponent();
    }
    
    /**
     * Show or hide the text evaluation component.
     */
    public boolean showHideTextEval(boolean show)
    {
        if (showingTextEvaluator == show) // already showing the right thing?
            return false;

        if (show) {
            //addTextEvaluatorPane();
            textEvaluator.requestFocus();
        }
        else {
            removeTextEvaluatorPane();
            return true;
        }

        showingTextEvaluator = show;
        return false;
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
/*


    /**
     * Add the text evaluation pane in the lower area of the frame.
     */
    protected JSplitPane addTextEvaluatorPane()
    {
       	JSplitPane objectBenchSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, objbench, textEvaluator);
        objectBenchSplitPane.setBorder(null);
        objectBenchSplitPane.setResizeWeight(1.0);
        objectBenchSplitPane.setDividerSize(5);
        if (!Config.isRaspberryPi()) objectBenchSplitPane.setOpaque(false);
        objectBenchSplitPane.setLeftComponent(objbench);
                
        showingTextEvaluator = true;
        return objectBenchSplitPane;
    }       
}
