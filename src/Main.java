import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.print("Добро пожаловать в Реверси.\n" +
                    "Игрок против слабого бота - /l\n" +
                    "Игрок против сильного бота - /h\n" +
                    "Игрок против игрока - /p\n" +
                    "Рекорд текущей сессии - /r\n" +
                    "Выйти - /e\n");
            switch (in.nextLine()) {
                case ("/l"):
                    Game game1 = new Game(false);
                    game1.startGame();
                    break;
                case ("/h"):
                    Game game2 = new Game(true);
                    game2.startGame();
                    break;
                case ("/p"):
                    Game game3 = new Game();
                    game3.startGame();
                    break;
                case ("/r"):
                    System.out.println("Текущий рекорд за сессию: " + Game.bestScore + " очк.");
                    break;
                case ("/e"):
                    System.out.println("До новых встреч.");
                    flag = false;
                    break;
            }
        }
    }
}