import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;

public class TicTacToe extends JComponent {
    public static final int FIELD_EMPTY = 0;
    public static final int FIELD_X = 1;
    public static final int FIELD_O = 2;
    int[][] field;
    boolean isXturn;

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.clearRect(0, 0, getWidth(), getHeight());
        drawGrid(graphics);
        drawXO(graphics);
    }

    @Override
    protected void processMouseEvent(MouseEvent mouseEvent) {
        super.processMouseEvent(mouseEvent);
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();

            int i = (int) ((float) x / getWidth() * 3);
            int a = (int) ((float) y / getHeight() * 3);

            if (field[i][a] == FIELD_EMPTY) {
                field[i][a] = isXturn ? FIELD_X : FIELD_O;
                isXturn = !isXturn;
                repaint();
                int res = checkState();
                if(res!=0){
                    if(res == FIELD_O*3){
                        JOptionPane.showMessageDialog(this, "Круги захватили власть на поле!", "Win", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(res==FIELD_X*3){
                        JOptionPane.showMessageDialog(this, "Крeсты захватили власть на поле!", "Win", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Все лохи", "Ничья", JOptionPane.INFORMATION_MESSAGE);
                    }
                    initGame();
                    repaint();
                }
            }
        }
    }

    void drawGrid(Graphics graphics) {
        int w = getWidth();
        int h = getHeight();
        int dw = w / 3;
        int dh = h / 3;
        graphics.setColor(Color.black);
        for (int i = 1; i < 3; i++) {
            graphics.drawLine(0, dh * i, w, dh * i);
            graphics.drawLine(dw * i, 0, dw * i, h);
        }
    }

    public TicTacToe() {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        field = new int[3][3];
        initGame();
    }

    public void initGame() {
        for (int i = 0; i < 3; ++i) {
            for (int a = 0; a < 3; ++a) {
                field[i][a] = FIELD_EMPTY;
            }
        }
    }

    void drawX(int i, int a, Graphics graphics) {
        graphics.setColor(Color.blue);
        int dw = getWidth() / 3;
        int dh = getHeight() / 3;
        int x = i * dw;
        int y = a * dh;

        graphics.drawLine(x, y, x + dw, y + dh);
        graphics.drawLine(x, y + dh, x + dw, y);
    }

    void drawO(int i, int a, Graphics graphics) {
        graphics.setColor(Color.blue);
        int dw = getWidth() / 3;
        int dh = getHeight() / 3;
        int x = i * dw;
        int y = a * dh;

        graphics.drawOval(x + 5 * dw / 100, y, dw * 9 / 10, dh);
    }

    void drawXO(Graphics graphics) {
        for (int i = 0; i < 3; ++i) {
            for (int a = 0; a < 3; ++a) {
                if (field[i][a] == FIELD_X) {
                    drawX(i, a, graphics);
                } else {
                    if (field[i][a] == FIELD_O) {
                        drawO(i, a, graphics);
                    }
                }
            }
        }
    }

    int checkState() {
        int diaq = 0;
        int diaq2 = 0;
        for (int i = 0; i < 3; i++) {
            diaq += field[i][i];
            diaq2 += field[i][2 - i];
        }
        if(diaq == FIELD_O * 3 || diaq == FIELD_X*3){
            return diaq;
        }
        if(diaq2 == FIELD_O * 3 || diaq2 == FIELD_X*3){
            return diaq2;
        }
        int check_i, check_a;
        boolean hasEmpty = false;

        for(int i = 0; i < 3; i++){
            check_i = 0;
            check_a = 0;
            for(int a = 0; a<3; a++){
                if(field[i][a]==0){
                    hasEmpty = true;
                }
                check_i += field[i][a];
                check_a += field[a][i];
            }

            if(check_i == FIELD_O*3 || check_i == FIELD_X*3){
                return check_i;
            }
            if(check_a==FIELD_O*3 || check_a == FIELD_X*3){
                return check_a;
            }
        }
        if(hasEmpty){
            return 0;
        }
        else{
            return -1;
        }


    }
}
