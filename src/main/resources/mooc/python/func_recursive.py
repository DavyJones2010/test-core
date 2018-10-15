#!/usr/bin/env python
def fact(n):
	if n == 1:
		return 1
	else:
		return n * fact(n - 1)
val = raw_input('Please input number: ')
while 'stop' != val:
	n = int(val)	
	print 'fact', n, '=', fact(n)
	val = raw_input('Please input number: ')

def fact_2(n):
	return fact_tail_recursive(n, 1)
def fact_tail_recursive(n, curr):
	if n == 1:
		return curr
	else:
		return fact_tail_recursive(n - 1, curr * n)

val = raw_input('Please input number: ')
while 'stop' != val:
	n = int(val)	
	print 'fact', n, '=', fact_2(n)
	val = raw_input('Please input number: ')

def fact_loop(n):
	val = 1
	while n >= 1:
		val *= n
		n -= 1
	return val

val = raw_input('Please input number: ')
while 'stop' != val:
	n = int(val)	
	print 'fact', n, '=', fact_loop(n)
	val = raw_input('Please input number: ')

