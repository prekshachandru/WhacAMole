import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
public class WhacAMole {
    //window creation
    int boardWidth = 600;
    int boardHeight = 650; //50px for the text panel on top
    
    //Window title
    JFrame frame = new JFrame("Mario: Whac A Mole");
    //creating a text for window
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
     //To craete 9 tile
    JButton[] board = new JButton[9];
    //load images
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currMoleTile;
    JButton currPlantTile;

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;
    int score;

    //create constructor
    WhacAMole() 
    {
        //frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.black);
        frame.add(boardPanel);
        //load images
        //plantIcon = new ImageIcon(getClass().getResource("./piranha.png"));
        //to rasize the image
        Image plantImg = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
        
        Image moleImg = new ImageIcon(getClass().getResource("./monty.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        score = 0;
        //To craete 9 tile
        for(int i = 0;i < 9;i++)
        {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            //to add image to the button
            //tile.setIcon(moleIcon);
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    JButton tile = (JButton) e.getSource();
                    if(tile == currMoleTile ){
                        score += 10;
                        textLabel.setText("Score: " + Integer.toString(score));
                    }
                    else if(tile == currPlantTile) {
                        textLabel.setText("GameOver " + Integer.toString(score));
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        for(int i = 0;i<9;i++){
                            board[i].setEnabled(false);
                        }
                    }
                }
            });
            
        }
        setMoleTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //remove mole from current tile
                if(currMoleTile != null) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }
                //randomly select number tile
                int num = random.nextInt(9); //0-8
                JButton tile = board[num];

                
                //if tile is occupied by plant skip tile for this time
                if (currPlantTile == tile) return;

                 //set tile to mole
                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        });

        setPlantTimer = new Timer(1500, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(currPlantTile != null){
                    currPlantTile.setIcon(null); 
                    currPlantTile = null;

            }
            int num = random.nextInt(9);
            JButton tile = board[num];
            
            
            //if tile is occupied by plant skip tile for this time
            if (currMoleTile == tile) return;

            currPlantTile = tile;
            currPlantTile.setIcon(plantIcon);
        }
    });
        setMoleTimer.start();
        setPlantTimer.start();
        frame.setVisible(true);
    }
}
