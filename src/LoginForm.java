import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginForm extends JFrame implements ActionListener {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton loginButton;

    public LoginForm() {
        super("Login Form");
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        txtPassword.setEchoChar('*');
        loginButton = new JButton("Login");
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("Username:"));
        content.add(txtUsername);
        content.add(new JLabel("Password:"));
        content.add(txtPassword);
        content.add(loginButton);
        loginButton.addActionListener(this);

        this.setContentPane(content);
        this.pack();
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(loginButton)){
            ClientController controller = new ClientController();
            controller.openConnect();

            String username = txtUsername.getText();
            String password = txtPassword.getText();

            User user = new User(username, password);
            controller.sendData(user);
            if(controller.receiveData()){
                JOptionPane.showMessageDialog(this, "Login ngon vai lon ban oi");
            }
            else {
                JOptionPane.showMessageDialog(this, "Co cai lon bo may cho Login nhe");
            }
            controller.closeConnect();
        }
    }
}
