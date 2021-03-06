package org.aptrust.client.api;

/**
 * An class that encapsulates all the information about an Institutional 
 * participant in the Academic Preservation Trust.
 */
public class InstitutionInfo {

    private String id;

    private String fullName;

    public InstitutionInfo(String id, String fullName){
        this.id = id;
        this.fullName = fullName;
        
    }
    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

}
