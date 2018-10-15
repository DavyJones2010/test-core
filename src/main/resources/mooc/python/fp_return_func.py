#!/usr/bin/env python
def lazy_sum(*args):
	def sum():
		ax = 0
		for n in args:
			ax += n
		return ax
	return sum
f = lazy_sum(1, 2, 3, 4, 5)
f2 = lazy_sum(1, 2, 3, 4, 5)
# it is only we call f() will their sum be calculated
print f()
print f == f2

# closure
def count():
	fs = []
	for i in range(1, 4):
		def f():
			return i * i
		fs.append(f)
	return fs
# fs is an array that contains a series of function 'f'
f1, f2, f3 = count()
print 'f1==f2==f3?', f1 == f2 == f3
print f1()
print f2()
print f3()

def count_non_closure():
	fs = []
	for i in range(1, 4):
		def f(i):
			def g():
				return i * i
			return g
		fs.append(f(i))
	return fs
f1, f2, f3 = count_non_closure()
print 'f1 == f2 == f3?', f1 == f2 == f3
print f1()
print f2()
print f3()
