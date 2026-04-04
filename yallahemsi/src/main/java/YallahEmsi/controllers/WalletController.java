package YallahEmsi.controllers;

import YallahEmsi.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    // API bach n-cherjiw l'flouss (ex: ?utilisateurId=2&montant=50)
    @PostMapping("/recharger")
    public String recharger(@RequestParam Integer utilisateurId, @RequestParam BigDecimal montant) {
        return walletService.rechargerWallet(utilisateurId, montant);
    }
}