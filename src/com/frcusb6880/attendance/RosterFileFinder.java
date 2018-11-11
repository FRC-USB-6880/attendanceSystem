package com.frcusb6880.attendance;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RosterFileFinder {
    private JPanel panel1;
    private JTextField directory;
    private JButton submitButton;
    private JLabel dirLabel;
    private JTextField rosterName;
    private JLabel nameLabel;
    private AttendanceForm attendanceForm;

    public RosterFileFinder(AttendanceForm attendanceForm){
        this.attendanceForm = attendanceForm;
        submitButton.addActionListener(new SubmitButtonClicked());
        rosterName.addActionListener(new EnterClicked());
    }

    private class EnterClicked implements ActionListener {

        public EnterClicked(){}

        @Override
        public void actionPerformed(ActionEvent e){
            attendanceForm.setDir(directory.getText(), rosterName.getText(), dirLabel);
            System.out.println("Set directory to "+directory.getText());
            System.out.println("Set roster to "+rosterName.getText());
            nameLabel.setText(dirLabel.getText());
        }
    }

    private class SubmitButtonClicked implements ActionListener{

        public SubmitButtonClicked(){}

        @Override
        public void actionPerformed(ActionEvent e){
            attendanceForm.setDir(directory.getText(), rosterName.getText(), dirLabel);
            System.out.println("Set directory to "+directory.getText());
            System.out.println("Set roster to "+rosterName.getText());
            nameLabel.setText(dirLabel.getText());
        }
    }

    public void createWindow(AttendanceForm attendanceForm){
        JFrame frame = new JFrame("Roster File Finder");
        frame.setContentPane(new RosterFileFinder(attendanceForm).panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
