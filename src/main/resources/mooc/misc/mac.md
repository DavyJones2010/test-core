## mac快捷键 
* 在mac下通过terminal打开finder
```
	open .
```
* 后退
```
	cd -
```
* 改变命令提示符
```
	PS1='\[\e[36m\][\u@\h \w]$ \[\e[m\]'
```

## 必备软件
* Mou(markdown编辑神器)
* iTerm
* OmniGraffle
* startUML(UML+流程图)
	[破解](http://blog.csdn.net/mergades/article/details/46662413)
* duimoo(doubanfm)
* [github desktop](https://desktop.github.com/)
* sequelpro(mysql client)
	[sequelpro](http://www.sequelpro.com/download)
* [homebrew](http://jingyan.baidu.com/article/335530da8b2b0419cb41c338.html)

## ssh免密码登录
```
	//在A机下生成公钥/私钥对
	ssh-keygen -t rsa -P ''
	//把A机下的id_rsa.pub复制到B机~/.ssh目录下
	scp ~/.ssh/id_rsa.pub davywalker@host:/home/davywalker/.ssh/
	//把id_rsa.pub添加到B机~/.ssh/authorized_keys文件里
	cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
	chmod 600 ~/.ssh/authorized_keys
```

## 查看ip地址
* mac下
```
netstat
```
* linux下
```
ifconfig -a
```
```
netstat -antlp
```



