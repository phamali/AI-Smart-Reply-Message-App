package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

/**
 * The Landing View for navigating to Login or Signup.
 */
public class LandingView extends JPanel {
    private static final Color BG_COLOR = new Color(248, 250, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color PRIMARY_BLUE = new Color(66, 133, 244);
    private static final Color TEXT_DARK = new Color(15, 23, 42);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);

    private final String viewName = "landing";

    public LandingView(ActionListener loginListener, ActionListener signupListener) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Inner centered card panel matching Login/Signup styling
        final JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(CARD_BG);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        cardPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 24");
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.setMaximumSize(new Dimension(380, 400));

        // Title and Subtitle
        final JLabel title = new JLabel("AI Chat Messenger");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(TEXT_DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel subtitle = new JLabel("Connect, chat, and explore with AI");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Primary Login Button
        final JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(PRIMARY_BLUE);
        loginButton.setPreferredSize(new Dimension(300, 44));
        loginButton.setMaximumSize(new Dimension(300, 44));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.putClientProperty(FlatClientProperties.STYLE, "arc: 12; borderWidth: 0; focusWidth: 0");
        loginButton.addActionListener(loginListener);

        // Secondary Signup Button
        final JButton signupButton = new JButton("Create Account");
        signupButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        signupButton.setForeground(TEXT_DARK);
        signupButton.setBackground(new Color(241, 245, 249));
        signupButton.setPreferredSize(new Dimension(300, 44));
        signupButton.setMaximumSize(new Dimension(300, 44));
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupButton.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; borderWidth: 1; borderColor: #CBD5E1; focusWidth: 0; hoverBackground: #E2E8F0");
        signupButton.addActionListener(signupListener);

        cardPanel.add(title);
        cardPanel.add(Box.createVerticalStrut(8));
        cardPanel.add(subtitle);
        cardPanel.add(Box.createVerticalStrut(36));
        cardPanel.add(loginButton);
        cardPanel.add(Box.createVerticalStrut(14));
        cardPanel.add(signupButton);

        add(Box.createVerticalGlue());
        add(cardPanel);
        add(Box.createVerticalGlue());
    }

    public String getViewName() {
        return viewName;
    }
}
