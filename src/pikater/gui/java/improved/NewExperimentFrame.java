/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewExperimentFrame.java
 *
 * Created on Mar 14, 2011, 12:14:04 AM
 */
package pikater.gui.java.improved;

import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.util.leap.ArrayList;
import jade.util.leap.LinkedList;
import java.awt.Component;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.util.ResourceBundle;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import pikater.gui.java.Agent_GUI_Java;
import pikater.ontology.messages.Agent;
import pikater.ontology.messages.GetFileInfo;
import pikater.ontology.messages.Interval;
import pikater.ontology.messages.Metadata;
import pikater.ontology.messages.Option;

/**
 *
 * @author martin
 */
public class NewExperimentFrame extends javax.swing.JDialog {


    public static final String[] searches = {"ChooseXValues", "RandomSearch", "SimulatedAnnealing", "GASearch"};
    java.util.ArrayList<String> files = new java.util.ArrayList<String>();
    java.util.ArrayList<AgentOptionsDialog> agentDialogs = new java.util.ArrayList<AgentOptionsDialog>();
    AgentsListModel agentList = new AgentsListModel();
    FileGroupsModel fileGroups = new FileGroupsModel();
    String[] agentTypes;
    GuiAgent myAgent;
    AgentOptionsDialog aod;
    Agent searchAgent = new Agent();


    class AgentsListModel extends DefaultListModel {

        java.util.ArrayList<Agent> agents = new java.util.ArrayList<Agent>();

        @Override
        public int getSize() {
            return agents.size();
        }

        @Override
        public int size() {
            return getSize();
        }

        @Override
        public Object getElementAt(int index) {
            return super.get(index);
        }

        public void add(Agent a) {
            agents.add(a);
            super.addElement(a.getType() + " " + a.toGuiString());

            for (int i = 0; i < agents.size(); i++) {
                System.err.println(getElementAt(i).toString());
            }
        }

        public void insert(Agent a, int index) {
            agents.add(index, a);
            super.add(index, a.getType() + " " + a.toGuiString());

            for (int i = 0; i < agents.size(); i++) {
                System.err.println(getElementAt(i).toString());
            }
        }

        public java.util.ArrayList<Agent> getAgents() {
            return agents;
        }

        @Override
        public Object remove(int index) {
            agents.remove(index);
            return super.remove(index);
        }
    }

    class FileGroupsModel extends DefaultListModel {

        java.util.ArrayList<FileGroup> fileGroups = new java.util.ArrayList<FileGroup>();

        @Override
        public int getSize() {
            return fileGroups.size();
        }

        @Override
        public Object getElementAt(int index) {
            return super.get(index);
        }

        public void add(FileGroup fg) {
            fileGroups.add(fg);
            super.addElement(fg);
        }

        public java.util.ArrayList<FileGroup> getFileGroups() {
            return fileGroups;
        }

        @Override
        public Object remove(int index) {
            fileGroups.remove(index);
            return super.remove(index);
        }
    }

    public int getNumberOfTasks() {
        int sum = 0;
        for (Agent a: agentList.agents) {
            int product = 1;
            for (int i = 0; i < a.getOptions().size(); i++) {
                Option o = (Option)a.getOptions().get(i);
                product *= o.getNumberOfOptions();
            }
            sum += product;
        }
        return sum;
    }

    public boolean openFileDialog() {
        addFilesButtonActionPerformed(null);
        return fileGroups.getSize() > 0;
    }

    public void setFiles(ArrayList files) {

        this.files.clear();
        for (int i = 0; i < files.size(); i++) {
            this.files.add(((Metadata) files.get(i)).getExternal_name());
            System.err.println(files.get(i));
        }

    }

