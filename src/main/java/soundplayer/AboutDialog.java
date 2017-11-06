package soundplayer;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A sample modal dialog that displays a message and waits for the user to click
 * the OK button.
 */
public class AboutDialog extends JDialog {

    public AboutDialog(JFrame owner) {
        super(owner, "About", true);
        
        getRootPane().setBorder(
            BorderFactory.createEmptyBorder(4, 4, 4, 4));

        add(
            new JLabel(
                "<html><h1><i>MP3 Sound Player</i></h1>"
                + "<hr>This player plays only MP3 files. "
                + "Player has next features:"
                + "<ul>"
                + "<li>Prevents duplicates of the same tracks</li>"
                + "<li>If the songs was added in a random order, "
                + "<p>the button \"Sort\" sorting songs by their "
                + "location on the PC</li>"
                + "</ul>"
                + "<hr>By Denis Avtsin</html>"),
            BorderLayout.CENTER);

        JButton ok = new JButton("OK");
        ok.addActionListener(event -> setVisible(false));

        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);

        pack();
    }
}
