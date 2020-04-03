package com.uwo.isms.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

//import oracle.sql.DATE;

import org.apache.log4j.Logger;

public class IsmsDateUtil {
    	
    	/* 로그 */
	private static Logger logger = Logger.getLogger(IsmsDateUtil.class);
	/* 날짜포멧 */
	public static DateFormat DATEFORMAT_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	
	/* *
	 * 수행일 주기 타입코드 
	 * 현재 100번대는 비주기 200번대는 주기로 사용되어지나 추후 반대로 변경할것임
	 * */

	public static int CNTR_TERM_NON_PERIOD 		= 100;	// 비주기
	public static int CNTR_TERM_PERIOD 			= 200;	// 주기 
	public static int CNTR_TERM_PERIOD_DAY 		= 201;	// 일간
	public static int CNTR_TERM_PERIOD_WEEK 	= 202;	// 주간
	public static int CNTR_TERM_PERIOD_MONTH_1 	= 203;	// 1개월
	public static int CNTR_TERM_PERIOD_MONTH_2 	= 204;	// 2개월
	public static int CNTR_TERM_PERIOD_MONTH_3 	= 205;	// 3개월
	public static int CNTR_TERM_PERIOD_MONTH_4 	= 206;	// 4개월
	public static int CNTR_TERM_PERIOD_MONTH_5 	= 207;	// 5개월
	public static int CNTR_TERM_PERIOD_MONTH_6 	= 208;	// 6개월
	public static int CNTR_TERM_PERIOD_MONTH_7 	= 209;	// 7개월
	public static int CNTR_TERM_PERIOD_MONTH_8 	= 210;	// 8개월
	public static int CNTR_TERM_PERIOD_MONTH_9 	= 211;	// 9개월
	public static int CNTR_TERM_PERIOD_MONTH_10 = 212;	// 10개월
	public static int CNTR_TERM_PERIOD_MONTH_11 = 213;	// 11개월
	public static int CNTR_TERM_PERIOD_MONTH_12 = 214;	// 12개월
	public static int CNTR_TERM_PERIOD_ONCE 	= 215;	// 항구
	
	/* 마감일 지정 주기 타입 코드  */
	public static String PERIOD_DAY 		= "PERIOD00";	// 1 일 후 까지
	public static String PERIOD_WEEK 		= "PERIOD01";	// 1 주 후 까지
	public static String PERIOD_MONTH 		= "PERIOD02";	// 1 달 후까지
	public static String PERIOD_MONTH_DAY 	= "PERIOD03";	// 해당 월 1일 까지
	public static String PERIOD_DATE 		= "PERIOD04";	// 2013-11-07일 까지  특정 지정일까지
	
	public static String WRK_TERM_PERIOD_NONE		= "N";
	public static String WRK_TERM_PERIOD_DAY		= "D";
	public static String WRK_TERM_PERIOD_WEEK		= "W";
	public static String WRK_TERM_PERIOD_MONTH		= "M";
	public static String WRK_TERM_PERIOD_BIMONTH	= "T";
	public static String WRK_TERM_PERIOD_QUARTER	= "Q";
	public static String WRK_TERM_PERIOD_HALF		= "H";
	public static String WRK_TERM_PERIOD_YEAR		= "Y";
	
