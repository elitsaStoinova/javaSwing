package gui;
import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
public class CustomTreeCellRenderer extends DefaultTreeCellRenderer  {

    public CustomTreeCellRenderer() {
        setFont(new Font("Arial", Font.PLAIN, 18));
    }

    @Override
    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        super.getTreeCellRendererComponent(
                tree, value, sel,
                expanded, leaf, row,
                hasFocus);

        setClosedIcon(null);
        setOpenIcon(null);
        setLeafIcon(null);

        return this;
    }

}
