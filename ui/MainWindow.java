package ui;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Turing Maschine");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JButton openButton = new JButton("Open File");
        openButton.addActionListener(_ -> openFileChooser());

        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(openButton);

        setVisible(true);
    }

    private void openFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Selected file: " + selectedFile.getAbsolutePath());
        }
    }
}
