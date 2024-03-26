package com.etudiant.dao;

import com.etudiant.model.Etudiant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EtudiantDAOImpl implements EtudiantDAO {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static final String URL = "jdbc:postgresql://localhost:5432/jaxrs";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public List<Etudiant> getAllEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM etudiant")) {
            while (rs.next()) {
                int numEt = rs.getInt("numEt");
                String nom = rs.getString("nom");
                double noteMath = rs.getDouble("note_math");
                double notePhys = rs.getDouble("note_phys");
                Etudiant etudiant = new Etudiant(numEt, nom, noteMath, notePhys);
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }

    @Override
    public Etudiant getEtudiantById(int numEt) {
        Etudiant etudiant = null;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM etudiant WHERE numEt = ?")) {
            stmt.setInt(1, numEt);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nom");
                double noteMath = rs.getDouble("note_math");
                double notePhys = rs.getDouble("note_phys");
                etudiant = new Etudiant(numEt, nom, noteMath, notePhys);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiant;
    }

    @Override
    public void addEtudiant(Etudiant etudiant) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO etudiant (nom, note_math, note_phys) VALUES (?, ?, ?)")) {
            stmt.setString(1, etudiant.getNom());
            stmt.setDouble(2, etudiant.getNoteMath());
            stmt.setDouble(3, etudiant.getNotePhys());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEtudiant(Etudiant etudiant) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE etudiant SET nom = ?, note_math = ?, note_phys = ? WHERE numEt = ?")) {
            stmt.setString(1, etudiant.getNom());
            stmt.setDouble(2, etudiant.getNoteMath());
            stmt.setDouble(3, etudiant.getNotePhys());
            stmt.setInt(4, etudiant.getNumEt());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEtudiant(int numEt) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM etudiant WHERE numEt = ?")) {
            stmt.setInt(1, numEt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getMoyenneClasse() {
        double moyenne = 0.0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT AVG((note_math + note_phys) / 2) AS moyenne_classe FROM etudiant")) {
            if (rs.next()) {
                moyenne = rs.getDouble("moyenne_classe");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moyenne;
    }

    @Override
    public double getMoyenneMin() {
        double moyenne = 0.0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT MIN((note_math + note_phys) / 2) AS moyenne_min FROM etudiant")) {
            if (rs.next()) {
                moyenne = rs.getDouble("moyenne_min");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moyenne;
    }

    @Override
    public double getMoyenneMax() {
        double moyenne = 0.0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT MAX((note_math + note_phys) / 2) AS moyenne_max FROM etudiant")) {
            if (rs.next()) {
                moyenne = rs.getDouble("moyenne_max");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moyenne;
    }

    @Override
    public int getNbEtudiantsAdmis() {
        int nbEtudiants = 0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS nb_admis FROM etudiant WHERE (note_math + note_phys) / 2 >= 10")) {
            if (rs.next()) {
                nbEtudiants = rs.getInt("nb_admis");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nbEtudiants;
    }

    @Override
    public int getNbEtudiantsRedoublants() {
        int nbEtudiants = 0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS nb_redoublants FROM etudiant WHERE (note_math + note_phys) / 2 < 10")) {
            if (rs.next()) {
                nbEtudiants = rs.getInt("nb_redoublants");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nbEtudiants;
    }
}