    /** Creates new form NewExperimentFrame */
    public NewExperimentFrame(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public NewExperimentFrame(Frame parent, boolean modal, GuiAgent myAgent) {
        super(parent, modal);

        initComponents();

        aod = new AgentOptionsDialog((Frame)this.getParent(), true, searches, myAgent, true, true);
        this.myAgent = myAgent;


        searchAgent.setType(aod.getAgentType());
        searchAgent.setOptions(aod.getAgentOptions());
        optManagerLabel.setText(searchAgent.getType() + " " + searchAgent.toGuiString());

        GuiEvent ge = new GuiEvent(this, GuiConstants.GET_AGENT_TYPES);
        myAgent.postGuiEvent(ge);

        GetFileInfo gfi = new GetFileInfo();
        gfi.setUserID(1);

        if (new File("studentMode").exists()) {
            this.remove(jPanel1);
            this.pack();
        }

        ge = new GuiEvent((this), GuiConstants.GET_FILES_INFO);
        ge.addParameter(gfi);
        myAgent.postGuiEvent(ge);

    }

    public void setAgentTypes(String[] agentTypes) {
        this.agentTypes = agentTypes;

        for (String s : agentTypes) {
            System.err.println("agent: " + s);
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileDialog = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        optManagerLabel = new javax.swing.JLabel();
        editOptionManagerButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        addAgentButton = new javax.swing.JButton();
        removeAgentButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        agentJList = new javax.swing.JList();
        editAgentButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        filesJList = new javax.swing.JList();
        addFilesButton = new javax.swing.JButton();
        removeFilesButton = new javax.swing.JButton();
        startExperimentButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pikater/gui/java/improved/Bundle"); // NOI18N
        fileDialog.setDialogTitle(bundle.getString("LOAD XML")); // NOI18N
        fileDialog.setFileFilter(null);

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("pikater/gui/java/improved/Strings"); // NOI18N
        setTitle(bundle1.getString("NEW_EXPERIMENT_FRAME_TITLE")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle1.getString("OPTION_MANAGER"))); // NOI18N

        optManagerLabel.setText("Random -E 0.1 -M 5");

        editOptionManagerButton.setText(bundle.getString("EDIT")); // NOI18N
        editOptionManagerButton.setMaximumSize(new java.awt.Dimension(59, 25));
        editOptionManagerButton.setMinimumSize(new java.awt.Dimension(59, 25));
        editOptionManagerButton.setPreferredSize(new java.awt.Dimension(59, 25));
        editOptionManagerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editOptionManagerButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(optManagerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(editOptionManagerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(optManagerLabel)
                    .addComponent(editOptionManagerButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle1.getString("AGENTS"))); // NOI18N

        addAgentButton.setText(bundle.getString("ADD")); // NOI18N
        addAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAgentButtonActionPerformed(evt);
            }
        });

        removeAgentButton.setText(bundle.getString("REMOVE")); // NOI18N
        removeAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAgentButtonActionPerformed(evt);
            }
        });

        agentJList.setModel(agentList);
        agentJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(agentJList);

        editAgentButton.setText(bundle.getString("EDIT")); // NOI18N
        editAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editAgentButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addAgentButton, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(editAgentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(removeAgentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addAgentButton, editAgentButton, removeAgentButton});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(addAgentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeAgentButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editAgentButton)))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle1.getString("FILE_GROUPS"))); // NOI18N

        filesJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(filesJList);

        addFilesButton.setText(bundle.getString("ADD")); // NOI18N
        addFilesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFilesButtonActionPerformed(evt);
            }
        });

        removeFilesButton.setText(bundle.getString("REMOVE")); // NOI18N
        removeFilesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFilesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addFilesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeFilesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(addFilesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeFilesButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        startExperimentButton.setText(bundle.getString("START EXPERIMENT")); // NOI18N
        startExperimentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startExperimentButtonActionPerformed(evt);
            }
        });

        jMenu1.setText(bundle.getString("FILE")); // NOI18N

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText(bundle.getString("LOAD XML")); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, 0, 381, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(startExperimentButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startExperimentButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void removeFilesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFilesButtonActionPerformed
        for (int i : filesJList.getSelectedIndices()) {
            fileGroups.remove(i);
        }
    }//GEN-LAST:event_removeFilesButtonActionPerformed

    private void addAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAgentButtonActionPerformed
        AgentOptionsDialog aod = new AgentOptionsDialog((Frame) this.getParent(), true, agentTypes, myAgent);
        aod.setVisible(true);

        System.err.println("AgentOptionsDialog closed");
        agentDialogs.add(aod);
        Agent a = new Agent();
        a.setType(aod.getAgentType());
        a.setOptions(aod.getAgentOptions());
        agentList.add(a);

        agentJList.setModel(agentList);
        agentJList.revalidate();
        agentJList.repaint();

    }//GEN-LAST:event_addAgentButtonActionPerformed

    private void addFilesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFilesButtonActionPerformed
        FileGroupsPanel fgp = new FileGroupsPanel((Frame) this.getParent(), true, files, myAgent);
        fgp.setVisible(true);

        FileGroup fg = fgp.getFileGroup();

        if (fg == null)
            return;

        fileGroups.add(fg);

        for (int i = 0; i < fileGroups.getSize(); i++) {
            System.err.println(fileGroups.getElementAt(i).toString());
        }

        filesJList.setModel(fileGroups);
        filesJList.revalidate();
        filesJList.repaint();
    }//GEN-LAST:event_addFilesButtonActionPerformed

    private void startExperimentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startExperimentButtonActionPerformed

        if (agentList.isEmpty() || fileGroups.isEmpty()) {
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("pikater/gui/java/improved/Strings").getString("ERROR_AGENTS_FILES"), ResourceBundle.getBundle("pikater/gui/java/improved/Strings").getString("ERROR"), JOptionPane.ERROR_MESSAGE);
            return;
        }

