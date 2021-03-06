package org.aptrust.ingest.ips.solr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aptrust.client.api.IngestStatus;
import org.aptrust.common.solr.AptrustSolrDocument;
import org.aptrust.common.solr.SolrField;
import org.aptrust.ingest.api.DigitalObject;
import org.aptrust.ingest.api.IngestManifest;
import org.aptrust.ingest.api.IngestPackage;

/**
 * A simple class that wraps an IngestManfiest and other member variables and
 * exposes annotated methods that allow for easy creation of Solr Documents for
 * "ingest" operations.
 * 
 * @see org.aptrust.common.solr.AptrustSolrDocument#createValidSolrDocument(Object)
 */
public class IngestSolrDocument {

    private String institutionId;

    private IngestManifest m;

    private IngestStatus operationStatus;

    private int progress;

    private int total;

    private Date endDate;

    private String errorMessage;

    private IngestSolrDocument() {
    }

    /**
     * Gets a IngestSolrDocument that describes a brand new ingest operation
     * for the given institution with the given manifest.
     */
    public static IngestSolrDocument newIngest(String institutionId, IngestManifest m) {
        IngestSolrDocument d = new IngestSolrDocument();
        d.institutionId = institutionId;
        d.m = m;
        d.progress = 0;
        d.total = 0;
        for (IngestPackage p : m.getPackagesToSubmit()) {
            d.total += p.getDigitalObjects().length;
        }
        d.operationStatus = IngestStatus.IN_PROGRESS;
        return d;
    }

    /**
     * Gets a IngestSolrDocument that describes an in-progress ingest operation
     * for the given institution with the given manifest.
     */
    public static IngestSolrDocument updateIngest(String institutionId, IngestManifest m, int progress) {
        IngestSolrDocument d = new IngestSolrDocument();
        d.institutionId = institutionId;
        d.m = m;
        d.total = 0;
        for (IngestPackage p : m.getPackagesToSubmit()) {
            d.total += p.getDigitalObjects().length;
        }
        d.progress = progress;
        d.operationStatus = IngestStatus.IN_PROGRESS;
        return d;
    }

    /**
     * Gets a IngestSolrDocument that describes a failed ingest operation for
     * the given institution with the given manifest and error message.
     */
    public static IngestSolrDocument failedIngest(String institutionId, IngestManifest m, String errorMessage) {
        IngestSolrDocument d = new IngestSolrDocument();
        d.institutionId = institutionId;
        d.m = m;
        d.operationStatus = IngestStatus.FAILED;
        d.errorMessage = errorMessage;
        return d;
    }

    /**
     * Gets a IngestSolrDocument that describes a completed ingest operation
     * for the given institution.
     * @param completionDate the date when the operation was completed
     */
    public static IngestSolrDocument completedIngest(String institutionId, IngestManifest m, Date completionDate) {
        IngestSolrDocument d = new IngestSolrDocument();
        d.institutionId = institutionId;
        d.m = m;
        d.operationStatus = IngestStatus.COMPLETED;
        d.endDate = completionDate;
        d.total = 0;
        for (IngestPackage p : m.getPackagesToSubmit()) {
            d.total += p.getDigitalObjects().length;
        }
        d.progress = d.total;
        return d;
    }

    @SolrField(name=AptrustSolrDocument.ID)
    public String getId() {
        return m.getId();
    }

    @SolrField(name=AptrustSolrDocument.RECORD_TYPE)
    public String getRecordType() {
        return "ingest";
    }

    @SolrField(name=AptrustSolrDocument.INSTITUTION_ID)
    public String getInstitutionId() {
        return institutionId;
    }

    @SolrField(name=AptrustSolrDocument.TITLE)
    public String getTitle() {
        return m.getDescription().getName();
    }

    @SolrField(name=AptrustSolrDocument.SUBMITTING_USER)
    public String getUser() {
        return m.getDescription().getSuppliedUsername();
    }

    @SolrField(name=AptrustSolrDocument.OPERATION_STATUS)
    public String getStatus() {
        return operationStatus.name();
    }

    @SolrField(name=AptrustSolrDocument.COMPLETED_OBJECT_COUNT)
    public int getIngestedObjects() {
        return progress;
    }

    @SolrField(name=AptrustSolrDocument.OBJECT_COUNT)
    public int getTotalObjects() {
        return total;
    }
 
    @SolrField(name=AptrustSolrDocument.OPERATION_START_DATE)
    public Date getStartDate() {
        return m.getDescription().getIngestInitiated();
    }

    @SolrField(name=AptrustSolrDocument.OPERATION_END_DATE)
    public Date getEndDate() {
        return endDate;
    }

    @SolrField(name=AptrustSolrDocument.MESSAGE)
    public String getErrorMessage() {
        return errorMessage;
    }

    @SolrField(name=AptrustSolrDocument.INCLUDED_PID)
    public List<String> getIncludedPids() {
        List<String> values = new ArrayList<String>();
        for (IngestPackage p : m.getPackagesToSubmit()){
            for (DigitalObject o : p.getDigitalObjects()) {
                values.add(o.getId());
            }
        }
        return values;
    }

    @SolrField(name=AptrustSolrDocument.INCLUDED_PACKAGE)
    public List<String> getIncludedPackageIds() {
        List<String> values = new ArrayList<String>();
        for (IngestPackage p : m.getPackagesToSubmit()){
            values.add(p.getMetadata().getId());
        }
        return values;
    }
}
