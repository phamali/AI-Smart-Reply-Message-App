package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

import data_access.AiMessaging;
import entity.Message;
import interface_adapter.enter_chat.EnterChatController;
import interface_adapter.enter_chat.InChatState;
import interface_adapter.enter_chat.InChatViewModel;
import interface_adapter.send_message.SendMessageController;

/**
 * The View for an active conversation between two users.
 */
public class InChatView extends JPanel implements PropertyChangeListener {
    private static final Color BG_MAIN = new Color(248, 250, 252);
    private static final Color SURFACE_WHITE = Color.WHITE;
    private static final Color BORDER_SUBTLE = new Color(226, 232, 240);
    private static final Color PRIMARY_BLUE = new Color(66, 133, 244);
    private static final Color AVATAR_BG = new Color(219, 234, 254);
    private static final Color AI_BTN_BG = new Color(238, 242, 255);
    private static final Color AI_BTN_TEXT = new Color(67, 56, 202);
    private static final Color TEXT_PRIMARY = new Color(15, 23, 42);
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private static final Color TEXT_MUTED = new Color(148, 163, 184);

    private final String viewName = "in chat";
    private final InChatViewModel inChatViewModel;

    private final Timer timer;
    private final JPanel chatArea;
    private final JScrollPane chatAreaScrollPane;
    private final JScrollBar verticalScrollBar;
    private final JButton backButton = new JButton("← Back");

    private final JPanel topPanel = new JPanel(new BorderLayout(12, 0));
    private final JLabel otherUser = new JLabel();
    private String currentPartnerInitial = "?";
    private final JPanel avatarBadge;

    private final JPanel bottomPanel = new JPanel();
    private final JTextField textEntryField = new JTextField();
    private final JButton sendButton = new JButton("Send");
    private final JButton generateResponseButton = new JButton("✨ Generate AI Smart Reply");

    private EnterChatController enterChatController;
    private SendMessageController sendMessageController;

    private final AiMessaging aiMessaging = new AiMessaging();

    private int lastMessageCount = 0;
    private String currentChatPartner = "";
    private boolean userIsNearBottom = true;

