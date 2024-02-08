package attendancesystema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardLayout extends JFrame implements ActionListener {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public CardLayout() {
        setTitle("Card Layout Example");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel with CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel();

        // Create multiple panels to be added to the cardPanel
        JPanel panel1 = createPanel("Panel 1", Color.RED);
        JPanel panel2 = createPanel("Panel 2", Color.BLUE);
        JPanel panel3 = createPanel("Panel 3", Color.GREEN);

        // Add panels to cardPanel with unique names
        cardPanel.add(panel1, "panel1");
        cardPanel.add(panel2, "panel2");
        cardPanel.add(panel3, "panel3");

        // Create buttons to switch between panels
        JButton button1 = new JButton("Panel 1");
        button1.setActionCommand("panel1");
        button1.addActionListener(this);
        JButton button2 = new JButton("Panel 2");
        button2.setActionCommand("panel2");
        button2.addActionListener(this);
        JButton button3 = new JButton("Panel 3");
        button3.setActionCommand("panel3");
        button3.addActionListener(this);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        // Add cardPanel and buttonPanel to the frame
        getContentPane().add(cardPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Set the frame to be visible
        setVisible(true);
    }

    // Helper method to create a panel with a label
    private JPanel createPanel(String text, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setForeground(Color.WHITE);
        panel.setBackground(color);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the action command (panel name) from the button
        String panelName = e.getActionCommand();
        // Show the panel corresponding to the action command
        cardLayout.show(cardPanel, panelName);
    }

    private void show(JPanel cardPanel2, String panelName) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
        // Create an instance of CardLayoutExample
        SwingUtilities.invokeLater(() -> {
            new CardLayout();
        });
    }
}
