package com.FranksZhang.SimpleReverseAjax;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;

public class MessagePush {

	public void send(String msg) {
		//获取当前页面的scriptSession
		ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
		
		
	}
}
