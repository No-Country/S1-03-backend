package com.nocountry.messenger.controller;

import com.nocountry.messenger.exception.custom.FriendshipInvitationException;
import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.security.service.UserDetailsServiceImpl;
import com.nocountry.messenger.service.impl.FriendshipInvitationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    
    @Autowired
    private UserDetailsServiceImpl clientServiceImpl;
    
    @Autowired
    private FriendshipInvitationServiceImpl friendshipInvitationServiceImpl;

    @PostMapping(path = "/requestFriendship")
    public String requestFriendship(@RequestParam long newFriendId, Authentication loggedUser, ModelMap modelo) { // Principal principal La pagina de la que me guio decia usar ese objeto que tiene que ver con seguridad pero no entiendo su uso
        try {
            Client LoggedUser = clientServiceImpl.getByUserName(loggedUser.getName());
            Client NewFriend = clientServiceImpl.getById(newFriendId);
            friendshipInvitationServiceImpl.createFriendshipInvitation(LoggedUser, NewFriend);
            return null;
        } catch (FriendshipInvitationException friendshipInvitationException ) {
            modelo.addAttribute("error", "No se logró añadir al amigo");
            return "redirect:/NewFriend/NewFriend.getId()";
        } catch (Exception e) {
            modelo.addAttribute("error", "No se encontró el usuario");
            return "redirect:/";
            
        } 
      
    }

}