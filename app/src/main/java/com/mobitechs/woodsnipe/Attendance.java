package com.mobitechs.woodsnipe;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobitechs.woodsnipe.model.Attendance_items;
import com.mobitechs.woodsnipe.sessionManager.SessionManager;
import com.mobitechs.woodsnipe.webService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@TargetApi(3)
public class Attendance extends BaseActivity implements OnClickListener {
    private static final String tag = "Attendance";

    private TextView currentMonth;
    private TextView presentDaysInMonth;
    private TextView absentDaysInMonth;
    private TextView lateMarksInMonth;
    private TextView totalDaysInMonth;
    //private Button selectedDayMonthYearButton;
    //private Button btnHome;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    @SuppressLint("NewApi")
    private int month, year;
    String ResponseResult;
    String webMethName;
    String currentMonthForAttendanceDetails;
    String currentYearForAttendanceDetails;

    private String[] absentDateArrayList;
    private List<String> absentDateList = new ArrayList<String>();
    private List<String> presentDateList = new ArrayList<String>();
    private List<String> lateMarkDateList = new ArrayList<String>();
    private List<String> daysInMonthList = new ArrayList<String>();
    private List<String> schoolDaysInMonthList = new ArrayList<String>();

    private List<Attendance_items> itemsOfAttendance = new ArrayList<Attendance_items>();
    RecyclerView.Adapter adapterForAsyncTask;

    @SuppressWarnings("unused")
    @SuppressLint({"NewApi", "NewApi", "NewApi", "NewApi"})
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";
    private ProgressDialog progressDialog = null;
    String userId;
    String deptType;
    int thisYear;
    int thisMonth;
    int thisDay;
    int responseCounter = 1;

    int totalSchoolDaysInMonth;
    int totalPresentDay;
    int totalLateMarksDay;
    int totalAbsentDay;

    int totalDays = 0;


    GridView gridCalendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_attendance);

        SessionManager sessionManager = new SessionManager(Attendance.this);
        HashMap<String, String> typeOfUser = sessionManager.getUserDetails();
        userId = typeOfUser.get(SessionManager.KEY_USERID);
        deptType = typeOfUser.get(SessionManager.KEY_DEPTTYPE);

//        presentDaysInMonth = (TextView) this.findViewById(R.id.presentDaysInMonth);
//        absentDaysInMonth = (TextView) this.findViewById(R.id.absentDaysInMonth);
//        totalDaysInMonth = (TextView) this.findViewById(R.id.totalDaysInMonth);
//        lateMarksInMonth = (TextView) this.findViewById(R.id.lateMarksInMonth);

//        btnHome = (Button) this.findViewById(R.id.btnHome);
//        btnHome.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        thisYear = calendar.get(Calendar.YEAR);
        thisMonth = calendar.get(Calendar.MONTH) + 1;
        thisDay = calendar.get(Calendar.DAY_OF_MONTH);

