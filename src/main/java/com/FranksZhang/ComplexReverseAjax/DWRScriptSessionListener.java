package com.FranksZhang.ComplexReverseAjax;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;

/**
 * scriptSession监听器
 * @author FranksZhang
 *
 */
public class DWRScriptSessionListener implements ScriptSessionListener{

	private static final Map<String, ScriptSession> scriptSessionMap = new HashMap<String, ScriptSession>();
	
	/**
	 * 添加客户端scriptSession
	 */
	@Override
	public void sessionCreated(ScriptSessionEvent ev) {
		WebContext webContext = WebContextFactory.get();
		HttpSession session = webContext.getSession();
		ScriptSession scriptSession = ev.getSession();
		//将scriptSession与httpSession关联起来
		scriptSessionMap.put(session.getId(), scriptSession);
	}

	/**
	 * 销毁scriptSession
	 */
	@Override
	public void sessionDestroyed(ScriptSessionEvent ev) {
		WebContext webContext = WebContextFactory.get();
		HttpSession session = webContext.getSession();
		scriptSessionMap.remove(session.getId());
	}

	/**
	 * 获取所有ScriptSession
	 * @return
	 */
	public static Collection<ScriptSession> getScriptSessions() {
		return scriptSessionMap.values();
	}
	
}
