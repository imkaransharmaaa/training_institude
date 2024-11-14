package com.traininginstitute.daoimpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.traininginstitute.dao.AdmissionRequestDao;
import com.traininginstitute.pojo.AdmissionRequest;

@Component
public class AdmissionRequestDaoImpl 
	implements AdmissionRequestDao {

	@Autowired
	private JdbcTemplate jTemplate;

	@Override
	public List<AdmissionRequest> 
	viewAllRequestsForInstitute(String instituteID) {
	
		List<AdmissionRequest> lst = new ArrayList<>();
		String query = "SELECT * FROM admissionrequest"
				+ " WHERE instituteID = ?";
		try {
			lst = jTemplate.query(query,
			new BeanPropertyRowMapper<AdmissionRequest>(
			AdmissionRequest.class),instituteID);
			
			return lst;
		}
		catch(EmptyResultDataAccessException exc) {
			return lst;
		}
		catch (Exception e) {
			e.printStackTrace();
			lst.clear();
			return lst;
		}
	}

	@Override
	public boolean submitRequest(AdmissionRequest request) {
		String query = "INSERT INTO admissionrequest"
+ "(userID,courseID,instituteID,requestDate,"
+ "status,rejected) VALUES (?,?,?,?,?,?)";
		
		try {
			int count = jTemplate.update(query,
new Object[] {request.getUserID(),request.getCourseID(),
request.getInstituteID(),request.getRequestDate(),
false,false});
			
			if(count > 0)
				return true;
			else
				return false;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public AdmissionRequest getRequestInfo(int requestID) {
		
		AdmissionRequest request = null;
		
		String query = "SELECT * FROM admissionrequest"
				+ " WHERE requestID = ?";
		try {
			request = jTemplate.queryForObject(query,
			new BeanPropertyRowMapper<AdmissionRequest>(
			AdmissionRequest.class),requestID);
			
			return request;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isAlreadyRequested(
			AdmissionRequest request) {
		
		AdmissionRequest req = null;
		
		String query = "SELECT * FROM admissionrequest"
				+ " WHERE userID = ? AND courseID = ?"
		   + " AND instituteID = ? AND status = false"
				+ " AND rejected = false";
		try {
			req = jTemplate.queryForObject(query,
			new BeanPropertyRowMapper<AdmissionRequest>(
			AdmissionRequest.class),new Object[] {
			request.getUserID(),request.getCourseID(),
			request.getInstituteID()});
			
			if(req != null)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<AdmissionRequest> getAllRequestsForCourse(
			int courseID) {
		List<AdmissionRequest> lst = new ArrayList<>();
		String query = "SELECT * FROM admissionrequest"
				+ " WHERE courseID = ?";
		try {
			lst = jTemplate.query(query,
			new BeanPropertyRowMapper<AdmissionRequest>(
			AdmissionRequest.class),courseID);
			
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			lst.clear();
			return lst;
		}
	}

	@Override
	public List<AdmissionRequest> getAllRequestsForUser(
			String userID) {
		List<AdmissionRequest> lst = new ArrayList<>();
		String query = "SELECT * FROM admissionrequest"
				+ " WHERE userID = ?";
		try {
			lst = jTemplate.query(query,
			new BeanPropertyRowMapper<AdmissionRequest>(
			AdmissionRequest.class),userID);
			
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			lst.clear();
			return lst;
		}
	}

	@Override
	public boolean approveRequest(int requestID) {
		String query = "UPDATE admissionrequest"
				+ " SET status = true WHERE requestID = ?";
						
						try {
							int count = jTemplate.update(query,
				requestID);
							
							if(count > 0)
								return true;
							else
								return false;
						}
						catch(Exception ex) {
							ex.printStackTrace();
							return false;
						}
	}
	
	@Override
	public boolean rejectRequest(int requestID) {
		String query = "UPDATE admissionrequest"
			+ " SET rejected = true WHERE requestID = ?";
						
						try {
							int count = jTemplate.update(query,
				requestID);
							
							if(count > 0)
								return true;
							else
								return false;
						}
						catch(Exception ex) {
							ex.printStackTrace();
							return false;
						}
	}	
}
