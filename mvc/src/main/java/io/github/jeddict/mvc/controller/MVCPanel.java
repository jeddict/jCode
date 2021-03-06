/**
 * Copyright 2013-2019 the original author or authors from the Jeddict project (https://jeddict.github.io/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.jeddict.mvc.controller;

import io.github.jeddict.jcode.LayerConfigPanel;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.lang.model.SourceVersion;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import static javax.swing.JOptionPane.OK_OPTION;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.JTextComponent;
import io.github.jeddict.util.StringUtils;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.SourceGroup;
import static io.github.jeddict.jcode.util.JavaSourceHelper.isValidPackageName;
import io.github.jeddict.mvc.controller.event.ControllerEventType;
import io.github.jeddict.mvc.controller.returntype.ControllerReturnType;
import io.github.jeddict.rest.applicationconfig.RestConfigData;
import io.github.jeddict.rest.applicationconfig.RestConfigDialog;
import org.netbeans.spi.java.project.support.ui.PackageView;
import org.openide.util.NbBundle;

/**
 *
 * @author Gaurav Gupta
 */
public class MVCPanel extends LayerConfigPanel<MVCData> {

    private static final String DEFAULT_PACKAGE = "controller";
    private RestConfigDialog configDialog;
    private final Map<JCheckBox, ControllerEventType> eventTypeBoxs = new HashMap<>();

    public MVCPanel() {
        initComponents();

    }

    @Override
    public boolean hasError() {
        warningLabel.setText("");
        if (!isValidPackageName(getPackage())) {
            warningLabel.setText(NbBundle.getMessage(MVCPanel.class, "MVCPanel.invalidPackage.message"));
            return true;
        }
        String prefix = getPrefix();
        String suffix = getSuffix();

        if (StringUtils.isNotBlank(prefix) && !SourceVersion.isName(prefix)) {
            warningLabel.setText(NbBundle.getMessage(MVCPanel.class, "MVCPanel.invalidPrefix.message"));
            return true;
        }
        if (StringUtils.isNotBlank(suffix) && !SourceVersion.isName(prefix + '_' + suffix)) {
            warningLabel.setText(NbBundle.getMessage(MVCPanel.class, "MVCPanel.invalidSuffix.message"));
            return true;
        }
        return false;
    }

    @Override
    public void read() {
        MVCData data = this.getConfigData();
        if (StringUtils.isNotBlank(data.getPackage())) {
            setPackage(data.getPackage());
        }

        if (StringUtils.isNotBlank(data.getPrefixName())) {
            setPrefix(data.getPrefixName());
        }

        if (StringUtils.isNotBlank(data.getSuffixName())) {
            setSuffix(data.getSuffixName());
        }

        if (data.getReturnType() != null) {
            viewCombo.setSelectedItem(data.getReturnType());
            beanValidation.setSelected(data.isBeanValidation());
            csrfCheckbox.setSelected(data.isCSRF());
            xssCheckbox.setSelected(data.isXSS());
            authenticationCheckbox.setSelected(data.isAuthentication());
            hybridClassCheckbox.setSelected(data.isHybridClass());
        }

        setSelectedEventType(data.getEventType());
    }

    @Override
    public void store() {
        MVCData data = this.getConfigData();
        data.setPrefixName(getPrefix());
        data.setSuffixName(getSuffix());
        data.setPackage(getPackage());
        if (data.getRestConfigData() == null) {// && !useJersey// && !configuredREST){
            RestConfigData restConfigData = new RestConfigData();
//            restConfigData.setPackage(getPackage());
            data.setRestConfigData(restConfigData);
        }
        data.setBeanValidation(getBeanValidation());
        data.setCSRF(getCSRF());
        data.setXSS(getXSS());
        data.setAuthentication(getAuthentication());
        data.setHybridClass(getHybridClass());
        data.setReturnType(getReturnType());
        data.setEventType(getSelectedEventType());
    }

    private Project project;
    private SourceGroup sourceGroup;

