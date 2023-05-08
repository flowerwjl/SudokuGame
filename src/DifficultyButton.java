
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class DifficultyButton extends JDialog {

    public DifficultyButton(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel tips = new JLabel("Please Choose Difficulty.");
        //Label settings
        tips.setVisible(true);
        tips.setFont(new Font("Monospaced", Font.BOLD, 18));
        tips.setPreferredSize(new Dimension(400,150));
        tips.setHorizontalAlignment(JLabel.CENTER);
        jPanel.add(tips);

        JButton easy = new JButton("Easy");
        JButton intermediate = new JButton("Intermediate");
        JButton difficult = new JButton("Difficult");
        JButton exitBut = new JButton("EXIT");
        //Button settings
        easy.setPreferredSize(new Dimension(150,42));
        intermediate.setPreferredSize(new Dimension(150,42));
        difficult.setPreferredSize(new Dimension(150,42));
        exitBut.setPreferredSize(new Dimension(72,42));
        easy.setFont(new Font("Monospaced", Font.PLAIN, 18));
        intermediate.setFont(new Font("Monospaced", Font.PLAIN, 16));
        difficult.setFont(new Font("Monospaced", Font.PLAIN, 18));
        exitBut.setFont(new Font("Monospaced", Font.PLAIN, 16));
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Test.setTaskNum(4);
                dispose();
            }
        });
        intermediate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Test.setTaskNum(22);
                dispose();
            }
        });
        difficult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Test.setTaskNum(32);
                dispose();
            }
        });
        exitBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jPanel.add(easy);
        jPanel.add(intermediate);
        jPanel.add(difficult);
        jPanel.add(exitBut);


        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                new warningDialog();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        add(jPanel);
        setSize(500,300);
        setTitle("DIFFICULTY");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setModal(true);
        setVisible(true);
    }

    static class warningDialog extends JDialog implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
        public warningDialog(){
            JLabel tips = new JLabel("Please choose the difficulty.");
            JButton ok = new JButton("OK");

            tips.setVisible(true);
            tips.setBounds(-23,23,400,45);
            tips.setFont(new Font("Monospaced", Font.BOLD, 16));
            tips.setHorizontalAlignment(JLabel.CENTER);

            ok.setBounds(142,126,98,31);
            ok.addActionListener(this);

            add(tips);
            add(ok);
            setLayout(null);
            setTitle("WARNING");
            setResizable(false);
            setSize(400,250);
            setLocationRelativeTo(null);
            setModal(true);
            setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }
}
