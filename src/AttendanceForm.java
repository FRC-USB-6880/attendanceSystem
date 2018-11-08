import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AttendanceForm {
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JButton submitButton;
    private JPanel formField;
    private JLabel status;
    private ArrayList<Member> members;
    private ArrayList<String> names;
    private MemberListReader memberListReader;

    public AttendanceForm(){
        submitButton.addActionListener(new SubmitButtonClicked());
        members = new ArrayList<Member>();
        memberListReader = new MemberListReader("C:\\Users\\pb8xe\\IdeaProjects\\attendanceSystem\\buildTeam.csv");
        names = memberListReader.getNames();
    }

    private class SubmitButtonClicked implements ActionListener{
        private String fName;
        private String lName;

        public SubmitButtonClicked(){
        }

        @Override
        public void actionPerformed(ActionEvent e){
            fName = firstNameTextField.getText();
            lName = lastNameTextField.getText();
            ArrayList<String> memberNames = new ArrayList<String>();
            for (int i=0;i<members.size();i++){
                memberNames.add(members.get(i).getfName()+","+members.get(i).getlName());
            }
            if(fName.equalsIgnoreCase("end")&&lName.equalsIgnoreCase("end")){
                memberListReader.saveMembers(members);
                status.setText("Saved");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie){
                    ie.printStackTrace();
                }
                System.exit(0);
            }
            if (memberListReader.findName(memberNames, fName, lName)){
                for (int i=0;i<members.size();i++){
                    if (members.get(i).getfName().equalsIgnoreCase(fName) && members.get(i).getlName().equalsIgnoreCase(lName)){
                        members.get(i).signOut();
                        System.out.println("Signing out "+fName+" "+lName);
                        status.setText("Signed Out");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ie){
                            ie.printStackTrace();
                        }
                        status.setText("Enter Name");
                        break;
                    }
                }
            }
            else if(memberListReader.findName(names, fName, lName)) {
                System.out.println("Adding "+fName+" "+lName);
                members.add(new Member(fName, lName));
                status.setText("Success!");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie){
                    ie.printStackTrace();
                }
                status.setText("Enter Name");
            }
            else {
                status.setText("Error: Name not found! Please contact Pranav");
            }
        }
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("AttendanceForm");
        frame.setContentPane(new AttendanceForm().formField);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
