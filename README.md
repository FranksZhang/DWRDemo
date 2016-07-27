# DWRDemo
	DWR(Direct Web Remoting),是一个Ajax开源框架。他允许js调用服务器上的Java函数。也可以在后台操作前端的js函数，用来实现推送。这个Demo分为三个部分。

## Ajax
	这个小demo实现了点击前端按钮，通过DWR调用服务器端的java代码。这个和我们平时的http请求流程很像。

## SimpleReverseAjax
	这个反转Ajax是DWR2.0后新增的。它采用的是长连接方式。
	Reverse Ajax包括两种模式：主动模式和别动模式。主动模式有polling和comet两种，被动模式有piggyback这一种。这里的demo是使用comet模式。