	/* 
	// 수행일 : cntrDate
	// 기간 타입 : periodCd
	// 기간 : period
       */
	/**
	 * 
	 * @param cntrDate 기준일
	 * @param periodCd
	 * @param period 지정마감일은 날짜, 그 외는 숫자
	 * @param addDay : 첫 업무생성시의 기준일과 지정마감일간의 차이
	 * @return
	 */
	public static Date getTaskDeadlineDate(Date cntrDate,String periodCd, Object period, long addDay) {
	    Date taskDeadlineDate = new Date();
	    
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar cal = Calendar.getInstance();
	    
	    Date periodDate = null;
	    
	    // 수행일이 있는지 확인하여 있을경우 수행일을 조회하여 수행일 + 기간
	    // 수행일이 없을 경우 오늘 날짜 기준으로 생성   오늘날짜 + 기간
	    if(cntrDate == null) {
	    	cntrDate = new Date();
	    } 
	    
	    // 날짜 세팅
	    cal.setTime(cntrDate);
	    
	    // period_cd 마감일 기간 조회
	    if(periodCd.equals(PERIOD_DAY)) {				// {1} 일 후 까지
	    	cal.add(Calendar.DATE,Integer.parseInt((String) period));
	    } else if(periodCd.equals(PERIOD_WEEK)) {		// {1} 주 후 까지
	    	cal.add(Calendar.WEEK_OF_MONTH, Integer.parseInt((String) period));
	    } else if(periodCd.equals(PERIOD_MONTH)) {		// {1} 달 후 까지 
	    	cal.add(Calendar.MONTH, Integer.parseInt((String) period));
	    } else if(periodCd.equals(PERIOD_MONTH_DAY)) {	// 지정일 
			int appointDay = cal.get(Calendar.DATE);		// 마감일 일 
	
			int periodDay = Integer.parseInt((String) period);
			
			// 해당 월 {1} 일 까지
			// 특정달의 날을 지정한 경우 해당 달의 마지막 일수를 
			// 체크하여 넘어갈 경우 그달의 마지막날로 지정
			if(periodDay > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			    periodDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			cal.set(Calendar.DATE, periodDay);
			
			Date deadlineDate = cal.getTime();
			if(cntrDate.compareTo(deadlineDate) > 0) {
			    // 지정일이 마감일보다 클경우 마감일을 해당달+1 , 지정마감일 로 하기로함
			    if(appointDay > Integer.parseInt((String) period)) {
				int month = cal.get(Calendar.MONTH) + 1 ;
				cal.set(Calendar.MONTH, month);
			    }
			} 
		
	    } else if(periodCd.equals(PERIOD_DATE)) {
	    	// 기준일에 지정마감일과의 차이만큼 더해줌.
	    	cal.add(Calendar.DATE,(int) addDay);
	    }

    	taskDeadlineDate = cal.getTime();
	    
	    return taskDeadlineDate;
	}
	
	// 지정일 반환 
	// 수행일 : cntrDate
	// 지정일 : appointDate
	// 주기,비주기 여부 : termCd
	// 주기일 경우 
	// 주기기간 : termDetail
	public static Date getTaskAppointDate(Date appointDate, Date cntrDate, int termCd, int termDetail, String periodCd, String period) throws Exception {
	    
	    DateFormat dateFormat = new SimpleDateFormat("dd");

	    Calendar cntrCal = Calendar.getInstance();	    // 수행일
	    cntrCal.setTime(cntrDate);	    				// 날짜 세팅
	    Calendar appointCal = Calendar.getInstance();	// 지정일
	    appointCal.setTime(appointDate);	    		// 날짜 세팅
	    
	    int dayOfWeek = appointCal.get(Calendar.DAY_OF_WEEK);
	    int dd = appointCal.get(Calendar.DATE);
	    
	    // 마감일이 특정 지정일로 되어있을경우 예) 2013-11-25
	    // 지정일은 마감일을 넘지 않아야 한다.
	    Date periodDate = null;
	    if(IsmsDateUtil.PERIOD_DATE.equals(periodCd)) {
	    	try {
	    		periodDate = IsmsDateUtil.getStringToDate(period);
	    	} catch (ParseException e) {
		    // TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    }
	    
	    // 수행일이 지정일보다 이전일 경우 
	    // 수행일이 지정일보다 이후일 경우
	    // 수행일이 지정일에 이미 존재할경우 
	    // 해당 양식서의 수행일 목록을 확인함  ??
	    
	    // 지정일 ~  1년 안에 해당하는 일자를 구하여 수행일과 제일가까운 미래의 날짜로 지정하도록함.
	    

	    if(termCd == CNTR_TERM_NON_PERIOD) {	    // 비주기
	    	
	    } else if(termCd == CNTR_TERM_PERIOD) {		// 주기

			if(termDetail == CNTR_TERM_PERIOD_DAY) {			// 일간
				
			}else if(termDetail == CNTR_TERM_PERIOD_WEEK){
			    cntrCal.add(Calendar.WEEK_OF_MONTH, 1);
			    appointDate = cntrCal.getTime();
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_1){		    // 1개월 
			    appointDate =  getAppointDate(appointDate,cntrDate,1,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_2) {			    // 2개월   
			    appointDate =  getAppointDate(appointDate,cntrDate,2,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_3) {			    // 3개월
			    appointDate =  getAppointDate(appointDate,cntrDate,3,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_4) {			    // 4개월
			    appointDate =  getAppointDate(appointDate,cntrDate,4,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_5) {			    // 5개월
			    appointDate =  getAppointDate(appointDate,cntrDate,5,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_6) {			    // 6개월
			    appointDate =  getAppointDate(appointDate,cntrDate,6,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_7) {			    // 7개월
			    appointDate =  getAppointDate(appointDate,cntrDate,7,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_8) {			    // 8개월   
			    appointDate =  getAppointDate(appointDate,cntrDate,8,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_9) {			    // 9개월   
			    appointDate =  getAppointDate(appointDate,cntrDate,9,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_10) {			// 10개월
			    appointDate =  getAppointDate(appointDate,cntrDate,10,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_11) {			// 11개월
			    appointDate =  getAppointDate(appointDate,cntrDate,11,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_12) {			// 12개월  
			    appointDate =  getAppointDate(appointDate,cntrDate,12,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_ONCE) {			    // 항구

			}
	    }
	    
	    return appointDate;
	}
	
	// 날짜를 문자열로 반환
	public static String getDateToString(Date date) {
	    String dateStr = "";
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    
	    return dateFormat.format(date);
	}
	
	// 문자를 날짜로 반환
	public static Date getStringToDate(String dateString) throws Exception {
		Date tempDate = DATEFORMAT_YYYY_MM_DD.parse(dateString);
		return tempDate;
	}
	
	
	// 지정일 ~  1년 안에 해당하는 일자를 구하여 수행일(오늘날짜)과 제일가까운 미래의 날짜로 지정하도록함.
	public static Date getAppointDate(Date appointDate, Date cntrDate, int termDetail, Date periodDate) {
	    ArrayList<Date> periodDateList = new ArrayList<Date>();
	    Date endDate = new Date();
	    
	    // 1년후 
	    Calendar appointCal = Calendar.getInstance();
            // 날짜 세팅
	    appointCal.setTime(appointDate);
	    appointCal.add(Calendar.YEAR, 1);
	    
	    endDate = appointCal.getTime();
	    
	    // 지정일 이후 1년안에 수행예정일을 반환
	    appointCal.setTime(appointDate);
	    
	    Calendar appointTempCal = Calendar.getInstance();
	    appointTempCal.setTime(appointDate);
	    Date appointTempDate = new Date();
	    appointTempDate = appointTempCal.getTime();
	    // 수행예정일이 기한 마지막일보다 작을때까지
	    while (appointTempDate.compareTo(endDate) <= 0) {
		if(periodDate == null) {
        		if( appointTempDate.compareTo(cntrDate) >= 0 ) {
        		    appointDate = appointTempDate;
        		    break ;
        		}
        		
        		appointTempCal.add(Calendar.MONTH, termDetail);
        		appointTempDate = appointTempCal.getTime();
		} else {
        		if( appointTempDate.compareTo(periodDate) <= 0 && appointTempDate.compareTo(cntrDate) >= 0 ) {
        		    appointDate = appointTempDate;
        		    break ;
        		}
        		
        		appointTempCal.add(Calendar.MONTH, termDetail);
        		appointTempDate = appointTempCal.getTime();
		}
	    }
	    
	    return appointDate;
	}
	 public static Calendar getCalendar() {
		  Calendar cal = new GregorianCalendar( TimeZone.getTimeZone("GMT+09:00"), Locale.KOREA );
		  cal.setTime( new Date() );
		  return cal;
	}	
	 /**
	  * 
	  * @param appointDate 		지정일
	  * @param cntrDate  		수행일
	  * @param termCd			주기 100 , 비주기 200
	  * @param termDetail		주기일경우 일간, 월간, 주간, 월간, 격월...
	  * @param periodCd
	  * @param period
	  * @return
	  */
	 
		// 지정일 반환 
		// 지정일 : 
		// 주기,비주기 여부 : termCd
		// 주기일 경우 
		// 주기기간 : termDetail
		public static Date getTaskAppointToDate(Date appointDate, Date cntrDate, int termCd, int termDetail, String periodCd, String period) throws Exception {
		    
		    DateFormat dateFormat = new SimpleDateFormat("dd");

		    Calendar cntrCal = Calendar.getInstance();	    
		    cntrCal.setTime(cntrDate);	    				// 수행일 날짜 세팅
		    Calendar appointCal = Calendar.getInstance();	// 
		    appointCal.setTime(appointDate);	    		// 지정일 날짜 세팅
		    
		    int dayOfWeek = appointCal.get(Calendar.DAY_OF_WEEK);
		    int dd = appointCal.get(Calendar.DATE);
		    
		    // 마감일이 특정 지정일로 되어있을경우 예) 2013-11-25
		    // 지정일은 마감일을 넘지 않아야 한다.
		    Date periodDate = null;
		    if(IsmsDateUtil.PERIOD_DATE.equals(periodCd)) {
				try {
				    periodDate = IsmsDateUtil.getStringToDate(period);
				} catch (ParseException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
				}
		    }
		    
			if(termDetail == CNTR_TERM_PERIOD_DAY) {			// 일간
				
			}else if(termDetail == CNTR_TERM_PERIOD_WEEK){ // 주간업무 금주의 월요일 Return
			    cntrCal.add(Calendar.WEEK_OF_MONTH, 1);
			    appointDate = cntrCal.getTime();
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_1){		    // 1개월 
			    appointDate =  getAppointDateToday(appointDate,cntrDate,1,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_2) {			    // 2개월   
			    appointDate =  getAppointDateToday(appointDate,cntrDate,2,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_3) {			    // 3개월
			    appointDate =  getAppointDateToday(appointDate,cntrDate,3,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_4) {			    // 4개월
			    appointDate =  getAppointDateToday(appointDate,cntrDate,4,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_5) {			    // 5개월
			    appointDate =  getAppointDateToday(appointDate,cntrDate,5,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_6) {			    // 6개월
			    appointDate =  getAppointDateToday(appointDate,cntrDate,6,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_7) {			    // 7개월
			    appointDate =  getAppointDateToday(appointDate,cntrDate,7,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_8) {			    // 8개월   
			    appointDate =  getAppointDateToday(appointDate,cntrDate,8,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_9) {			    // 9개월   
			    appointDate =  getAppointDateToday(appointDate,cntrDate,9,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_10) {			// 10개월
			    appointDate =  getAppointDateToday(appointDate,cntrDate,10,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_11) {			// 11개월
			    appointDate =  getAppointDateToday(appointDate,cntrDate,11,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_MONTH_12) {			// 12개월  
			    appointDate =  getAppointDateToday(appointDate,cntrDate,12,periodDate);
			} else if(termDetail == CNTR_TERM_PERIOD_ONCE) {			    // 항구
			}
		    
		    
		    return appointDate;
		}
		
		/**
		 *  
		 * @param appointDate 	지정일
		 * @param cntrDate		업무 생성일
		 * @param termDetail	기간 ( 월)
		 * @param periodDate	
		 * @return
		 */
		
		public static Date getAppointDateToday(Date appointDate, Date cntrDate, int termDetail, Date periodDate) {
		    
		    Date endDate = new Date();
		    

		    Calendar endCal = Calendar.getInstance();
		    endCal.setTime(endDate);	            
		    endCal.add(Calendar.YEAR, 1); 			// 현재로부터 1년후
		    endDate = endCal.getTime();		    
		    
		    Calendar appointCal = Calendar.getInstance();  
		    appointCal.setTime(appointDate);
		    
		    Date appointTempDate = new Date();
		    appointTempDate = appointCal.getTime();	

	    	Calendar cntrDateCal = Calendar.getInstance();
	    	cntrDateCal.setTime(cntrDate) ;

	    	
	    	SimpleDateFormat formatter  = new SimpleDateFormat ("yyyyMMdd");
	    	
			int appointTempDateInt 		= Integer.parseInt(formatter.format(appointCal.getTime()));			
			int endDateInt 				= Integer.parseInt(formatter.format(endCal.getTime()));
			int cntrDateCalInt 			= Integer.parseInt(formatter.format(cntrDateCal.getTime())); ;
			
		    //System.out.println ("appointTempDateInt : " +appointTempDateInt) ;
		    //System.out.println ("endDateInt : " 		+endDateInt) ; 
		    //System.out.println ("cntrDateCalInt : " 	+cntrDateCalInt) ; 
		    
		    // 지정일 -> 현재 + 1년까지 검색해 오늘을 포함한 작업 수행일을 찾음
			int x = 0 ;
			int term = 0 ;
		    while (appointTempDateInt <= endDateInt) {
		    	
		    	
		    	term = term + termDetail ;
		    	
			    Calendar appointTempCal = Calendar.getInstance();  
			    appointTempCal.setTime(appointDate);
			    
		    	//System.out.println("생성된 주기 확인 11 : " + appointTempDateInt ) ;
		    	if( appointTempDateInt >= cntrDateCalInt ) {
		    	    appointDate = appointTempDate;
		    	    break ;
		    	}
		    			
		    	appointTempCal.add(Calendar.MONTH, term);
		    	appointTempDate = appointTempCal.getTime();
		        		
		    	appointTempDateInt = Integer.parseInt(formatter.format(appointTempDate.getTime())); 
		        		
		    	x ++ ;
		    	if( x > 1000) break ;  // 혹시나 모를 무한루프 방지.!
//				} else {
//					//System.out.println (" 3 - 1 - 1 :cCntrDate " +cCntrDate.getTime()) ;
//		        		if( appointTempDate.compareTo(periodDate) <= 0 && appointTempDate.compareTo(cntrDate) >= 0 ) {
//		        		    appointDate = appointTempDate;
//		        		    break ;
//		        		}
//		        		
//		        		appointTempCal.add(Calendar.MONTH, termDetail);
//		        		appointTempDate = appointTempCal.getTime();
//				}		        		
		    }
		    
		    return appointDate;
		}

}









