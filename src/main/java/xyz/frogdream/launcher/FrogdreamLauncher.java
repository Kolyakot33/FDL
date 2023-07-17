package xyz.frogdream.launcher;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class FrogdreamLauncher extends JFrame {
    private static boolean isTextChanged = false;
    private static JLabel enterLabel;
    private static JTextField nickname;
    private static JLabel rectangleLabel;
    private static FrogdreamLauncher display;

    FrogdreamLauncher() {
        center();

        ImageIcon logo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/logo.png")));
        JLabel logoLabel = new JLabel(logo);
        Dimension size = logoLabel.getPreferredSize();
        logoLabel.setBounds(300, 203, size.width, size.height);
        add(logoLabel);
    }

    public void center() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        int x = (screenWidth - frameWidth) / 7;
        int y = (screenHeight - frameHeight) / 7;
        setLocation(x, y);
    }

    public static void main(String[] args) {
        display = new FrogdreamLauncher();
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        display.setTitle("Frogdream Launcher");
        display.setSize(1022, 589);
        display.getContentPane().setBackground(new Color(12, 12, 12));
        display.setLayout(null);

        JLabel launcherText = new JLabel("Launcher");
        launcherText.setForeground(new Color(154, 189, 57));
        InputStream is = FrogdreamLauncher.class.getResourceAsStream("/Fonts/GolosText-Bold.ttf");

        Font font;
        try {
            assert is != null;
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            Font sizedFont = font.deriveFont(56f);
            launcherText.setFont(sizedFont);
            launcherText.setBounds(478, -19, 2000, 600);
            display.add(launcherText);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        nickname = new JTextField();

        InputStream nicknameFontStream = FrogdreamLauncher.class.getResourceAsStream("/Fonts/GolosText-Medium.ttf");
        Font nicknameFont;
        try {
            assert nicknameFontStream != null;
            nicknameFont = Font.createFont(Font.TRUETYPE_FONT, nicknameFontStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(nicknameFont);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        Font sizedNicknameFont = nicknameFont.deriveFont(16f);
        nickname.setFont(sizedNicknameFont);
        nickname.setText("Ник");
        nickname.setBorder(null);
        nickname.setOpaque(false);
        Color defaultTextColor = new Color(100, 101, 101);
        nickname.setForeground(defaultTextColor);
        nickname.setBounds(315, 375, 385, 60);

        nickname.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isTextChanged) {
                    nickname.setText("");
                    isTextChanged = true;
                }
            }
        });

        // Rectangle
        ImageIcon rectangle = new ImageIcon(Objects.requireNonNull(FrogdreamLauncher.class.getResource("/Images/rectangle.png")));

        rectangleLabel = new JLabel(rectangle);
        Dimension rectangleSize = rectangleLabel.getPreferredSize();
        rectangleLabel.setBounds(293, 380, rectangleSize.width, rectangleSize.height);

        // Enter
        ImageIcon enter = new ImageIcon(Objects.requireNonNull(FrogdreamLauncher.class.getResource("/Images/enter.png")));

        enterLabel = new JLabel(enter);
        enterLabel.setBounds(704, 390, 30, 30);

        // Animation
        enterLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                enterLabel.setIcon(getBrighterIcon(enter));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                enterLabel.setIcon(enter);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                performAction();
            }
        });

        KeyListener enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performAction();
                }
            }
        };
        nickname.addKeyListener(enterKeyListener);

        display.add(enterLabel);
        display.add(nickname);
        display.add(rectangleLabel);
        
        display.setVisible(true);
        display.setResizable(false);
    }

    private static void performAction() {
        String enteredNickname = nickname.getText();

        if (enteredNickname.equals("cubelius2") || enteredNickname.equals("Redmor") || enteredNickname.equals("Kolyakot33")) {
            ImageApp.main();
        }

        Database.main(enteredNickname);

        if (Database.statusOfKey) {
            MainScreen mainscreen = new MainScreen();
            MainScreen.MainScreenInitializer.initialize(mainscreen, enteredNickname);
            mainscreen.setVisible(true);
            display.setVisible(false);
        }

        if (Database.statusOfKey) {
            display.dispose();

        } else {
            Color textColorPon = new Color(168, 61, 61);
            Color textColorPon2 = new Color(99, 99, 99);

            nickname.setText("Проходка не найдена");

            Timer timer = new Timer(10, e1 -> {
                if (nickname.getText().equals("Проходка не найдена")) {
                    nickname.setForeground(textColorPon);
                } else {
                    nickname.setForeground(textColorPon2);
                }
            });
            timer.start();
        }
    }

    private static ImageIcon getBrighterIcon(ImageIcon icon) {
        Image img = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(
                img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.drawImage(img, 0, 0, null);
        graphics.dispose();

        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                int rgb = bufferedImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                r = Math.min((int) (r * (float) 1.4), 255);
                g = Math.min((int) (g * (float) 1.4), 255);
                b = Math.min((int) (b * (float) 1.4), 255);

                bufferedImage.setRGB(x, y, (rgb & 0xFF000000) | (r << 16) | (g << 8) | b);
            }
        }

        return new ImageIcon(bufferedImage);
    }
}