//		selectedDayMonthYearButton = (Button) this
//				.findViewById(R.id.selectedDayMonthYear);
//		selectedDayMonthYearButton.setText("Selected: ");

        prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(this);

        nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);

        calendarView = (GridView) this.findViewById(R.id.calendar);

        // Initialised calender for current month

        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);

        currentMonth = (TextView) this.findViewById(R.id.currentMonth);
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));


        presentDateList.clear();
        daysInMonthList.clear();
        schoolDaysInMonthList.clear();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.");
        progressDialog.show();

        getAttendanceDetails(month, year);

        adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year, itemsOfAttendance);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }


    private void setGridCellAdapterToDate(int month, int year) {

        absentDateList.clear();
        presentDateList.clear();
        lateMarkDateList.clear();
        daysInMonthList.clear();
        schoolDaysInMonthList.clear();
        totalDays = 0;

        adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year, itemsOfAttendance);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }



    @Override
    public void onClick(View v) {
        if (v == prevMonth) {
            if (month <= 1) {
                month = 12;
                year--;
            } else {
                month--;
            }
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait.");
            progressDialog.show();
            getAttendanceDetails(month, year);
            setGridCellAdapterToDate(month, year);
        }
        if (v == nextMonth) {
            if (month > 11) {
                month = 1;
                year++;
            } else {
                month++;
            }
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait.");
            progressDialog.show();
            getAttendanceDetails(month, year);
            setGridCellAdapterToDate(month, year);

        }
    }

    private void getAttendanceDetails(int month, int year) {
        currentMonthForAttendanceDetails = String.valueOf(month);
        currentYearForAttendanceDetails = String.valueOf(year);

        absentDateList.clear();
        presentDateList.clear();
        lateMarkDateList.clear();
        daysInMonthList.clear();
        schoolDaysInMonthList.clear();

        //getAttendaceDetailsRecords();

        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }



    public class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            webMethName = "EmployeeAttendance";
            int currentMonthForAttendanceDetails = month;
            int currentYearForAttendanceDetails = year;
            ResponseResult = WebService.AttendanceDetails(userId, currentMonthForAttendanceDetails, currentYearForAttendanceDetails, webMethName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (ResponseResult.equals("Attendance Details Not Found.")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(Attendance.this);
                builder.setTitle("Result");
                builder.setMessage("Attendance Details Not Available For this Month.");
                AlertDialog alert1 = builder.create();
                alert1.show();
                responseCounter = 1;
            } else if (ResponseResult.equals("No Network Found")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(Attendance.this);
                builder.setTitle("Result");
                builder.setMessage("There is Some Network Issues Please Try Again Later.");
                AlertDialog alert1 = builder.create();
                alert1.show();
                responseCounter = 1;
            } else {
                progressDialog.dismiss();
                itemsOfAttendance.clear();
                try {
                    responseCounter = 0;
                    JSONArray jsonArray = new JSONArray(ResponseResult);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            Attendance_items attendanceItems = new Attendance_items();

                            attendanceItems.setBookedThrough(obj.getString("AppointmentBookedThrough"));
                            attendanceItems.setAppointmentstatus(obj.getString("Appointmentstatus"));
                            attendanceItems.setAppointmentid(obj.getString("Appointmentid"));
                            attendanceItems.setFull_Name(obj.getString("Full_Name"));
                            attendanceItems.setPhone_no(obj.getString("Phone_no"));
                            attendanceItems.setAppointment_dates(obj.getString("Appointment_dates"));
                            attendanceItems.setAppointment_time(obj.getString("Appointment_time"));
                            attendanceItems.setCountryName(obj.getString("CountryName"));
                            attendanceItems.setcity(obj.getString("city"));
                            attendanceItems.setHotelName(obj.getString("Hotel_name"));
                            attendanceItems.setHotelMobileNo(obj.getString("HotelMobileNo"));
                            attendanceItems.setAppointment_Type(obj.getString("Appointment_Type"));
                            attendanceItems.setPresentDate(obj.getString("AppointmentDate"));
                            attendanceItems.setPresentMonth(obj.getString("AppointmentMonth"));
                            attendanceItems.setPresentYear(obj.getString("AppointmentYear"));

                            if (obj.getString("Appointment_Type").equals("P")) {
                                presentDateList.add(obj.getString("AppointmentDate"));
                            }
                            itemsOfAttendance.add(attendanceItems);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Inner Class Adapter

    public class GridCellAdapter extends BaseAdapter implements OnClickListener {
        private static final String tag = "GridCellAdapter";
        private final Context _context;

        private final List<String> list;
        private static final int DAY_OFFSET = 1;
        private final String[] weekdays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        private int daysInMonth;
        private int currentDayOfMonth;
        private int currentWeekDay;
        private Button gridcell;
        private TextView num_events_per_day;
        GridView calendarGrid;
        private final HashMap<String, Integer> eventsPerMonthMap;
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        List<Attendance_items> itemsDetails;
        Attendance_items listItems;

        // Days in Current Month
        public GridCellAdapter(Context context, int textViewResourceId, int month, int year, List<Attendance_items> itemsOfAttendance) {
            super();
            this._context = context;
            this.list = new ArrayList<String>();
            this.itemsDetails = itemsOfAttendance;
            Calendar calendar = Calendar.getInstance();
            setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
            setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
            // Print Month
            printMonth(month, year);
            // Find Number of Events
            eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);

        }

        private String getMonthAsString(int i) {
            return months[i];
        }

        private String getWeekDayAsString(int i) {
            return weekdays[i];
        }

        private int getNumberOfDaysOfMonth(int i) {
            return daysOfMonth[i];
        }

        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        private void printMonth(int mm, int yy) {
            int trailingSpaces = 0;
            int daysInPrevMonth = 0;
            int prevMonth = 0;
            int prevYear = 0;
            int nextMonth = 0;
            int nextYear = 0;

            int currentMonth = mm - 1;
            String currentMonthName = getMonthAsString(currentMonth);
            daysInMonth = getNumberOfDaysOfMonth(currentMonth);

            GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);

            if (currentMonth == 11) {
                prevMonth = currentMonth - 1;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 0;
                prevYear = yy;
                nextYear = yy + 1;
            } else if (currentMonth == 0) {
                prevMonth = 11;
                prevYear = yy - 1;
                nextYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 1;
            } else {
                prevMonth = currentMonth - 1;
                nextMonth = currentMonth + 1;
                nextYear = yy;
                prevYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            }

            int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
            trailingSpaces = currentWeekDay;

            if (cal.isLeapYear(cal.get(Calendar.YEAR)))
                if (mm == 2)
                    ++daysInMonth;
                else if (mm == 3)
                    ++daysInPrevMonth;

            // Trailing Month days
            for (int i = 0; i < trailingSpaces; i++) {
                list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
            }
            // Current Month Days
            for (int i = 1; i <= daysInMonth; i++) {

                String mydate = (String.valueOf(i) + " " + getMonthAsString(currentMonth) + " " + yy);
                SimpleDateFormat inFormat = new SimpleDateFormat("d MMM yyyy");
                Date date = null;
                try {
                    date = inFormat.parse(mydate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                String currentDayName = outFormat.format(date);
                if (!currentDayName.equals("Sunday")) {
                    schoolDaysInMonthList.add(String.valueOf(i));
                }

                if (i == getCurrentDayOfMonth()) {
                    list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                } else {
                    list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
                }
            }
            // Leading Month days
            for (int i = 0; i < list.size() % 7; i++) {
                list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
            }
        }

        private HashMap<String, Integer> findNumberOfEventsPerMonth(int year, int month) {
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            return map;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (itemsDetails.size() != 0 && position <= itemsDetails.size() - 1) {
                listItems = new Attendance_items();
                listItems = itemsDetails.get(position);
            }

            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.my_attendance_gridcell, parent, false);
            }
            num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
            // Get a reference to the Day gridcell
            gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);

            gridcell.setTag(new Integer(position));

            // ACCOUNT FOR SPACING
            String[] day_color = list.get(position).split("-");
            String color = day_color[1];
            String theday = day_color[0];
            String themonth = day_color[2];
            String theyear = day_color[3];

            gridcell.setText(theday);
            gridcell.setTag(theday + "-" + themonth + "-" + theyear);

            String myCurrentDate = theday + " " + themonth + " " + theyear;
            SimpleDateFormat inFormat = new SimpleDateFormat("d MMM yyyy");
            Date date = null;
            try {
                date = inFormat.parse(myCurrentDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            String currentDayName = outFormat.format(date);

            // to avoid similar data of list {"ptk", "a","b","ptk"}result={"ptk", "a","b"}
            Set<String> hsForPresentDateList = new HashSet<>();
            hsForPresentDateList.addAll(presentDateList);
            presentDateList.clear();
            presentDateList.addAll(hsForPresentDateList);

            totalSchoolDaysInMonth = schoolDaysInMonthList.size();
            //totalDaysInMonth.setText("Total School Days : " + totalSchoolDaysInMonth);

            totalPresentDay = presentDateList.size();

            gridcell.setOnClickListener(this);

            if (month == thisMonth && year == thisYear) {
                if (day_color[1].equals("WHITE")) {
                    gridcell.setOnClickListener(this);
                }
            }

            //for event
            if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
                if (eventsPerMonthMap.containsKey(theday)) {
                    num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
                    Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
                    num_events_per_day.setText(numEvents.toString());
                }
            }
            // Set the Day GridCell
            gridcell.setText(theday);
            gridcell.setTag(theday + "-" + themonth + "-" + theyear);

            //checking to add day for remaining space of month and add next n previous months date

            if (day_color[1].equals("GREY")) {
                gridcell.setTextColor(getResources().getColor(R.color.lightgray02));
                gridcell.setBackgroundColor(getResources().getColor(R.color.colorwhite));
            }
            //checking for actual days of month
            else if (day_color[1].equals("WHITE")) {
                if (currentDayName.equals("Sunday")) {
                    gridcell.setTextColor(getResources().getColor(R.color.colorred500));
                    gridcell.setBackgroundColor(getResources().getColor(R.color.colorwhite));

                }
//                else {
                    if (responseCounter == 1) {
                        gridcell.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                    }
                    else {
                        String currentDay = day_color[0];
                        //for present
                        if (presentDateList.contains(currentDay)) {
                            if (currentDayName.equals("Sunday")) {
                                gridcell.setTextColor(getResources().getColor(R.color.colorred500));
                                gridcell.setBackgroundColor(getResources().getColor(R.color.colorGreen500));
                            }else{
                                gridcell.setBackgroundColor(getResources().getColor(R.color.colorGreen500));
                            }

                        }
                        else {
                            gridcell.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                        }
                    }
//                }
            }
            //checking for current date
            else if (day_color[1].equals("BLUE")) {

                if (currentDayName.equals("Sunday")) {
                    gridcell.setTextColor(getResources().getColor(R.color.colorred500));
                    gridcell.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                }
//                else {
                    if (responseCounter == 1) {
                        gridcell.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                    }
                    else {
                        if (thisMonth < month && year == thisYear) {
                            absentDateList.clear();
                            gridcell.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                        }
                        else {
                            String currentDay = day_color[0];

                            //for present
                            if (presentDateList.contains(currentDay)) {
                                if (currentDayName.equals("Sunday")) {
                                    gridcell.setTextColor(getResources().getColor(R.color.colorred500));
                                    gridcell.setBackgroundColor(getResources().getColor(R.color.colorGreen500));
                                }else{
                                    gridcell.setBackgroundColor(getResources().getColor(R.color.colorGreen500));
                                }

                            }

                            else {
                                gridcell.setBackgroundColor(getResources().getColor(R.color.colorwhite));
                            }
                        }
                    }
//                }
            }
            return row;
        }

        @Override
        public void onClick(View view) {

            //add event for each date and show it{timing}
            if (view.getId() == R.id.calendar_day_gridcell) {
                // Attendance_items listItems = new Attendance_items();
                
                String position = (String) view.getTag();
                SimpleDateFormat inFormat = new SimpleDateFormat("d-MMM-yyyy");
                Date date = null;
                try {
                    date = inFormat.parse(position);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat outFormat = new SimpleDateFormat("d");
                String day = outFormat.format(date);

                String  selectedDay = day;
                String  selectedMonth = String.valueOf(month);
                String  selectedYear = String.valueOf(year);

            }
        }

        public int getCurrentDayOfMonth() {
            return currentDayOfMonth;
        }

        private void setCurrentDayOfMonth(int currentDayOfMonth) {
            this.currentDayOfMonth = currentDayOfMonth;
        }

        public void setCurrentWeekDay(int currentWeekDay) {
            this.currentWeekDay = currentWeekDay;
        }

        public int getCurrentWeekDay() {
            return currentWeekDay;
        }
    }

    @Override
    public void onBackPressed() {


        Attendance.this.finish();

    }

}
