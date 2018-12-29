package com.app.note.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

public class AppUtil {

	private static final Logger logger = LogManager.getLogger(AppUtil.class);

	/**
	 * This method is used to check whether an object is null. Returns true if the
	 * object is null and return false otherwise.
	 * 
	 * @param pObj The object to be validated
	 * @return Returns a boolean value.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNull(Object... pObj) {

		Boolean isNull = false;

		if (pObj == null) {
			isNull = true;
		} else {
			for (Object lObj : pObj) {
				if (lObj == null) {
					isNull = true;
				} else if (lObj instanceof String) {
					isNull = ((String) lObj).trim().equals("");
				} else if (lObj instanceof Collection) {
					isNull = (((Collection) lObj).size() == 0);
				} else if (lObj instanceof Map) {
					isNull = (((Map) lObj).size() == 0);
				} else {
					isNull = false;
				}
				if (isNull) {
					break;
				}
			}
		}
		return isNull;
	}

	/**
	 * This method is used to check whether an object is not null. Returns true if
	 * the object is not null and return false otherwise.
	 * 
	 * @param pObj The object to be validated.
	 * @return Returns a boolean value.
	 */
	public static boolean isNotNull(Object... pObj) {
		return !isNull(pObj);
	}

	/**
	 * To get a secured random number as string.
	 * 
	 * @return Returns a secured random number as string.
	 */
	public static String generateRandomNumber() {
		SecureRandom lRandomGeneration = new SecureRandom();
		String randomNumber = "";
		try {
			lRandomGeneration = SecureRandom.getInstance("SHA1PRNG");
			byte[] lKey = new byte[16];
			lRandomGeneration.nextBytes(lKey);
			randomNumber = "" + System.currentTimeMillis() + Math.abs(lRandomGeneration.nextInt());
		} catch (NoSuchAlgorithmException pException) {
			pException.printStackTrace();
			Random r = new Random(System.currentTimeMillis());
			randomNumber = "" + System.currentTimeMillis() + Math.abs(r.nextInt());
		}
		return randomNumber;
	}

	public static String getEmptyStringIfNull(String pValue) {
		return (pValue == null ? "" : pValue);
	}

	public static Date getDateWithoutTime(Date pInDate) {
		if (pInDate == null) {
			return null;
		}
		Date lOutDate = pInDate;
		Calendar lCalendar = Calendar.getInstance();

		lCalendar.setTime(pInDate);
		lCalendar.set(Calendar.HOUR_OF_DAY, 0);
		lCalendar.set(Calendar.MINUTE, 0);
		lCalendar.set(Calendar.SECOND, 0);
		lCalendar.set(Calendar.MILLISECOND, 0);

		lOutDate = lCalendar.getTime();

		return lOutDate;
	}

	public static Date getNextDate(int numOfDays) {
		Date lOutDate = getCurrentDate();
		Calendar lCalendar = Calendar.getInstance();

		lCalendar.setTime(lOutDate);
		lCalendar.add(Calendar.DATE, numOfDays);
		lCalendar.set(Calendar.HOUR_OF_DAY, 0);
		lCalendar.set(Calendar.MINUTE, 0);
		lCalendar.set(Calendar.SECOND, 0);
		lCalendar.set(Calendar.MILLISECOND, 0);

		lOutDate = lCalendar.getTime();

		return lOutDate;

	}

	public static Date getCurrentDate() {
		return getDateWithoutTime(new Date());
	}

	public static String getDate(Date pInDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return (!isNull(pInDate) ? sdf.format(pInDate) : sdf.format(getCurrentDate()));
	}

