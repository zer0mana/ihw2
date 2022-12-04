import java.util.Scanner;

public class Game {
    private Scanner in = new Scanner(System.in);
    /**
     * Ссылка на объект поля.
     */
    private Field field;
    /**
     * Лучший результат за сессию.
     */
    public static int bestScore = 0;
    /**
     * Игра с компьютером или игрок против игрока.
     * true если против компьютера.
     * false если против игрока.
     */
    private boolean computer = true;
    /**
     * Ссылка на объект бота.
     */
    private Bot bot;

    /**
     * Конструктор для игры против игрока.
     */
    public Game() {
        field = new Field();
        computer = false;
    }

    /**
     * Конструктор для игры против компьютера.
     */
    public Game(boolean hard) {
        field = new Field();
        computer = true;
        bot = new Bot(field, hard);
    }

    /**
     * Старт игры.
     */
    public void startGame() {
        while (true) {
            System.out.println(field);
            if (field.stack.isEmpty()) {
                if (canContinue()) {
                    System.out.println("Нет возможного хода, ход переходит оппоненту.");
                    field.clear();
                    field.changeColor();
                    continue;
                } else {
                    endGame();
                    break;
                }
            }
            if (computer && field.whatColor() == -1) {
                bot.algorithm();
            } else {
                field.turns();
                System.out.println("Введите ваш ход.         /b - ход назад");
                tryTurn();
            }
        }
    }

    /**
     * Метод ходов игрока.
     */
    private void tryTurn() {
        boolean flag = true;
        while (flag) {
            try {
                String input = in.nextLine();
                if (input.charAt(0) == '/' && input.charAt(1) == 'b' && input.length() == 2) {
                    if (computer) {
                        if (field.logs.size() < 2) {
                            System.out.println("Нет обратных ходов.");
                        } else {
                            field.backTurn();
                            field.backTurn();
                            flag = false;
                        }
                    } else {
                        if (!field.backTurn()) {
                            System.out.println("Нет обратных ходов.");
                        } else {
                            flag = false;
                        }
                    }
                    continue;
                }
                if (input.split(" ").length != 1) {
                    throw new Exception();
                }
                int x = (int)input.charAt(0) - 97;
                int y = 8 - Integer.parseInt(input.split("")[1]);
                if (field.addPoint(x, y)) {
                    flag = false;
                } else {
                    System.out.println("Такой ход невозможен.");
                }
            }
            catch (Exception e) {
                System.out.println("Неверный формат ввода.");
            }
        }
    }

    /**
     * Проверяет, может ли игра продолжаться.
     */
    private boolean canContinue() {
        field.changeColor();
        field.clear();
        field.checkField();
        boolean flag = !field.stack.isEmpty();
        field.changeColor();
        field.clear();
        return flag;
    }

    /**
     * Конец игры.
     */
    private void endGame() {
        System.out.println("Игра окончена.");
        int whiteRes = field.score[0];
        int blackRes = -field.score[1];
        if (whiteRes > blackRes) {
            System.out.println("Победа белых.");
        }
        if (whiteRes < blackRes) {
            System.out.println("Победа черных.");
        }
        if (whiteRes == blackRes) {
            System.out.println("Ничья.");
        }
        System.out.println("Итоговый счет: " + whiteRes + " " + blackRes);
        if (computer == true && whiteRes > blackRes) {
            if (whiteRes - blackRes > bestScore) {
                bestScore = whiteRes - blackRes;
                System.out.println("Новый рекорд: " + bestScore);
            }
        }
    }
}