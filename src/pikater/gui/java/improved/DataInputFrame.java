/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DataInputDialog.java
 *
 * Created on May 21, 2011, 10:18:44 PM
 */

package pikater.gui.java.improved;

import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.util.leap.ArrayList;
import jade.util.leap.List;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import pikater.gui.java.improved.DataInstancesTableModel;
import pikater.gui.java.improved.GuiConstants;
import pikater.gui.java.improved.ResultsBrowserFrame;
import pikater.ontology.messages.Agent;
import pikater.ontology.messages.Data;
import pikater.ontology.messages.DataInstances;
import pikater.ontology.messages.Execute;
import pikater.ontology.messages.Task;

/**
 *
 * @author martin
 */
public class DataInputFrame extends javax.swing.JFrame {

    DataInstancesTableModel sampleModel;
    DataInstancesTableModel model;
    DataInstances sampleInstances;
    GuiAgent myAgent;
    ResultsBrowserFrame rbf;
    byte[] object;

    /** Creates new form DataInputDialog */
    public DataInputFrame(ResultsBrowserFrame rbf, DataInstances sampleInstances, GuiAgent myAgent, byte[] object) {
        super("");
        this.rbf = rbf;
        initComponents();

        this.myAgent = myAgent;
        this.sampleInstances = sampleInstances;
        this.object = object;

        DataInstances myData = new DataInstances();
        myData.setAttributes(sampleInstances.getAttributes());
        myData.setClass_index(sampleInstances.getClass_index());
        myData.setName(sampleInstances.getName());
        myData.setInstances(new ArrayList());

        model = new DataInstancesTableModel(myData);
        sampleModel = new DataInstancesTableModel(sampleInstances);

        jTable1.setModel(model);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        okButton = new javax.swing.JButton();
        loadCSVButton = new javax.swing.JButton();
        saveCSVButton = new javax.swing.JButton();
        addRowButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pikater/gui/java/improved/Strings"); // NOI18N
        setTitle(bundle.getString("LABEL_DATA_FRAME")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        okButton.setText(bundle.getString("LABEL_DATA_BUTTON")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        loadCSVButton.setText(bundle.getString("LOAD_DATA")); // NOI18N
        loadCSVButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadCSVButtonActionPerformed(evt);
            }
        });

