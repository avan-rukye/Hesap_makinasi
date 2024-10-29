package hesap_makinasi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HesapMakinasi extends JFrame {
    private JTextField app;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HesapMakinasi::new);
    }

    public HesapMakinasi() {
        setTitle("Hesap Makinesi");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        app = new JTextField();
        app.setEditable(false);
        add(app, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        String[] tuslar = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "C", "+",
                "", "", "", "="
        };

        for (String tus : tuslar) {
            JButton button = new JButton(tus);
            button.addActionListener(new TusDinleyici());
            panel.add(button);
        }

        add(panel);

        setVisible(true);
    }

    private class TusDinleyici implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton tus = (JButton) e.getSource();
            String tusText = tus.getText();

            if (tusText.equals("=")) {
                hesapla();
            } else if (tusText.equals("C")) {
                app.setText("");
            } else {
                app.setText(app.getText() + tusText);
            }
        }

        private void hesapla() {
            try {
                String ifade = app.getText();
                double sonuc = eval(ifade);
                app.setText(Double.toString(sonuc));
            } catch (Exception ex) {
                app.setText("Hata");
            }
        }

        private double eval(String ifade) {
            String[] tokens = ifade.split("(?<=[-+*/])|(?=[-+*/])");
            double result = Double.parseDouble(tokens[0]);

            for (int i = 1; i < tokens.length; i += 2) {
                String operator = tokens[i];
                double operand = Double.parseDouble(tokens[i + 1]);

                switch (operator) {
                    case "+":
                        result += operand;
                        break;
                    case "-":
                        result -= operand;
                        break;
                    case "*":
                        result *= operand;
                        break;
                    case "/":
                        if (operand != 0) {
                            result /= operand;
                        } else {
                            throw new ArithmeticException("Bölme işleminde sıfır hatası");
                        }
                        break;
                }
            }

            return result;
        }
    }
}
