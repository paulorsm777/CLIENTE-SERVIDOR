import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class form {
    private JPanel mainPanel;
    private JTextField valor01TextField;
    private JTextField valor02TextField;
    private JRadioButton adicaoRadioButton;
    private JRadioButton subtracaoRadioButton;
    private JRadioButton multiplicacaoRadioButton;
    private JRadioButton divisaoRadioButton;
    private JButton calcularButton;
    private JLabel valor02Label;
    private JLabel valor01Label;
    private ButtonGroup operacoesGroup;

    public form() {
        operacoesGroup = new ButtonGroup();
        operacoesGroup.add(adicaoRadioButton);
        operacoesGroup.add(subtracaoRadioButton);
        operacoesGroup.add(multiplicacaoRadioButton);
        operacoesGroup.add(divisaoRadioButton);

        calcularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double valor1 = Double.parseDouble(valor01TextField.getText());
                    double valor2 = Double.parseDouble(valor02TextField.getText());
                    double resultado = 0;

                    if (adicaoRadioButton.isSelected()) {
                        resultado = valor1 + valor2;
                    } else if (subtracaoRadioButton.isSelected()) {
                        resultado = valor1 - valor2;
                    } else if (multiplicacaoRadioButton.isSelected()) {
                        resultado = valor1 * valor2;
                    } else if (divisaoRadioButton.isSelected()) {
                        if (valor2 != 0) {
                            resultado = valor1 / valor2;
                        } else {
                            JOptionPane.showMessageDialog(mainPanel, "Divisão por zero não é permitida.", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Por favor, selecione uma operação.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(mainPanel, "Resultado: " + resultado);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Por favor, insira números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
