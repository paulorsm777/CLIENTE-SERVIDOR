package org.example;

import org.example.dao.AlunoDAO;
import org.example.dao.CursoDAO;
import org.example.model.Aluno;
import org.example.model.Curso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main extends JFrame {
    private final CursoDAO cursoDAO;
    private final AlunoDAO alunoDAO;
    private JTextField txtNomeAluno;
    private JTextField txtNomeCurso, txtSiglaCurso;
    private JComboBox<Curso.Area> cbArea;
    private JComboBox<String> cbCursos;
    private JTable tableCursos, tableAlunos;

    public Main() {
        cursoDAO = new CursoDAO();
        alunoDAO = new AlunoDAO();
        setTitle("Gerenciamento de Cursos e Alunos");
        setLayout(new GridLayout(2, 1));

        JPanel panelCursos = new JPanel();
        panelCursos.setLayout(new BorderLayout());
        JPanel panelInputCursos = new JPanel();
        panelInputCursos.setLayout(new GridLayout(4, 2));

        panelInputCursos.add(new JLabel("Nome:"));
        txtNomeCurso = new JTextField();
        panelInputCursos.add(txtNomeCurso);

        panelInputCursos.add(new JLabel("Sigla:"));
        txtSiglaCurso = new JTextField();
        panelInputCursos.add(txtSiglaCurso);

        panelInputCursos.add(new JLabel("Área:"));
        cbArea = new JComboBox<>(Curso.Area.values());
        panelInputCursos.add(cbArea);

        JButton btnCreateCurso = new JButton("Criar Curso");
        btnCreateCurso.addActionListener(new CreateCourseAction());
        panelInputCursos.add(btnCreateCurso);

        JButton btnUpdateCurso = new JButton("Atualizar Curso");
        btnUpdateCurso.addActionListener(new UpdateCourseAction());
        panelInputCursos.add(btnUpdateCurso);

        JButton btnDeleteCurso = new JButton("Deletar Curso");
        btnDeleteCurso.addActionListener(new DeleteCourseAction());
        panelInputCursos.add(btnDeleteCurso);

        panelCursos.add(panelInputCursos, BorderLayout.NORTH);

        tableCursos = new JTable();
        updateCourseTable();
        panelCursos.add(new JScrollPane(tableCursos), BorderLayout.CENTER);
        add(panelCursos);

        JPanel panelAlunos = new JPanel();
        panelAlunos.setLayout(new BorderLayout());
        JPanel panelInputAlunos = new JPanel();
        panelInputAlunos.setLayout(new GridLayout(3, 2));

        panelInputAlunos.add(new JLabel("Nome do Aluno:"));
        txtNomeAluno = new JTextField();
        panelInputAlunos.add(txtNomeAluno);

        panelInputAlunos.add(new JLabel("Curso:"));
        cbCursos = new JComboBox<>(); // JComboBox para selecionar o curso
        updateCourseComboBox();
        panelInputAlunos.add(cbCursos);

        JButton btnCreateAluno = new JButton("Criar Aluno");
        btnCreateAluno.addActionListener(new CreateStudentAction());
        panelInputAlunos.add(btnCreateAluno);

        JButton btnUpdateAluno = new JButton("Atualizar Aluno");
        btnUpdateAluno.addActionListener(new UpdateStudentAction());
        panelInputAlunos.add(btnUpdateAluno);

        JButton btnDeleteAluno = new JButton("Deletar Aluno");
        btnDeleteAluno.addActionListener(new DeleteStudentAction());
        panelInputAlunos.add(btnDeleteAluno);

        panelAlunos.add(panelInputAlunos, BorderLayout.NORTH);

        tableAlunos = new JTable();
        updateStudentTable();
        panelAlunos.add(new JScrollPane(tableAlunos), BorderLayout.CENTER);
        add(panelAlunos);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    private void updateCourseTable() {
        List<Curso> cursos = cursoDAO.findAll();
        String[] columnNames = {"Código", "Nome", "Sigla", "Área"};
        Object[][] data = new Object[cursos.size()][4];

        for (int i = 0; i < cursos.size(); i++) {
            data[i][0] = cursos.get(i).getCodigo();
            data[i][1] = cursos.get(i).getNome();
            data[i][2] = cursos.get(i).getSigla();
            data[i][3] = cursos.get(i).getArea();
        }

        tableCursos.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void updateStudentTable() {
        List<Aluno> alunos = alunoDAO.findAll();
        String[] columnNames = {"ID", "Nome", "Curso"};
        Object[][] data = new Object[alunos.size()][3];

        for (int i = 0; i < alunos.size(); i++) {
            data[i][0] = alunos.get(i).getId();
            data[i][1] = alunos.get(i).getNome();
            data[i][2] = alunos.get(i).getCurso();
        }

        tableAlunos.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private void updateCourseComboBox() {
        List<Curso> cursos = cursoDAO.findAll();
        cbCursos.removeAllItems();

        for (Curso curso : cursos) {
            cbCursos.addItem(curso.getSigla());
        }
    }

    private class CreateCourseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Curso curso = new Curso();
            curso.setNome(txtNomeCurso.getText());
            curso.setSigla(txtSiglaCurso.getText());
            curso.setArea((Curso.Area) cbArea.getSelectedItem());
            cursoDAO.create(curso);
            updateCourseTable();
            updateCourseComboBox();
        }
    }

    private class UpdateCourseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tableCursos.getSelectedRow();
            if (selectedRow >= 0) {
                Long codigo = (Long) tableCursos.getValueAt(selectedRow, 0);
                Curso curso = cursoDAO.findById(codigo);
                curso.setNome(txtNomeCurso.getText());
                curso.setSigla(txtSiglaCurso.getText());
                curso.setArea((Curso.Area) cbArea.getSelectedItem());
                cursoDAO.update(curso);
                updateCourseTable();
                updateCourseComboBox();
            }
        }
    }

    private class DeleteCourseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tableCursos.getSelectedRow();
            if (selectedRow >= 0) {
                Long codigo = (Long) tableCursos.getValueAt(selectedRow, 0);
                cursoDAO.delete(codigo);
                updateCourseTable();
                updateCourseComboBox();
            }
        }
    }

    private class CreateStudentAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Aluno aluno = new Aluno();
            aluno.setNome(txtNomeAluno.getText());
            aluno.setCurso((String) cbCursos.getSelectedItem());
            alunoDAO.create(aluno);
            updateStudentTable();
        }
    }

    private class UpdateStudentAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tableAlunos.getSelectedRow();
            if (selectedRow >= 0) {
                Long id = (Long) tableAlunos.getValueAt(selectedRow, 0);
                Aluno aluno = alunoDAO.findById(id);
                aluno.setNome(txtNomeAluno.getText());
                aluno.setCurso((String) cbCursos.getSelectedItem());
                alunoDAO.update(aluno);
                updateStudentTable();
            }
        }
    }

    private class DeleteStudentAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tableAlunos.getSelectedRow();
            if (selectedRow >= 0) {
                Long id = (Long) tableAlunos.getValueAt(selectedRow, 0);
                alunoDAO.delete(id);
                updateStudentTable();
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}