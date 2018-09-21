package com.mobitechs.woodsnipe.webService;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService {

	private static String NAMESPACE = "http://tempuri.org/";
	private static String URL = "http://wood.atraiu.in/woodwebservice.asmx";
	private static String SOAP_ACTION = "http://tempuri.org/";

	public static String CreateLogin(String mobileNo, String password, String webMethName) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo celsiusPI = new PropertyInfo();
		// Set Name
		celsiusPI.setName("UserName");
		// Set Value
		celsiusPI.setValue(mobileNo);
		// Set dataType
		celsiusPI.setType(String.class);
		// Add the property to request object
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Password");
		celsiusPI.setValue(password);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}

		return resTxt;
	}

    public static String PunchIN(String userId, String latLong, String method) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, method);
		// Property which holds input parameters
		PropertyInfo celsiusPI = new PropertyInfo();
		// Set Name
		celsiusPI.setName("EmpID");
		// Set Value
		celsiusPI.setValue(userId);
		// Set dataType
		celsiusPI.setType(String.class);
		// Add the property to request object
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("LatLong");
		celsiusPI.setValue(latLong);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+method, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}

		return resTxt;
    }

	public static String PunchOut(String userId, String attendanceId, String method) {

		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, method);
		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("EmpID");
		celsiusPI.setValue(userId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("AttendanceID");
		celsiusPI.setValue(attendanceId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION+method, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}

		return resTxt;
	}


	public static String SaveUserLocation(String userId, String latitude, String longitude, String method) {

		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, method);
		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("EmpID");
		celsiusPI.setValue(userId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Lat");
		celsiusPI.setValue(latitude);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Lang");
		celsiusPI.setValue(longitude);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION+method, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}

	public static String ShowSchoolList(String userId, String method) {
		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, method);
		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("EmpID");
		celsiusPI.setValue(userId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION+method, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}

		return resTxt;
	}
	public static String AttendanceDetails(String userId, int month, int year, String method) {

		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, method);
		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("EmpID");
		celsiusPI.setValue(userId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("month");
		celsiusPI.setValue(month);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("year");
		celsiusPI.setValue(year);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION+method, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}

		return resTxt;
	}

	public static String SchoolCheckIn(String userId,String schoolId, String latLon, String method) {
		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, method);
		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("EmpID");
		celsiusPI.setValue(userId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("SchoolID");
		celsiusPI.setValue(schoolId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("CheckInLatLong");
		celsiusPI.setValue(latLon);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION+method, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}

	public static String TodaysBday(String bDate, String method) {
		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, method);
		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("bDate");
		celsiusPI.setValue(bDate);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION+method, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}

	public static String SchoolCheckOut(String userId, String schoolId, String schoolCheckInId, String typesName, String response, String contactPerson, String email, String contactNo, String boardName, String categoryName, String strengthOfClass, String sampleOfMonthName, String orderOfMonthName, String resonForVisitName, String radioValueTsTl, boolean isIntrested, String statusOfCallName, String comment, boolean isNextVisit, String selectedReminderDate, String remark, String latitude, String longitude, String method) {

		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, method);
		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("UserId");
		celsiusPI.setValue(userId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("SchoolId");
		celsiusPI.setValue(schoolId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("SchoolCheckinId");
		celsiusPI.setValue(schoolCheckInId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("TypesName");
		celsiusPI.setValue(typesName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("response");
		celsiusPI.setValue(response);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("contactPerson");
		celsiusPI.setValue(contactPerson);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("email");
		celsiusPI.setValue(email);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("contactNo");
		celsiusPI.setValue(contactNo);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("boardName");
		celsiusPI.setValue(boardName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("categoryName");
		celsiusPI.setValue(categoryName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("strengthOfClass");
		celsiusPI.setValue(strengthOfClass);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("sampleOfMonthName");
		celsiusPI.setValue(sampleOfMonthName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("orderOfMonthName");
		celsiusPI.setValue(orderOfMonthName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("resonForVisitName");
		celsiusPI.setValue(resonForVisitName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("radioValueTsTl");
		celsiusPI.setValue(radioValueTsTl);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("isIntrested");
		celsiusPI.setValue(isIntrested);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("statusOfCallName");
		celsiusPI.setValue(statusOfCallName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("comment");
		celsiusPI.setValue(comment);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("isNextVisit");
		celsiusPI.setValue(isNextVisit);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("selectedReminderDate");
		celsiusPI.setValue(selectedReminderDate);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("remark");
		celsiusPI.setValue(remark);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("lattitude");
		celsiusPI.setValue(latitude);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("longitude");
		celsiusPI.setValue(longitude);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION+method, envelope);
			SoapPrimitive response2 = (SoapPrimitive) envelope.getResponse();
			resTxt = response2.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}
}
