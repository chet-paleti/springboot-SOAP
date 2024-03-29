package com.chet.springboot_soap.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.chet.courses.CourseDetails;
import com.chet.courses.DeleteCourseDetailsRequest;
import com.chet.courses.DeleteCourseDetailsResponse;
import com.chet.courses.GetAllCourseDetailsRequest;
import com.chet.courses.GetAllCourseDetailsResponse;
import com.chet.courses.GetCourseDetailsRequest;
import com.chet.courses.GetCourseDetailsResponse;
import com.chet.springboot_soap.soap.bean.Course;
import com.chet.springboot_soap.soap.service.CourseDetailsService;

@Endpoint
public class CourseDetailsEndpoint {
	
	 @Autowired
	    CourseDetailsService service;

	    // method
	    // input - GetCourseDetailsRequest
	    // output - GetCourseDetailsResponse

	    // http://in28minutes.com/courses
	    // GetCourseDetailsRequest
	    @PayloadRoot(namespace = "http://chet.com/courses", localPart = "GetCourseDetailsRequest")
	    @ResponsePayload
	    public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {

	        Course course = service.findById(request.getId());

	        return mapCourseDetails(course);
	    }

	    private GetCourseDetailsResponse mapCourseDetails(Course course) {
	        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
	        response.setCourseDetails(mapCourse(course));
	        return response;
	    }

	    private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
	        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
	        for (Course course : courses) {
	            CourseDetails mapCourse = mapCourse(course);
	            response.getCourseDetails().add(mapCourse);
	        }
	        return response;
	    }

	    private CourseDetails mapCourse(Course course) {
	        CourseDetails courseDetails = new CourseDetails();

	        courseDetails.setId(course.getId());

	        courseDetails.setName(course.getName());

	        courseDetails.setDescription(course.getDescription());
	        return courseDetails;
	    }

	    @PayloadRoot(namespace = "http://chet.com/courses", localPart = "GetAllCourseDetailsRequest")
	    @ResponsePayload
	    public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
	            @RequestPayload GetAllCourseDetailsRequest request) {

	        List<Course> courses = service.findAll();

	        return mapAllCourseDetails(courses);
	    }
	    
	    @PayloadRoot(namespace = "http://chet.com/courses", localPart = "DeleteCourseDetailsRequest")
	    @ResponsePayload
	    public DeleteCourseDetailsResponse deleteCourseDetailsRequest(
	            @RequestPayload DeleteCourseDetailsRequest request) {

	        int status = service.deleteById(request.getId());

	        DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
	        response.setStatus(status);

	        return response;
	    }

}
