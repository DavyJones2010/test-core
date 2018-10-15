#!/bin/bash
# 返回:(类似windows下的后退)
cd -

# 返回上级目录:(类似windows下的向上)
cd ..

# view memory info
cat /proc/meminfo

# view cpu info
cat /proc/cpuinfo

# view load average info
cat /proc/loadavg
```
	load1: 一分钟的系统平均负载
	load5: 五分钟的系统平均负载
	load15:十五分钟的系统平均负载
	runq: 在采样时刻,运行队列的任务的数目,与/proc/stat的procs_running表示相同意思
	plit: 在采样时刻,系统中活跃的任务的个数（不包括运行已经结束的任务）
```

# view hd info
df -h

# view traffic
cat /proc/net/dev

# view tcp info
cat /proc/net/snmp

# view sys info in mac
sysctl -a hw

# 列出目录下指定文件的行数:
wc -l aaa*.txt

# 输出文件中包含某个关键字的行:
grep "key_word" file_name

# 输出文件中不包含某个关键字的行:
grep -v "key_word" file_name

# 列出任务
ps aux | grep java

# 解压
　　tar –xvf file.tar //解压 tar包
　　tar -xzvf file.tar.gz //解压tar.gz
　　tar -xjvf file.tar.bz2 //解压 tar.bz2
　　tar –xZvf file.tar.Z //解压tar.Z
　　unrar e file.rar //解压rar
　　unzip file.zip //解压zip

# 压缩
   tar –cvf jpg.tar *.jpg //将目录里所有jpg文件打包成tar.jpg
　　tar –czf jpg.tar.gz *.jpg //将目录里所有jpg文件打包成jpg.tar后，并且将其用gzip压缩，生成一个gzip压缩过的包，命名为jpg.tar.gz
　　tar –cjf jpg.tar.bz2 *.jpg //将目录里所有jpg文件打包成jpg.tar后，并且将其用bzip2压缩，生成一个bzip2压缩过的包，命名为jpg.tar.bz2
　　tar –cZf jpg.tar.Z *.jpg //将目录里所有jpg文件打包成jpg.tar后，并且将其用compress压缩，生成一个umcompress压缩过的包，命名为jpg.tar.Z
　　rar a jpg.rar *.jpg //rar格式的压缩，需要先下载rar for linux
　　zip jpg.zip *.jpg //zip格式的压缩，需要先下载zip for linux

# 查看端口占用情况
	netstat -alnp tcp

# 查看端口属于哪个程序？端口被哪个进程占用
	lsof -i :8083

### grep与fgrep区别
* grep把模式当做正则表达式看
* fgrep把模式当做固定字符串看,所以速度更快

### top
这个是Linux自带的命令，查看系统资源消耗情况，可以看看CPU、内存、SWAP、I/O的消耗情况，需要特别注意的有几个值：
ni，这个值如果特别高说明线程上下文切换开销较大，看看是不是开了太多的线程导致的
res，这个代表了进程实际占用的内存
swap，内存不足就会占用swap空间，这个时候一般应用的性能会急剧下降，需要特别关注

### vim删除每行前n个字符, 配合fgrep能够快速从日志文件中捞取关键数据
* 删除每行的第一个字符
	```
:%s/^.
	```
* 删除每行的前3个字符
	```
:%s/^.\{3}
	```
* 删除每行前面的数字:
	```
:%s/^.\d*
	```
* 删除每行的最后一个字符
	```
:%s/.$//
	```
* 删除每行的最后3个字符
	```
:%s/.\{3}$
	```

### 切分大文件
* split -l <no_of_line_in_each_file> <orig_file_name> <new_file_prefix_>
* split -b <size_of_each_file> <orig_file_name> <new_file_prefix_>
```
	split -l 1000 aaa.csv small_
	split -b 100m aaa.txt small_
	split -C 100m aaa.txt small_
```

