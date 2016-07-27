package com.FranksZhang.ComplexReverseAjax;

import org.directwebremoting.impl.DefaultScriptSessionManager;

public class DWRScriptSessionManager extends DefaultScriptSessionManager {

	public DWRScriptSessionManager() {
		this.addScriptSessionListener(new DWRScriptSessionListener());
	}
}
