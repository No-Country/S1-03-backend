package com.nocountry.messenger.model.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "invitation")
@Data
public class FriendshipInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invitation")
    private Long idInvitation;

    @ManyToOne // (optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    private Client sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Client receiver;

    @Column(name = "state", nullable = false)
    public static EFriendshipInvitationState state;

    @Column(name = "creationTimestamp", nullable = false)
    private Date creationTimestamp;

    @JoinColumn(name = "answerTimestamp")
    private Date answerTimestamp;

    public static EFriendshipInvitationState getState() {
        return state;
    }

    public static void setState(EFriendshipInvitationState state) {
        FriendshipInvitation.state = state;
    }
}
