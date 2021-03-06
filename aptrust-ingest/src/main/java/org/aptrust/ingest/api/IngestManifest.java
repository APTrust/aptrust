package org.aptrust.ingest.api;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An object that contains the the data describing an ingest operation with
 * JAXB Xml binding annotations to support XML serialization.
 */
@XmlRootElement
public class IngestManifest {

    private String id;

    private Description description;

    private IngestPackage[] submit;

    public IngestManifest() {
    }

    /**
     * Instantiates a simple manifest with the given label and list of packages
     * to submit.
     * @param label the label to be displayed for this ingest operation.
     * @param username the name/id of the user creating this manifest
     * @param submit a list of packages to submit
     */
    public IngestManifest(String label, String username, IngestPackage[] submit) {
        description = new Description();
        description.setName(label);
        description.setSuppliedUsername(username);
        this.submit = submit;
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description d) {
        description = d;
    }

    @XmlElementWrapper(name="packages")
    @XmlElement(name="package")
    public IngestPackage[] getPackagesToSubmit() {
        return submit;
    }

    /**
     *  Walks through the packages and counts the total number of objects 
     *  referenced.
     * @return the total number of objects in all the packages combined
     */
    public int getTotalObjectsToSubmit() {
        int result = 0;
        for (IngestPackage p : submit) {
            result += p.getDigitalObjects().length;
        }
        return result;
    }

    public void setPackagesToSubmit(IngestPackage[] p) {
        submit = p;
    }

    /**
     * Use of the manifest to remove/delete pacakges is not yet supported.
     */
    public List<String> listPackageIdsToRemove() {
        return Collections.emptyList();
    }

    public static class Description {

        private String name;

        private String suppliedUsername;

        private Date ingestInitiated = new Date();

        @XmlElement
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement
        public String getSuppliedUsername() {
            return suppliedUsername;
        }

        public void setSuppliedUsername(String suppliedUsername) {
            this.suppliedUsername = suppliedUsername;
        }

        @XmlElement
        public Date getIngestInitiated() {
            return ingestInitiated;
        }

        public void setIngestInitiated(Date ingestInitated) {
            this.ingestInitiated = ingestInitated;
        }
    }
}
