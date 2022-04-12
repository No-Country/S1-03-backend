package com.nocountry.messenger.controller;

import com.nocountry.messenger.exception.custom.FriendshipInvitationException;
import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.model.entity.EFriendshipInvitationState;
import com.nocountry.messenger.security.service.UserDetailsServiceImpl;
import com.nocountry.messenger.service.impl.FriendshipInvitationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messenger")
public class MainController {
    
    @Autowired
    private UserDetailsServiceImpl clientServiceImpl;
    
    @Autowired
    private FriendshipInvitationServiceImpl friendshipInvitationServiceImpl;
    
    @GetMapping("/createFriendshipInvitation")
    public String createFriendshipInvitation(Authentication loggedUserReceiver,  ModelMap model) {
        try {
            //friendshipInvitationServiceImpl.createFriendshipInvitation(sender, receiver)
            return null; // agregar
        } catch (Exception e) {
            model.addAttribute("error", "No se logro encontrar las invitaciones pendientes");
            return "redirect:/"; // agregar
        }
        
    }
    
    @PostMapping("/requestFriendship") // cambiar id por username
    public String requestFriendship(@RequestParam long newFriendId, Authentication loggedUser, ModelMap model) { // Principal principal La pagina de la que me guio decia usar ese objeto que tiene que ver con seguridad pero no entiendo su uso
        try {
            Client LoggedUser = clientServiceImpl.getByUserName(loggedUser.getName());
            Client NewFriend = clientServiceImpl.getById(newFriendId);
            friendshipInvitationServiceImpl.createFriendshipInvitation(LoggedUser, NewFriend);
            return null;
        } catch (FriendshipInvitationException friendshipInvitationException ) {
            model.addAttribute("error", "No se logró añadir al amigo");
            return null; // "redirect:/NewFriend/NewFriend.getId()";
        } catch (Exception e) {
            model.addAttribute("error", "No se encontró el usuario");
            return "redirect:/"; // agregar
            
        } 
      
    }
    
    @GetMapping("/readFriendshipInvitation")
    public String readFriendshipInvitation(Authentication loggedUserReceiver, ModelMap model) {
        try {
            friendshipInvitationServiceImpl.readfriendshipInvitations((Client) loggedUserReceiver, EFriendshipInvitationState.OPEN);
            return null; // agregar
        } catch (Exception e) {
            model.addAttribute("error", "No se logro encontrar las invitaciones pendientes");
            return "redirect:/"; // agregar
        }
        
    }

    
    

}