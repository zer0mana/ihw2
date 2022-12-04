import java.util.ArrayList;
import java.util.Stack;
import java.util.HashSet;

public class Field {
    /**
     * Содержит информацию о всех координатах.
     * 0 - пусто.
     * 1 - белая точка.
     * 2 - крестик (возможный ход).
     * -1 - черная точка.
     */
    private int[][][] map = new int[8][8][1];
    /**
     * Хранит информацию о счете.
     * Счет черных отрицательный для удобных вычислений.
     */
    public int[] score = {2, -2};
    /**
     * Хранит информацию о текущем цвете.
     * 1 - ход белых.
     * -1 - ход черных.
     */
    private int color = 1;
    /**
     * Список возможных ходов.
     */
    public ArrayList<Log> stack = new ArrayList<>();
    /**
     * Стэк предыдущих ходов.
     */
    public Stack<Log> logs = new Stack<>();

    public Field() {
        map[3][4][0] = 1;
        map[4][3][0] = 1;
        map[3][3][0] = -1;
        map[4][4][0] = -1;
    }

    /**
     * Возвращает текущий цвет.
     */
    public int whatColor() {
        return color;
    }

    /**
     * Позволяет менять цвет извне.
     */
    public void changeColor() {
        color = -color;
    }

    /**
     * Очищает список возможных ходов и убирает крестики с поля.
     */
    public void clear() {
        stack.clear();
        for (int y = 0; y < 8; ++y) {
            for (int x = 0; x < 8; ++x) {
                if (map[x][y][0] == 2) {
                    map[x][y][0] = 0;
                }
            }
        }
    }

    /**
     * Проверяет поле на наличие возможных ходов.
     */
    public void checkField() {
        stack.clear();
        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                if (map[x][y][0] == color) {
                    for (int i1 = -1; i1 < 2; ++i1) {
                        for (int i2 = -1; i2 < 2; ++i2) {
                            if (!(i1 == 0 && i2 == 0)) {
                                checkLine(x, y, i1, i2, color);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Проверяет линию на наличие возможных ходов.
     */
    private void checkLine(int x, int y, int iterX, int iterY, int color) {
        ArrayList<Integer> points = new ArrayList<>();
        boolean flag = true;
        boolean check = false;
        for (int xi = x, yi = y; flag; xi += iterX, yi += iterY) {
            if (x == xi && y == yi) {
                continue;
            }
            if (xi > 7 || xi < 0 || yi > 7 || yi < 0) {
                flag = false;
            } else {
                if (map[xi][yi][0] == -color) {
                    points.add(xi);
                    points.add(yi);
                    check = true;
                } else {
                    flag = false;
                    if (map[xi][yi][0] != color && check) {
                        stack.add(new Log(xi, yi, color, points));
                        map[xi][yi][0] = 2;
                    }
                }
            }
        }
    }

    /**
     * Выводит список возможных ходов.
     */
    public void turns() {
        System.out.print("Возможные ходы: ");
        HashSet<String> uniqueMoves = new HashSet<>();
        for (Log log : stack) {
            uniqueMoves.add((char)(log.X + 97) + "" + (8 - log.Y) + " ");
        }
        for (String move : uniqueMoves) {
            System.out.print(move);
        }
        System.out.println("\n");
    }

    /**
     * Добавляет точку на поле.
     */
    public boolean addPoint(int x, int y) {
        Log current = new Log(x, y, color, new ArrayList<>());
        for (Log log : stack) {
            if (log.X == x && log.Y == y) {
                current.add(log);
            }
        }
        if (current.isEmpty()) {
            return false;
        }
        current.addPoint(map, color, score);
        color = -color;
        logs.push(current);
        clear();
        return true;
    }

    /**
     * Возвращает предыдущий ход.
     */
    public boolean backTurn() {
        if (logs.empty()) {
            return false;
        }
        Log log = logs.pop();
        log.backTurn(map, score);
        color = log.COLOR;
        clear();
        return true;
    }

    /**
     * Метод вывода поля в консоль.
     */
    public String toString() {
        String out = "";
        checkField();
        if (color == 1) {
            out += "Ход Белых";
        } else {
            out += "Ход Черных";
        }
        out += "     " + score[0] + " : " + (-score[1]) + "\n";
        for (int y = 0; y < 8; ++y) {
            for (int x = 0; x < 8; ++x) {
                if (x == 0) {
                    out += 8 - y;
                    out += "  ";
                }
                switch (map[x][y][0]) {
                    case 0:
                        out += "-  ";
                        break;
                    case 1:
                        out += "W  ";
                        break;
                    case -1:
                        out += "B  ";
                        break;
                    case 2:
                        out += "x  ";
                }
                if (x == 7) {
                    out += "\n";
                }
            }
        }
        out += "   a  b  c  d  e  f  g  h";
        return out;
    }
}