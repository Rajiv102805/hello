import java.util.Random;
import java.util.Scanner;

public class Main {
    static final int SIZE = 5;
    static char[][] board = new char[SIZE][SIZE];
    static boolean[][] treasures = new boolean[SIZE][SIZE];
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n=== Selection Board ===");
            System.out.println("1 - Play Game");
            System.out.println("2 - Read Game Mechanics");
            System.out.println("3 - Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Select Role (1 - Game Master, 2 - Player): ");
                    int role = sc.nextInt();
                    if (role == 1) {
                        setupGameByMaster();
                    } else if (role == 2) {
                        playGameAsPlayer();
                    } else {
                        System.out.println("Invalid role selected.");
                    }
                    break;
                case 2:
                    readMechanics();
                    break;
                case 3:
                    System.out.println("Thank you for playing!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }

    static void readMechanics() {
        System.out.println("\n--- Game Mechanics ---");
        System.out.println("This is a 1-player game using a 5x5 grid.");
        System.out.println("The Game Master manually places hidden treasures.");
        System.out.println("The Player gets 5 guesses to find them.");
        System.out.println("Coordinates are selected by row and column numbers (0-4).");
        System.out.println("After setup, the Player starts guessing.");
        System.out.println("Good luck!");
    }

    static void initBoard() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = '-';
                treasures[i][j] = false;
            }
    }

  
    static void setupGameByMaster() {
        initBoard();
        System.out.print("Enter number of treasures to place: ");
        int num = sc.nextInt();

        int placed = 0;
        while (placed < num) {
            System.out.print("Enter row (0-4) for treasure #" + (placed + 1) + ": ");
            int r = sc.nextInt();
            System.out.print("Enter col (0-4) for treasure #" + (placed + 1) + ": ");
            int c = sc.nextInt();
            if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && !treasures[r][c]) {
                treasures[r][c] = true;
                placed++;
            } else {
                System.out.println("Invalid or already occupied position.");
            }
        }

        System.out.println("Setup complete! Here's the board with treasures:");
        displayBoard(true);  
        System.out.println("\nNow hand the game over to the Player to begin.");
    }

  
    static void playGameAsPlayer() {
        initBoard();
        System.out.print("Enter number of treasures to place randomly: ");
        int num = sc.nextInt();

        placeTreasuresRandomly(num);

        int guesses = 5;
        int found = 0;

        while (guesses > 0) {
            displayBoard(false);  
            System.out.print("\nEnter row (0-4): ");
            int row = sc.nextInt();
            System.out.print("Enter col (0-4): ");
            int col = sc.nextInt();

            if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
                if (treasures[row][col]) {
                    System.out.println("🎉 Congratulations! You found a treasure!");
                    board[row][col] = 'T';
                    found++;
                    treasures[row][col] = false;

                    System.out.print("\nDo you want to play again? (1 - YES, 2 - NO): ");
                    int playAgain = sc.nextInt();
                    if (playAgain == 1) {
                        returnToMainMenu();
                        return; // Exit from the current method and go back to main menu
                    } else {
                        System.out.println("Thanks for playing!");
                        returnToMainMenu();
                        return;
                    }
                } else {
                    System.out.println("No treasure here.");
                    board[row][col] = 'X';
                }
                guesses--;
            } else {
                System.out.println("Invalid coordinates.");
            }
        }

        displayBoard(true);
        System.out.println("Game Over. You found " + found + " treasures!");
    
        System.out.print("\nDo you want to play again? (1 - YES, 2 - NO): ");
        int playAgain = sc.nextInt();
        if (playAgain == 1) {
            returnToMainMenu();
        } else {
            System.out.println("Thanks for playing!");
            returnToMainMenu();
        }
    }

    // Randomly places treasures
    static void placeTreasuresRandomly(int numTreasures) {
        Random rand = new Random();  // Using java.util.Random
        int placed = 0;
        while (placed < numTreasures) {
            int r = rand.nextInt(SIZE);  // Random row between 0 and 4
            int c = rand.nextInt(SIZE);  // Random column between 0 and 4
            if (!treasures[r][c]) {
                treasures[r][c] = true;
                placed++;
            }
        }
    }

    static void displayBoard(boolean revealAll) {
        System.out.println("\nBoard:");

        if (!revealAll) {
            // Column headers
            System.out.print("   ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < SIZE; i++) {
            if (!revealAll) {
                System.out.print(i + "  ");  // Row label
            }
            for (int j = 0; j < SIZE; j++) {
                if (revealAll && treasures[i][j])
                    System.out.print("T ");
                else
                    System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Return to main menu after game
    static void returnToMainMenu() {
        System.out.println("\nReturning to the main menu...\n");
    }
}
