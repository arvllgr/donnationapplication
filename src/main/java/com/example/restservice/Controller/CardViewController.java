package com.example.restservice.Controller;

import com.example.restservice.Model.Card;
import com.example.restservice.Model.CustomUserDetails;
import com.example.restservice.Model.User;
import com.example.restservice.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/card")
public class CardViewController {

    @Autowired
    private CardService cardService;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String showCardConnectedUser(Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        Card card = cardService.getCardByUserId(user.getId());

        if (card != null) {
            model.addAttribute("card", card);
            model.addAttribute("user_name", user.getUsername());
            return "card";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value = "/annonce/{annonceId}", produces = MediaType.TEXT_HTML_VALUE)
    public String AddAnnonceinCard(@PathVariable Long annonceId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        cardService.addAnnonce(user.getId(), annonceId);

        return "redirect:/card";
    }

    @DeleteMapping(value = "/annonce/{annonceId}", produces = MediaType.TEXT_HTML_VALUE)
    public String removeAnnonceFromCard(@PathVariable Long annonceId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        cardService.removeAnnonce(user.getId(), annonceId);

        return "redirect:/card";
    }

    @DeleteMapping(value = "/lot/{lotId}", produces = MediaType.TEXT_HTML_VALUE)
    public String removeLotFromCard(@PathVariable Long lotId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        cardService.removeLot(user.getId(), lotId);

        return "redirect:/card";
    }
}