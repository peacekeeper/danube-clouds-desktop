package danube.discoverydemo.external;

import java.io.Serializable;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExternalCall implements Serializable {

	private static final long serialVersionUID = 1613026640058299442L;

	private static final Logger log = LoggerFactory.getLogger(ExternalCall.class);

	private String path;
	private String query;

	public ExternalCall(String path, String query) {

		this.path = path;
		this.query = query;
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

		String url = request.getRequestURI().toString();
		String path = url.lastIndexOf('/') != -1 ? url.substring(url.lastIndexOf('/') + 1) : null;
		if (path == null || path.isEmpty()) return null;
		String query = request.getQueryString();

		return new ExternalCall(path, query);
	}

	public String getPath() {

		return this.path;
	}

	public void setPath(String path) {

		this.path = path;
	}

	public String getQuery() {

		return this.query;
	}

	public void setQuery(String query) {

		this.query = query;
	}

	@Override
	public String toString() {

		return "PATH: " + this.getPath() + ", QUERY: " + this.getQuery();
	}
}
