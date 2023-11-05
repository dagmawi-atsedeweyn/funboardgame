//import neccesary lib
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {
    private JButton[][] buttons;  // 2D array to hold the buttons
    private JButton selectedButton;  // Represents the currently selected button

    public GameBoard() {
        setTitle("Game Board");  // Set the title of the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Specify the close operation
        setSize(300, 300);  // Set the size of the JFrame
        setLayout(new GridLayout(3, 3));  // Set the layout manager for the JFrame

        buttons = new JButton[3][3];  // Initialize the 2D array to hold the buttons
        selectedButton = null;  // Set the selected button to null initially

        // Create and initialize buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();  // Create a new JButton
                buttons[i][j].addActionListener(new ButtonListener());  // Add an ActionListener to the button
                add(buttons[i][j]);  // Add the button to the JFrame
            }
        }

        // Initialize the initial pieces
        initializePieces();

        setVisible(true);  // Make the JFrame visible
    }

    private void initializePieces() {
        // Place three pieces on each side of the board
        for (int i = 0; i < 3; i++) {
            buttons[0][i].setText("X");  // Set the text of the button to "X"
            buttons[2][i].setText("O");  // Set the text of the button to "O"
        }
    }


//look for win
   private void checkWin() {
     // Check for vertical
      for (int i = 0; i < 3; i++) {
        String text = buttons[0][i].getText();
        if (!text.isEmpty() && text.equals(buttons[1][i].getText()) && text.equals(buttons[2][i].getText())) {
            JOptionPane.showMessageDialog(this, text + " wins!");
            return;
        }
    }

    // Check for second row
    String secondRowText = buttons[1][0].getText();
    if (!secondRowText.isEmpty() && secondRowText.equals(buttons[1][1].getText()) && secondRowText.equals(buttons[1][2].getText())) {
        JOptionPane.showMessageDialog(this, secondRowText + " wins!");
        return;
    }

    // Check for diagonal
    String center = buttons[1][1].getText();
    if (!center.isEmpty()) {
        if ((center.equals(buttons[0][0].getText()) && center.equals(buttons[2][2].getText())) ||
                (center.equals(buttons[0][2].getText()) && center.equals(buttons[2][0].getText()))) {
            JOptionPane.showMessageDialog(this, center + " wins!");
            return;
           }
       }
   }
//class to handle user's button click
    
    private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (selectedButton == null) {
            selectedButton = clickedButton;
            selectedButton.setEnabled(false);
        } else {
            int selectedRow = -1;
            int selectedColumn = -1;
            int clickedRow = -1;
            int clickedColumn = -1;

            // Find the indices of the selected and clicked buttons in the 2D array
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (selectedButton == buttons[i][j]) {
                        selectedRow = i;
                        selectedColumn = j;
                    } else if (clickedButton == buttons[i][j]) {
                        clickedRow = i;
                        clickedColumn = j;
                    }
                }
            }

            // Check if the destination button is occupied
            if (!clickedButton.getText().isEmpty()) {
                // Destination button is occupied, handle the capture logic here
                JOptionPane.showMessageDialog(null, "Invalid move! Destination button is occupied.");
            }
            // Check if there are any pieces between the selected piece and the destination button
            else if (hasObstacle(selectedRow, selectedColumn, clickedRow, clickedColumn)) {
                // There is an obstacle in the path, handle the invalid move here
                JOptionPane.showMessageDialog(null, "Invalid move! There is an obstacle in the path.");
            }
            // Move the piece to the destination button
            else {
                clickedButton.setText(selectedButton.getText());
                selectedButton.setText("");
                selectedButton.setEnabled(true);
                selectedButton = null;

                // Check for a win after each move
                checkWin();
            }
        }
    }

    private boolean hasObstacle(int selectedRow, int selectedColumn, int clickedRow, int clickedColumn) {
        // Moving horizontally
        if (selectedRow == clickedRow) {
            int startColumn = Math.min(selectedColumn, clickedColumn);
            int endColumn = Math.max(selectedColumn, clickedColumn);
            for (int column = startColumn + 1; column < endColumn; column++) {
                if (!buttons[selectedRow][column].getText().isEmpty()) {
                    return true;
                }
            }
        }
        // Moving vertically
        else if (selectedColumn == clickedColumn) {
            int startRow = Math.min(selectedRow, clickedRow);
            int endRow = Math.max(selectedRow, clickedRow);
            for (int row = startRow + 1; row < endRow; row++) {
                if (!buttons[row][selectedColumn].getText().isEmpty()) {
                    return true;
                }
            }
        }
        // Moving diagonally
        else if (Math.abs(selectedRow - clickedRow) == Math.abs(selectedColumn - clickedColumn)) {
            int startRow = Math.min(selectedRow, clickedRow);
            int startColumn = Math.min(selectedColumn, clickedColumn);
            int endRow = Math.max(selectedRow, clickedRow);
            int endColumn = Math.max(selectedColumn, clickedColumn);
            int row = startRow + 1;
            int column = startColumn + 1;
            while (row < endRow && column < endColumn) {
                if (!buttons[row][column].getText().isEmpty()) {
                    return true;
                }
                row++;
                column++;
            }
        }

        return false;
    }
}
}
                                                                                                                                                   