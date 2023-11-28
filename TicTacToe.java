import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe extends JFrame implements ActionListener {
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
    JLabel l1, l2;
    JPanel p1, p2, p3;
    int player = 1;
    int matrix[][] = new int[3][3];

    public TicTacToe() {
        p1 = new JPanel(new GridLayout(3, 3));
        p2 = new JPanel(new GridLayout(1, 1));
        p3 = new JPanel(new GridLayout(1, 1));

        l2 = new JLabel("Tic Tac Toe Game");
        p3.add(l2);
        
        b1 = new Button("");
        b2 = new Button("");
        b3 = new Button("");
        b4 = new Button("");
        b5 = new Button("");
        b6 = new Button("");
        b7 = new Button("");
        b8 = new Button("");
        b9 = new Button("");

        l1 = new JLabel("Player 1 Turn (X)");

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);

        p1.add(b1);
        p1.add(b2);
        p1.add(b3);
        p1.add(b4);
        p1.add(b5);
        p1.add(b6);
        p1.add(b7);
        p1.add(b8);
        p1.add(b9);

        p2.add(l1);

        add(p3);
        add(p1);
        add(p2);

        setLayout(new GridLayout(3, 1));

        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l2.setFont(l2.getFont().deriveFont(24.0f));
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        l1.setFont(l1.getFont().deriveFont(18.0f));
        l1.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        Button clickedButton = (Button) e.getSource();

        if (clickedButton.getLabel().equals("")) {
            if (player == 1) {
                clickedButton.setLabel("X");
                matrix[getButtonRow(clickedButton)][getButtonColumn(clickedButton)] = 1;
                player = 2;
            } else {
                clickedButton.setLabel("O");
                matrix[getButtonRow(clickedButton)][getButtonColumn(clickedButton)] = 2;
                player = 1;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Cannot Mark here", "Warning", JOptionPane.WARNING_MESSAGE);
        }

        int winner = checkWinCond();

        if (winner != 0) {
            String winnerText = (winner == 1) ? "Player 1" : "Player 2";
            JOptionPane.showMessageDialog(this, winnerText + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }

        if (checkFull() && winner == 0) {
            JOptionPane.showMessageDialog(this, "Game Draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }

        if (player == 1) {
            l1.setText("Player 1 Turn (X)");
        } else {
            l1.setText("Player 2 Turn (O)");
        }
    }

    int getButtonRow(Button button) {
        if (button == b1 || button == b2 || button == b3) {
            return 0;
        } else if (button == b4 || button == b5 || button == b6) {
            return 1;
        } else {
            return 2;
        }
    }

    int getButtonColumn(Button button) {
        if (button == b1 || button == b4 || button == b7) {
            return 0;
        } else if (button == b2 || button == b5 || button == b8) {
            return 1;
        } else {
            return 2;
        }
    }

    int checkWinCond() {
        for (int row = 0; row < 3; row++) {
            if (matrix[row][0] == matrix[row][1] && matrix[row][1] == matrix[row][2] && matrix[row][0] != 0) {
                return matrix[row][0];
            }
        }

        for (int col = 0; col < 3; col++) {
            if (matrix[0][col] == matrix[1][col] && matrix[1][col] == matrix[2][col] && matrix[0][col] != 0) {
                return matrix[0][col];
            }
        }

        if (matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2] && matrix[0][0] != 0) {
            return matrix[0][0];
        }

        if (matrix[0][2] == matrix[1][1] && matrix[1][1] == matrix[2][0] && matrix[0][2] != 0) {
            return matrix[0][2];
        }

        return 0;
    }

    boolean checkFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}