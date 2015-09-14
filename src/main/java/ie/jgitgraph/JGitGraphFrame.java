/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.jgitgraph;

import ie.jgitgraph.controller.LogViewController;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 *
 * @author dfitzsimons
 */
public class JGitGraphFrame extends JFrame {

    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger( JGitGraphFrame.class.getName() );

    private static final long serialVersionUID = 1L;

    /* Git container */
    private Git git;

    /**
     * File chooser
     */
    private final JFileChooser jfc = new JFileChooser();

    /**
     * Creates new form JGitGraphFrame
     */
    public JGitGraphFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        displayPanel = new javax.swing.JPanel();
        logViewPanel = new ie.jgitgraph.view.LogViewPanel();
        logGraphForm = new ie.jgitgraph.view.LogGraphForm();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        fileMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        logMenuItem = new javax.swing.JMenuItem();
        graphMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logViewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
            .addGroup(displayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logGraphForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, displayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logViewPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logGraphForm, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setText("File");
        fileMenu.setToolTipText("File actions");

        fileMenuItem.setText("Connect to Repository");
        fileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.ALT_MASK));
        fileMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuItem);
        fileMenu.add(jSeparator1);

        exitMenuItem.setText("Exit");
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        viewMenu.setText("View");

        logMenuItem.setText("Log");
        logMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(logMenuItem);

        graphMenuItem.setText("View graph");
        graphMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(graphMenuItem);

        menuBar.add(viewMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuItemActionPerformed

        if( evt.getSource().equals( fileMenuItem ) ) {
            jfc.setFileHidingEnabled( true );
            jfc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
            boolean found = false;
            boolean canceled = false;
            while( !found && !canceled ) {
                int returnVal = jfc.showOpenDialog( this );

                if( returnVal == JFileChooser.APPROVE_OPTION ) {
                    final File repoDirectory = jfc.getSelectedFile();
                    // Find hidden .git directory

                    final File gitDirectory = new File( repoDirectory.getPath() + File.separator + ".git" );

                    if( gitDirectory.exists() ) {
                        //Load the repository
                        found = true;
                        git = loadGitContainer( gitDirectory );
                        JOptionPane.showMessageDialog( this,
                                "Connected to Git repository.",
                                "Connected.",
                                JOptionPane.INFORMATION_MESSAGE );
                        logViewPanel.setRepoName( git );
                        LOGGER.log( Level.INFO, "Opening: {0}.", repoDirectory.getName() );
                    } else {
                        JOptionPane.showMessageDialog( this,
                                "This directory does not contain a Git repository.",
                                "Repository not Found.",
                                JOptionPane.INFORMATION_MESSAGE );

                    }

                } else {
                    canceled = true;
                    LOGGER.log( Level.INFO, "Open command cancelled by user." );
                }
            }
        }
    }//GEN-LAST:event_fileMenuItemActionPerformed

    private void logMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logMenuItemActionPerformed
        if( evt.getSource().equals( logMenuItem ) ) {
            if( git != null ) {
                LogViewController lvc = new LogViewController( git );
                logViewPanel.displayLog( lvc );
            } else {
                JOptionPane.showMessageDialog( this,
                        "You must first connect to a Git repository.",
                        "No Git Repository connected.",
                        JOptionPane.INFORMATION_MESSAGE );
            }

        }
    }//GEN-LAST:event_logMenuItemActionPerformed

    private void graphMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graphMenuItemActionPerformed
        if( evt.getSource().equals( graphMenuItem ) ) {
            if( git != null ) {
                //LogViewController lvc = new LogViewController( git );

                logGraphForm.displayGraphContent();
                
            } else {
                JOptionPane.showMessageDialog( this,
                        "You must first connect to a Git repository.",
                        "No Git Repository connected.",
                        JOptionPane.INFORMATION_MESSAGE );
            }

        }
    }//GEN-LAST:event_graphMenuItemActionPerformed

    private Git loadGitContainer( final File gitDirectory ) {
        Git gitModel = null;
        try {
            final FileRepositoryBuilder builder = new FileRepositoryBuilder();
            final File gitRepo = gitDirectory;
            final Repository repo = builder.setGitDir( gitRepo ).setMustExist( true ).build();
            gitModel = new Git( repo );
        } catch( final IOException ex ) {
            LOGGER.log( Level.SEVERE, "IOException", ex );
        } 

        return gitModel;
    }

    /**
     * @param args the command line arguments
     */
    public static void main( String args[] ) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for( javax.swing.UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels() ) {
                if( "Nimbus".equals( info.getName() ) ) {
                    UIManager.setLookAndFeel( info.getClassName() );
                    break;
                }
            }
        } catch( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex ) {
            LOGGER.log( Level.SEVERE, "Problem setting Look and feel.", ex );
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater( () -> {
            new JGitGraphFrame().setVisible( true );
        } );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel displayPanel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem fileMenuItem;
    private javax.swing.JMenuItem graphMenuItem;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private ie.jgitgraph.view.LogGraphForm logGraphForm;
    private javax.swing.JMenuItem logMenuItem;
    private ie.jgitgraph.view.LogViewPanel logViewPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu viewMenu;
    // End of variables declaration//GEN-END:variables
}
