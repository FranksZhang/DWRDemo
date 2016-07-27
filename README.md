# DWRDemo
DWR(Direct Web Remoting),是一个Ajax开源框架。他允许js调用服务器上的Java函数。也可以在后台操作前端的js函数，用来实现推送。这个Demo分为三个部分。

## Ajax
这个小demo实现了点击前端按钮，通过DWR调用服务器端的java代码。这个和我们平时的http请求流程很像。

## SimpleReverseAjax
这个反转Ajax是DWR2.0后新增的。它采用的是长连接方式。

Reverse Ajax包括两种模式：主动模式和别动模式。主动模式有polling和comet两种，被动模式有piggyback这一种。这里的demo是使用comet模式。

## ComplexReverseAjax
在第二部分已经实现了推送的功能，但里面是对所有的scriptSession进行推送，并没有过滤。如果推送要根据具体用户进行推送的话，就需要对第二部分进行改善了。

### 关于ScriptSession
通过ScriptSession我们可以得到客户端（浏览器）的脚本执行权。即我们可以直接调用浏览器的js代码。ScriptSession是第一次访问和刷新都会生成一个新的ScriptSession。而我们常说的HttpSession刷新是会保留当前的HttpSession的。

要获取当前的ScriptSession,可以通过DWR的webcontext
```java
//从全文工厂中获取代表当前会话的session
ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
```
当我们要向所有的页面访问者推送时，可以通过下面方法获取所有的页面访问者
```java
//得到所有的ScriptSession
Collection<ScriptSession> sessions = Browser.getTargetSessions();
```

### 管理ScriptSession
我们可以通过一个map对象，将HttpSession和ScriptSession关联起来。定义key就是HttpSession的Id,其值就是ScriptSession对象。
这个demo里面通过自定义类实现ScriptSessionListener接口，来维护自己的Map。并注册一个ScriptSessionManager类来管理监听器。

### 使用ScriptSessionFilter过滤
```java
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
```
执行推送时经过过滤器
```java
Browser.withAllSessionsFiltered(filter, run); 
```

### 在海口项目中，思路是将用户的session和scriptSession关联起来
```java
	/**
	 * 初始化：将用户的session与scriptsession关联起来
	 */
	public void init(String eventId) {
		// 从全文工厂中获取代表当前会话的session
		ScriptSession scriptSession = WebContextFactory.get()
				.getScriptSession();
		// http的session
		// Map<String, Object> session =
		// ActionContext.getContext().getSession();
		HttpSession httpSession = WebContextFactory.get().getSession();
		// 获取当前用户
		String userId = (String) httpSession.getAttribute("userId");
		// String userId = (String) session.get("userId");
		User loginUser = userService.get(userId);
		// 在scriptsession中设置用户
		scriptSession.setAttribute("loginUser", loginUser);
		// 事件id
		scriptSession.setAttribute("currentEventId", eventId);
	}

```
这样我可以直接从scriptSession中getAttribute得到用户信息。并确定是否推送给该用户。而不需要使用过滤器。