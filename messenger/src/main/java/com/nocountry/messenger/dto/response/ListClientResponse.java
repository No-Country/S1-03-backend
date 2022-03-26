package com.nocountry.messenger.dto.response;

import java.util.List;

public class ListClientResponse {

    private List<ClientResponse> clients;

    public List<ClientResponse> getClients() {
        return clients;
    }

    public void setClients(List<ClientResponse> clients) {
        this.clients = clients;
    }
}
