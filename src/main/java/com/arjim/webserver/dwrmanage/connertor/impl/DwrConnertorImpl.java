/**
 ***************************************************************************************
 *  @Author     1044053532@qq.com   
 *  @GIT        https://gitee.com/arjim/arjim-server
 *  @License    http://www.apache.org/licenses/LICENSE-2.0
 ***************************************************************************************
 */
package com.arjim.webserver.dwrmanage.connertor.impl;

import org.directwebremoting.ScriptSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arjim.constant.Constants;
import com.arjim.server.model.MessageWrapper;
import com.arjim.server.model.Session;
import com.arjim.server.session.SessionManager;
import com.arjim.webserver.dwrmanage.connertor.DwrConnertor;

@Service("dwrConnertorImpl")
public class DwrConnertorImpl implements DwrConnertor {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SessionManager sessionManager;

	@Override
	public void close(ScriptSession scriptSession) {
		String sessionId = (String) scriptSession.getAttribute(Constants.SessionConfig.SESSION_KEY);
		try {
			String nid = scriptSession.getId();
			Session session = sessionManager.getSession(sessionId);
			if (session != null) {
				sessionManager.removeSession(sessionId, nid);

				log.info("dwrconnector close sessionId -> " + sessionId + " success ");
			}
		} catch (Exception e) {
			log.error("dwrconnector close sessionId -->" + sessionId + "  Exception.", e);
			throw new RuntimeException(e.getCause());
		}
	}

	@Override
	public void connect(ScriptSession scriptSession, String sessionid) {
		try {
			log.info("dwrconnector connect sessionId -> " + sessionid);
			sessionManager.createSession(scriptSession, sessionid);
		} catch (Exception e) {
			log.error("dwrconnector connect  Exception.", e);
		}
	}

	@Override
	public void pushMessage(String sessionId, MessageWrapper wrapper) throws RuntimeException {
		Session session = sessionManager.getSession(sessionId);
		session.write(wrapper.getBody());
		// dwrScriptSessionManagerImpl.getScriptSessionsByHttpSessionId(scriptSession.getHttpSessionId());
		// DwrUtil.sendMessageAuto((String)request.getParameter("my"), "sssssss中文");
	}

}
