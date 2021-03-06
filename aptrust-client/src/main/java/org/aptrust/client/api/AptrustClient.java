package org.aptrust.client.api;

import java.util.Date;
import java.util.List;

import org.aptrust.client.impl.AptrustPackageDetail;
import org.aptrust.common.exception.AptrustException;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Defines a client interface that exposes methods to get information about
 * content in the AP Trust system.
 * 
 * @author Daniel Bernstein
 * @created Dec 11, 2012
 * 
 */
public interface AptrustClient {

    /**
     * 
     * @return
     * @throws AptrustException
     */
    List<InstitutionInfo> getInstitutions() throws AptrustException;

    public InstitutionInfo getInstitutionInfo(String institutionId) throws AptrustException;
        
    /**
     * Returns a summary for the specified institution.
     * 
     * @param id
     *            the unique identifier for the institution
     * @return
     * @throws AptrustException
     */
    @PreAuthorize("hasPermission(#institutionId, 'institutionId', 'admin')")
    public Summary getSummary(String institutionId) throws AptrustException;

    /**
     * Returns a list of ingest process summaries ordered from most recently
     * started to least recently started.
     * 
     * @param institutionId
     * @param startDate
     *            optional
     * @param name
     *            optional
     * @param status
     *            optional
     * @return
     * @throws AptrustException
     * @deprecated
     */
    @PreAuthorize("hasPermission(#institutionId, 'institutionId', 'admin')")
    List<IngestProcessSummary> findIngestProcesses(String institutionId,
                                                   Date startDate,
                                                   String name,
                                                   IngestStatus status)
        throws AptrustException;

    /**
     * Returns a partial list of all ingest process summaries ordered from most
     * recently started to least recently started.
     * 
     * @param institutionId
     * @param startDate
     *            optional
     * @param name
     *            optional
     * @param status
     *            optional
     * @param start
     *            the offset from the beginning of the list at which to start
     *            returning results 
     * @param rows
     *            the maximum number of result to return
     * @return
     * @throws AptrustException
     */
    public List<IngestProcessSummary> findIngestProcesses(String institutionId, Date startDate, String name, IngestStatus status, int start, int rows) throws AptrustException;

    /**
     * 
     * @param institutionId
     * @param searchParams
     * @param facetFields
     * @return
     * @throws AptrustException
     */
    @PreAuthorize("hasPermission(#institutionId, 'institutionId', 'admin')")
    public PackageSummaryQueryResponse findPackageSummaries(String institutionId,
                                                     SearchParams searchParams,
                                                     String ... facetFields)
        throws AptrustException;

    /**
     * 
     * @param institutionId
     * @param packageId
     * @return
     * @throws AptrustException
     */
    @PreAuthorize("hasPermission(#institutionId, 'institutionId', 'admin')")
    public AptrustPackageDetail getPackageDetail(String institutionId, String packageId)
        throws AptrustException;

    /**
     * 
     * @param institutionId
     * @param packageId
     * @param objectId
     * @return
     * @throws AptrustException
     */
    @PreAuthorize("hasPermission(#institutionId, 'institutionId', 'admin')")
    public AptrustObjectDetail getObjectDetail(String institutionId, String packageId, String objectId)
        throws AptrustException;
    
    /**
     * 
     * @param institutionId The institution identifieer
     * @param staging Set to true to indicate staging report.  Otherwise processing space storage info is returned.
     * @return
     * @throws AptrustException
     */
    @PreAuthorize("hasPermission(#institutionId, 'institutionId', 'admin')")
    public String getStorageReport(String institutionId, boolean staging)
        throws AptrustException;

    
    /**
     * Rebuilds indexes from the source data stored in underlying preservation system.
     * This method is intended to be used by AP Trust root administrators to support 
     * disaster recovery and schema updates.
     */
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public void rebuildIndex() throws AptrustException;
}
