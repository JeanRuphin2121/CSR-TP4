package org.inria.restlet.mta.internals;

/**
 * Represents a user in the system.
 */
public class ClientEntity {

    private int id_;
    private String name_;
    private String state;


    public ClientEntity(String name, String state) {
        this.name_ = name;
        this.state = state;
    }

    public int getId() {
        return id_;
    }
    public void setId(int id) {
        this.id_ = id;
    }

    public String getName() {
        return name_;
    }
    public void setName(String name) {
        this.name_ = name;
    }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

}
