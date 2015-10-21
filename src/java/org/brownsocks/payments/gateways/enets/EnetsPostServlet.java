package org.brownsocks.payments.gateways.enets;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet for eNets 'postURL' parameter.
 * 
 * Receives and decodes payments messages and generate payment events.
 * 
 * @author cveilleux
 */
public class EnetsPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger _log = LoggerFactory.getLogger(EnetsPostServlet.class);  
	
	/**
	 * Init param name for the name of the servlet context attribute containing a reference
	 * to the EnetsServerGateway.
	 */
	public static final String ENETS_SERVER_GATEWAY_KEY = "enets-server-gateway";
	
	public static final String DEFAULT_ENETS_GATEWAY_ATTRIBUTE_NAME = "enetsGateway";
	
	private EnetsServerGateway _gateway;
	
	@Override
	public void init() throws ServletException {
		
		if (_gateway != null)
			return; // already initialized, possibly through setEnetsServerGateway()
		
		String enetsGatewayAttributeName = getServletConfig().getInitParameter(ENETS_SERVER_GATEWAY_KEY);
		
		if (StringUtils.isEmpty(enetsGatewayAttributeName))
			enetsGatewayAttributeName = DEFAULT_ENETS_GATEWAY_ATTRIBUTE_NAME;
		
		_gateway = (EnetsServerGateway) getServletContext().getAttribute(enetsGatewayAttributeName);
		if (_gateway == null) {
			_log.warn("You need to instantiate and place a EnetsServerGateway instance under the " + enetsGatewayAttributeName + " servlet context attribute.");
			throw new ServletException("No enets gateway found. You need to instantiate and place a EnetsServerGateway instance under the " + enetsGatewayAttributeName + " servlet context attribute.");
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if (_gateway == null) {
			_log.warn("EnetsServerGateway was not set. Please refer to the documentation on how to use the EnetsPostServlet.");
			resp.sendError(500, "Post servlet uninitialized.");
			return;
		}
		
		String message = req.getParameter("message");
		if (message == null) {
			_log.warn("Invalid POST request received: No 'message' content. Ignoring.");
			resp.sendError(500, "Invalid request");
			return;
		}
		
		_gateway.handlePostCallback(message);
		
	}
	
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Allow", "POST, OPTIONS");
	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.sendError(500, "Invalid request");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String error = "<html><head><title>Invalid request</title></head><body>Invalid request</body></html>\n";
		
		resp.setStatus(500);
		
		resp.setContentType("text/html; charset=iso-8859-1");
		resp.setContentLength(error.length());
		Writer writer = resp.getWriter();
		writer.write(error);
		writer.flush();
		writer.close();
		resp.flushBuffer();
	}

	public void setEnetsServerGateway(EnetsServerGateway gateway) {
		_gateway = gateway;
	}
	
}
