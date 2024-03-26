package com.etudiant.dao;

import com.etudiant.model.Etudiant;
import java.util.List;
public interface EtudiantDAO {
    List<Etudiant> getAllEtudiants();
    Etudiant getEtudiantById(int numEt);
    void addEtudiant(Etudiant etudiant);
    void updateEtudiant(Etudiant etudiant);
    void deleteEtudiant(int numEt);
    double getMoyenneClasse();
    double getMoyenneMin();
    double getMoyenneMax();
    int getNbEtudiantsAdmis();
    int getNbEtudiantsRedoublants();
}