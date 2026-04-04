package YallahEmsi.services;

import YallahEmsi.entities.Utilisateur;
import YallahEmsi.entities.Wallet;
import YallahEmsi.repositories.UtilisateurRepository;
import YallahEmsi.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // --- Fonction bach n-cherjiw l'Wallet ---
    public String rechargerWallet(Integer utilisateurId, BigDecimal montant) {

        Optional<Utilisateur> userOpt = utilisateurRepository.findById(utilisateurId);
        if (userOpt.isEmpty()) {
            return "Erreur: Had l'utilisateur makaynch!";
        }

        Utilisateur user = userOpt.get();

        // N-choufou wach 3ndou Wallet awla ba9i (ila ba9i, n-creeyewh lih f l'blassa)
        Wallet wallet = walletRepository.findByUtilisateurId(utilisateurId).orElse(new Wallet());

        if (wallet.getId() == null) {
            wallet.setUtilisateur(user);
            wallet.setSolde(BigDecimal.ZERO);
            wallet.setDevise("MAD");
        }

        // N-zidou l'flouss l'solde dyalo
        wallet.setSolde(wallet.getSolde().add(montant));
        walletRepository.save(wallet);

        return "Mabrouk! Tzadét " + montant + " MAD f l'Wallet dyal " + user.getNom();
    }
}