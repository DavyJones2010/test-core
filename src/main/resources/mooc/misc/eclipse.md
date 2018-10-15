##在mac下eclipse工程在构建时总是pending在validating log4j.xml:来禁止构建工程时验证xml等结构化文件正确性
```java
	Eclipse->Prefs->Validation->Disable All
```
##在mac下eclipse多开:
```shell
	Termianl->open -n /Application/Eclipse.app
```
##当maven项目更新了pom文件之后,需要更新依赖包
* 在eclipse中选中项目:右键->Maven->Update Project
* 如果改变(新加)的依赖是snapshot版本,记得勾选"force update of snapshots"

## eclipse插件
* [OpenExplorer](https://github.com/samsonw/OpenExplorer/downloads)
* 