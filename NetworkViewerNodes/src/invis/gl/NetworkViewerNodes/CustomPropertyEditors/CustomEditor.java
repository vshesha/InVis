package invis.gl.NetworkViewerNodes.CustomPropertyEditors;

import java.util.Collection;
import java.util.Set;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.collections.map.MultiValueMap;

/**
 *
 * @author Matt
 */
public class CustomEditor extends javax.swing.JPanel
{

    /**
     * Creates new form CustomEditor
     */
    public CustomEditor(MultiValueMap mvm)
    {
        initComponents();
        Set<String> PostConditionSet = mvm.keySet();

        DefaultTableModel data = new DefaultTableModel();
        data.setColumnCount(2);

        jTable1.setValueAt("Column1", 0, 0);
        jTable1.setValueAt("Column2", 0, 1);
        data.setColumnIdentifiers(new String[]
                {
                    "Post Condition", "Case"
                });
        data.setRowCount(mvm.totalSize());

        int position = 0;
        for (int PostconditionItr = 0; PostconditionItr < PostConditionSet.size(); PostconditionItr++)
        {

            data.setValueAt(PostConditionSet.toArray()[PostconditionItr].toString(), position, 0);
            Collection<String> collection = (Collection<String>)mvm.getCollection(PostConditionSet.toArray()[PostconditionItr]);
            for (int i = 0; i < collection.size(); i++)
            {
                data.setValueAt(collection.toArray()[i].toString(), position + i, 1);
            }
            position += collection.size();
        }
        jTable1.setModel(data);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setEditingColumn(0);
        jTable1.setEditingRow(0);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}