package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

import entity.ChatEntry;
import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.ChatListState;
import interface_adapter.add_friend.ChatListViewModel;
import interface_adapter.enter_chat.EnterChatController;
import interface_adapter.login.LoginController;
import interface_adapter.settings.SettingsController;

/**
 * The View for displaying and managing the user's active chat list.
 */
public class ChatListView extends JPanel implements PropertyChangeListener {
    private static final Color BG_MAIN = new Color(248, 250, 252);
    private static final Color SURFACE_WHITE = Color.WHITE;
    private static final Color CARD_HOVER = new Color(241, 245, 249);
    private static final Color BORDER_SUBTLE = new Color(226, 232, 240);
    private static final Color PRIMARY_BLUE = new Color(66, 133, 244);
    private static final Color AVATAR_BG = new Color(219, 234, 254);
    private static final Color TEXT_PRIMARY = new Color(15, 23, 42);
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private static final Color TEXT_MUTED = new Color(148, 163, 184);

    private final String viewName = "chat list";

    private final JPanel chatListPanel;
    private final JTextField chatSearchField;
    private final String searchPlaceholder = "Search chats...";
    private final ChatListViewModel chatListViewModel;
    private AddFriendController addFriendController;
    private EnterChatController enterChatController;
    private SettingsController settingsController;
    private LoginController loginController;

    private final JButton addFriendButton;
    private final JButton returnButton;
    private final JButton settingsButton;

    public ChatListView(ChatListViewModel chatListViewModel) {
        this.chatListViewModel = chatListViewModel;
        this.chatListViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout(0, 0));
        this.setBackground(BG_MAIN);
        this.setPreferredSize(new Dimension(420, 620));

