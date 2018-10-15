#!/usr/bin/env python
def now():
	print '2015-09-30'
f = now
f()
print f.__name__
print now.__name__

def log(func):
	def wrapper(*args, **kw):
		print 'before calling %s():' % func.__name__
		val = func(*args, **kw)
		print 'after calling %s():' % func.__name__
		return val
	return wrapper

@log
def now_2():
	print '2015-09-30'
@log
def now_3():
	print '2015-09-30'
now_2()
now_3()
print now_2.__name__
print now_3.__name__
print now_2 == now_3
