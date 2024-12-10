package org.inria.restlet.mta.backend;

public enum EtatClient {
    WAITING_TO_ENTER, // en attente pour rentrer
    AT_THE_BUFFET, //en train de se servir (ou dattendre un remplissage)
    EATING, //en train de manger
    WAITING_FOR_THE_COOK, //en attente du cuisinier ou de la cuisson de son plat
    OUT, //sorti

}
