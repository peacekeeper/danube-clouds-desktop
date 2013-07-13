package danube.clouds.desktop.external;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ExternalCall implements Serializable {

	private static final long serialVersionUID = 1613026640058299442L;

	private String requestURL;
	private String path;
	private String query;
	private Map<?, ?> parameterMap;

	private ExternalCall(String requestURL, String path, String query, Map<?, ?> parameterMap) {

		this.requestURL = requestURL;
		this.path = path;
		this.query = query;
		this.parameterMap = parameterMap;
	}

	public static Stack<ExternalCall> getStackFromSession(HttpSession session) {

		@SuppressWarnings("unchecked")
		Stack<ExternalCall> stack = (Stack<ExternalCall>) session.getAttribute("__externalcalls");

		if (stack == null) {

			stack = new Stack<ExternalCall> ();
			session.setAttribute("__externalcalls", stack);
		}

		return stack;
	}

	public static ExternalCall fromRequest(HttpServletRequest request) {

		String requestURL = request.getRequestURL().toString();
		String requestURI = request.getRequestURI();
		String path = requestURI.lastIndexOf('/') != -1 ? requestURI.substring(requestURI.lastIndexOf('/') + 1) : null;
		if (path == null || path.isEmpty()) return null;
		if (path.endsWith("/clouds")) return null;
		String query = request.getQueryString();
		Map<?, ?> parameterMap = new HashMap<Object, Object> ((Map<?, ?>) request.getParameterMap());

		ExternalCall externalCall = new ExternalCall(requestURL, path, query, parameterMap);

		return externalCall;
	}

	public String getRequestURL() {

		return this.requestURL;
	}

	public String getPath() {

		return this.path;
	}

	public String getQuery() {

		return this.query;
	}

	public Map<?, ?> getParameterMap() {

		return this.parameterMap;
	}

	@Override
	public String toString() {

		return "REQUESTURL: " + this.getRequestURL() + ", PATH: " + this.getPath() + ", QUERY: " + this.getQuery() + ", PARAMETERMAP: " + this.getParameterMap();
	}
}
