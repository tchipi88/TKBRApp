/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Commande;
import com.itsolution.tkbr.domain.Compte;
import com.itsolution.tkbr.domain.Decaissement;
import com.itsolution.tkbr.domain.Encaissement;
import com.itsolution.tkbr.domain.Reglement;
import com.itsolution.tkbr.domain.enumeration.CaisseMouvementMotif;
import com.itsolution.tkbr.domain.enumeration.CompteAnalytiqueType;
import com.itsolution.tkbr.domain.enumeration.EtatCommande;
import com.itsolution.tkbr.domain.enumeration.SensEcritureComptable;
import com.itsolution.tkbr.repository.CommandeRepository;
import com.itsolution.tkbr.repository.ReglementRepository;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class ReglementService {

    @Autowired
    ReglementRepository reglementRepository;

    @Autowired
    CompteService cs;
    @Autowired
    DecaissementService decaissementService;
    @Autowired
    EncaissementService encaissementService;
    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    EcritureCompteAnalytiqueService ecritureCompteAnalytiqueService;

    public Reglement save(Reglement r) throws Exception {

        if (r.getId() != null) {
            throw new Exception("Mise à jour des reglements interdites");
        }

        //http://www.compta-facile.com/comptabilisation-operations-bancaires/
        //Verifier que la commande n'est pas deja regle
        // if (r.getCommande().getReglements().stream().collect())
        //@todo  crediter la caisse
        BigDecimal totalttc = r.getMontant();

        switch (r.getCommande().getType()) {
            case ACHAT: {

                ecritureCompteAnalytiqueService.create(r.getCommande().getFournisseur(), CompteAnalytiqueType.FOURNISSEUR, totalttc, SensEcritureComptable.D,"Versement pour Achat N:"+r.getCommande().getId());

                Compte compteFournisseurs = cs.getCompteFournisseurs();
                compteFournisseurs.setDebit(totalttc.add(compteFournisseurs.getDebit()));
                cs.save(compteFournisseurs);

                switch (r.getMode()) {
                    case ESPECES: {

                        Compte compteCaisse = cs.getCompteCaisse();
                        compteCaisse.setCredit(totalttc.add(compteCaisse.getCredit()));
                        cs.save(compteCaisse);

                        Decaissement decaissement = new Decaissement();
                        decaissement.setMontant(r.getMontant());
                        decaissement.setDateVersement(r.getDateVersement());
                        decaissement.setModePaiement(r.getMode());
                        decaissement.setMotif(CaisseMouvementMotif.ACHAT);
                        decaissement.setCommentaires("Mvt commande " + r.getCommande().getId());

                        decaissementService.save(decaissement);

                        break;
                    }
                    case CHEQUE: {
                        Compte compteBanque = cs.getCompteBanque();
                        compteBanque.setCredit(totalttc.add(compteBanque.getCredit()));
                        cs.save(compteBanque);
                        break;
                    }
                    case VIREMENT: {
                        Compte compteBanque = cs.getCompteBanque();
                        compteBanque.setCredit(totalttc.add(compteBanque.getCredit()));
                        cs.save(compteBanque);
                        break;
                    }

                }

                break;
            }
            case VENTE: {

                ecritureCompteAnalytiqueService.create(r.getCommande().getClient(), CompteAnalytiqueType.CLIENT, totalttc, SensEcritureComptable.C,"Versement pour Vente N:"+r.getCommande().getId());

                Compte compteClient = cs.getCompteClient();
                compteClient.setCredit(totalttc.add(compteClient.getCredit()));
                cs.save(compteClient);

                switch (r.getMode()) {
                    case ESPECES: {

                        Compte compteCaisse = cs.getCompteCaisse();
                        compteCaisse.setDebit(totalttc.add(compteCaisse.getDebit()));
                        cs.save(compteCaisse);

                        Encaissement encaissement = new Encaissement();
                        encaissement.setMontant(r.getMontant());
                        encaissement.setDateVersement(r.getDateVersement());
                        encaissement.setModePaiement(r.getMode());
                        encaissement.setMotif(CaisseMouvementMotif.VENTE);
                        encaissement.setCommentaires("Mvt commande " + r.getCommande().getId());

                        encaissementService.save(encaissement);

                        break;
                    }
                    case CHEQUE: {
                        Compte compteCheque = cs.getCompteCheque();
                        compteCheque.setDebit(totalttc.add(compteCheque.getDebit()));
                        cs.save(compteCheque);
                        break;
                    }
                    case VIREMENT: {
                        Compte compteBanque = cs.getCompteBanque();
                        compteBanque.setDebit(totalttc.add(compteBanque.getDebit()));
                        cs.save(compteBanque);
                        break;
                    }
                }
                break;
            }
            default: {
                throw new Exception("Type de commande non spécifié");
            }
        }

        Commande commande = r.getCommande();
        commande.setMontantPaye(commande.getMontantPaye() == null ? r.getMontant() : commande.getMontantPaye().add(r.getMontant()));

        commandeRepository.save(commande);

        return reglementRepository.save(r);
    }
    
     @Autowired
    protected ResourceLoader resourceLoader;

    @Autowired
    protected DataSource dataSource;

    public Resource print(Reglement reglement) throws Exception {
        File uploadedfile = new File("." + File.separator + "reports");
        if (!uploadedfile.exists()) {
            uploadedfile.mkdirs();
        }
        
        String destfile = uploadedfile.getAbsolutePath() + File.separator+"TicketReglement" + reglement.getId() + ".pdf";

        String reportfile = "classpath:com/itsolution/tkbr/reports/TicketReglement.jasper";
        //remplissage des parametres du report
        Map params = new HashMap();
        //fill report
        JasperPrint jp = JasperFillManager.fillReport(
                resourceLoader.getResource(reportfile).getInputStream(), //file jasper
                params, //params report
                dataSource.getConnection());  //datasource
        JasperExportManager.exportReportToPdfFile(jp, destfile);

        return resourceLoader.getResource("file:" + destfile);
    }

}
