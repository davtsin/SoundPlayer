/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundplayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author denis
 */
public class SoundPlayerGUI extends JFrame {

    Mp3Player mp3Player = new Mp3Player();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem openMItem = new JMenuItem("Open..."),
        aboutMItem = new JMenuItem("About"),
        exitMItem = new JMenuItem("Exit");
    private JList trackList = new JList();
    private JPanel controlsPanel = new JPanel();
    private JButton sortButton = new JButton("Sort"),
        playButton = new JButton("Play"),
        pauseButton = new JButton("Pause"),
        stopButton = new JButton("Stop"),
        openButton = new JButton("Add..."),
        clearButton = new JButton("Clear");
    // in this place the path to Music was hardcoded
    private JFileChooser fileChooser
        = new JFileChooser("/home/denis/Music");
    private DefaultListModel<File> listModel
        = new DefaultListModel<>();
    private AboutDialog dialog;

    public SoundPlayerGUI(String name) {
        super(name);
        openMItem.addActionListener(openListener);
        aboutMItem.addActionListener(aboutListener);
        exitMItem.addActionListener(exitListener);
        fileMenu.add(openMItem);
        fileMenu.add(aboutMItem);
        fileMenu.add(exitMItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        fileChooser.setMultiSelectionEnabled(true);
        trackList.addMouseListener(trackListListener);
        trackList.setModel(listModel);
        trackList.setDragEnabled(true);
        trackList.setCellRenderer(new FileRenderer());
        add(new JScrollPane(trackList));
        sortButton.addActionListener(sortListener);
        playButton.addActionListener(playListener);
        pauseButton.addActionListener(event -> mp3Player.pause());
        stopButton.addActionListener(event -> mp3Player.stop());
        openButton.addActionListener(openListener);
        clearButton.addActionListener(clearListener);
        controlsPanel.add(playButton);
        controlsPanel.add(pauseButton);
        controlsPanel.add(stopButton);
        controlsPanel.add(openButton);
        controlsPanel.add(clearButton);
        controlsPanel.add(sortButton);

        add(BorderLayout.SOUTH, controlsPanel);
    }

    public static void main(String[] args) {
        // set system default look-and-feel style
        try {
            UIManager.setLookAndFeel(
                "javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SoundPlayerGUI.class.getName()).
                log(Level.SEVERE, null, ex);
        }
        // run program
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SoundPlayerGUI sp
                    = new SoundPlayerGUI("MP3 Sound Player");
                sp.setSize(400, 600);
                sp.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE);
                sp.setVisible(true);
            }
        });
    }

    // "About" listener
    private ActionListener aboutListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (dialog == null) {
                dialog = new AboutDialog(SoundPlayerGUI.this);
            }
            dialog.setVisible(true);
        }
    };

    // "Exit" listener
    private ActionListener exitListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };

    // "Play" button click
    private ActionListener playListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            File file = (File) trackList.getSelectedValue();
            if (file == null) {
                return;
            }
            mp3Player.play(file);
        }
    };

    // "Add.." button click and File->Open click
    private ActionListener openListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int rVal = fileChooser.showOpenDialog(SoundPlayerGUI.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                // add only .mp3
                for (File file : fileChooser.getSelectedFiles()) {
                    if (file.toString().endsWith(".mp3")
                        && !listModel.contains(file)) {
                        listModel.addElement(file);
                    }
                }
            }
        }
    };

    // "Sort" button click
    private ActionListener sortListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            List<File> files = new ArrayList<>();
            Enumeration<File> els = listModel.elements();
            while (els.hasMoreElements()) {
                files.add(els.nextElement());
            }
            Collections.sort(files);
            listModel.removeAllElements();
            for (File file : files) {
                listModel.addElement(file);
            }
        }
    };

    private MouseListener trackListListener = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2
                && e.getButton() == MouseEvent.BUTTON1) {
                File file = (File) trackList.getSelectedValue();
                mp3Player.play(file);
            }
        }
    };

    // "Clear" button listener
    private ActionListener clearListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            for (Object file : trackList.getSelectedValuesList()) {
                listModel.removeElement(file);
            }
        }
    };

    private class FileRenderer extends DefaultListCellRenderer {

        public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus
        ) {
            super.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);
            if (value instanceof File) {
                File file = (File) value;
                setText(file.getName());
            }
            return this;
        }
    }
}
