/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IntegerOptionPanel.java
 *
 * Created on Mar 14, 2011, 4:23:35 PM
 */
package pikater.gui.java.improved;

import java.awt.Component;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import pikater.gui.java.improved.verifiers.FloatSetInputVerifier;
import pikater.gui.java.improved.verifiers.IntegerSetInputVerifier;
import pikater.ontology.messages.Option;

/**
 *
 * @author martin
 */
public class IntegerOptionPanel extends javax.swing.JPanel {

    String dataType;

    public boolean isDefault() {
        return defaultValueRadio.isSelected();
    }

    public boolean isSetByOptionManager() {
        return valueSetByOptMan.isSelected();
    }

    public boolean isUserSpecified() {
        return manualValue.isSelected();
    }

    public boolean isInterval() {
        return intervalRadio.isSelected();
    }

    public boolean isSet() {
        return setRadio.isSelected();
    }

    public String getSet() {
        try {
            if (this.dataType.equals("INT"))
                return setTextField.getText();
            String[] numbers = setTextField.getText().split(" ");
            String outNumbers = "";
            NumberFormat nf = NumberFormat.getInstance();
            for (int i = 0; i < numbers.length; i++) {
                ParsePosition pos = new ParsePosition(0);
                if (i < numbers.length - 1) {
                    outNumbers += nf.parse(numbers[i], pos).floatValue() + " ";
                } else {
                    outNumbers += nf.parse(numbers[i], pos).floatValue();
                }
                if (pos.getIndex() != numbers[i].length())
                    throw new ParseException("Incorrect string", pos.getIndex());
            }
            return outNumbers;
        } catch (ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Incorrect number format", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public Float getLowerLimit() {
        return Float.parseFloat(fromSpinner.getValue().toString());
    }

    public Float getUpperLimit() {
        return Float.parseFloat(toSpinner.getValue().toString());
    }

    public int getNumberOfTries() {
        return (Integer) triesSpinner.getValue();
    }

    public String getUserSpecifiedValue() {
        return manualSpinner.getValue().toString();
    }

    public void setOption(Option o) {

        System.err.println(o.getName());

        if (this.dataType.equals("INT")) {

            if (o.getValue().contains("?")) {
                valueSetByOptMan.setSelected(true);
                if (o.getRange().getMin() != null && o.getRange().getMax() != null) {
                    intervalRadio.setSelected(true);
                    fromSpinner.setValue(o.getRange().getMin().intValue());
                    toSpinner.setValue(o.getRange().getMax().intValue());
                }
                if (o.getIs_a_set()) {
                    setRadio.setSelected(true);

                    String set = "";
                    for (int i = 0; i < o.getSet().size(); i++) {
                        set += o.getSet().get(i).toString();
                        if (i != o.getSet().size() - 1)
                            set += " ";
                    }
                    setTextField.setText(set);
                }
                triesSpinner.setValue(o.getNumber_of_values_to_try());
            } else {
                manualValue.setSelected(true);
                manualSpinner.setValue(Integer.parseInt(o.getValue()));
            }
        }
        
        if (this.dataType.equals("FLOAT")) {

            if (o.getValue().contains("?")) {
                valueSetByOptMan.setSelected(true);
                if (o.getRange().getMin() != null && o.getRange().getMax() != null) {
                    fromSpinner.setValue(o.getRange().getMin());
                    toSpinner.setValue(o.getRange().getMax());
                }
                if (o.getIs_a_set()) {
                    setRadio.setSelected(true);

                    String set = "";
                    for (int i = 0; i < o.getSet().size(); i++) {
                        set += o.getSet().get(i).toString();
                        if (i != o.getSet().size() - 1)
                            set += " ";
                    }
                    setTextField.setText(set);
                }
                triesSpinner.setValue(o.getNumber_of_values_to_try());
            } else {
                manualValue.setSelected(true);
                manualSpinner.setValue(Float.parseFloat(o.getValue()));
            }

        }

    }

    /** Creates new form IntegerOptionPanel */
    public IntegerOptionPanel() {
        initComponents();
    }

    public IntegerOptionPanel(int lower, int upper, int tries, int defaultValue, boolean hideAuto) {
        dataType = "INT";

        initComponents();

        fromSpinner.setModel(new SpinnerNumberModel(lower, lower, upper, 1));
        toSpinner.setModel(new SpinnerNumberModel(upper, lower, upper, 1));
        manualSpinner.setModel(new SpinnerNumberModel(defaultValue, lower, upper, 1));
        triesSpinner.setModel(new SpinnerNumberModel(tries, 0, 100, 1));
        jPanel1.setVisible(false);
        jPanel2.setVisible(false);
        setTextField.setInputVerifier(new IntegerSetInputVerifier(this));

        if (hideAuto) {
            valueSetByOptMan.setVisible(false);
        }

    }

    public IntegerOptionPanel(int lower, int upper, int tries, int defaultValue) {

        this(lower, upper, tries, defaultValue, false);
    }


    public IntegerOptionPanel(double lower, double upper, int tries, double defaultValue, boolean hideAuto) {

        dataType = "FLOAT";

        initComponents();

        fromSpinner.setModel(new SpinnerNumberModel(lower, lower, upper, 0.01));
        toSpinner.setModel(new SpinnerNumberModel(upper, lower, upper, 0.01));
        manualSpinner.setModel(new SpinnerNumberModel(defaultValue, lower, upper, 0.01));
        triesSpinner.setModel(new SpinnerNumberModel(tries, 0, 100, 1));
        jPanel1.setVisible(false);
        jPanel2.setVisible(false);
        setTextField.setInputVerifier(new FloatSetInputVerifier((this)));

        if (hideAuto) {
            valueSetByOptMan.setVisible(false);
        }

    }

    public IntegerOptionPanel(double lower, double upper, int tries, double defaultValue) {

        this(lower, upper, tries, defaultValue, false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        toSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        fromSpinner = new javax.swing.JSpinner();
        triesSpinner = new javax.swing.JSpinner();
        intervalRadio = new javax.swing.JRadioButton();
        setRadio = new javax.swing.JRadioButton();
        setTextField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        manualSpinner = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        defaultValueRadio = new javax.swing.JRadioButton();
        valueSetByOptMan = new javax.swing.JRadioButton();
        manualValue = new javax.swing.JRadioButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pikater/gui/java/improved/Strings"); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("AUTO_SETTINGS"))); // NOI18N
        jPanel1.setEnabled(false);

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("pikater/gui/java/improved/Bundle"); // NOI18N
        jLabel3.setText(bundle1.getString("TO")); // NOI18N
        jLabel3.setEnabled(false);

        jLabel4.setText(bundle1.getString("MAXIMUM TRIES")); // NOI18N
        jLabel4.setEnabled(false);

        toSpinner.setEnabled(false);

        jLabel2.setText(bundle1.getString("TRY VALUES FROM")); // NOI18N
        jLabel2.setEnabled(false);

        fromSpinner.setEnabled(false);

        triesSpinner.setEnabled(false);

        buttonGroup2.add(intervalRadio);
        intervalRadio.setSelected(true);
        intervalRadio.setText(bundle1.getString("INTERVAL")); // NOI18N
        intervalRadio.setEnabled(false);

        buttonGroup2.add(setRadio);
        setRadio.setText(bundle1.getString("SET")); // NOI18N
        setRadio.setEnabled(false);

        setTextField.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(triesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(intervalRadio)
                            .addComponent(setRadio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(fromSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(toSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(setTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
                        .addGap(36, 36, 36)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {fromSpinner, toSpinner});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intervalRadio)
                    .addComponent(toSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fromSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setRadio)
                    .addComponent(setTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(triesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("MANUAL_SETTINGS"))); // NOI18N
        jPanel2.setEnabled(false);

        manualSpinner.setEnabled(false);

        jLabel1.setLabelFor(manualSpinner);
        jLabel1.setText(bundle1.getString("VALUE")); // NOI18N
        jLabel1.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manualSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(117, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manualSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        buttonGroup1.add(defaultValueRadio);
        defaultValueRadio.setSelected(true);
        defaultValueRadio.setText(bundle1.getString("KEEP DEFAULT VALUE")); // NOI18N
        defaultValueRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultValueRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(valueSetByOptMan);
        valueSetByOptMan.setText(bundle1.getString("VALUE IS SET BY OPTION MANAGER")); // NOI18N
        valueSetByOptMan.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                valueSetByOptManStateChanged(evt);
            }
        });

        buttonGroup1.add(manualValue);
        manualValue.setText(bundle1.getString("SPECIFY MANUAL VALUE")); // NOI18N
        manualValue.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                manualValueStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(defaultValueRadio)
            .addComponent(valueSetByOptMan)
            .addComponent(manualValue)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(defaultValueRadio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(valueSetByOptMan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manualValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void defaultValueRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultValueRadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_defaultValueRadioActionPerformed

    private void valueSetByOptManStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_valueSetByOptManStateChanged
        jPanel1.setVisible(valueSetByOptMan.isSelected());
        for (Component c : jPanel1.getComponents()) {
            c.setEnabled(valueSetByOptMan.isSelected());
        }
    }//GEN-LAST:event_valueSetByOptManStateChanged

    private void manualValueStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_manualValueStateChanged
        jPanel2.setVisible(manualValue.isSelected());
        for (Component c : jPanel2.getComponents()) {
            c.setEnabled(manualValue.isSelected());
        }
    }//GEN-LAST:event_manualValueStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JRadioButton defaultValueRadio;
    private javax.swing.JSpinner fromSpinner;
    private javax.swing.JRadioButton intervalRadio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner manualSpinner;
    private javax.swing.JRadioButton manualValue;
    private javax.swing.JRadioButton setRadio;
    private javax.swing.JTextField setTextField;
    private javax.swing.JSpinner toSpinner;
    private javax.swing.JSpinner triesSpinner;
    private javax.swing.JRadioButton valueSetByOptMan;
    // End of variables declaration//GEN-END:variables
}
