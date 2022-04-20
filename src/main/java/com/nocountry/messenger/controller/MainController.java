package com.nocountry.messenger.controller;

import com.nocountry.messenger.dto.response.FriendshipResponse;
import com.nocountry.messenger.dto.response.FriendInvitationResponse;
import com.nocountry.messenger.exception.custom.ExceptionHandler;
import com.nocountry.messenger.exception.custom.FriendshipInvitationException;
import com.nocountry.messenger.service.impl.FriendshipInvitationServiceImpl;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private FriendshipInvitationServiceImpl friendshipInvitationServiceImpl;
    
    @PostMapping("/createFriendshipInvitation/{receiver}")
    public ResponseEntity<?> createFriendshipInvitation(Authentication loggedUser, @PathVariable String receiver,  ModelMap model) {
        try {
            String sender = loggedUser.getName();
            FriendInvitationResponse response = friendshipInvitationServiceImpl.createFriendshipInvitation(sender, receiver);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            model.addAttribute("error", "Error al crear la invitación.");
            return new ResponseEntity<>(model, HttpStatus.NOT_FOUND);
        }
        
    }

    @PostMapping("/respondFriendshipInvitation")
    public ResponseEntity<?> respondFriendshipInvitation(@RequestBody FriendshipResponse response, Principal principal, ModelMap model) {
        try {
            String userName = principal.getName();
            friendshipInvitationServiceImpl.respondFriendshipInvitation(userName, response);
            //model.addAttribute("status","Invitación respondida con éxito.");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (FriendshipInvitationException e) {
            return ExceptionHandler.throwError(HttpStatus.NOT_FOUND, e.getMessage());
            
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