        saveCSVButton.setText(bundle.getString("SAVE_CSV")); // NOI18N
        saveCSVButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveCSVButtonActionPerformed(evt);
            }
        });

        addRowButton.setText(bundle.getString("ADD_ROW")); // NOI18N
        addRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowButtonActionPerformed(evt);
            }
        });

        clearButton.setText(bundle.getString("CLEAR")); // NOI18N
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveCSVButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(loadCSVButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addRowButton, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addRowButton, clearButton, loadCSVButton, okButton, saveCSVButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(saveCSVButton)
                    .addComponent(loadCSVButton)
                    .addComponent(addRowButton)
                    .addComponent(clearButton))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowButtonActionPerformed
        model.addNewInstance();
    }//GEN-LAST:event_addRowButtonActionPerformed

    public String getArffData() {
        String fileName = "tempLabelFile";
        String arffHeader = "";
        arffHeader += "@RELATION " + fileName + "\n";

        for (int i = 0; i < model.getColumnCount() - 1; i++) {
            arffHeader += "@ATTRIBUTE " + model.getColumnName(i) + " NUMERIC\n";
        }

        String[] classes = sampleModel.getClasses();

        String classList = "{";
        for (int i = 0; i < classes.length - 1; i++) {
            classList += classes[i] + ",";
        }

        classList += classes[classes.length - 1] + "}";

        arffHeader += "@ATTRIBUTE " + model.getColumnName(model.getColumnCount() - 1) + " " + classList;

        String CSVString = model.getCSVString();

        if (CSVString.isEmpty())
            return null;

        return arffHeader + "\n@DATA\n" + CSVString;
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed

        String arffData = getArffData();

        if (arffData == null) {
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("pikater/gui/java/improved/Strings").getString("ERROR_EMPTY_DATA"), ResourceBundle.getBundle("pikater/gui/java/improved/Strings").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        saveCSVButton.setEnabled(false);
        loadCSVButton.setEnabled(false);
        addRowButton.setEnabled(false);
        clearButton.setEnabled(false);
        okButton.setEnabled(false);
        okButton.setText(java.util.ResourceBundle.getBundle("pikater/gui/java/improved/Strings").getString("PLEASE_WAIT"));

        String fileName = "tempLabelFile_" + System.currentTimeMillis();

        GuiEvent ge = new GuiEvent(this, GuiConstants.LABEL_NEW_DATA);
        ge.addParameter(arffData);
        ge.addParameter(fileName);

        //String internalFilename = DataManagerService.translateFilename(myAgent, 0, fileName, null);

        Agent a = new Agent();
        a.setName("agent" + System.currentTimeMillis());

        a.setObject(object);
        a.setGui_id("trained agent");

        Data d = new Data();
        d.setMode("test_only");
        d.setTest_file_name("data" + System.getProperty("file.separator") + "files" + System.getProperty("file.separator") + "temp" + System.getProperty("file.separator") + fileName);
        d.setTrain_file_name("data" + System.getProperty("file.separator")  + "files" + System.getProperty("file.separator") + "temp" + System.getProperty("file.separator") + fileName);
        d.setExternal_test_file_name(fileName);
        d.setExternal_train_file_name(fileName);
        d.setOutput("predictions");

        Task t = new Task();
        t.setAgent(a);
        t.setData(d);
        t.setGet_results("after_each_computation");
        t.setGui_agent(myAgent.getName());
        t.setId("pokusny task pro pokusneho oziveneho agenta");
        t.setComputation_id("neni soucasti zadne computation");
        t.setProblem_id("neni soucasti zadneho problemu");

        Execute ex = new Execute();
        ex.setTask(t);

        ge.addParameter(ex);
        myAgent.postGuiEvent(ge);

    }//GEN-LAST:event_okButtonActionPerformed

    private void loadCSVButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadCSVButtonActionPerformed
        JFileChooser openFile = new JFileChooser();

        FileNameExtensionFilter fnf = new FileNameExtensionFilter("CSV, ARFF", "csv", "arff");
        openFile.setFileFilter(fnf);

        int result = openFile.showOpenDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File f = openFile.getSelectedFile();

        String[] nameParts = f.getName().split("\\.");
        String extension = nameParts[nameParts.length - 1];

        String arffData = "";

        if (extension.equals("arff")) {

            try {
                Scanner s = new Scanner(f);
                while (s.hasNextLine()) {
                    arffData += s.nextLine() + "\n";
                }
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            }

        }

        if (extension.equals("csv")) {

            try {
                Scanner s = new Scanner(f);

                String line = s.nextLine();
                while (line.isEmpty()) {
                    line = s.nextLine();
                }


                String[] columns = line.split(",");

                String arffHeader = "";
                arffHeader += "@RELATION " + f.getName() + "\n";

                for (int i = 0; i < columns.length - 1; i++) {
                    arffHeader += "@ATTRIBUTE " + columns[i].trim() + " NUMERIC\n";
                }

                String arffContent = "@DATA\n";
                java.util.ArrayList<String> classes = new java.util.ArrayList<String>();
                while (s.hasNextLine()) {

                    line = s.nextLine();
                    if (line.isEmpty()) {
                        continue;
                    }

                    arffContent += line + "\n";

                    String className = line.split(",")[columns.length - 1];

                    if (!classes.contains(className)) {
                        classes.add(className);
                    }
                }

                String classList = "{";
                for (int i = 0; i < classes.size() - 1; i++) {
                    classList += classes.get(i) + ",";
                }

                classList += classes.get(classes.size() - 1) + "}";

                arffHeader += "@ATTRIBUTE " + columns[columns.length - 1].trim() + " " + classList;

                arffData = arffHeader + "\n" + arffContent;
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            }

        }

        GuiEvent ge = new GuiEvent(this, GuiConstants.IMPORT_TEMP_FILE);
        ge.addParameter(arffData);
        ge.addParameter("tempLabelFile_" + System.currentTimeMillis());
        myAgent.postGuiEvent(ge);

    }//GEN-LAST:event_loadCSVButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        rbf.dataInputDialogClosed();
    }//GEN-LAST:event_formWindowClosing

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed

        DataInstances myData = new DataInstances();
        myData.setAttributes(sampleInstances.getAttributes());
        myData.setClass_index(sampleInstances.getClass_index());
        myData.setName(sampleInstances.getName());
        myData.setInstances(new ArrayList());

        model = new DataInstancesTableModel(myData);
        jTable1.setModel(model);
    }//GEN-LAST:event_clearButtonActionPerformed

    private void saveCSVButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveCSVButtonActionPerformed
        JFileChooser fChooser = new JFileChooser();
        fChooser.showSaveDialog(this);

        File output = fChooser.getSelectedFile();

        if (output == null) {
            return;
        }

        try {
            FileWriter out = new FileWriter(output);

            for (int i = 0; i < model.getColumnCount(); i++) {
                out.write("\"" + model.getColumnName(i) + "\"");
                if (i != model.getColumnCount() - 1) {
                    out.write(",");
                }
            }

            out.write("\n");

            out.write(model.getCSVString());

            out.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }//GEN-LAST:event_saveCSVButtonActionPerformed

    public void setDataInstances(DataInstances di) {

        okButton.setEnabled(true);
        saveCSVButton.setEnabled(true);
        loadCSVButton.setEnabled(true);
        addRowButton.setEnabled(true);
        clearButton.setEnabled(true);
        okButton.setText(java.util.ResourceBundle.getBundle("pikater/gui/java/improved/Strings").getString("LABEL_DATA_BUTTON"));
        
        if (di.getAttributes() != null)
            if (di.getAttributes().size() != sampleInstances.getAttributes().size()) {
                JOptionPane.showMessageDialog(this, "Incompatible data sets", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        DataInstances myData = new DataInstances();
        myData.setAttributes(sampleInstances.getAttributes());
        myData.setClass_index(sampleInstances.getClass_index());
        myData.setName(sampleInstances.getName());
        myData.setInstances(di.getInstances());

        model = new DataInstancesTableModel(myData);
        jTable1.setModel(model);

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRowButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton loadCSVButton;
    private javax.swing.JButton okButton;
    private javax.swing.JButton saveCSVButton;
    // End of variables declaration//GEN-END:variables

}
