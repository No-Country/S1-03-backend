package com.nocountry.messenger.repository;

import com.nocountry.messenger.model.entity.Client;
import com.nocountry.messenger.model.entity.EFriendshipInvitationState;
import com.nocountry.messenger.model.entity.FriendshipInvitation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipInvitationRepository extends JpaRepository<FriendshipInvitation, Long> {
    
    List<FriendshipInvitation> findBySenderAndReceiverAndState(Client sender, Client receiver, EFriendshipInvitationState state);
    List<FriendshipInvitation> findByReceiverAndState(Client receiver, EFriendshipInvitationState state); 

}
