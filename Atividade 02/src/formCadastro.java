import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class formCadastro {
    private JPanel mainPanel;
    private JTextField nomeTextField;
    private JTextField cpfTextField;
    private JRadioButton masculinoRadioButton;
    private JRadioButton femininoRadioButton;
    private JRadioButton outroRadioButton;
    private JTextField dataNascimentoTextField;
    private JComboBox<String> estadoCivilComboBox;
    private JTextField profissaoTextField;
    private JButton cadastrarButton;
    private ButtonGroup sexoGroup;

    public formCadastro() {
        sexoGroup = new ButtonGroup();
        sexoGroup.add(masculinoRadioButton);
        sexoGroup.add(femininoRadioButton);
        sexoGroup.add(outroRadioButton);

        estadoCivilComboBox.addItem("Solteiro(a)");
        estadoCivilComboBox.addItem("Casado(a)");
        estadoCivilComboBox.addItem("Divorciado(a)");
        estadoCivilComboBox.addItem("Viúvo(a)");

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeTextField.getText().trim();
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "O campo 'Nome' não pode ser deixado em branco.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String cpf = cpfTextField.getText().trim();
                if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                    JOptionPane.showMessageDialog(mainPanel, "CPF inválido. Utilize o formato XXX.XXX.XXX-XX.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String dataNascimentoStr = dataNascimentoTextField.getText().trim();
                LocalDate dataNascimento;
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Data de nascimento inválida. Utilize o formato DD/MM/AAAA.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idade = Period.between(dataNascimento, LocalDate.now()).getYears();

                String sexo = "";
                if (masculinoRadioButton.isSelected()) {
                    sexo = "Masculino";
                } else if (femininoRadioButton.isSelected()) {
                    sexo = "Feminino";
                } else if (outroRadioButton.isSelected()) {
                    sexo = "Outro";
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Por favor, selecione o sexo.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String estadoCivil = (String) estadoCivilComboBox.getSelectedItem();

                String profissao = profissaoTextField.getText().trim();
                if (profissao.isEmpty()) {
                    profissao = "Desempregado(a)";
                }

                StringBuilder mensagem = new StringBuilder();
                mensagem.append("Nome: ").append(nome).append("\n");
                mensagem.append("Idade: ").append(idade).append("\n");
                mensagem.append("Sexo: ").append(sexo).append("\n");
                mensagem.append("Estado Civil: ").append(estadoCivil).append("\n");
                mensagem.append("Profissão: ").append(profissao).append("\n");

                if (profissao.equalsIgnoreCase("Engenheiro") || profissao.equalsIgnoreCase("Analista de Sistemas")) {
                    mensagem.append("\nVagas disponíveis na área!");
                }

                JOptionPane.showMessageDialog(mainPanel, mensagem.toString(), "Cadastro Realizado", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
