package connectfour;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class main {

    static int[][] board = new int[6][7];
    static connect4 panel = new connect4();
    static boolean turn = true;

    /*
        main function
    */
    public static void main(String[] args) {
        buildGUI();
    }
    
    /*
        buildGUI builds the game board. It has 7 button that drop a piece into the 7x6
        gameboard.
    */
    public static void buildGUI() {
        JFrame frame = new JFrame();
        JPanel bPanel = new JPanel();
        JButton[] buttons = new JButton[7];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("Drop");
            buttons[i].addActionListener(new drop(i));
            bPanel.add(buttons[i]);
            bPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        }

        frame.setSize(717, 700);

        frame.add(panel);
        frame.add(bPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /*
        ActionListener for when a button is pressed. That button
        calls dropPiece to change the board. The board is then
        updated.
    */
    private static class drop implements ActionListener {

        private int col;

        public drop(int col) {
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dropPiece(col);
            panel.update();
            if (win()) {
                winPopUp();
            }

        }

    }
    
    /*
        Displays a message that says "You Win"
    */
    public static void winPopUp() {
        JOptionPane.showMessageDialog(null, "You Win", "Congrats!", JOptionPane.INFORMATION_MESSAGE);
    }
    

    /*
        Drops a piece in the chosen column. If it cannot be placed, the turn does 
        not change and no piece is dropped.
    */
    public static void dropPiece(int column) {
        int val;
        if (turn == true) {
            val = 1;
        } else {
            val = 2;
        }
        for (int i = board.length - 1; i >= 0; i--) {
            if (board[i][column] == 0) {
                board[i][column] = val;
                turn = !turn;
                return;
            }
        }
        System.out.println("Piece could not be put there.");
    }

    /*
        Checks whether a horizontal, vertical, or diagonal win occured
     */
    public static boolean win() {
        return horiWin() || vertWin() || diagWin();
    }

    /*
        checks if a horizontal win occured
    */
    public static boolean horiWin() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (c + 3 < board[0].length) {
                    if (board[r][c] == board[r][c + 1]
                            && board[r][c] == board[r][c + 2]
                            && board[r][c] == board[r][c + 3]
                            && board[r][c] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
        Checks if a vertical win occured
    */
    public static boolean vertWin() {
        for (int c = 0; c < board[0].length; c++) {
            for (int r = 0; r < board.length; r++) {
                if (r + 3 < board.length) {
                    if (board[r][c] == board[r + 1][c]
                            && board[r + 2][c] == board[r][c]
                            && board[r + 3][c] == board[r][c]
                            && board[r][c] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
        Checks if a diagonal win occured
    */
    public static boolean diagWin() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (c + 3 < board[0].length
                        && r + 3 < board.length) {
                    if (board[r][c] == board[r + 1][c + 1]
                            && board[r][c] == board[r + 2][c + 2]
                            && board[r][c] == board[r + 3][c + 3]
                            && board[r][c] != 0) {
                        return true;
                    }
                }
                if(c+3 < board[0].length
                        && r - 3 >= 0){
                    if (board[r][c] == board[r - 1][c+1]
                            && board[r][c] == board[r-2][c+2]
                            && board[r][c] == board[r-3][c+3]
                            && board[r][c] != 0){
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    /*
        Creates the gui for the connect4 panel and updates it.
     */
    private static class connect4 extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            //Horizontal Lines
            g.drawLine(0, 0, 700, 0);
            g.drawLine(0, 100, 700, 100);
            g.drawLine(0, 200, 700, 200);
            g.drawLine(0, 300, 700, 300);
            g.drawLine(0, 400, 700, 400);
            g.drawLine(0, 500, 700, 500);
            g.drawLine(0, 600, 700, 600);

            //Vertical Lines
            g.drawLine(0, 0, 0, 600);
            g.drawLine(100, 0, 100, 600);
            g.drawLine(200, 0, 200, 600);
            g.drawLine(300, 0, 300, 600);
            g.drawLine(400, 0, 400, 600);
            g.drawLine(500, 0, 500, 600);
            g.drawLine(600, 0, 600, 600);
            g.drawLine(700, 0, 700, 600);

            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[0].length; c++) {
                    if (board[r][c] == 1) {
                        g.setColor(Color.red);
                    }
                    if (board[r][c] == 2) {
                        g.setColor(Color.black);
                    }
                    if (board[r][c] != 0) {
                        g.drawOval((c * 100), (r * 100), 100, 100);
                    }
                }
            }
        }
        
        /*
            update calls repaint()
        */
        public void update() {
            repaint();
        }
    }

}
