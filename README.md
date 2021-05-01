# accountBook
Java开发爱好者，为了解决生活记账便捷业余开发记账本系统，打算开启myHome系列，做一系列便捷生活的web、安卓或者小程序等应用。

项目主要使用vue + springboot，前端在vue-admin-template模板基础上开发，后端整合mybatis-plus处理mysql数据。
使用该项目需要了解vue、nodejs和一些webpackage、babel等杂碎前端知识，了解JavaEE、springboot，mybatis-plus基本使用。
目前项目是1.0版本，主要有用户user、流水record、标签label三个模块，在需要记账的时候按提示输入流水信息，创建新的流水记录即可，流水信息可以模糊查询等等...功能不再累赘介绍。

在前端工作区目录终端执行npm install下载依赖，npm run dev运行前端服务；
在后端运行myhome.sql文件生成数据库，pom、application.properties、maven等根据个人需要修改，之后运行AccountbookApplication.java