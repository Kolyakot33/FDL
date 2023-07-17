package xyz.frogdream.launcher;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.Timer;
import java.util.TimerTask;

public class ImageApp {
    public static void main() {

        {

            JFrame frame = new JFrame("AHAHAHAHAHAHHAHAHAHAHHAHAHAHA");
            frame.getContentPane().setLayout(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1284, 982);

            ImageIcon icon = new ImageIcon("/Images/AHAHAHAHA.png");
            JLabel label = new JLabel(icon);
            label.setBounds(0, 0,1284, 982);

            frame.add(label);
            frame.getContentPane().add(label);

            frame.setVisible(true);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    frame.dispose();
                    System.exit(0);
                }
            }, 5000);
        }
    }
}