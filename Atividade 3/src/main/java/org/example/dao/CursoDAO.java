package org.example.dao;

import org.example.model.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO implements ICursoDAO {
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "9519";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void create(Curso curso) {
        String sql = "INSERT INTO cursos (nome, sigla, area) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, curso.getNome());
            pstmt.setString(2, curso.getSigla());
            pstmt.setString(3, curso.getArea().name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Curso curso) {
        String sql = "UPDATE cursos SET nome = ?, area = ? WHERE sigla = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, curso.getNome());
            pstmt.setString(2, curso.getArea().name());
            pstmt.setString(3, curso.getSigla());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long codigo) {
        String sql = "DELETE FROM cursos WHERE codigo = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, codigo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Curso> findAll() {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM cursos";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Curso curso = new Curso();
                curso.setCodigo(rs.getLong("codigo"));
                curso.setNome(rs.getString("nome"));
                curso.setSigla(rs.getString("sigla"));
                curso.setArea(Curso.Area.valueOf(rs.getString("area")));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    @Override
    public Curso findById(Long codigo) {
        String sql = "SELECT * FROM cursos WHERE codigo = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Curso curso = new Curso();
                curso.setCodigo(rs.getLong("codigo"));
                curso.setNome(rs.getString("nome"));
                curso.setSigla(rs.getString("sigla"));
                curso.setArea(Curso.Area.valueOf(rs.getString("area")));
                return curso;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Curso findBySigla(String sigla) {
        String sql = "SELECT * FROM cursos WHERE sigla = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sigla);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Curso curso = new Curso();
                curso.setCodigo(rs.getLong("codigo"));
                curso.setNome(rs.getString("nome"));
                curso.setSigla(rs.getString("sigla"));
                curso.setArea(Curso.Area.valueOf(rs.getString("area")));
                return curso;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Curso> findByArea(Curso.Area area) {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM cursos WHERE area = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, area.name());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Curso curso = new Curso();
                curso.setCodigo(rs.getLong("codigo"));
                curso.setNome(rs.getString("nome"));
                curso.setSigla(rs.getString("sigla"));
                curso.setArea(Curso.Area.valueOf(rs.getString("area")));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }
}