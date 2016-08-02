import javax.swing.*;
import java.awt.*;

/**
 * @author Alexander Tsupko (alexander.tsupko@outlook.com)
 *         Copyright (c) All rights reserved.
 */
class MyTextField extends JTextField {
    private String hint;
    private static Font font = new Font("Arial", Font.ITALIC, 12);

    MyTextField(int columns, String hint) {
        super(columns);
        this.hint = hint;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty()) {
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(font);
            g.drawString(hint, 6, 18);
        }
    }
}