        // ================= TOP HEADER PANEL =================
        final JPanel headerContainer = new JPanel();
        headerContainer.setLayout(new BoxLayout(headerContainer, BoxLayout.Y_AXIS));
        headerContainer.setBackground(SURFACE_WHITE);
        headerContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SUBTLE),
                new EmptyBorder(18, 20, 14, 20)
        ));

        // Title Row
        final JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setBackground(SURFACE_WHITE);
        final JLabel headerTitle = new JLabel("Messages");
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerTitle.setForeground(TEXT_PRIMARY);
        titleRow.add(headerTitle, BorderLayout.WEST);

        addFriendButton = new JButton("+ Add Friend");
        addFriendButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        addFriendButton.setForeground(Color.WHITE);
        addFriendButton.setBackground(PRIMARY_BLUE);
        addFriendButton.setFocusPainted(false);
        addFriendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addFriendButton.setPreferredSize(new Dimension(118, 34));
        addFriendButton.putClientProperty(FlatClientProperties.STYLE,
                "arc: 10; borderWidth: 0; focusWidth: 0");
        titleRow.add(addFriendButton, BorderLayout.EAST);

        // Search Row
        final JPanel searchRow = new JPanel(new BorderLayout());
        searchRow.setBackground(SURFACE_WHITE);
        chatSearchField = new JTextField();
        chatSearchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        chatSearchField.setPreferredSize(new Dimension(0, 38));
        chatSearchField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, searchPlaceholder);
        chatSearchField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, true);
        chatSearchField.putClientProperty(FlatClientProperties.STYLE,
                "arc: 12; margin: 0,12,0,12; background: #F1F5F9; borderColor: #E2E8F0; focusedBorderColor: #4285F4");

        chatSearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                final String query = chatSearchField.getText().trim();
                refreshChatList(query);
            }
        });

        searchRow.add(chatSearchField, BorderLayout.CENTER);

        headerContainer.add(titleRow);
        headerContainer.add(Box.createVerticalStrut(12));
        headerContainer.add(searchRow);
        this.add(headerContainer, BorderLayout.NORTH);

        // ================= MIDDLE CHAT LIST PANEL =================
        chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
        chatListPanel.setBackground(BG_MAIN);
        chatListPanel.setBorder(new EmptyBorder(12, 16, 12, 16));

        final JScrollPane chatListScrollPane = new JScrollPane(chatListPanel);
        chatListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatListScrollPane.setBorder(BorderFactory.createEmptyBorder());
        chatListScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        chatListScrollPane.getViewport().setBackground(BG_MAIN);

        this.add(chatListScrollPane, BorderLayout.CENTER);

        // ================= BOTTOM TOOLBAR PANEL =================
        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(SURFACE_WHITE);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_SUBTLE),
                new EmptyBorder(12, 20, 12, 20)
        ));

        returnButton = createSecondaryButton("Refresh");
        settingsButton = createSecondaryButton("Settings");

        bottomPanel.add(returnButton, BorderLayout.WEST);
        bottomPanel.add(settingsButton, BorderLayout.EAST);

        this.add(bottomPanel, BorderLayout.SOUTH);

        // ================= ACTION LISTENERS =================
        returnButton.addActionListener(evt -> resetSearch());

        addFriendButton.addActionListener(evt -> {
            final ChatListState currentState = chatListViewModel.getState();
            final String friendName = JOptionPane.showInputDialog(
                    this,
                    "Enter your friend's username:",
                    "Add Friend",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (friendName != null && !friendName.trim().isEmpty()) {
                addFriendController.execute(
                        currentState.getActiveUser(),
                        friendName.trim(),
                        "Hello! This is a new conversation."
                );
                refreshChatList("");
            }
            chatListViewModel.setState(currentState);
        });

        settingsButton.addActionListener(evt -> {
            if (evt.getSource().equals(settingsButton)) {
                final ChatListState currentState = chatListViewModel.getState();
                this.settingsController.execute(currentState.getActiveUser());
            }
        });

        refreshChatList("");
    }

    private JButton createSecondaryButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(TEXT_PRIMARY);
        button.setBackground(new Color(241, 245, 249));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 34));
        button.putClientProperty(FlatClientProperties.STYLE,
                "arc: 10; borderWidth: 1; borderColor: #E2E8F0; focusWidth: 0; hoverBackground: #E2E8F0");
        return button;
    }

    private void refreshChatList(String query) {
        chatListPanel.removeAll();
        final ChatListState currentState = chatListViewModel.getState();
        final ArrayList<ChatEntry> chats = currentState.getChatList();

        int visibleCount = 0;
        if (chats != null) {
            for (int i = chats.size() - 1; i >= 0; i--) {
                final ChatEntry chat = chats.get(i);
                final String partnerName = getPartnerName(chat, currentState.getCurrentUsername());
                if (query.isEmpty() || partnerName.toLowerCase().contains(query.toLowerCase())) {
                    final JPanel chatItemPanel = createChatItemPanel(chat, partnerName);
                    chatListPanel.add(chatItemPanel);
                    chatListPanel.add(Box.createVerticalStrut(8));
                    visibleCount++;
                }
            }
        }

        if (visibleCount == 0) {
            final JLabel emptyLabel = new JLabel(
                    query.isEmpty() ? "No conversations yet. Click '+ Add Friend' to start!" : "No matching chats found.",
                    SwingConstants.CENTER
            );
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            emptyLabel.setForeground(TEXT_MUTED);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyLabel.setBorder(new EmptyBorder(40, 10, 40, 10));
            chatListPanel.add(emptyLabel);
        }

        chatListPanel.revalidate();
        chatListPanel.repaint();
    }

    private String getPartnerName(ChatEntry chatEntry, String currentUsername) {
        final String partner;
        if (currentUsername != null && currentUsername.equals(chatEntry.getUser1())) {
            partner = chatEntry.getUser2();
        }
        else {
            partner = chatEntry.getUser1();
        }
        return partner != null ? partner : "";
    }

    private void resetSearch() {
        chatSearchField.setText("");
        final ChatListState currentState = chatListViewModel.getState();
        if (loginController != null && currentState.getActiveUser() != null) {
            loginController.execute(
                    currentState.getCurrentUsername(),
                    currentState.getActiveUser().getPassword()
            );
        }
        refreshChatList("");
    }

    private JPanel createChatItemPanel(ChatEntry chatEntry, String partnerName) {
        final ChatListState state = chatListViewModel.getState();

        final JPanel chatItemPanel = new JPanel(new BorderLayout(12, 0));
        chatItemPanel.setBackground(SURFACE_WHITE);
        chatItemPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chatItemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_SUBTLE, 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));
        chatItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 68));
        chatItemPanel.setPreferredSize(new Dimension(0, 68));
        chatItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Circular Avatar Badge
        final JPanel avatarBadge = createAvatarBadge(partnerName);

        // Name and Time top row
        final JLabel nameLabel = new JLabel(partnerName);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        nameLabel.setForeground(TEXT_PRIMARY);

        final JLabel timeLabel = new JLabel(chatEntry.getTime());
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        timeLabel.setForeground(TEXT_MUTED);

        final JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);
        headerRow.add(nameLabel, BorderLayout.WEST);
        headerRow.add(timeLabel, BorderLayout.EAST);

        // Last Message Preview
        final JLabel messageLabel = new JLabel(chatEntry.getLastMessagePreview());
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        messageLabel.setForeground(TEXT_SECONDARY);

        final JPanel textPanel = new JPanel(new BorderLayout(0, 4));
        textPanel.setOpaque(false);
        textPanel.add(headerRow, BorderLayout.NORTH);
        textPanel.add(messageLabel, BorderLayout.CENTER);

        chatItemPanel.add(avatarBadge, BorderLayout.WEST);
        chatItemPanel.add(textPanel, BorderLayout.CENTER);

        // Hover and Click Listener
        chatItemPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                chatItemPanel.setBackground(CARD_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                chatItemPanel.setBackground(SURFACE_WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                if (chatEntry.getUser1().equals(state.getCurrentUsername())) {
                    enterChatController.execute(chatEntry.getUser1(), chatEntry.getUser2());
                }
                else {
                    enterChatController.execute(chatEntry.getUser2(), chatEntry.getUser1());
                }
            }
        });

        return chatItemPanel;
    }

    private JPanel createAvatarBadge(String username) {
        final String initial = (username != null && !username.isEmpty())
                ? username.substring(0, 1).toUpperCase()
                : "?";

        final JPanel badge = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AVATAR_BG);
                g2.fillOval(0, 0, getWidth(), getHeight());

                g2.setColor(PRIMARY_BLUE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 16));
                final FontMetrics fm = g2.getFontMetrics();
                final int x = (getWidth() - fm.stringWidth(initial)) / 2;
                final int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(initial, x, y);
                g2.dispose();
            }
        };
        badge.setOpaque(false);
        badge.setPreferredSize(new Dimension(42, 42));
        return badge;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ChatListState state = (ChatListState) evt.getNewValue();
        if (state.getAddFriendError() != null) {
            JOptionPane.showMessageDialog(this, state.getAddFriendError());
            state.setAddFriendError(null);
        }
        refreshChatList("");
        chatListViewModel.setState(state);
    }

    public void setAddFriendController(AddFriendController addFriendController) {
        this.addFriendController = addFriendController;
    }

    public void setEnterChatController(EnterChatController enterChatController) {
        this.enterChatController = enterChatController;
    }

    public void setSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public AbstractButton getAddFriendButton() {
        return addFriendButton;
    }

    public void getRefreshChatList() {
    }
}
