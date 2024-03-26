package com.etudiant.resources;

import com.etudiant.dao.EtudiantDAO;
import com.etudiant.dao.EtudiantDAOImpl;
import com.etudiant.model.Etudiant;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/etudiants")
public class EtudiantResource {

    private EtudiantDAO etudiantDAO = new EtudiantDAOImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Etudiant> getAllEtudiants() {
        return etudiantDAO.getAllEtudiants();
    }

    @GET
    @Path("/{numEt}")
    @Produces(MediaType.APPLICATION_JSON)
    public Etudiant getEtudiantById(@PathParam("numEt") int numEt) {
        return etudiantDAO.getEtudiantById(numEt);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Etudiant addEtudiant(Etudiant etudiant) {
        etudiantDAO.addEtudiant(etudiant);
        return etudiant;
    }

    @PUT
    @Path("/{numEt}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Etudiant updateEtudiant(@PathParam("numEt") int numEt, Etudiant etudiant) {
        etudiant.setNumEt(numEt);
        etudiantDAO.updateEtudiant(etudiant);
        return etudiant;
    }

    @DELETE
    @Path("/{numEt}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteEtudiant(@PathParam("numEt") int numEt) {
        etudiantDAO.deleteEtudiant(numEt);
    }

    @GET
    @Path("/stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Double> getStats() {
        Map<String, Double> stats = new HashMap<>();
        stats.put("Moyenne de la Classe", etudiantDAO.getMoyenneClasse());
        stats.put("Moyenne Min", etudiantDAO.getMoyenneMin());
        stats.put("Moyenne Max", etudiantDAO.getMoyenneMax());
        return stats;
    }

    @GET
    @Path("/admis")
    @Produces(MediaType.APPLICATION_JSON)
    public int getNbEtudiantsAdmis() {
        return etudiantDAO.getNbEtudiantsAdmis();
    }

    @GET
    @Path("/redoublants")
    @Produces(MediaType.APPLICATION_JSON)
    public int getNbEtudiantsRedoublants() {
        return etudiantDAO.getNbEtudiantsRedoublants();
    }
}
