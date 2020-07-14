package com.demo.jpa.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import ch.qos.logback.classic.ClassicConstants;
import eu.bitwalker.useragentutils.UserAgent;

@WebFilter(urlPatterns = { "/*" })
public class MDCFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(MDCFilter.class);
	public static final String REQUEST_METHOD_MDC_KEY 	= "req.requestMethod";		// requestMethod
	public static final String REQUEST_LOGIN_ID 		= "req.loginId"; 			// 로그인 아이디
	public static final String REQUEST_LOGIN_NAME 		= "req.loginName"; 			// 로그인 사용자명
	public static final String REQUEST_METHOD 			= "req.requestMethod"; 		// requestMethod
	public static final String REQUEST_DEVICE 			= "req.requestDevice";  	// 접속 장비 : PC (pc웹) / APP + MO (모바일)
	public static final String REQUEST_BROWSER 			= "req.requestBorwser"; 	// 접속 장비 : PC (pc웹) / MO (모바일웹) / APP (모바일App)
	public static final String NOT_LOGIN 				= "anonymous"; 				// 로그인 미 상태 일반 접속

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		generate(request);
		try {
			chain.doFilter(request, response);
		} finally {
			clear();
		}
	}
	
	private void generate(ServletRequest request) {
		try {
			
			MDC.put(ClassicConstants.REQUEST_REMOTE_HOST_MDC_KEY, request.getRemoteAddr());
			
			if (request instanceof HttpServletRequest) {
				 HttpServletRequest httpServletRequest = (HttpServletRequest) request; 
				 
				 String header = ( httpServletRequest.getHeader("User-Agent") != null ? httpServletRequest.getHeader("User-Agent").toLowerCase().replaceAll(" ", "").toLowerCase() : "" );
				 String device = getDevice(header);
				 String browser = getBrowser(header);

				 MDC.put(REQUEST_DEVICE, device); 
				 MDC.put(REQUEST_BROWSER, browser ); 
				 MDC.put(REQUEST_METHOD, httpServletRequest.getMethod()); 
			     MDC.put(REQUEST_LOGIN_NAME, NOT_LOGIN);
			     MDC.put(ClassicConstants.REQUEST_REQUEST_URI, httpServletRequest.getRequestURI()); 
			        
			     StringBuffer requestURL = httpServletRequest.getRequestURL(); 
			        
			     if (requestURL != null) { 
			         MDC.put(ClassicConstants.REQUEST_REQUEST_URL, requestURL.toString()); 
			     } 
			        
			     
			} 
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private void clear() {
		 MDC.remove(ClassicConstants.REQUEST_REMOTE_HOST_MDC_KEY); 
	     MDC.remove(REQUEST_LOGIN_ID); 
	     MDC.remove(REQUEST_LOGIN_NAME); 
	     MDC.remove(ClassicConstants.REQUEST_REQUEST_URI); 
	     //MDC.remove(ClassicConstants.REQUEST_QUERY_STRING); 
	     // removing possibly inexistent item is OK 
	     MDC.remove(ClassicConstants.REQUEST_REQUEST_URL); 
	     //MDC.remove(ClassicConstants.REQUEST_USER_AGENT_MDC_KEY); 
	     //MDC.remove(ClassicConstants.REQUEST_X_FORWARDED_FOR); 
	     MDC.remove(REQUEST_METHOD); 
	     MDC.remove(REQUEST_DEVICE); 
	     MDC.remove(REQUEST_BROWSER); 
	}

	private String getDevice(String header) {

		if (header != null) {
			if( "".equals(header) ){
				return "Unknown";
			}
			
			try{
				UserAgent userAgent = UserAgent.parseUserAgentString(header);
				String os = userAgent.getOperatingSystem().getName().replaceAll("\\s",""); //공백 제거
				return os;
			}catch(Exception e){
				return "Unknown";
			}
		}
		return "Unknown";
	}
	
	private String getBrowser(String header) {

		if (header != null) {
			if( "".equals(header) ){
				return "Unknown";
			}
			
			try{

				UserAgent userAgent = UserAgent.parseUserAgentString(header);
				String bw = userAgent.getBrowser().getName().replaceAll("\\s","").replaceAll("InternetExplorer","IE");
				return bw;
				//String ma = userAgent.getBrowserVersion().getMajorVersion().replaceAll("\\s","");
				//String mi = userAgent.getBrowserVersion().getMinorVersion().replaceAll("\\s","");
				//return bw.concat("-V").concat(ma).concat(".").concat(mi);
				
			}catch(Exception e){
				return "Unknown";
			}
			
		}
		return "Unknown";
	}
}
