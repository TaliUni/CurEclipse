package overview;


import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import java.io.File;
import java.util.Vector;

public class MyTree implements TreeModel {
    private boolean showAncestors;
    private Vector<TreeModelListener> treeModelListeners =
        new Vector<TreeModelListener>();
    private File rootFile;

    public MyTree(File root) {
        showAncestors = false;
        rootFile = root;
    }

    /**
     * Used to toggle between show ancestors/show descendant and
     * to change the root of the tree.
     */
    public void showAncestor(boolean b, Object newRoot) {
        showAncestors = b;
        File oldRoot = rootFile;
        if (newRoot != null) {
           rootFile = (File)newRoot;
        }
        fireTreeStructureChanged(oldRoot);
    }


//////////////// Fire events //////////////////////////////////////////////

    /**
     * The only event raised by this model is TreeStructureChanged with the
     * root as path, i.e. the whole tree has changed.
     */
    protected void fireTreeStructureChanged(File oldRoot) {
        int len = treeModelListeners.size();
        TreeModelEvent e = new TreeModelEvent(this, 
                                              new Object[] {oldRoot});
        for (TreeModelListener tml : treeModelListeners) {
            tml.treeStructureChanged(e);
        }
    }


//////////////// TreeModel interface implementation ///////////////////////

    /**
     * Adds a listener for the TreeModelEvent posted after the tree changes.
     */
    public void addTreeModelListener(TreeModelListener l) {
        treeModelListeners.addElement(l);
    }

    /**
     * Returns the child of parent at index index in the parent's child array.
     */
    public Object getChild(Object parent, int index) {
        File f = (File)parent;
        if (showAncestors) {
            
            return f.getParent();
        }
        return f.listFiles()[index];
    }

    /**
     * Returns the number of children of parent.
     */
    public int getChildCount(Object parent) {
        File f = (File)parent;
        return f.list().length;
        
    }

    /**
     * Returns the index of child in parent.
     */
    public int getIndexOfChild(Object parent, Object child) {
        File f = (File)parent;
        
        return 0;
    }

    /**
     * Returns the root of the tree.
     */
    public Object getRoot() {
        return rootFile;
    }

    /**
     * Returns true if node is a leaf.
     */
    public boolean isLeaf(Object node) {
        File f = (File)node;
        
        if(f.isDirectory()==false)
        {
        	return true;
        }
        else return false;
        
    }

    /**
     * Removes a listener previously added with addTreeModelListener().
     */
    public void removeTreeModelListener(TreeModelListener l) {
        treeModelListeners.removeElement(l);
    }

    /**
     * Messaged when the user has altered the value for the item
     * identified by path to newValue.  Not used by this model.
     */
    public void valueForPathChanged(TreePath path, Object newValue) {
        System.out.println("*** valueForPathChanged : "
                           + path + " --> " + newValue);
    }
}