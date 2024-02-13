package attendancesystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilePanel extends JPanel {

    private JTextField imageField;
    private JTextField nameField;
    private JTextField majorField;
    private JTextField birthField;
    private JTextField genderField;
    private JTextField addressField;
    private JTextField idField;
    private JPasswordField passwordField;
    private JTextField newPasswordField;
    private JTextField confirmNewPasswordField;
    private JButton saveButton;

    public ProfilePanel() {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);

        imageField = new JTextField("**나중에 여기에 사진넣기**");
        imageField.setSize(150, 170);

        JLabel nameLabel = new JLabel("이름");
        nameField = new JTextField(20);
        nameLabel.setBounds(650, 250, 50, 40);
        nameField.setBounds(650, 280, 300, 40);

        JLabel majorLabel = new JLabel("전공");
        majorField = new JTextField(20);
        majorLabel.setBounds(1000, 250, 50, 40);
        majorField.setBounds(1000, 280, 300, 40);

        JLabel birthLabel = new JLabel("생년월일");
        birthField = new JTextField(20);
        birthLabel.setBounds(650, 350, 50, 40);
        birthField.setBounds(650, 380, 300, 40);

        JLabel genderLabel = new JLabel("성별");
        genderField = new JTextField(20);
        genderLabel.setBounds(1000, 350, 50, 40);
        genderField.setBounds(1000, 380, 300, 40);

        JLabel addressLabel = new JLabel("주소");
        addressField = new JTextField(100);
        addressLabel.setBounds(450, 450, 50, 40);
        addressField.setBounds(450, 480, 850, 40);

        JLabel idLabel = new JLabel("아이디");
        idField = new JTextField(20);
        idLabel.setBounds(450, 550, 50, 40);
        idField.setBounds(450, 580, 380, 40);

        JLabel passwordLabel = new JLabel("현재 비밀번호");
        passwordField = new JPasswordField(100);
        passwordLabel.setBounds(920, 550, 100, 40);
        passwordField.setBounds(920, 580, 380, 40);

        JLabel newPasswordLabel = new JLabel("새 비밀번호");
        newPasswordField = new JTextField(20);
        newPasswordLabel.setBounds(450, 650, 100, 40);
        newPasswordField.setBounds(450, 680, 380, 40);

        JLabel confirmNewPasswordLabel = new JLabel("새 비밀번호 확인");
        confirmNewPasswordField = new JTextField(20);
        confirmNewPasswordLabel.setBounds(920, 650, 100, 40);
        confirmNewPasswordField.setBounds(920, 680, 380, 40);

        saveButton = new JButton("저장하기");
        saveButton.setBounds(1100, 780, 200, 40);
        saveButton.setForeground(new Color(255, 255, 255));
        saveButton.setBackground(new Color(133, 91, 221));
        saveButton.setFont(new Font("Inter", Font.BOLD, 20));

        add(imageField);
        add(nameLabel);
        add(nameField);
        add(majorLabel);
        add(majorField);
        add(birthLabel);
        add(birthField);
        add(genderLabel);
        add(genderField);
        add(addressLabel);
        add(addressField);
        add(idLabel);
        add(idField);
        add(passwordLabel);
        add(passwordField);
        add(newPasswordLabel);
        add(newPasswordField);
        add(confirmNewPasswordLabel);
        add(confirmNewPasswordField);
        add(saveButton);
    }


}