/**
 * Copyright [2016] Gaurav Gupta
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
package org.netbeans.jcode.mvc.viewer.jsp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.prefs.Preferences;
import javax.swing.event.ChangeEvent;
import org.apache.commons.lang.StringUtils;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import static org.netbeans.jcode.core.util.WebUtil.isInternetReachable;
import static org.netbeans.jcode.mvc.viewer.jsp.JSPData.DEFAULT_FOLDER;
import org.netbeans.jcode.stack.config.panel.*;
import org.netbeans.jcode.ui.browse.BrowseFolders;
import org.netbeans.jcode.util.PreferenceUtils;
import org.netbeans.modules.web.api.webmodule.WebProjectConstants;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;
import static org.openide.util.NbBundle.getMessage;

/**
 *
 * @author Gaurav Gupta
 */
public class JSPPanel extends LayerConfigPanel<JSPData> {

    private Preferences pref;

    public JSPPanel() {
        initComponents();
    }

    @Override
    public boolean hasError() {
        warningLabel.setText("");
        if (StringUtils.isBlank(getFolder())) {
            warningLabel.setText(NbBundle.getMessage(JSPPanel.class, "JSPPanel.invalidFolder.message"));
            return true;
        }
        return false;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        hasError();
    }

    @Override
    public void read() {
        this.setConfigData(PreferenceUtils.get(pref, JSPData.class));
        JSPData data = this.getConfigData();
        if (StringUtils.isNotBlank(data.getFolder())) {
            setFolder(data.getFolder());
        }
        cdnCheckBox.setSelected(data.isOnlineTheme());
    }

    @Override
    public void store() {
        this.getConfigData().setFolder(getFolder());
        this.getConfigData().setOnlineTheme(isOnlineTheme());
        PreferenceUtils.set(pref, this.getConfigData());
    }

    private Project project;

    @Override
    public void init(String folder, Project project, SourceGroup sourceGroup) {
        pref = ProjectUtils.getPreferences(project, JSPData.class, true);
        this.project = project;
        addChangeListener(folderTextField);
        setFolder(DEFAULT_FOLDER);
//        checkCDNAvailable();
    }

    public String getFolder() {
        return folderTextField.getText().trim();
    }

    public boolean isOnlineTheme() {
        return cdnCheckBox.isSelected();
    }

    private void setFolder(String folder) {
        folderTextField.setText(folder);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wrapperPanel = new javax.swing.JPanel();
        folderLabel = new javax.swing.JLabel();
        folderTextField = new javax.swing.JTextField();
        browseButton1 = new javax.swing.JButton();
        warningPanel = new javax.swing.JPanel();
        warningLabel = new javax.swing.JLabel();
        cdnPanel = new javax.swing.JPanel();
        cdnCheckBox = new javax.swing.JCheckBox();
        internetNotAvailable = new javax.swing.JLabel();

        wrapperPanel.setLayout(new java.awt.BorderLayout(10, 0));

        org.openide.awt.Mnemonics.setLocalizedText(folderLabel, org.openide.util.NbBundle.getMessage(JSPPanel.class, "JSPPanel.folderLabel.text")); // NOI18N
        wrapperPanel.add(folderLabel, java.awt.BorderLayout.LINE_START);

        folderTextField.setText(org.openide.util.NbBundle.getMessage(JSPPanel.class, "JSPPanel.folderTextField.text")); // NOI18N
        folderTextField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                folderTextFieldPropertyChange(evt);
            }
        });
        wrapperPanel.add(folderTextField, java.awt.BorderLayout.CENTER);

        org.openide.awt.Mnemonics.setLocalizedText(browseButton1, org.openide.util.NbBundle.getMessage(JSPPanel.class, "JSPPanel.browseButton1.text")); // NOI18N
        browseButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButton1ActionPerformed(evt);
            }
        });
        wrapperPanel.add(browseButton1, java.awt.BorderLayout.EAST);

        warningPanel.setLayout(new java.awt.BorderLayout(10, 0));

        warningLabel.setForeground(new java.awt.Color(200, 0, 0));
        warningLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(warningLabel, org.openide.util.NbBundle.getMessage(JSPPanel.class, "JSPPanel.warningLabel.text")); // NOI18N
        warningPanel.add(warningLabel, java.awt.BorderLayout.CENTER);

        cdnCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(cdnCheckBox, org.openide.util.NbBundle.getMessage(JSPPanel.class, "JSPPanel.cdnCheckBox.text")); // NOI18N

        internetNotAvailable.setForeground(new java.awt.Color(206, 0, 0));

        javax.swing.GroupLayout cdnPanelLayout = new javax.swing.GroupLayout(cdnPanel);
        cdnPanel.setLayout(cdnPanelLayout);
        cdnPanelLayout.setHorizontalGroup(
            cdnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cdnPanelLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(cdnCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(internetNotAvailable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        cdnPanelLayout.setVerticalGroup(
            cdnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cdnPanelLayout.createSequentialGroup()
                .addGroup(cdnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(internetNotAvailable, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cdnCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(wrapperPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
                    .addComponent(cdnPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(warningPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(wrapperPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cdnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(88, Short.MAX_VALUE)
                    .addComponent(warningPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void folderTextFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_folderTextFieldPropertyChange
        fire();
    }//GEN-LAST:event_folderTextFieldPropertyChange

    private void browseButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButton1ActionPerformed
        Sources sources = ProjectUtils.getSources(project);
        SourceGroup sourceGroups[] = sources.getSourceGroups(WebProjectConstants.TYPE_DOC_ROOT);
        FileObject fileObject = BrowseFolders.showDialog(new SourceGroup[]{sourceGroups[0]},
                org.openide.loaders.DataFolder.class, "");
        if(fileObject!=null){
        String folderPath = FileUtil.getRelativePath(sourceGroups[0].getRootFolder(), fileObject);
        setFolder(folderPath);
        }
    }//GEN-LAST:event_browseButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton1;
    private javax.swing.JCheckBox cdnCheckBox;
    private javax.swing.JPanel cdnPanel;
    private javax.swing.JLabel folderLabel;
    private javax.swing.JTextField folderTextField;
    private javax.swing.JLabel internetNotAvailable;
    private javax.swing.JLabel warningLabel;
    private javax.swing.JPanel warningPanel;
    private javax.swing.JPanel wrapperPanel;
    // End of variables declaration//GEN-END:variables

    private void checkCDNAvailable() {//fix in next release
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            if (!isInternetReachable(getMessage(JSPPanel.class, "JSPPanel.internet.check"))) {
                cdnCheckBox.setSelected(true);
                internetNotAvailable.setText("");
            } else {
                 cdnCheckBox.setSelected(false);
                 internetNotAvailable.setText(getMessage(JSPPanel.class, "JSPPanel.internetNotAvailable.text"));
            }
        });
    }
}