    @Override
    public void init(String _package, Project project, SourceGroup sourceGroup) {
        this.project = project;
        this.sourceGroup = sourceGroup;

        if (sourceGroup != null) {
            packageCombo.setRenderer(PackageView.listRenderer());
            ComboBoxModel model = PackageView.createListView(sourceGroup);
            if (model.getSize() > 0) {
                model.setSelectedItem(model.getElementAt(0));
            }
            packageCombo.setModel(model);
            addChangeListener(packageCombo);
            if (StringUtils.isBlank(_package)) {
                _package = DEFAULT_PACKAGE;
            } else {
                _package = _package + '.' + DEFAULT_PACKAGE;
            }
            setPackage(_package);
        }
        addChangeListener(prefixField);
        addChangeListener(suffixField);

        eventObserversPanel.removeAll();

        for (ControllerEventType type : ControllerEventType.values()) {
            JCheckBox eventTypeBox = new JCheckBox();
            org.openide.awt.Mnemonics.setLocalizedText(eventTypeBox, type.toString()); // NOI18N
            eventObserversPanel.add(eventTypeBox);
            eventTypeBoxs.put(eventTypeBox, type);
        }
    }

    public String getPackage() {
        return ((JTextComponent) packageCombo.getEditor().getEditorComponent()).getText().trim();
    }

    public boolean getBeanValidation() {
        return beanValidation.isSelected();
    }

    public boolean getCSRF() {
        return csrfCheckbox.isSelected();
    }

    public boolean getXSS() {
        return xssCheckbox.isSelected();
    }

    public boolean getAuthentication() {
        return authenticationCheckbox.isSelected();
    }

    public boolean getHybridClass() {
        return hybridClassCheckbox.isSelected();
    }

    public ControllerReturnType getReturnType() {
        return (ControllerReturnType) viewCombo.getSelectedItem();
    }

