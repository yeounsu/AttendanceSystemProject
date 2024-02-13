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

        imageField = new JTextField("**���߿� ���⿡ �����ֱ�**");
        imageField.setSize(150, 170);

        JLabel nameLabel = new JLabel("�̸�");
        nameField = new JTextField(20);
        nameLabel.setBounds(650, 250, 50, 40);
        nameField.setBounds(650, 280, 300, 40);

        JLabel majorLabel = new JLabel("����");
        majorField = new JTextField(20);
        majorLabel.setBounds(1000, 250, 50, 40);
        majorField.setBounds(1000, 280, 300, 40);

        JLabel birthLabel = new JLabel("�������");
        birthField = new JTextField(20);
        birthLabel.setBounds(650, 350, 50, 40);
        birthField.setBounds(650, 380, 300, 40);

        JLabel genderLabel = new JLabel("����");
        genderField = new JTextField(20);
        genderLabel.setBounds(1000, 350, 50, 40);
        genderField.setBounds(1000, 380, 300, 40);

        JLabel addressLabel = new JLabel("�ּ�");
        addressField = new JTextField(100);
        addressLabel.setBounds(450, 450, 50, 40);
        addressField.setBounds(450, 480, 850, 40);

        JLabel idLabel = new JLabel("���̵�");
        idField = new JTextField(20);
        idLabel.setBounds(450, 550, 50, 40);
        idField.setBounds(450, 580, 380, 40);

        JLabel passwordLabel = new JLabel("���� ��й�ȣ");
        passwordField = new JPasswordField(100);
        passwordLabel.setBounds(920, 550, 100, 40);
        passwordField.setBounds(920, 580, 380, 40);

        JLabel newPasswordLabel = new JLabel("�� ��й�ȣ");
        newPasswordField = new JTextField(20);
        newPasswordLabel.setBounds(450, 650, 100, 40);
        newPasswordField.setBounds(450, 680, 380, 40);

        JLabel confirmNewPasswordLabel = new JLabel("�� ��й�ȣ Ȯ��");
        confirmNewPasswordField = new JTextField(20);
        confirmNewPasswordLabel.setBounds(920, 650, 100, 40);
        confirmNewPasswordField.setBounds(920, 680, 380, 40);

        saveButton = new JButton("�����ϱ�");
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