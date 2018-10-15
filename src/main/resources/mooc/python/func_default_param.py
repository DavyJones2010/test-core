#!/usr/bin/env python
def pow(x, n = 2):
	val = x
	while n > 1:
		val *= x
		n -= 1
	return val

x = raw_input('Input your number:')
if x == 'stop':
	exit()	
n = raw_input('Input your factor:')
while x != 'stop':
	x = float(x) 
	if n == ' ':
		print 'default power of', x, 'is:', pow(x)
	else:
		n = int(n)
		print n, 'power of', x, 'is:', pow(x, n)
	x = raw_input('Input your number:')
	if x == 'stop':
		break	
	n = raw_input('Input your factor:')
