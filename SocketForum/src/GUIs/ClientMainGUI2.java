package GUIs;

import DTO.Post;
import ServerAndClient.ForumClient;
import ServerAndClient.UpdateListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.List;

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

        JLabel Title = new JLabel("Socket Forum. User: " + user);
        Title.setFont(new Font("Serif", Font.BOLD, 30));
        Title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAutoscrolls(true);
        panel.setBackground(Color.GREEN);
        frame.add(panel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 600, 550);

        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(675, 600));
        contentPane.add(scrollPane);
        contentPane.setBackground(Color.lightGray);

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
        List posts = getAllPost();
        // unique client instance
        fc = new ForumClient();
        for (int i = 0; i < posts.size(); i++) {
            Post p1 = (Post) posts.get(i);
            JPanel sp1 = new JPanel(); //Post
            sp1.setLayout(new BoxLayout(sp1, BoxLayout.X_AXIS));
            sp1.setBackground(Color.blue);
            sp1.setPreferredSize(new Dimension(550, 200));

            JPanel ssp1 = new JPanel(); //Cuadros y labels
            //ssp1.setLayout(new FlowLayout());
            ssp1.setBackground(new Color(51, 153, 255));
            ssp1.setPreferredSize(new Dimension(100, 200));

            JPanel ssp2 = new JPanel(); //Botones
            //ssp2.setLayout(new FlowLayout());
            ssp2.setBackground(new Color(51, 153, 255));
            ssp2.setPreferredSize(new Dimension(300, 200));

            JLabel pTitle = new JLabel(p1.getTitle());
            pTitle.setForeground(Color.BLACK);
            pTitle.setFont(new Font("Times New Roman", Font.BOLD, 12));
            pTitle.setPreferredSize(new Dimension(100, 20));

            JLabel pTopic = new JLabel(p1.getTopic());
            pTopic.setForeground(Color.BLACK);
            pTopic.setFont(new Font("Times New Roman", Font.BOLD, 12));
            pTopic.setPreferredSize(new Dimension(100, 20));

            JLabel pUser = new JLabel(p1.getUser());
            pUser.setForeground(Color.BLACK);
            pUser.setFont(new Font("Times New Roman", Font.BOLD, 12));
            pUser.setPreferredSize(new Dimension(100, 20));

            JLabel pMessage = new JLabel(p1.getMessage());
            pMessage.setForeground(Color.BLACK);
            pMessage.setFont(new Font("Times New Roman", Font.BOLD, 12));
            pMessage.setPreferredSize(new Dimension(100, 20));

            /*JLabel pDate = new JLabel(p1.getDate());
            pDate.setForeground(Color.BLACK);
            pDate.setFont(new Font("Times New Roman", Font.BOLD, 12));
            pDate.setPreferredSize(new Dimension(100, 20));*/
            JButton btnComments = new JButton("Comments");
            btnComments.setPreferredSize(new Dimension(100, 20));

            ssp1.add(pTitle);
            ssp1.add(pTopic);
            ssp1.add(pUser);
            ssp1.add(pMessage);
            //ssp1.add(pDate);

            ssp2.add(btnComments);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonDe1) { // New Post
            CreatePostGUI cpg = new CreatePostGUI(user, this);
            this.setVisible(false);
            cpg.setVisible(true);
        } else if (e.getSource() == botonDe2) { //Log Out
            Login l = new Login();
            l.setVisible(true);
            System.exit(0);
            this.dispose();
        }
    }

    public List getAllPost() {
        ForumClient fc = new ForumClient();
        List l = null;
        l = fc.getAllPost();
        if (l != null) {
            for (int i = 0; i < l.size(); i++) {
                Post p = (Post) l.get(i);
                System.out.println("\n" + p.toString());
            }
        } else {
            System.err.println("NULL POSTS");
        }
        return l;
    }

}
