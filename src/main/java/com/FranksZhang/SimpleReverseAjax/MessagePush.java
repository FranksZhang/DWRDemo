package com.FranksZhang.SimpleReverseAjax;

import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;


public class MessagePush {

	public void send(final String msg) {
		
		Runnable run = new Runnable(){
			private ScriptBuffer script = new ScriptBuffer();
			public void run() {
				//调用要js函数及参数
				script.appendCall("show", msg);
				//得到所有的scriptSession
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				//遍历每一个ScriptSession
				for(ScriptSession session : sessions) {
					session.addScript(script);
				}
			}
		};
		Browser.withAllSessions(run);
	}
}
