package YallahEmsi.services;

import YallahEmsi.entities.Paiement;
import YallahEmsi.entities.ReservationTrajet;
import YallahEmsi.entities.Wallet;
import YallahEmsi.repositories.PaiementRepository;
import YallahEmsi.repositories.ReservationTrajetRepository;
import YallahEmsi.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaiementService {

    @Autowired private PaiementRepository paiementRepository;
    @Autowired private ReservationTrajetRepository reservationRepository;
    @Autowired private WalletRepository walletRepository;

    // --- Fonction bach n-khelsou l'réservation ---
    public String payerReservation(Integer reservationId) {

        Optional<ReservationTrajet> resOpt = reservationRepository.findById(reservationId);
        if (resOpt.isEmpty()) {
            return "Erreur: L'Réservation makaynach!";
        }
        ReservationTrajet res = resOpt.get();

        // N-jibou l'wallet dyal l'Passager (li ghadi ykheles)
        Optional<Wallet> walletPassagerOpt = walletRepository.findByUtilisateurId(res.getPassager().getId());

        if (walletPassagerOpt.isEmpty() || walletPassagerOpt.get().getSolde().compareTo(res.getMontantTotal()) < 0) {
            return "Erreur: Solde insuffisant! Sserji l'wallet dyalk b3da.";
        }

        Wallet walletPassager = walletPassagerOpt.get();

        // N-jibou l'wallet dyal l'Conducteur (li ghadi yched l'flouss). Ila ma3ndouch nsawbouh lih.
        Wallet walletConducteur = walletRepository.findByUtilisateurId(res.getTrajet().getConducteur().getId()).orElse(new Wallet());
        if(walletConducteur.getId() == null) {
            walletConducteur.setUtilisateur(res.getTrajet().getConducteur());
            walletConducteur.setSolde(BigDecimal.ZERO);
        }

        // 💰 TRANSACTION 💰 : N9ess mn hna w zid hna
        walletPassager.setSolde(walletPassager.getSolde().subtract(res.getMontantTotal()));
        walletConducteur.setSolde(walletConducteur.getSolde().add(res.getMontantTotal()));

        walletRepository.save(walletPassager);
        walletRepository.save(walletConducteur);

        // N-ssjlou f l'historique dyal l'Paiement
        Paiement paiement = new Paiement();
        paiement.setReservation(res);
        paiement.setWallet(walletPassager);
        paiement.setMontantPaye(res.getMontantTotal());
        paiement.setStatutPaiement(Paiement.StatutPaiement.accepte);
        paiementRepository.save(paiement);

        return "L'khelass daze b naja7! Tqet3at " + res.getMontantTotal() + " MAD mn 3nd " + res.getPassager().getNom();
    }
}