package GUIs;

import DAO.ForumDAO;
import DTO.Comment;
import DTO.Post;
import ServerAndClient.ForumClient;
import ServerAndClient.UpdateListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ClientMainGUI extends JFrame implements ActionListener {


    private static JButton botonDe1, botonDe2, searchButton;
    private static JTextField searchTextField;
    private JButton btnComments[];
    BufferedImage myPicture;
    JLabel picLabel;

    private String user;
    private ForumClient fc;
    private int listenerPort = 65535;
    private ArrayList<Integer> postWithImage;

    private List allPosts = null;
    private int total_btns;
    private List comments[];
    private List posts;
    private String current_user;

    public ClientMainGUI(String user) {
        current_user = user;
        JFrame frame = new JFrame();
        postWithImage = new ArrayList<>();
        total_btns = 0;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(frame);
            ForumClient fc = new ForumClient();
            UpdateListener ul = new UpdateListener(this, listenerPort);
            Thread t = new Thread(ul);
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
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
        searchButton = new JButton("Search");
        searchButton.setSize(100, 30);
        searchButton.addActionListener(this);
        botonDe1 = new JButton("Create New Post");
        botonDe1.setSize(100, 30);
        botonDe1.addActionListener(this);
        botonDe2 = new JButton("Log Out");
        botonDe2.setSize(100, 30);
        botonDe2.addActionListener(this);
        
        
        //searchTextField = new JTextField();
        //searchTextField.setSize(100, 30);
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.add(botonDe1);
        panelDerecho.add(botonDe2);
        panelDerecho.add(searchButton);
        //panelDerecho.add(searchTextField);

        // We call this method to obtain posts
        posts = getAllPost();
        // unique client instance
        fc = new ForumClient();
        
        if (posts != null){

            total_btns = posts.size();
            comments = new List[total_btns];
            btnComments = new JButton[posts.size()];
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
                ssp2.setLayout(new FlowLayout());
                ssp2.setBackground(Color.WHITE);
                ssp2.setPreferredSize(new Dimension(350, 170));
             
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
                btnComments[i] = new JButton("Comments ");
                comments[i] = ((Post)posts.get(i)).getComments();
                btnComments[i].setPreferredSize(new Dimension(100, 20));
                btnComments[i].addActionListener(this);
                ssp1.add(pTitle);
                ssp1.add(pTopic);
                ssp1.add(pUser);
                ssp1.add(pMessage);
                //ssp1.add(pDate);
                try {
                    myPicture = ImageIO.read(new File(p1.getPath_img()));
                } catch (IOException ex) {
                    Logger.getLogger(ClientMainGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                picLabel = new JLabel(new ImageIcon(new ImageIcon(myPicture).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
                ssp2.add(picLabel);
                ssp2.add(btnComments[i]);

                sp1.add(ssp1); //Cuadros y labels
                sp1.add(ssp2); //Botones
                panel.add(sp1);

            }
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
        } else if (e.getSource() == searchButton){
            String query = JOptionPane.showInputDialog("Search");
            if (query.equals("") || query == null){
                System.out.println("CANT SEARCH");
            }
            else {
                List foundPosts = search(query);
                if (foundPosts != null){
                    for (int i = 0; i < foundPosts.size(); i++) {
                        Post p = (Post) foundPosts.get(i);
                        System.out.println(p.toString());
                    }
                    // Usar los posts encontrado para mostrarlos
                } else {
                    System.out.println("NO RESULTS FOUND");
                }   
            }
        }
        for(int i = 0; i < total_btns; i++) {
            if(e.getSource() == btnComments[i]) {
                ForumDAO fd = new ForumDAO();
                try {
                    comments[i] = fd.getComments((Post)(posts.get(i)));
                } catch (SQLException ex) {
                    Logger.getLogger(ClientMainGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Post post = (Post)posts.get(i);
                System.out.println(post.toString());
                
                new CommentGUI(comments[i], post.getIdpost(), current_user).setVisible(true);
            }
        }
        
    }

    public List getAllPost() {
        ForumClient fc = new ForumClient();
        List l = null;
        l = fc.getAllPost();
        if (l != null) {
            allPosts = l;
            for (int i = 0; i < l.size(); i++) {
                Post p = (Post) l.get(i);
                System.out.println("\n" + p.toString());
                if (postWithImage.contains(p.getIdpost())){
                    continue;
                }
                else if (p.getPath_img() != null){
                    fc.getPostImage(p);
                    postWithImage.add(p.getIdpost());
                }
                
            }
        } else {
            System.err.println("NULL POSTS");
        }
        return l;
    }

    public ArrayList search (String keyword){
        ArrayList foundPosts = null;
        if (allPosts != null){
        foundPosts = new ArrayList();
            for (int i = 0; i < allPosts.size(); i++) {
                Post p = (Post) allPosts.get(i);
                if (p.getDate().toString().equals(keyword) || p.getTopic().equals(keyword)) {
                    foundPosts.add(p);
                }
            }
        }
        return foundPosts;
    }
}
