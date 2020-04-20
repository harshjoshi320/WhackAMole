package WhackAMole.MoleWhack;

import java.util.Random;

import java.util.Scanner;

class WhackAMole {
    private int score = 0, attemptsLeft, molesLeft;
    private final char[][] gameGrid;
    private final char[][] userGrid;
    
    WhackAMole(final int numAttempts, final int gridDimensions) {
        gameGrid = new char[gridDimensions][gridDimensions];
        userGrid = new char[gridDimensions][gridDimensions];
        attemptsLeft = numAttempts;
        molesLeft = gridDimensions;
        
        populateGrid();
    }
    
    void populateGrid() {
        final int length = gameGrid.length;
        final Random coorRand = new Random();
        for(int i = 0; i < length;) {
            final int x = coorRand.nextInt(length);
            final int y = coorRand.nextInt(length);
            if (gameGrid[x][y] == 'm') {
                continue;
            }
            gameGrid[x][y] = 'm';
            i++;
        }
    }
    
    boolean whack(final int x, final int y) {
        if( gameGrid[x][y] == 'm') {
            userGrid[x][y] = '*';
            updateMolesLeft();
            updateScore();
            return true;
        }
        else {
            userGrid[x][y] = 'X';
            updateAttempts();
            return false;
        }
    }
    
    int getGridSize() {
        return gameGrid.length;
    }
    
    int getScore() {
        return score;
    }
    
    void updateScore() {
        score++;
    }
    
    int getAttemptsLeft() {
        return attemptsLeft;
    }
    
    void updateAttempts() {
        attemptsLeft--;
    }
    
    int getMolesLeft() {
        return molesLeft;
    }
    
    void updateMolesLeft() {
        molesLeft--;
    }
    
    void printGrid() {
        System.out.println();
        
        for(final char[] row: gameGrid) {
            for(final char dot: row) {
                if( dot == '\0') {
                    System.out.print("o ");
                }
                else {
                    System.out.print("m ");
                }
            }
            System.out.println();
        }
    }
    
    void printUserGrid() {
        System.out.println();
        
        for(final char[] row: userGrid) {
            for(final char dot: row) {
                if( dot == '\0') {
                    System.out.print("o ");
                }
                else {
                    System.out.print(dot + " ");
                }
            }
            System.out.println();
        }
    }
    
    void printGameWindow() {
        System.out.println("----------------");
        printUserGrid();
        System.out.println( "\nScore: " + getScore() + " | Moles Left: " + getMolesLeft() + " | Attempts Left: " + getAttemptsLeft());
    }
}

class Game {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        char choice = 'n';
        while (choice != 'q') {
            System.out.println("\nWelcome to 'Whack-A-Mole 2: Whack a More' !!");
            System.out.print("Enter your choice: \n" + 
            "y. play\n" +
            "q. quit\n" +
            ">> ");
            choice =  scanner.next().charAt(0);
            switch (choice) {
                case 'y' : newGame(scanner);
                break;
                case 'q' : System.exit(0);
                break;
                default: System.exit(0);
            }
        }
        scanner.close();        
    }
    
    static void newGame(final Scanner scanner) {
        final int[] settings = getDifficulty(scanner);
        
        final WhackAMole game = new WhackAMole(settings[1], settings[0]);
        
        while( game.getAttemptsLeft() > 0 ) {
            if ( game.getMolesLeft() == 0) {
                win(game);
                break;
            }
            
            game.printGameWindow();
            System.out.print("\nEnter the coordinates(row, col) to WHACK!");
            
            final int row = getInput(game.getGridSize(), "\n row >> ", scanner);
            
            final int col = getInput(game.getGridSize(), "\n col >> ", scanner);
            
            
            final boolean didItWhack = game.whack(row, col);
            if ( didItWhack) {
                System.out.println("\nWHACK!!...Nice Job!");
            }
            else {
                System.out.println("\nWHUMP!!...Oops, try again.");
            }
        }
        if (game.getAttemptsLeft() == 0) {
            game.printGameWindow();
            System.out.println("\n0 Attempts Left...You Lost");
            System.out.println("\nBOARD >> ");
            game.printGrid();
            System.out.println("\n-- GAME OVER --");
        }
        
    }
    
    static int[] getDifficulty(final Scanner scanner) {
        int attempts, size;
        System.out.print("\nSelect Difficulty : " + 
        "\n e. Easy" + 
        "\n n. Normal" + 
        "\n h. Hard" +
        "\n>> ");
        final char difficulty = scanner.next().charAt(0);
        switch (difficulty) {
            case 'e' : size = 3;
            attempts = 5;
            break;
            case 'n' : size = 4;
            attempts = 6;
            break;
            case 'h' : size = 5;
            attempts = 7;
            break;
            default : size = 3;
            attempts = 5;
            break;
        }
        final int[] settings = { size, attempts};
        return settings;
    } 
    
    static void win(final WhackAMole game) {
        System.out.println("\nWow!! You Whacked All of'em!");
        game.printGameWindow();
        System.out.println(
        "\nScore: " + game.getScore() +
        "\nAttempts Left: " + game.getAttemptsLeft() +
        "\nTotal Score ( Score + Attempts Left ): " + (game.getScore() + game.getAttemptsLeft()));
    }
    
    static int getInput( final int inputLimit, final String message, final Scanner scanner) {
        int inVar = 0;
        boolean notValid = true;
        while(notValid) {
            System.out.print(message);
            inVar = scanner.nextInt();
            if( inVar < inputLimit) {
                notValid = false;
            }
        }
        return inVar;
    }
    
}
