package com.traininginstitute.dao;

import java.util.List;
import com.traininginstitute.pojo.AdmissionRequest;

public interface AdmissionRequestDao {

 List<AdmissionRequest>	viewAllRequestsForInstitute(
			String instituteID);
 
 boolean submitRequest(AdmissionRequest request);
 
 AdmissionRequest getRequestInfo(int requestID);
 
 boolean isAlreadyRequested(AdmissionRequest request);
 
 List<AdmissionRequest> getAllRequestsForCourse(
		 int courseID);
 
 List<AdmissionRequest> getAllRequestsForUser(
		 String userID);
 
 boolean approveRequest(int requestID);
 boolean rejectRequest(int requestID);
}
