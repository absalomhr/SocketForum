package GUIs;

import DTO.Post;
import ServerAndClient.ForumClient;
import ServerAndClient.UpdateListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClientMainGUI2 extends JFrame implements ActionListener {

    private static JButton botonDe1, botonDe2;
    private String user;
    private ForumClient fc;
    private int listenerPort = 65535;

    public ClientMainGUI2(String user) {
        JFrame frame = new JFrame();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(frame);
            ForumClient fc = new ForumClient();
            UpdateListener ul = new UpdateListener(this, listenerPort);
            Thread t = new Thread(ul);
            t.start();
        } catch (Exception e) {
        }

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 600;

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Socket Forum");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JLabel Title = new JLabel("Socket Forum");
        Title.setFont(new Font("Serif", Font.BOLD, 30));
        Title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAutoscrolls(true);
        frame.add(panel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 600, 550);

        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(675, 600));
        contentPane.add(scrollPane);

        JPanel panelDerecho = new JPanel();
        botonDe1 = new JButton("Create New Post");
        botonDe1.setSize(100, 30);
        botonDe1.addActionListener(this);
        botonDe2 = new JButton("Log Out");
        botonDe2.setSize(100, 30);
        botonDe2.addActionListener(this);
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.add(botonDe1);
        panelDerecho.add(botonDe2);

        // We call this method to obtain posts
        getAllPost();
        // unique client instance
        fc = new ForumClient();
        for (int i = 0; i < 2; i++) {
            JPanel sp1 = new JPanel(); //Post
            sp1.setLayout(new FlowLayout());
            sp1.setBackground(Color.WHITE);
            sp1.setPreferredSize(new Dimension(550, 300));

            JPanel ssp1 = new JPanel(); //Cuadros y labels
            ssp1.setLayout(new FlowLayout());
            ssp1.setBackground(Color.WHITE);
            ssp1.setPreferredSize(new Dimension(500, 170));

            JPanel ssp2 = new JPanel(); //Botones
            ssp2.setLayout(new FlowLayout());
            ssp2.setBackground(Color.WHITE);
            ssp2.setPreferredSize(new Dimension(350, 170));

            JLabel l3 = new JLabel("Title: ");
            l3.setForeground(Color.BLACK);
            l3.setPreferredSize(new Dimension(100, 20));
            JTextField t1 = new JTextField("Electronic Basics");
            t1.setPreferredSize(new Dimension(320, 20));

            JLabel l4 = new JLabel("Type: ");
            l4.setForeground(Color.BLACK);
            l4.setPreferredSize(new Dimension(100, 20));
            JTextField t2 = new JTextField("Book");
            t2.setPreferredSize(new Dimension(320, 20));

            JLabel l5 = new JLabel("Authors: ");
            l5.setForeground(Color.BLACK);
            l5.setPreferredSize(new Dimension(100, 20));
            JTextField t3 = new JTextField("Bob the Builder");
            t3.setPreferredSize(new Dimension(320, 20));

            JLabel l6 = new JLabel("Publisher: ");
            l6.setForeground(Color.BLACK);
            l6.setPreferredSize(new Dimension(100, 20));
            JTextField t4 = new JTextField("ABC Company");
            t4.setPreferredSize(new Dimension(320, 20));

            JLabel l7 = new JLabel("Location: ");
            l7.setForeground(Color.BLACK);
            l7.setPreferredSize(new Dimension(100, 20));
            JTextField t5 = new JTextField("Shelf 1 Row 3");
            t5.setPreferredSize(new Dimension(320, 20));

            JLabel l8 = new JLabel("Status: ");
            l8.setForeground(Color.BLACK);
            l8.setPreferredSize(new Dimension(100, 20));
            JTextField t6 = new JTextField("Available");
            t6.setPreferredSize(new Dimension(320, 20));

            JButton btnLoanHistory = new JButton("Loan History");
            btnLoanHistory.setPreferredSize(new Dimension(300, 20));
            JButton btnLoanItem = new JButton("Loan Item");
            btnLoanItem.setPreferredSize(new Dimension(300, 20));
            JButton btnProcessReturn = new JButton("Process Return");
            btnProcessReturn.setPreferredSize(new Dimension(300, 20));

            ssp1.add(l3);
            ssp1.add(t1);
            ssp1.add(l4);
            ssp1.add(t2);
            ssp1.add(l5);
            ssp1.add(t3);
            ssp1.add(l6);
            ssp1.add(t4);
            ssp1.add(l7);
            ssp1.add(t5);
            ssp1.add(l8);
            ssp1.add(t6);

            ssp2.add(btnLoanHistory);
            ssp2.add(btnLoanItem);
            ssp2.add(btnProcessReturn);

            sp1.add(ssp1); //Cuadros y labels
            sp1.add(ssp2); //Botones
            panel.add(sp1);

        }
        frame.add(panelDerecho, BorderLayout.EAST);
        frame.add(Title, BorderLayout.NORTH);
        frame.add(contentPane, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonDe1) { // New Post
            CreatePostGUI cpg = new CreatePostGUI(user, this);
            this.setVisible(false);
            cpg.setVisible(true);
        } else if (e.getSource() == botonDe2) { //Log Out
            Login l = new Login();
            l.setVisible(true);
            this.dispose();
        }
    }

    public void getAllPost() {
        ForumClient fc = new ForumClient();
        java.util.List l = null;
        l = fc.getAllPost();
        if (l != null) {
            for (int i = 0; i < l.size(); i++) {
                Post p = (Post) l.get(i);
                System.out.println("\n" + p.toString());
            }
        } else {
            System.err.println("NULL POSTS");
        }
    }

}
