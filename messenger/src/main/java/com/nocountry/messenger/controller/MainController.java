package com.nocountry.messenger.controller;

import com.nocountry.messenger.dto.response.FriendInvitationResponse;
import com.nocountry.messenger.security.service.UserDetailsServiceImpl;
import com.nocountry.messenger.service.impl.FriendshipInvitationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messenger")
public class MainController {
    
    @Autowired
    private UserDetailsServiceImpl clientServiceImpl;
    
    @Autowired
    private FriendshipInvitationServiceImpl friendshipInvitationServiceImpl;
    
    @PostMapping("/createFriendshipInvitation/{receiver}")
    public String createFriendshipInvitation(Authentication loggedUser, @PathVariable String receiver,  ModelMap model) {
        try {
            String sender = loggedUser.getName();
            friendshipInvitationServiceImpl.createFriendshipInvitation(sender, receiver);
            return null; // agregar
        } catch (Exception e) {
            model.addAttribute("error", "No se logro encontrar las invitaciones pendientes");
            return "redirect:/"; // agregar
        }
        
    }

    @PostMapping("/respondFriendshipInvitation")
    public String respondFriendshipInvitation(@RequestBody FriendInvitationResponse response, ModelMap model) {
        try {
            friendshipInvitationServiceImpl.respondFriendshipInvitation(response);
            return "Respuesta exitosa";
        } catch (Exception e) {
            model.addAttribute("error", "No se encontró el usuario");
            return null;
            
        } 
      
    }
    
    /*
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
*/
    
    

}