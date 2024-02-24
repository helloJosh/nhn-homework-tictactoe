import java.util.Random;
import java.util.Scanner;

public class Tictactoe {
    public static final int USER = 1;
    public static final int COMPUTER = 2;

    public static void moveUser(int[][] board, Scanner scanner) {
        System.out.print("사용자 턴(x y):");

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        if (board[m][n] != 0) {
            System.out.println("이미 자리가 있습니다. 다시 입력해주세요.");
            moveUser(board, scanner);
        } else {
            board[m][n] = USER;
        }
    }

    public static void printBoard(int[][] board) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (j % 2 != 0) {
                    System.out.print("|");
                } else if (i % 2 != 0) {
                    System.out.print("-");
                } else {
                    if (board[i / 2][j / 2] == 1) {
                        System.out.print("O");
                    } else if (board[i / 2][j / 2] == 2) {
                        System.out.print("X");
                    } else {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void moveComputer(int[][] board) {
        System.out.println("컴퓨터 턴");
        Random random = new Random();
        while (true) {
            int n = random.nextInt(3);
            int m = random.nextInt(3);
            if (board[m][n] == 0) {
                board[m][n] = COMPUTER;
                break;
            }
        }
    }

    public static void moveComputerAdvanced(int[][] board) {
        int[] bestMove = { -1, -1 };
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = COMPUTER;
                    int score = tryInAdvance(board, 0, false);
                    board[i][j] = 0;
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        board[bestMove[0]][bestMove[1]] = COMPUTER;
    }

    public static int tryInAdvance(int[][] board, int depth, boolean check) {
        if (isBoardFull(board) || depth == 3) {
            if (isWin(board, COMPUTER)) {
                return 1;
            } else if (isWin(board, USER)) {
                return -1;
            } else {
                return 0;
            }
        }

        if (check) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = COMPUTER;
                        int score = tryInAdvance(board, depth + 1, false);
                        board[i][j] = 0;
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = USER;
                        int score = tryInAdvance(board, depth + 1, true);
                        board[i][j] = 0;
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public static boolean isWin(int[][] board, int turn) {
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == turn && board[1][i] == turn && board[2][i] == turn)
                return true;
        }
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == turn && board[i][1] == turn && board[i][2] == turn)
                return true;
        }
        if (board[2][0] == turn && board[1][1] == turn && board[0][2] == turn)
            return true;
        if (board[0][0] == turn && board[1][1] == turn && board[2][2] == turn)
            return true;
        return false;
    }

    public static boolean isBoardFull(int[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] board = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
        Scanner scanner = new Scanner(System.in);
        System.out.print("난이도를 설정하세요(1:쉬움, 2:어려움):");
        int level = scanner.nextInt();

        while (true) {
            // 사용자 차례
            moveUser(board, scanner);
            printBoard(board);
            if (isWin(board, USER)) {
                System.out.println("사용자 승리");
                break;
            } else {
                if (isBoardFull(board)) {
                    System.out.println("판이 꽉찼습니다. 새로운 게임을 실행해 주세요");
                    break;
                }
            }

            // 컴퓨터(난이도 설정) 차례
            if (level == 1) {
                moveComputer(board);
            } else {
                moveComputerAdvanced(board);
            }
            printBoard(board);
            if (isWin(board, COMPUTER)) {
                System.out.println("컴퓨터 승리");
                break;
            } else {
                if (isBoardFull(board)) {
                    System.out.println("판이 꽉찼습니다. 새로운 게임을 실행해 주세요");
                    break;
                }
            }

        }
    }
}