/*        LinkedList optManOptions = new LinkedList();
        optManOptions.add(omd.getType());
        if (omd.getType().equals("Random")) {
            optManOptions.add(omd.getError());
            optManOptions.add(omd.getMaxTries());
        }
        if (omd.getType().equals("ChooseXValues")) {
            optManOptions.add(omd.getNumTries());
        }*/

        GuiEvent ge = new GuiEvent(this, GuiConstants.START_EXPERIMENT);
        ge.addParameter(aod.getAgentType());
        ge.addParameter(aod.getAgentOptions());
        ge.addParameter(agentList.getAgents());
        ge.addParameter(fileGroups.getFileGroups());
        ge.addParameter(getNumberOfTasks());

        myAgent.postGuiEvent(ge);

        this.setVisible(false);

    }//GEN-LAST:event_startExperimentButtonActionPerformed

    private void removeAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAgentButtonActionPerformed
        for (int i : agentJList.getSelectedIndices()) {
            agentList.remove(i);
            agentDialogs.remove(i);
        }
    }//GEN-LAST:event_removeAgentButtonActionPerformed

    public void loadXML(File xml) {

        try {

            SAXBuilder builder = new SAXBuilder();
            FileInputStream fis = new FileInputStream(xml);
            Document doc = builder.build(fis);
            Element root_element = doc.getRootElement();

            java.util.List _problems = root_element.getChildren("experiment"); // return
            // all
            // children
            // by
            // name
            java.util.Iterator p_itr = _problems.iterator();
            while (p_itr.hasNext()) {
                Element next_problem = (Element) p_itr.next();

                //int p_id = createNewProblem(next_problem.getAttributeValue("timeout"));

                java.util.List method = next_problem.getChildren("method");
                java.util.Iterator m_itr = method.iterator();
                if (method.size() == 0) {
                    // TODO select default
                }
                if (method.size() > 1) {
                    // TODO error
                }
                while (m_itr.hasNext()) {
                    Element next_method = (Element) m_itr.next();
                    aod.setAgentType(next_method.getAttributeValue("name"));
/*                    if (omd.getType().equals("Random")) {
                        String errRate = next_method.getAttributeValue("error_rate");
                        if (errRate != null)
                            omd.setError(Double.parseDouble(errRate));
                        String maxTries = next_method.getAttributeValue("maximum_tries");
                        if (maxTries != null)
                            omd.setMaxTries(Integer.parseInt(maxTries));
                    }

                    if (omd.getType().equals("ChooseXValues")) {
                        String numTries = next_method.getAttributeValue("number_of_values_to_try");
                        if (numTries != null)
                            omd.setNumTries(Integer.parseInt(numTries));
                    }*/
                }

                optManagerLabel.setText(searchAgent.getType() + " " + searchAgent.toGuiString());

                java.util.List dataset = next_problem.getChildren("dataset");
                java.util.Iterator ds_itr = dataset.iterator();
                while (ds_itr.hasNext()) {
                    Element next_dataset = (Element) ds_itr.next();
                    FileGroup fg = new FileGroup();
                    fg.setTestFile(next_dataset.getAttributeValue("test"));
                    fg.setTrainFile(next_dataset.getAttributeValue("train"));
                    //int d_id = addDatasetToProblem(p_id,
                    //        next_dataset.getAttributeValue("train"),
                    //        next_dataset.getAttributeValue("test"),
                    //        next_dataset.getAttributeValue("label"),
                    //        next_dataset.getAttributeValue("output"),
                    //        next_dataset.getAttributeValue("mode"));

                    fileGroups.add(fg);
                    filesJList.setModel(fileGroups);
                    java.util.List metadata = next_dataset.getChildren("metadata");
                   /* if (metadata.size() > 0) {
                        java.util.Iterator md_itr = metadata.iterator();
                        Element next_metadata = (Element) md_itr.next();

                        addMetadataToDataset(d_id, next_dataset.getAttributeValue("train"), next_metadata.getAttributeValue("missing_values"), next_metadata.getAttributeValue("number_of_attributes"),
                                next_metadata.getAttributeValue("number_of_instances"),
                                next_metadata.getAttributeValue("attribute_type"),
                                next_metadata.getAttributeValue("default_task"));
                    }*/
                }

                java.util.List _agents = next_problem.getChildren("agent");
                java.util.Iterator a_itr = _agents.iterator();
                while (a_itr.hasNext()) {
                    Element next_agent = (Element) a_itr.next();

                    String agent_name = next_agent.getAttributeValue("name");
                    String agent_type = next_agent.getAttributeValue("type");

                    AgentOptionsDialog aod = new AgentOptionsDialog((Frame)this.getParent(), true, agentTypes, myAgent, false);

                    if (!agent_type.equals("?")) {
                        aod.setAgentOptions(((Agent_GUI_Java)myAgent).getAgentOptionsSynchronous(agent_type));
                        System.err.println("OPTIONS COUNT:" + aod.getAgentOptions().size());
                    }
                    aod.setAgentType(agent_type);
                    aod.setAgentTypeChangedEventEnabled(true);

                    System.err.println("Here");

                    java.util.List _options = next_agent.getChildren("parameter");
                    java.util.Iterator o_itr = _options.iterator();
                    jade.util.leap.ArrayList options = new ArrayList();
                    while (o_itr.hasNext()) {
                        Element next_option = (Element) o_itr.next();
                        Option o = new Option();
                        o.setName(next_option.getAttributeValue("name"));
                        o.setValue(next_option.getAttributeValue("value"));
                        if (o.getValue().contains("?")) {
                            o.setMutable(true);
                        }
                        String set = next_option.getAttributeValue("set");
                        if (set != null) {
                            LinkedList l = new LinkedList();
                            for (String s : set.split(",[ ]*"))
                                l.add(s);

                            o.setSet(l);
                            o.setIs_a_set(true);
                        }
                        Interval range = new Interval();
                        if (next_option.getAttributeValue("lower") != null)
                            range.setMin(Float.parseFloat(next_option.getAttributeValue("lower")));
                        if (next_option.getAttributeValue("upper") != null)
                            range.setMax(Float.parseFloat(next_option.getAttributeValue("upper")));
                        o.setRange(range);
                        if (next_option.getAttributeValue("number_of_values_to_try") != null)
                            o.setNumber_of_values_to_try(Integer.parseInt(next_option.getAttributeValue("number_of_values_to_try")));
                        aod.setOption(o);
                    }
                    agentDialogs.add(aod);
                    Agent a = new Agent();
                    a.setType(aod.getAgentType());
                    a.setOptions(aod.getAgentOptions());
                    agentList.add(a);
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        FileNameExtensionFilter fnf = new FileNameExtensionFilter("XML Files (*.bxml)", "bxml");
        fileDialog.setFileFilter(fnf);
        
        int result = fileDialog.showOpenDialog(this);

        if (result == JFileChooser.CANCEL_OPTION) {
            return;
        }

        loadXML(fileDialog.getSelectedFile());


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void editAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editAgentButtonActionPerformed
        for (int i : agentJList.getSelectedIndices()) {
            AgentOptionsDialog aod = agentDialogs.get(i);

            aod.setVisible(true);
            Agent a = agentList.getAgents().get(i);
            a.setOptions(aod.getAgentOptions());
            a.setType(aod.getAgentType());

            agentList.remove(i);
            agentList.insert(a, i);

            agentJList.setModel(agentList);
        }
    }//GEN-LAST:event_editAgentButtonActionPerformed

    private void editOptionManagerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editOptionManagerButtonActionPerformed

        aod.setVisible(true);


        searchAgent.setType(aod.getAgentType());
        searchAgent.setOptions(aod.getAgentOptions());
        optManagerLabel.setText(searchAgent.getType() + " " + searchAgent.toGuiString());
        
    }//GEN-LAST:event_editOptionManagerButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAgentButton;
    private javax.swing.JButton addFilesButton;
    private javax.swing.JList agentJList;
    private javax.swing.JButton editAgentButton;
    private javax.swing.JButton editOptionManagerButton;
    private javax.swing.JFileChooser fileDialog;
    private javax.swing.JList filesJList;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel optManagerLabel;
    private javax.swing.JButton removeAgentButton;
    private javax.swing.JButton removeFilesButton;
    private javax.swing.JButton startExperimentButton;
    // End of variables declaration//GEN-END:variables
}