	public static Date getFormattedDate(Date pInDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return (!isNull(pInDate) ? sdf.parse(getDate(pInDate)) : getCurrentDate());
		} catch (ParseException e) {
		}
		return getCurrentDate();
	}

	public static Date getDate(String pInDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return sdf.parse(pInDate);
		} catch (ParseException e) {
		}
		return new Date();
	}

	public static String getExceptionName(Throwable obj) {
		if (!isNull(obj)) {
			String[] arr = obj.getClass().getName().split("\\.");
			return arr[arr.length - 1];
		}
		return null;
	}

	public static String rediredtedToString(String pageName) {
		return isNotNull(pageName) ? "redirect:" + pageName : "redirect:/";
	}

	public static String rediredtedTo(String pageName) {
		return isNotNull(pageName) ? "redirect:" + pageName : "redirect:/";
	}

	public static String forwaredTo(String pageName) {
		return (isNotNull(pageName) ? "forward:" + pageName : "forward:/");
	}

	public static String goTo(String pageName) {
		return pageName;
	}

	public static Locale getLocale(Locale locale, String lang) {
		if (!isNull(lang)) {
			if (AppConstants.LANGUAE_ENGLISH.equals(lang)) {
				return new Locale(AppConstants.LANGUAE_ENGLISH, AppConstants.COUNTRY_INDIA);
			} else if (AppConstants.LANGUAE_TAMIL.equals(lang)) {
				return new Locale(AppConstants.LANGUAE_TAMIL, AppConstants.COUNTRY_INDIA);
			} else {
				return new Locale(AppConstants.LANGUAE_ENGLISH, AppConstants.COUNTRY_INDIA);
			}
		} else {
			return (!isNull(locale) ? locale : new Locale(AppConstants.LANGUAE_ENGLISH, AppConstants.COUNTRY_INDIA));
		}
	}

	public static Locale getLocale(Locale locale) {
		return getLocale(locale, null);
	}

	public static Locale getLocale() {
		return getLocale(null, null);
	}

	public static String getTempPasswordString() {
		try {
			int left = 65, right = 122, len = 12;
			Random random = new Random();
			StringBuilder buffer = new StringBuilder(len);
			for (int i = 0; i < len; i++) {
				int randomInt = left + (int) (random.nextFloat() * (right - left + 1));
				buffer.append((char) randomInt);
			}
			return buffer.toString();
		} catch (Exception ex) {
			throw new AppException(ex);
		}
	}

	public static String getTokenString() {
		SecureRandom secureRandom = null;
		long genratedNum = 169;
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
			byte[] keys = new byte[15];
			secureRandom.nextBytes(keys);
			genratedNum = secureRandom.nextInt();
		} catch (NoSuchAlgorithmException ex) {
			Random random = new Random();
			genratedNum = random.nextInt();
		} catch (Exception ex) {
			throw new AppException(ex);
		}
		long div = Math.abs(System.currentTimeMillis() / Math.abs(genratedNum / 13));
		long finalDiv = Math.abs((div * genratedNum) / Math.abs(div / 2));
		return ("" + finalDiv + UUID.randomUUID().toString().replace("-", "")).toUpperCase();
	}

	public static String getTempSixDigitNumber() {
		return ("" + Math.abs(System.currentTimeMillis())).substring(7);
	}

	public static File getFile(String fileName) {
		File htmlFile = null;
		try {
			htmlFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + fileName);
		} catch (Exception ex) {
			logger.error(ex);
		}
		return htmlFile;
	}

//	public static String getFileContents(String fileName){
//		StringBuilder resultStr		= new StringBuilder("");
//		try (Scanner scanner=new Scanner(getFile(fileName))) {
//			while(scanner.hasNextLine()){
//				resultStr.append(scanner.nextLine()).append("\n");
//			}
//			scanner.close();
//		} catch (FileNotFoundException e) {
//			logger.error(e);
//		}
//		catch (Exception e) {
//			logger.error(e);
//		}
//		finally{
//		}
//		return resultStr.toString();
//	}

	public static String getFileContents(String fileName) {
		String data = null;
		try {
			ClassPathResource cpr = new ClassPathResource(fileName);
			byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
			data = new String(bdata, StandardCharsets.UTF_8);
		} catch (Exception ex) {

		}
		return data;
	}

	public static String getUserRegVerifyUrl(String token, String userMail, String tmpToken) {
		String appUrl = AppConstants.HTTP_PREFIX + AppConstants.APP_SERVER_HOST + 
//				AppConstants.COLON + AppConstants.APP_SERVER_PORT +
//				AppConstants.FORWAREDSLASH + AppConstants.APP_NAME + 
				AppConstants.FORWAREDSLASH + AppConstants.NEW_USER_VERIFICATION_STRING + AppConstants.QUESTION_MARK
				+ AppConstants.PATHVARIABLE_TOKEN + AppConstants.EQUAL_TO + token + AppConstants.AMBERSON
				+ AppConstants.PATHVARIABLE_USER_EMAIL + AppConstants.EQUAL_TO + userMail + AppConstants.AMBERSON
				+ AppConstants.PATHVARIABLE_TMP_TOKEN + AppConstants.EQUAL_TO + tmpToken;
		// try {
		// return URLEncoder.encode(appUrl,AppConstants.UTF_8);
		// } catch (UnsupportedEncodingException e) {
		// logger.error(e);
		// }
		return appUrl;
	}

	public static String getUserLoginUrl() {
		String appUrl = AppConstants.HTTP_PREFIX 
				+ AppConstants.APP_SERVER_HOST +
//				AppConstants.COLON + AppConstants.APP_SERVER_PORT +
//				AppConstants.FORWAREDSLASH + AppConstants.APP_NAME +
				AppConstants.URL_LOGIN;
		return appUrl;
	}

	public static void setLocaleContextHolder(Locale locale) {
		if (isNull(locale)) {
			locale = getLocale();
		}
		LocaleContextHolder.setLocale(locale);
	}

	public static Locale getCurrentLocale() {
		return LocaleContextHolder.getLocale();
	}

	public static String compareDate(Date d1, Date d2) {
		try {
			if (!isNull(d1, d2)) {
				if (d1.compareTo(d2) > 0) {
					return AppConstants.GRATER;
				} else if (d1.compareTo(d2) < 0) {
					return AppConstants.LESS;
				} else if (d1.compareTo(d2) == 0) {
					return AppConstants.EQUAL;
				} else {
					return AppConstants.NOT_EQUAL;
				}
			} else {
				return AppConstants.NOT_EQUAL;
			}
		} catch (Exception ex) {
			logger.error(ex);
			return AppConstants.NOT_EQUAL;
		}
	}

	public static String getTempTitleString(String inpString) {
		return (isNotNull(inpString) && inpString.length() > 10 ? inpString.substring(0, 10) + "..." : inpString);
	}

//	public static String getColorValue(String color){
//		return Color(color);
//	}
}