    private void setPackage(String _package) {
        ComboBoxModel model = packageCombo.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            if (model.getElementAt(i).toString().equals(_package)) {
                model.setSelectedItem(model.getElementAt(i));
                break;
            }
        }
        ((JTextComponent) packageCombo.getEditor().getEditorComponent()).setText(_package);
    }

    public String getSuffix() {
        return suffixField.getText().trim();
    }

    public String getPrefix() {
        return prefixField.getText().trim();
    }

    private void setPrefix(String prefix) {
        prefixField.setText(prefix);
    }

    private void setSuffix(String suffix) {
        suffixField.setText(suffix);
    }

    public List<ControllerEventType> getSelectedEventType() {
        List<ControllerEventType> eventTypes = new ArrayList<>();
        for (Entry<JCheckBox, ControllerEventType> eventTypeBoxEntry : eventTypeBoxs.entrySet()) {
            if (eventTypeBoxEntry.getKey().isSelected()) {
                eventTypes.add(eventTypeBoxEntry.getValue());
            }
        }
        return eventTypes;
    }

    public void setSelectedEventType(List<ControllerEventType> controllerEventTypes) {
        for (Entry<JCheckBox, ControllerEventType> eventTypeBoxEntry : eventTypeBoxs.entrySet()) {
            eventTypeBoxEntry.getKey().setSelected(controllerEventTypes.contains(eventTypeBoxEntry.getValue()));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        packagePanel = new javax.swing.JPanel();
        packageLabel = new javax.swing.JLabel();
        packageCombo = new javax.swing.JComboBox();
        warningPanel = new javax.swing.JPanel();
        warningLabel = new javax.swing.JLabel();
        suffixPanel = new javax.swing.JPanel();
        namePane = new javax.swing.JLayeredPane();
        prefixField = new javax.swing.JTextField();
        entityLabel = new javax.swing.JLabel();
        suffixField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        viewPanel = new javax.swing.JPanel();
        viewLabel = new javax.swing.JLabel();
        viewCombo = new javax.swing.JComboBox();
        eventObserversPanel = new javax.swing.JPanel();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        securityPanel = new javax.swing.JPanel();
        securityLabel = new javax.swing.JLabel();
        securityCompPanel = new javax.swing.JLayeredPane();
        authenticationCheckbox = new javax.swing.JCheckBox();
        csrfCheckbox = new javax.swing.JCheckBox();
        xssCheckbox = new javax.swing.JCheckBox();
        miscPanel = new javax.swing.JPanel();
        applicationConfigButton = new javax.swing.JButton();
        wrapper = new javax.swing.JLayeredPane();
        beanValidation = new javax.swing.JCheckBox();
        hybridClassCheckbox = new javax.swing.JCheckBox();

        packagePanel.setLayout(new java.awt.BorderLayout(10, 0));

        packageLabel.setLabelFor(packageCombo);
        org.openide.awt.Mnemonics.setLocalizedText(packageLabel, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.packageLabel.text")); // NOI18N
        packageLabel.setPreferredSize(new java.awt.Dimension(100, 17));
        packagePanel.add(packageLabel, java.awt.BorderLayout.LINE_START);

        packageCombo.setEditable(true);
        packageCombo.setEditable(true);
        packageCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        packageCombo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                packageComboPropertyChange(evt);
            }
        });
        packagePanel.add(packageCombo, java.awt.BorderLayout.CENTER);

        warningPanel.setLayout(new java.awt.BorderLayout(10, 0));

        warningLabel.setForeground(new java.awt.Color(200, 0, 0));
        warningLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(warningLabel, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.warningLabel.text")); // NOI18N
        warningPanel.add(warningLabel, java.awt.BorderLayout.CENTER);

        suffixPanel.setLayout(new java.awt.BorderLayout(10, 0));

        namePane.setLayout(new javax.swing.BoxLayout(namePane, javax.swing.BoxLayout.LINE_AXIS));

        prefixField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        prefixField.setText(org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.prefixField.text")); // NOI18N
        prefixField.setToolTipText(org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.prefixField.toolTipText")); // NOI18N
        prefixField.setPreferredSize(new java.awt.Dimension(100, 27));
        prefixField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                prefixFieldPropertyChange(evt);
            }
        });
        namePane.add(prefixField);

        entityLabel.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.shadow"));
        entityLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(entityLabel, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.entityLabel.text")); // NOI18N
        entityLabel.setPreferredSize(new java.awt.Dimension(58, 27));
        entityLabel.setRequestFocusEnabled(false);
        namePane.add(entityLabel);

        suffixField.setText(org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.suffixField.text")); // NOI18N
        suffixField.setPreferredSize(new java.awt.Dimension(100, 27));
        suffixField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                suffixFieldPropertyChange(evt);
            }
        });
        namePane.add(suffixField);

        suffixPanel.add(namePane, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(nameLabel, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.nameLabel.text")); // NOI18N
        nameLabel.setPreferredSize(new java.awt.Dimension(100, 17));
        suffixPanel.add(nameLabel, java.awt.BorderLayout.WEST);

        viewPanel.setLayout(new java.awt.BorderLayout(10, 0));

        viewLabel.setLabelFor(viewCombo);
        org.openide.awt.Mnemonics.setLocalizedText(viewLabel, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.viewLabel.text")); // NOI18N
        viewLabel.setPreferredSize(new java.awt.Dimension(100, 17));
        viewPanel.add(viewLabel, java.awt.BorderLayout.LINE_START);

        viewCombo.setModel(new DefaultComboBoxModel(ControllerReturnType.values()));
        viewCombo.setRenderer(new BasicComboBoxRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (isSelected) {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                    if (index > -1) {
                        ControllerReturnType returnType = (ControllerReturnType)value;
                        list.setToolTipText(returnType.getDescription());
                    }
                }
                else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }
                setFont(list.getFont());
                setText((value == null) ? "" : value.toString());

                return this;
            }
        });
        viewCombo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                viewComboPropertyChange(evt);
            }
        });
        viewPanel.add(viewCombo, java.awt.BorderLayout.CENTER);

        eventObserversPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.eventObserversPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(100, 100, 100))); // NOI18N
        eventObserversPanel.setLayout(new java.awt.GridLayout(2, 3));

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox4, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.jCheckBox4.text")); // NOI18N
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });
        eventObserversPanel.add(jCheckBox4);

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.jCheckBox1.text")); // NOI18N
        eventObserversPanel.add(jCheckBox1);

        securityPanel.setLayout(new java.awt.BorderLayout(10, 0));

        org.openide.awt.Mnemonics.setLocalizedText(securityLabel, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.securityLabel.text")); // NOI18N
        securityLabel.setPreferredSize(new java.awt.Dimension(100, 17));
        securityPanel.add(securityLabel, java.awt.BorderLayout.LINE_START);

        securityCompPanel.setLayout(new java.awt.GridLayout(1, 0));

        org.openide.awt.Mnemonics.setLocalizedText(authenticationCheckbox, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.authenticationCheckbox.text")); // NOI18N
        securityCompPanel.add(authenticationCheckbox);

        org.openide.awt.Mnemonics.setLocalizedText(csrfCheckbox, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.csrfCheckbox.text")); // NOI18N
        securityCompPanel.add(csrfCheckbox);

        org.openide.awt.Mnemonics.setLocalizedText(xssCheckbox, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.xssCheckbox.text")); // NOI18N
        securityCompPanel.add(xssCheckbox);

        securityPanel.add(securityCompPanel, java.awt.BorderLayout.CENTER);

        miscPanel.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(applicationConfigButton, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.applicationConfigButton.text")); // NOI18N
        applicationConfigButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applicationConfigButtonActionPerformed(evt);
            }
        });
        miscPanel.add(applicationConfigButton, java.awt.BorderLayout.EAST);

        beanValidation.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(beanValidation, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.beanValidation.text")); // NOI18N
        beanValidation.setToolTipText(org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.beanValidation.toolTipText")); // NOI18N

        hybridClassCheckbox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(hybridClassCheckbox, org.openide.util.NbBundle.getMessage(MVCPanel.class, "MVCPanel.hybridClassCheckbox.text")); // NOI18N

        wrapper.setLayer(beanValidation, javax.swing.JLayeredPane.DEFAULT_LAYER);
        wrapper.setLayer(hybridClassCheckbox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout wrapperLayout = new javax.swing.GroupLayout(wrapper);
        wrapper.setLayout(wrapperLayout);
        wrapperLayout.setHorizontalGroup(
            wrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wrapperLayout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(hybridClassCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(beanValidation, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
        );
        wrapperLayout.setVerticalGroup(
            wrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, wrapperLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(wrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(beanValidation, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hybridClassCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        miscPanel.add(wrapper, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(eventObserversPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(packagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(suffixPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(securityPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(miscPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(warningPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(suffixPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(packagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(miscPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(viewPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(securityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(eventObserversPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(279, Short.MAX_VALUE)
                    .addComponent(warningPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void packageComboPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_packageComboPropertyChange
        fire();
    }//GEN-LAST:event_packageComboPropertyChange

    private void prefixFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_prefixFieldPropertyChange
        fire();
    }//GEN-LAST:event_prefixFieldPropertyChange

    private void suffixFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_suffixFieldPropertyChange
        fire();
    }//GEN-LAST:event_suffixFieldPropertyChange

    private void applicationConfigButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applicationConfigButtonActionPerformed
        openApplicationConfig();
    }//GEN-LAST:event_applicationConfigButtonActionPerformed

    private void viewComboPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_viewComboPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_viewComboPropertyChange

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox4ActionPerformed
    
     private void openApplicationConfig() {
        if (configDialog == null) {
            configDialog = new RestConfigDialog();
            configDialog.init(getPackage(), project, sourceGroup);
//            final RestSupport restSupport = project.getLookup().lookup(RestSupport.class);
//            if (restSupport != null) {
//                runWhenScanFinished(() -> {
//                    configDialog.setRestApplicationClasses(restSupport.getRestApplications());
//                    configDialog.setVisible(true);
//                }, getMessage(MVCPanel.class, "MVCPanel.scanningExistingApp.text"));
//            }
//        } else {
        }
        configDialog.setVisible(true);
        if (configDialog.getDialogResult() == OK_OPTION) {
            this.getConfigData().setRestConfigData(configDialog.getRestConfigData());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton applicationConfigButton;
    private javax.swing.JCheckBox authenticationCheckbox;
    private javax.swing.JCheckBox beanValidation;
    private javax.swing.JCheckBox csrfCheckbox;
    private javax.swing.JLabel entityLabel;
    private javax.swing.JPanel eventObserversPanel;
    private javax.swing.JCheckBox hybridClassCheckbox;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JPanel miscPanel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLayeredPane namePane;
    private javax.swing.JComboBox packageCombo;
    private javax.swing.JLabel packageLabel;
    private javax.swing.JPanel packagePanel;
    private javax.swing.JTextField prefixField;
    private javax.swing.JLayeredPane securityCompPanel;
    private javax.swing.JLabel securityLabel;
    private javax.swing.JPanel securityPanel;
    private javax.swing.JTextField suffixField;
    private javax.swing.JPanel suffixPanel;
    private javax.swing.JComboBox viewCombo;
    private javax.swing.JLabel viewLabel;
    private javax.swing.JPanel viewPanel;
    private javax.swing.JLabel warningLabel;
    private javax.swing.JPanel warningPanel;
    private javax.swing.JLayeredPane wrapper;
    private javax.swing.JCheckBox xssCheckbox;
    // End of variables declaration//GEN-END:variables
}
