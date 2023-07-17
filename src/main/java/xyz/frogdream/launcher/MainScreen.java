package xyz.frogdream.launcher;

import xyz.frogdream.launcher.downloader.Download;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Desktop;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MainScreen extends JFrame {
    MainScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        int frameWidth = getWidth();
        int frameHeight = getHeight();

        int x = (screenWidth - frameWidth) / 7;
        int y = (screenHeight - frameHeight) / 7;

        setLocation(x, y);
    }

    public static class MainScreenInitializer {
        public static void initialize(MainScreen mainScreen, String enteredNickname) {

            mainScreen.setDefaultCloseOperation(EXIT_ON_CLOSE);
            mainScreen.setTitle("Frogdream Launcher");
            mainScreen.setSize(1022, 589);
            mainScreen.getContentPane().setBackground(new Color(12, 12, 12));
            mainScreen.setLayout(null);
            mainScreen.setResizable(false);

            JLabel launcherText = new JLabel("Launcher");
            launcherText.setForeground(new Color(154, 189, 57));

            InputStream is = FrogdreamLauncher.class.getResourceAsStream("/Fonts/GolosText-Bold.ttf");

            Font font;
            try {
                assert is != null;
                font = Font.createFont(Font.TRUETYPE_FONT, is);
            } catch (FontFormatException | IOException e) {
                throw new RuntimeException(e);
            }

            // x: 0 y: -290 is the corner of FD Launcher

            // Logo
            ImageIcon logo = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/smallLogo.png")));
            JLabel logoLabel = new JLabel(logo);
            logoLabel.setBounds(145, 20, 75, 75);
            mainScreen.add(logoLabel);

            // Font
            Font sizedFont = font.deriveFont(26f);
            launcherText.setFont(sizedFont);
            launcherText.setBounds(243, -290 + 45, 2000, 600);
            mainScreen.add(launcherText);

            // Change nickname icon
            ImageIcon changeNicknameIcon = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/changeNickname.png")));
            JLabel changeNicknameIconLabel = new JLabel(changeNicknameIcon);
            Dimension changeNicknameIconSize = changeNicknameIconLabel.getPreferredSize();
            changeNicknameIconLabel.setBounds(443, 147, changeNicknameIconSize.width, changeNicknameIconSize.height);

            changeNicknameIconLabel.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    mainScreen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    changeNicknameIconLabel.setIcon(getBrighterIcon(changeNicknameIcon, 1.4f));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    mainScreen.setCursor(Cursor.getDefaultCursor());
                    changeNicknameIconLabel.setIcon(changeNicknameIcon);
                }
                @Override
                public void mouseClicked(MouseEvent e) {

                    FrogdreamLauncher launcher = new FrogdreamLauncher();
                    launcher.center();
                    launcher.main(new String[]{});
                    mainScreen.dispose();
                }
            });

            mainScreen.add(changeNicknameIconLabel);

            // Head
            try {
                URL headURL = new URL("https://new.frogdream.xyz/getUserHead/" + enteredNickname + ".png");
                Image headImage = ImageIO.read(headURL);

                // Scaling
                headImage = headImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                ImageIcon head = new ImageIcon(headImage);
                JLabel headLabel = new JLabel(head);
                headLabel.setBounds(43, 589 / 2 - 154, 32, 32);
                mainScreen.add(headLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Nickname
            InputStream is2 = FrogdreamLauncher.class.getResourceAsStream("/Fonts/GolosText-Medium.ttf");
            Color defaultTextColor = new Color(99, 99, 99);
            Font font2;
            try {
                assert is2 != null;
                font2 = Font.createFont(Font.TRUETYPE_FONT, is2);
            } catch (FontFormatException | IOException e) {
                throw new RuntimeException(e);
            }

            JLabel nicknameLabel = new JLabel(enteredNickname);
            nicknameLabel.setForeground(defaultTextColor);
            Font sizedFontOfNickname = font2.deriveFont(16f);
            nicknameLabel.setFont(sizedFontOfNickname);
            nicknameLabel.setBounds(90, 148, 500, 16);

            mainScreen.add(nicknameLabel);

            // PlayText & Font for it
            Color defaultTextColor2 = new Color(255, 255, 255);

            InputStream is3 = FrogdreamLauncher.class.getResourceAsStream("/Fonts/GolosText-Medium.ttf");

            Font font3;
            try {
                assert is3 != null;
                font3 = Font.createFont(Font.TRUETYPE_FONT, is3);
            } catch (FontFormatException | IOException e) {
                throw new RuntimeException(e);
            }

            JLabel playTextLabel = new JLabel("грать");
            playTextLabel.setForeground(defaultTextColor2);
            Font sizedFontForPlayText = font3.deriveFont(16f);
            playTextLabel.setFont(sizedFontForPlayText);
            playTextLabel.setBounds(298, 188, 100, 100);
            mainScreen.add(playTextLabel);

            // Go 2
            ImageIcon go2 = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/go.png")));
            JLabel go2Label = new JLabel(go2);
            go2Label.setBounds(960, 243, 10, 18);
            mainScreen.add(go2Label);

            // Go 3
            ImageIcon go3 = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/go.png")));
            JLabel go3Label = new JLabel(go3);
            go3Label.setBounds(960, 508, 10, 18);
            mainScreen.add(go3Label);

            // News text
            JLabel newsTextLabel = new JLabel("Последние новости");
            newsTextLabel.setForeground(defaultTextColor2);
            Font sizedFontNewsTextLabel = font3.deriveFont(16f);
            newsTextLabel.setFont(sizedFontNewsTextLabel);
            newsTextLabel.setBounds(793, 200, 1000, 100);
            mainScreen.add(newsTextLabel);

            // Map text
            JLabel mapTextLabel = new JLabel("Карта");
            mapTextLabel.setForeground(defaultTextColor2);
            Font sizedMapTextLabel = font3.deriveFont(16f);
            mapTextLabel.setFont(sizedMapTextLabel);
            mapTextLabel.setBounds(902, 508, 1000, 16);
            mainScreen.add(mapTextLabel);

            // Rectangle
            ImageIcon rectangle = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/rectangle.png")));
            JLabel rectangleLabel = new JLabel(rectangle);
            Dimension rectangleSize = rectangleLabel.getPreferredSize();
            rectangleLabel.setBounds(28, 131, rectangleSize.width, rectangleSize.height);
            mainScreen.add(rectangleLabel);

            // Divider
            ImageIcon divider = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/divider.png")));
            JLabel dividerLabel = new JLabel(divider);
            Dimension dividerSize = dividerLabel.getPreferredSize();
            dividerLabel.setBounds(512, 0, dividerSize.width, dividerSize.height);
            mainScreen.add(dividerLabel);

            // News
            ImageIcon news = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/news.png")));
            JLabel newsLabel = new JLabel(news);
            newsLabel.setBounds(542, 32 - 8, 449, 250);

            animation2(go2Label, newsTextLabel, newsLabel);

            newsLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    mainScreen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    mainScreen.setCursor(Cursor.getDefaultCursor());
                }
            });

            mainScreen.add(newsLabel);

            // Map
            ImageIcon map = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/map.png")));
            JLabel mapLabel = new JLabel(map);
            mapLabel.setBounds(542, 315 - 32 + 8, 449, 250);
            mainScreen.add(mapLabel);

            animation(go3Label, mapTextLabel, mapLabel);

            mapLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    mainScreen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    mainScreen.setCursor(Cursor.getDefaultCursor());
                }
            });

            // Folder
            ImageIcon folder = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/folder.png")));
            JLabel folderLabel = new JLabel(folder);
            folderLabel.setBounds(31, 517, 24, 24);
            mainScreen.add(folderLabel);

            folderLabel.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    folderLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    folderLabel.setIcon(getBrighterIcon(folder, 1.4f));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    folderLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    folderLabel.setIcon(folder);
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    openDownloadsFolder();
                }
            });

            mainScreen.setVisible(true);

            // Wiki
            ImageIcon wiki = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/wiki.png")));
            JLabel wikiLabel = new JLabel(wiki);
            wikiLabel.setBounds(66, 517, 24, 24);
            mainScreen.add(wikiLabel);

            wikiLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    wikiLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    wikiLabel.setIcon(getBrighterIcon(wiki, 1.4f));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    wikiLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    wikiLabel.setIcon(wiki);
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://wiki.frogdream.xyz"));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Update launcher
            ImageIcon updateLauncher = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/updateLauncher.png")));
            JLabel updateLauncherLabel = new JLabel(updateLauncher);
            updateLauncherLabel.setBounds(99, 517, 24, 24);
            mainScreen.add(updateLauncherLabel);

            updateLauncherLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    updateLauncherLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    updateLauncherLabel.setIcon(getBrighterIcon(updateLauncher, 1.4f));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    updateLauncherLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    updateLauncherLabel.setIcon(updateLauncher);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://frogdream.xyz/updatelauncher"));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Premium
            ImageIcon premium = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/premium.png")));
            JLabel premiumLabel = new JLabel(premium);
            premiumLabel.setBounds(133, 517, 24, 24);
            mainScreen.add(premiumLabel);

            premiumLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    premiumLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    premiumLabel.setIcon(getBrighterIcon(premium, 1.4f));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    premiumLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    premiumLabel.setIcon(premium);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://frogdream.xyz/premium"));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Friend
            ImageIcon friend = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/friend.png")));
            JLabel friendLabel = new JLabel(friend);
            friendLabel.setBounds(167, 517, 24, 24);
            mainScreen.add(friendLabel);

            friendLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    friendLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    friendLabel.setIcon(getBrighterIcon(friend, 1.4f));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    friendLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    friendLabel.setIcon(friend);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://frogdream.xyz/friend"));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Go
            ImageIcon go = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/go.png")));
            JLabel goLabel = new JLabel(go);
            goLabel.setBounds(451, 229, 10, 18);
            mainScreen.add(goLabel);

            // Play button
            ImageIcon play = new ImageIcon(Objects.requireNonNull(MainScreenInitializer.class.getResource("/Images/play.png")));
            JLabel playLabel = new JLabel(play);
            playLabel.setBounds(279, 213, 198, 50);
            mainScreen.add(playLabel);

            playLabel.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    mainScreen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    playLabel.setIcon(getBrighterIcon(play, 1.1f));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    mainScreen.setCursor(Cursor.getDefaultCursor());
                    playLabel.setIcon(play);
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO: Starting minecraft client
                    Download.launch(enteredNickname);
                }
            });

        }
        private static void animation(JLabel go3Label, JLabel mapTextLabel, JLabel mapLabel) {
            int initialMapLabelY = mapLabel.getY();
            int initialMapTextLabelY = mapTextLabel.getY();
            int initialGo3LabelY = go3Label.getY();

            int animationDuration = 500;
            int animationSteps = 20;
            int animationDelay = animationDuration / animationSteps; // Delay
            int maxDeltaY = 5;

            final Timer[] timer = {null};
            final int[] deltaY = {0};

            mapLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://map.frogdream.xyz"));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (timer[0] != null && e.getSource() == mapLabel) {
                        timer[0].stop();
                        mapLabel.setLocation(mapLabel.getX(), initialMapLabelY);
                        mapTextLabel.setLocation(mapTextLabel.getX(), initialMapTextLabelY);
                        go3Label.setLocation(go3Label.getX(), initialGo3LabelY);
                        deltaY[0] = 0; // Reset
                        mapLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }
                }
            });

            mapLabel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    mapLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    if (timer[0] != null && timer[0].isRunning()) {
                        timer[0].restart(); // Reset
                    } else {
                        timer[0] = new Timer(animationDelay, actionEvent -> {
                            if (deltaY[0] >= maxDeltaY) {
                                timer[0].stop();
                                return;
                            }
                            mapLabel.setLocation(mapLabel.getX(), mapLabel.getY() - 1);
                            mapTextLabel.setLocation(mapTextLabel.getX(), mapTextLabel.getY() - 1);
                            go3Label.setLocation(go3Label.getX(), go3Label.getY() - 1);
                            deltaY[0]++;
                        });
                        timer[0].setInitialDelay(100);
                        timer[0].start();
                    }
                }
            });
        }

        private static void animation2(JLabel go2Label, JLabel newsTextLabel, JLabel newsLabel) {
            int initialNewsLabelY = newsLabel.getY();
            int initialNewsTextLabelY = newsTextLabel.getY();
            int initialGo2LabelY = go2Label.getY();

            int animationDuration = 500;
            int animationSteps = 20;
            int animationDelay = animationDuration / animationSteps; // Delay
            int maxDeltaY = 5;

            final Timer[] timer = {null};
            final int[] deltaY = {0};

            newsLabel.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseExited(MouseEvent e) {
                    if (timer[0] != null && e.getSource() == newsLabel) {
                        timer[0].stop();
                        newsLabel.setLocation(newsLabel.getX(), initialNewsLabelY);
                        newsTextLabel.setLocation(newsTextLabel.getX(), initialNewsTextLabelY);
                        go2Label.setLocation(go2Label.getX(), initialGo2LabelY);
                        deltaY[0] = 0; // Reset
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://vk.com/frogdream"));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            newsLabel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    if (timer[0] != null && timer[0].isRunning()) {
                        timer[0].restart(); // Reset
                    } else {
                        timer[0] = new Timer(animationDelay, actionEvent -> {
                            if (deltaY[0] >= maxDeltaY) {
                                timer[0].stop();
                                return;
                            }
                            newsLabel.setLocation(newsLabel.getX(), newsLabel.getY() - 1);
                            newsTextLabel.setLocation(newsTextLabel.getX(), newsTextLabel.getY() - 1);
                            go2Label.setLocation(go2Label.getX(), go2Label.getY() - 1);
                            deltaY[0]++;
                        });
                        timer[0].setInitialDelay(100);
                        timer[0].start();
                    }
                }
            });
        }
    }

    private static ImageIcon getBrighterIcon(ImageIcon icon, float brightness) {
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

                r = Math.min((int) (r * brightness), 255);
                g = Math.min((int) (g * brightness), 255);
                b = Math.min((int) (b * brightness), 255);

                bufferedImage.setRGB(x, y, (rgb & 0xFF000000) | (r << 16) | (g << 8) | b);
            }
        }

        return new ImageIcon(bufferedImage);
    }

    private static void openDownloadsFolder() {
        String osName = System.getProperty("os.name").toLowerCase();
        String downloadsPath;

        if (osName.contains("win")) {
            downloadsPath = System.getProperty("user.home") + "\\Downloads";
        } else if (osName.contains("mac")) {
            downloadsPath = System.getProperty("user.home") + "/Downloads";
        } else if (osName.contains("linux")) {
            downloadsPath = System.getProperty("user.home") + "/Downloads";
        } else {
            JOptionPane.showMessageDialog(null, "Ваша система не поддерживает открытие папки с игрой. \nПожалуйста, откройте баг-репорт и напишите, какую систему вы используете.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Desktop.getDesktop().open(new File(downloadsPath));
        } catch (IOException e) {
        }
    }
}