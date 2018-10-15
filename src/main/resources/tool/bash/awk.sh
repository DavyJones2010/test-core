# awk '{pattern + action}' {filenames}

# 默认分隔符为空格, action为"print $4"
head jmonitor.log | awk '{print $4}'
# 指定分隔符, action为"print $2"
head jmonitor.log | awk -F ':' '{print $2}'
head jmonitor.log | awk -F: '{print $2}'
# 在开头结尾分别加上字符
awk -F: 'BEGIN {print "name,shell"}  {print $1","$7} END {print "blue,/bin/nosh"}' /etc/passwd
# 先执行BEGIN，然后读取文件，读入有/n换行符分割的一条记录，然后将记录按指定的域分隔符划分域，填充域，$0则表示所有域,$1表示第一个域,$n表示第n个域,随后开始执行模式所对应的动作action。接着开始读入第二条记录······直到所有的记录都读完，最后执行END操作

# awk正则表达式
awk '/REG/{action}'
# 搜索/etc/passwd有root关键字的所有行, pattern为'/root/', pattern就是要表示的正则表达式，用斜杠括起来
awk -F: '/root/' /etc/passwd

# 使用awk去重: http://www.letuknowit.com/topics/20120401/use-awk-remove-duplicate-lines.html/
awk '!a[$1]++ {print $1}' /etc/passwd

# 去重+计算次数: http://blog.163.com/jwxue_2001/blog/static/5059471520114253370773/
awk '{prov[$1]++};END{for(i in prov) print i, prov[i]}' visitor.txt

# 使用awk多个分割符进行分割
awk -F '[-|]' '{print $2}' # 打印出来的是: 123\n99\n100

zhc-123|zhang
hongchangfirst-99|zhang
hongchang-100|zhang

# 改变输出
awk -F '[-|]' '{printf("%s,%s\n",$3,$1)}' # 打印出来的是: zhang,zhc\nzhang,hongchangfirst\nzhang,hongchang

# 使用if/else语句
awk -F '[-|]' '{if($2=="100"){printf("%s,%s\n",$3,$1)} else{print $2}}' # 打印出来: 123\n99\nzhang,hongchang


# 小练习: 
# 1. 统计1小时内访问各个url的次数, 并进行排序
fgrep 18/Mar/2016:00:00:00 2016-03-18-access_log | awk -F '[ ?]' '{a[$7]++};END{for(i in a) print i, a[i]}' > ~/abc.log
# 结果如下:
http://ww.api.test.net/uic-http/getnewwwprofile 11
http://ww.api.test.net/uic-http/getwwprofile 3
http://ww.api.test.net/uic-http/getemailnick 1
http://ww.api.test.net/uic-http/getmobilenick 1

# 降序排序:
sort -nr -t ' ' -k 2 ~/abc.log
http://ww.api.test.net/uic-http/getnewwwprofile 11
http://ww.api.test.net/uic-http/getwwprofile 3
http://ww.api.test.net/uic-http/getmobilenick 1
http://ww.api.test.net/uic-http/getemailnick 1


# 2. 找出访问最多的url对应的访问者ip:

10.176.20.193 4611 - [18/Mar/2016:00:00:00 +0800] "GET http://ww.api.test.net/uic-http/getnewwwprofile?type=loginprofile&uid=abcde" 200 344 "-" "Mozilla" 0.005 36378
10.192.162.100 3271 - [18/Mar/2016:00:00:00 +0800] "GET http://ww.api.test.net/uic-http/getwwprofile?type=cbuprofile&uid=bcedf" 200 428 "-" "Mozilla" 0.003 3048
10.206.147.104 1750 - [18/Mar/2016:00:00:00 +0800] "GET http://ww.api.test.net/uic-http/getnewwwprofile?type=fullprofile&uid=hrjk" 200 12 "-" "Mozilla" 0.001 8308
10.192.163.135 2536 - [18/Mar/2016:00:00:00 +0800] "GET http://ww.api.test.net/uic-http/getemailnick?email=opqrst" 200 32 "-" "Mozilla" 0.002 41092
10.192.163.135 2037 - [18/Mar/2016:00:00:00 +0800] "GET http://ww.api.test.net/uic-http/getnewwwprofile?type=fullprofile&uid=uvw&token=abcdefg" 200 12 "-" "Mozilla" 0.003 32842
10.192.160.101 1655 - [18/Mar/2016:00:00:00 +0800] "GET http://ww.api.test.net/uic-http/getnewwwprofile?type=fullprofile&uid=xyz&token=abcdefg" 200 12 "-" "Mozilla" 0.002 62539
10.192.163.133 1978 - [18/Mar/2016:00:00:00 +0800] "GET http://ww.api.test.net/uic-http/getnewwwprofile?type=fullprofile&uid=nnn&token=abcdefg" 200 12 "-" "Mozilla" 0.002 39768 