    public InChatView(InChatViewModel inChatViewModel) {
        this.inChatViewModel = inChatViewModel;
        this.inChatViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(BG_MAIN);
        this.setPreferredSize(new Dimension(420, 620));

        // ================= TOP HEADER PANEL =================
        topPanel.setBackground(SURFACE_WHITE);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SUBTLE),
                new EmptyBorder(12, 16, 12, 16)
        ));

        backButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        backButton.setForeground(TEXT_PRIMARY);
        backButton.setBackground(new Color(241, 245, 249));
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(84, 34));
        backButton.putClientProperty(FlatClientProperties.STYLE,
                "arc: 10; borderWidth: 1; borderColor: #E2E8F0; focusWidth: 0; hoverBackground: #E2E8F0");

        // Partner Avatar + Name Container
        avatarBadge = createHeaderAvatar();
        otherUser.setFont(new Font("SansSerif", Font.BOLD, 16));
        otherUser.setForeground(TEXT_PRIMARY);

        final JPanel partnerInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        partnerInfoPanel.setOpaque(false);
        partnerInfoPanel.add(otherUser);
        partnerInfoPanel.add(avatarBadge);

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(partnerInfoPanel, BorderLayout.EAST);

        // ================= MIDDLE CHAT AREA =================
        chatArea = new JPanel();
        chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));
        chatArea.setBackground(BG_MAIN);
        chatArea.setBorder(new EmptyBorder(14, 14, 14, 14));

        chatAreaScrollPane = new JScrollPane(chatArea);
        chatAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatAreaScrollPane.setBorder(BorderFactory.createEmptyBorder());
        chatAreaScrollPane.getViewport().setBackground(BG_MAIN);
        verticalScrollBar = chatAreaScrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16);

        // ================= BOTTOM COMPOSE PANEL =================
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(SURFACE_WHITE);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_SUBTLE),
                new EmptyBorder(12, 16, 14, 16)
        ));

        textEntryField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textEntryField.setPreferredSize(new Dimension(0, 40));
        textEntryField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Type a message...");
        textEntryField.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; margin: 0,12,0,12; background: #F8FAFC; borderColor: #CBD5E1; focusedBorderColor: #4285F4");
        textEntryField.addActionListener(evt -> sendMessage());

        sendButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(PRIMARY_BLUE);
        sendButton.setFocusPainted(false);
        sendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sendButton.setPreferredSize(new Dimension(78, 40));
        sendButton.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; borderWidth: 0; focusWidth: 0");

        final JPanel textAndSendPanel = new JPanel(new BorderLayout(8, 0));
        textAndSendPanel.setBackground(SURFACE_WHITE);
        textAndSendPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        textAndSendPanel.add(textEntryField, BorderLayout.CENTER);
        textAndSendPanel.add(sendButton, BorderLayout.EAST);

        generateResponseButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        generateResponseButton.setForeground(AI_BTN_TEXT);
        generateResponseButton.setBackground(AI_BTN_BG);
        generateResponseButton.setFocusPainted(false);
        generateResponseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        generateResponseButton.setPreferredSize(new Dimension(0, 36));
        generateResponseButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        generateResponseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateResponseButton.putClientProperty(FlatClientProperties.STYLE,
                "arc: 10; borderWidth: 1; borderColor: #C7D2FE; focusWidth: 0; hoverBackground: #E0E7FF");

        final JPanel aiButtonWrapper = new JPanel(new BorderLayout());
        aiButtonWrapper.setBackground(SURFACE_WHITE);
        aiButtonWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        aiButtonWrapper.add(generateResponseButton, BorderLayout.CENTER);

        bottomPanel.add(textAndSendPanel);
        bottomPanel.add(Box.createVerticalStrut(8));
        bottomPanel.add(aiButtonWrapper);

        // ================= ACTION LISTENERS =================
        backButton.addActionListener(evt -> {
            final InChatState state = inChatViewModel.getState();
            enterChatController.switchToChatListView(state.getSender());
            timer.stop();
        });

        sendButton.addActionListener(evt -> sendMessage());

        generateResponseButton.addActionListener(evt -> {
            final String generatedNewResponse = generateResponse();
            textEntryField.setText(generatedNewResponse);
            textEntryField.requestFocusInWindow();
        });

        final ActionListener refresh = evt -> {
            final InChatState currentState = inChatViewModel.getState();
            if (currentState.getMessages() != null
                    && currentState.getSender() != null
                    && currentState.getReceiver() != null) {
                enterChatController.execute(currentState.getSender(), currentState.getReceiver());
            }
        };
        timer = new Timer(500, refresh);
        timer.start();

        this.add(topPanel, BorderLayout.NORTH);
        this.add(chatAreaScrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        verticalScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent evt) {
                final int extent = verticalScrollBar.getModel().getExtent();
                final int maximum = verticalScrollBar.getMaximum();
                final int value = verticalScrollBar.getValue();
                final int scrollBarPosition = value + extent;
                final int threshold = 50;
                userIsNearBottom = (maximum - scrollBarPosition) <= threshold;
            }
        });
    }

    private JPanel createHeaderAvatar() {
        final JPanel badge = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AVATAR_BG);
                g2.fillOval(0, 0, getWidth(), getHeight());

                g2.setColor(PRIMARY_BLUE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 14));
                final FontMetrics fm = g2.getFontMetrics();
                final int x = (getWidth() - fm.stringWidth(currentPartnerInitial)) / 2;
                final int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(currentPartnerInitial, x, y);
                g2.dispose();
            }
        };
        badge.setOpaque(false);
        badge.setPreferredSize(new Dimension(34, 34));
        return badge;
    }

    private void sendMessage() {
        final String text = textEntryField.getText().trim();
        if (!text.isEmpty()) {
            final InChatState currentState = inChatViewModel.getState();
            sendMessageController.execute(
                    currentState.getSender(),
                    currentState.getReceiver(),
                    text
            );
            textEntryField.setText("");

            enterChatController.execute(
                    currentState.getSender(),
                    currentState.getReceiver()
            );
        }
    }

    private String generateResponse() {
        final InChatState currentState = inChatViewModel.getState();
        String str = aiMessaging.generateNewMessage(currentState.getSender(), currentState.getReceiver());
        if (str == null) {
            return "";
        }
        final int colonIdx = str.indexOf(':');
        if (colonIdx != -1) {
            str = str.substring(colonIdx + 1).
