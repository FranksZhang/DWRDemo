package com.FranksZhang.ComplexReverseAjax;

import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;

public class MessagePush {

	/**
	 * scriptSession过滤器
	 */
	ScriptSessionFilter filter = new ScriptSessionFilter() {
		@Override
		public boolean match(ScriptSession session) {
			//tag作为过滤的标志，可以自定义
			String tag = (String) session.getAttribute("tag");
			if ("receiverTag".equals(tag)) {
				return true;
			} else {
				return false;
			}
		}
	};

	public void send(final String msg) {

		Runnable run = new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();

			public void run() {
				// 调用要js函数及参数
				script.appendCall("show", msg);
				// 得到所有的scriptSession
				Collection<ScriptSession> sessions = Browser
						.getTargetSessions();
				// 遍历每一个ScriptSession
				for (ScriptSession session : sessions) {
					session.addScript(script);
				}
			}
		};
		// 执行有过滤器的推送
		Browser.withAllSessionsFiltered(filter, run);
	}
}