# sort: http://www.cnblogs.com/51linux/archive/2012/05/23/2515299.html
sort abc.txt #默认升序
sort -u abc.txt #去重
sort -r abc.txt #降序
sort -n abc.txt #以数字方式排序 1 2 10 11 而不是 1 10 11 2
sort -t : -k 2 atc.txt #-t来指定每行的间隔符号, -k来指定按照第几行排序
sort -f abc.txt #会将所有小写字母转变成大写字母进行比较, 即忽略大小写的排序
sort -c abc.txt #检查文件是否已经排好序, 如果乱序, 则输出第一个乱序的行的相关信息
sort -b abc.txt #会忽略每行前面的所有空白部分, 从第一个可见字符开始比较

## 
$ cat facebook.txt (公司名 人数 平均工资)
google 110 5000
baidu 100 5000
guge 50 3000
sohu 100 4500

# 按照公司平均工资降序,  如果平均工资相同, 则按照人数升序
sort -n -t ' ' -k 3r -k 2r facebook.txt

# 查看文件第a行到第a+k行的数据
# tail -n a: 显示最后的a行数据
# tail -n +a: 从第a行开始显示
cat file.txt | tail -n +a | head -n k
# 或者
cat file.txt | head -n a+k | tail -n +a
# 或者
sed -n 'a,a+kp' file.txt

# 查看文件倒数第200行到倒数第100行的数据
cat file.txt | tail -n 200 | head -n 100

# src/test/resources/log/updateIsvAccountId.log
# 找出timestamp>1464156000000(即2016-05-26 14:00)的记录(需要用到substr函数)
cat updateIsvAccountId.txt | awk -F ':' '{if(substr($12,0,13)>1464156000000) print $0}'

# awk 格式化输出: 用制表符分割, 需要手动添加\n换行符
awk -F ':' '{printf("%s\t%s\n",$1,$2)}' updateIsvAccountId.txt

# awk 内置变量
# NR(Number of Record): 代表当前行号 
# NF(Number of Field): 代表当前列号(用-F分割好之后的列号)
# FNR(File Number of Record): 多个文件awk, 每个文件会从0开始计数
# 打印出单数行
awk '{if(NR%2==1) print(NR, $0)}' updateIsvAccountId.txt
# 打印出班级信息, 并在每行开头加上编号
awk '{print (FNR, $0)}' class1.txt class2.txt class3.txt


# src/test/resources/log/names.log
# 找出名字长度大于10, 或者为两个字的名字
awk 'length($1)>10 {e++; print "long name in line", NR}
> NF != 1 {e++; print "bad name count in line", NR}
> END { if(e>0) print "total errors: ", e}' names.txt

# 关联数组(其实就是Map)
awk '{names[++n] = $1}
END { for ( i = 1; i <= n; i++ )
> for (j = 1; j <= n; j++ )
> print names[i],names[j]}' names2.txt

# 关联数组进行统计(如何遍历map)
chips 3
dip 2
chips 1
cola 5
dip 1
# 脚本: 
awk '{count[$1]=count[$1]+$2}
END {for(name in count) print name, count[name]}' list.txt

# 

# 有穷状态机(FSM): 抑制比特流中所有新出现的1
# Input: 	011010111
# Output: 	001000011
# 紧跟在0后边的1被改成0; 输入流中所有其他比特位不变
# 关键: 1) 列出状态 2) 找出状态转移 3) 分离状态与I/O
if(currentState == lastInputZero && input == 1) {
	currentState = lastInputOne;
	print(0)
} else if(currentState == lastInputZero && input == 0) {
	currentState = lastInputZero;
	print(0)
} else if(currentState == lastInputOne && input == 1) {
	currentState = lastInputOne;
	print(1)
} else if(currentState == lastInputOne && input == 0) {
	currentState = lastInputZero;
	print(0)
}

 


