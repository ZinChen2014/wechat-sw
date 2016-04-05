# wechat-sw
微信开发，wechat developing including CMS,Using Jfinal-Weixin 2.0 and AmazeUI2.5

﻿项目启动步骤

1： 使用 wechat_sw.sql中的 sql 语句创建数据库与数据库表

2: 修改 /resources/jfinal_config.txt 文件，填入正确的数据库连接用户名、密码

3: 将项目导入 eclipse。推荐使用 Eclipse IDE for Java EE Developers， 版本：Eclipse Kepler (4.3.2) SR2

4: 打开 com.desksoft.wechat.common.config包下的 MicroMsgJfinalConfig 文件，右键单击该文件并选择 Debug As ---> Java Application。
        其它启动项目的方式见 《JFinal手册》。除此之外，项目还可以与其它普通java web 项目一样使用 tomcat
   jetty 等 web server 来启动，启动方式与非jfinal项目完全一样。

5: 打开浏览器输入  localhost 即可查看运行效果

注意： 1.请确保您安装了 JavaSE 1.6 或更高版本，tomcat下运行项目需要先删除 jetty-server-xxx.jar，否则会有冲突
     2.不要升级到jfinal2.1以上的版本，2.2之后的版本去掉了JSON的包，会导致微信接口的诸多返回json无法解析
