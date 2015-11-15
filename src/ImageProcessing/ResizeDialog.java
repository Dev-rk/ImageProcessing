package ImageProcessing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Roman on 30.09.2015.
 */
public class ResizeDialog {

        public static final int YES = 0;
        public static final int NO = -1;
        public Dimension dimension = new Dimension();

        private int choice = NO;

    public Dimension showMessage(String title, String message) {

        JLabel messageLabel = new JLabel(message);

        JLabel heightLabel = new JLabel("Height: ");
        JLabel widthtLabel = new JLabel("Width: ");
        JTextField heightField = new JTextField(4);
        JTextField widthField = new JTextField(4);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dimension.height = Integer.parseInt(heightField.getText());
                dimension.width = Integer.parseInt(widthField.getText());

                JButton button = (JButton)e.getSource();
                SwingUtilities.getWindowAncestor(button).dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dimension.height = 0;
                dimension.width = 0;

                JButton button = (JButton) e.getSource();
                SwingUtilities.getWindowAncestor(button).dispose();
            }
        });

        JPanel panelDialog = new JPanel();

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panelDialog.setLayout(layout);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 2, 2, 2);

        layout.setConstraints(widthtLabel, c);
        panelDialog.add(widthtLabel);

        c.gridx = 1;
        c.gridy = 0;
        layout.setConstraints(widthField, c);
        panelDialog.add(widthField);

        c.gridx = 0;
        c.gridy = 1;
        layout.setConstraints(heightLabel, c);
        panelDialog.add(heightLabel);

        c.gridx = 1;
        c.gridy = 1;
        layout.setConstraints(heightField, c);
        panelDialog.add(heightField);

        c.gridx = 0;
        c.gridy = 2;
        layout.setConstraints(okButton, c);
        panelDialog.add(okButton);

        c.gridx = 1;
        c.gridy = 2;
        layout.setConstraints( cancelButton, c);
        panelDialog.add(cancelButton);



        JPanel content = new JPanel(new BorderLayout(8, 8));
        content.add(messageLabel, BorderLayout.CENTER);
        content.add(panelDialog, BorderLayout.SOUTH);

        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setTitle(title);
        dialog.getContentPane().add(content);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return dimension;
    }
}