package com.mobitechs.woodsnipe.webService;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;

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

	public static String PunchINOrNot(String userId, String method) {

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

    public static String PunchIN(String userId, String latLong, String method) {
		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, method);
		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("EmpID");
		celsiusPI.setValue(userId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("LatLong");
		celsiusPI.setValue(latLong);
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

	public static String InsertSchool(String userId, String schoolName, String schoolContactNo, String schoolEmail, String schoolAddress, String method) {

		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, method);
		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("EmpID");
		celsiusPI.setValue(userId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("SchoolName");
		celsiusPI.setValue(schoolName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("ContactNo");
		celsiusPI.setValue(schoolContactNo);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Email");
		celsiusPI.setValue(schoolEmail);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Address");
		celsiusPI.setValue(schoolAddress);
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

    public static String GetStatusOfCall(String webMethName) {
		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
    }

	public static String CustomerTypes(String webMethName) {
		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}

	public static String GetBoardList(String webMethName) {
		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}

	public static String GetCategoryList(String webMethName) {
		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}

	public static String GetReasonForVisit(String webMethName) {
		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}

    public static String SubmitReasonForVisitOther(String schoolDetailsId, String userId, String reasonForVisitName, String otherReasonForVisit, String method) {

		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, method);
		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("schoolDetailsId");
		celsiusPI.setValue(schoolDetailsId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("userId");
		celsiusPI.setValue(userId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("reasonForVisitName");
		celsiusPI.setValue(reasonForVisitName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("otherReasonForVisit");
		celsiusPI.setValue(otherReasonForVisit);
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

	public static String SubmitReasonForVisitPayment(String schoolDetailsId, String userId, String reasonForVisitName, String paymentDate, String paymentMode, String paymentAmount, File paymentImgFile, String method) {

		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, method);
		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("schoolDetailsId");
		celsiusPI.setValue(schoolDetailsId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("userId");
		celsiusPI.setValue(userId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("reasonForVisitName");
		celsiusPI.setValue(reasonForVisitName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("paymentDate");
		celsiusPI.setValue(paymentDate);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("paymentMode");
		celsiusPI.setValue(paymentMode);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("paymentAmount");
		celsiusPI.setValue(paymentAmount);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("paymentImgFile");
		celsiusPI.setValue(paymentImgFile);
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

	public static String SubmitReasonForVisitSample(String schoolDetailsId, String userId, String reasonForVisitName, String tinyStepsQty, String littleStepsQty, String computerQty, String grammarQty, String cursive1, String cursive2, String cursive3, String noteBookQty, String drawingBookQty, String scrapBookQty, String graphBookQty, String sampleOther, String sampleOtherQty, String sampleContactPerson, String sampleContactNo, String sampleEmailId, String method) {


		String resTxt = null;
		SoapObject request = new SoapObject(NAMESPACE, method);
		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("schoolDetailsId");
		celsiusPI.setValue(schoolDetailsId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("userId");
		celsiusPI.setValue(userId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("reasonForVisitName");
		celsiusPI.setValue(reasonForVisitName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("tinyStepsQty");
		celsiusPI.setValue(tinyStepsQty);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("littleStepsQty");
		celsiusPI.setValue(littleStepsQty);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("computerQty");
		celsiusPI.setValue(computerQty);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("grammarQty");
		celsiusPI.setValue(grammarQty);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("cursive1");
		celsiusPI.setValue(cursive1);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);


		celsiusPI=new PropertyInfo();
		celsiusPI.setName("cursive2");
		celsiusPI.setValue(cursive2);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("cursive3");
		celsiusPI.setValue(cursive3);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("noteBookQty");
		celsiusPI.setValue(noteBookQty);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("drawingBookQty");
		celsiusPI.setValue(drawingBookQty);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("scrapBookQty");
		celsiusPI.setValue(scrapBookQty);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("graphBookQty");
		celsiusPI.setValue(graphBookQty);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("sampleOther");
		celsiusPI.setValue(sampleOther);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("sampleOtherQty");
		celsiusPI.setValue(sampleOtherQty);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("sampleContactPerson");
		celsiusPI.setValue(sampleContactPerson);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("sampleContactNo");
		celsiusPI.setValue(sampleContactNo);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("sampleEmailId");
		celsiusPI.setValue(sampleEmailId);
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

    public static String SchoolCheckOut2(String userId, String schoolId, String schoolCheckInId, String typesName, String schoolSection, String otherCustomerType, String contactPerson, String email, String contactNo, String boardName, String categoryName, String seriesPrefrence, String storyBook, String strengthOfClass, String sampleOfMonthName, String orderOfMonthName, String isIntrested, String statusOfCallName, String comment, String isNextVisit, String selectedReminderDate, String remark, String latitude, String longitude, String method) {


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
		celsiusPI.setName("CustomerType");
		celsiusPI.setValue(typesName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("schoolSection");
		celsiusPI.setValue(schoolSection);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("CustomerTypeOther");
		celsiusPI.setValue(otherCustomerType);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("ContactPerson");
		celsiusPI.setValue(contactPerson);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("EmailID");
		celsiusPI.setValue(email);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("ContactNo");
		celsiusPI.setValue(contactNo);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Board");
		celsiusPI.setValue(boardName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Category");
		celsiusPI.setValue(categoryName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("SeriesPrefrence");
		celsiusPI.setValue(seriesPrefrence);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("StoryBook");
		celsiusPI.setValue(storyBook);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("StrenghtPreclass");
		celsiusPI.setValue(strengthOfClass);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("SampleMonth");
		celsiusPI.setValue(sampleOfMonthName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("OrderMonth");
		celsiusPI.setValue(orderOfMonthName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Response");
		celsiusPI.setValue(isIntrested);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("StatusOfCan");
		celsiusPI.setValue(statusOfCallName);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Comment");
		celsiusPI.setValue(comment);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("IsNextVisit");
		celsiusPI.setValue(isNextVisit);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("VisitDate");
		celsiusPI.setValue(selectedReminderDate);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Remark");
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

    public static String GetMonthList(String webMethName) {
        String resTxt = null;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resTxt = response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }
}
