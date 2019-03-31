package GUIs;

import DAO.ForumDAO;
import DTO.Post;
import ServerAndClient.ForumClient;
import ServerAndClient.UpdateListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SearchGUI extends JFrame implements ActionListener{

    private int total_btns;
    private BufferedImage myPicture;
    private JLabel picLabel;
    private ArrayList<List> comments;
    private ArrayList<JLabel> titlesLabels;
    private ArrayList<JLabel> topicsLabels;
    private ArrayList<JLabel> usersLabel;
    private ArrayList<JLabel> messagesLabel;
    private ArrayList<JLabel> picsLabel;
    private ArrayList<JButton> buttonsComment;
    private List posts;
    JPanel ssp1, ssp2;

    public SearchGUI(List foundPosts) {
        posts = foundPosts;
        titlesLabels = new ArrayList<>();
        messagesLabel = new ArrayList<>();
        topicsLabels = new ArrayList<>();
        usersLabel = new ArrayList<>();
        picsLabel = new ArrayList<>();
        buttonsComment = new ArrayList<>();
        comments = new ArrayList<List>();
        total_btns = foundPosts.size();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 600;
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("Search Results");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAutoscrolls(true);
        panel.setBackground(Color.GREEN);
        this.add(panel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 600, 550);

        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(675, 600));
        contentPane.add(scrollPane);
        contentPane.setBackground(Color.lightGray);

        for (int i = 0; i < foundPosts.size(); i++) {
            System.out.println("ENTRA AL FOR");
            Post p1 = (Post) foundPosts.get(i);
            JPanel sp1 = new JPanel(); //Post
            sp1.setLayout(new BoxLayout(sp1, BoxLayout.X_AXIS));
            sp1.setBackground(Color.blue);
            sp1.setPreferredSize(new Dimension(550, 200));

            ssp1 = new JPanel(); //Cuadros y labels
            //ssp1.setLayout(new FlowLayout());
            ssp1.setBackground(new Color(51, 153, 255));
            ssp1.setPreferredSize(new Dimension(100, 200));

            ssp2 = new JPanel(); //Botones
            ssp2.setLayout(new FlowLayout());
            ssp2.setBackground(Color.WHITE);
            ssp2.setPreferredSize(new Dimension(350, 170));

            ssp2.setBackground(new Color(51, 153, 255));
            ssp2.setPreferredSize(new Dimension(300, 200));

            createComponent(p1);
            Post pactual = (Post) foundPosts.get(i);
            comments.add(pactual.getComments());

            ssp1.add(titlesLabels.get(i));
            ssp1.add(topicsLabels.get(i));
            ssp1.add(usersLabel.get(i));
            ssp1.add(messagesLabel.get(i));

            ssp2.add(picsLabel.get(i));
            ssp2.add(buttonsComment.get(i));

            sp1.add(ssp1);
            sp1.add(ssp2);
            panel.add(sp1);
        }
        this.add(contentPane, BorderLayout.CENTER);
        
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void createComponent(Post p1) {
        JLabel pTitle = new JLabel(p1.getTitle());
        pTitle.setForeground(Color.BLACK);
        pTitle.setFont(new Font("Times New Roman", Font.BOLD, 12));
        pTitle.setPreferredSize(new Dimension(100, 20));
        titlesLabels.add(pTitle);

        JLabel pTopic = new JLabel(p1.getTopic());
        pTopic.setForeground(Color.BLACK);
        pTopic.setFont(new Font("Times New Roman", Font.BOLD, 12));
        pTopic.setPreferredSize(new Dimension(100, 20));
        topicsLabels.add(pTopic);

        JLabel pUser = new JLabel(p1.getUser());
        pUser.setForeground(Color.BLACK);
        pUser.setFont(new Font("Times New Roman", Font.BOLD, 12));
        pUser.setPreferredSize(new Dimension(100, 20));
        usersLabel.add(pUser);

        JLabel pMessage = new JLabel(p1.getMessage());
        pMessage.setForeground(Color.BLACK);
        pMessage.setFont(new Font("Times New Roman", Font.BOLD, 12));
        pMessage.setPreferredSize(new Dimension(100, 20));
        messagesLabel.add(pMessage);

        try {
            myPicture = ImageIO.read(new File(p1.getPath_img()));
        } catch (IOException ex) {
            Logger.getLogger(ClientMainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        picLabel = new JLabel(new ImageIcon(new ImageIcon(myPicture).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
        picsLabel.add(picLabel);

        JButton btnactual = new JButton("Comments ");
        btnactual.setPreferredSize(new Dimension(100, 20));
        btnactual.addActionListener(this);
        buttonsComment.add(btnactual);

        //ssp1.repaint();
        //ssp2.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < total_btns; i++) {
            if(e.getSource() == buttonsComment.get(i)) {
                ForumDAO fd = new ForumDAO();
                try {
                    comments.set(i, fd.getComments((Post)(posts.get(i))));
                } catch (SQLException ex) {
                    Logger.getLogger(ClientMainGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Post post = (Post)posts.get(i);
                //System.out.println(post.toString());
                
                new CommentGUI(comments.get(i), post.getIdpost(), "L").setVisible(true);
            }
        }

    }
}
