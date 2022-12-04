import java.awt.*;
import java.util.ArrayList;

public class Log {
    /**
     * Хранит списки координат.
     */
    private ArrayList<Integer> points = new ArrayList<Integer>();
    /**
     * Хранит координату x точки.
     */
    public final int X;
    /**
     * Хранит координату y точки.
     */
    public final int Y;
    /**
     * Хранит цвет предыдущего хода.
     */
    public final int COLOR;

    public Log(int x, int y, int color, ArrayList<Integer> points) {
        X = x;
        Y = y;
        COLOR = color;
        this.points = points;
    }

    /**
     * Проверяет пустой ли список точек.
     */
    public boolean isEmpty() {

        return points.isEmpty();
    }

    /**
     * Добавляет свою точку на поле.
     * Красит точки по координатам из списка.
     * Меняет счет.
     */
    public void addPoint(int[][][] map, int color, int[] score) {
        for (int i = 0; i < points.size(); ++i) {
            map[points.get(i++)][points.get(i)][0] *= -1;
            score[0] += color * (i % 2);
            score[1] += color * (i % 2);
        }
        score[(-color + 1) / 2] += color;
        map[X][Y][0] = color;
    }

    /**
     * Возвращает доску к состоянию до хода.
     * Меняет счет.
     */
    public void backTurn(int[][][] map, int[] score) {
        for (int i = 0; i < points.size(); ++i) {
            map[points.get(i++)][points.get(i)][0] *= -1;
            score[0] -= COLOR * (i % 2);
            score[1] -= COLOR * (i % 2);
        }
        score[(-COLOR + 1) / 2] -= COLOR;
        map[X][Y][0] = 0;
    }

    /**
     * Складывает списки координат двух Log-ов.
     */
    public void add(Log log) {
        for (int x : log.points) {
            points.add(x);
        }
    }
}