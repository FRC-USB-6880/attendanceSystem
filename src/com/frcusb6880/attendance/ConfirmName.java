package com.frcusb6880.attendance;

import javax.swing.*;
import java.awt.event.*;

public class ConfirmName extends JDialog {
    private JPanel contentPane;
    private JLabel label;
    private JButton buttonOk;
    private JButton buttonCancel;
    private AttendanceForm attendanceForm;
    private String first;
    private String last;

    public ConfirmName(AttendanceForm attendanceForm, String first, String last) {
        this.attendanceForm = attendanceForm;
        this.first = first;
        this.last = last;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOk);

        label.setText(first+" "+last+" not found. Would you like to add to roster?");

        buttonOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        attendanceForm.addName(first, last);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
