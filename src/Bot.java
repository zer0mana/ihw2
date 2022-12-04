import java.util.Random;

public class Bot {
    /**
     * Сложность бота.
     * true - сложный.
     * false - легкий.
     */
    private boolean hard;
    /**
     * Ссылка на объект поля.
     */
    private Field field;
    /**
     * Рандом.
     */
    private Random random = new Random();

    public Bot(Field field, boolean hard) {
        this.field = field;
        this.hard = hard;
    }

    /**
     * Бот совершает ход.
     * Если легкий, то рандомный ход.
     * Иначе оценка с выбором лучшего варианта.
     * Вариант считается лучшим, если в результате хода бота + ответном ходе игрока бот получает как можно больше очков.
     * Очки высчитываются как разница чёрн. - бел.
     */
    public void algorithm() {
        if (!hard) {
            Log current = field.stack.get(random.nextInt(field.stack.size()));
            field.addPoint(current.X, current.Y);
        } else {
            int[] res = new int[field.stack.size()];
            for (int i1 = 0; i1 < res.length; ++i1) {
                field.checkField();
                Log current = field.stack.get(i1);
                field.addPoint(current.X, current.Y);
                res[i1] = -field.score[1] - field.score[0];
                int max = -65;
                field.checkField();
                int n = field.stack.size();
                for (int i2 = 0; i2 < n; ++i2) {
                    field.checkField();
                    Log answer = field.stack.get(i2);
                    field.addPoint(answer.X, answer.Y);
                    if (max < -field.score[1] - field.score[0]) {
                        max = -field.score[1] - field.score[0];
                    }
                    field.backTurn();
                }
                res[i1] += max;
                field.backTurn();
            }
            int max = -128;
            for (int x : res) {
                if (max < x) {
                    max = x;
                }
            }
            int index = 0;
            for (int i = 0; i < res.length; ++i) {
                if (max == res[i]) {
                    index = i;
                    break;
                }
            }
            field.checkField();
            field.addPoint(field.stack.get(index).X, field.stack.get(index).Y);
        }
    }
}
