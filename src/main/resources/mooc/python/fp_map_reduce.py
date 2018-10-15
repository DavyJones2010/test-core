#!/usr/bin/env python
def f(x):
	return x * x
print map(f, [1, 2, 3, 4, 5])

def transform_to_integer(x, y):
	return x * 10 + y
print reduce(transform_to_integer, [1, 2, 3, 4, 5])

def add(x, y):
	return x + y
print reduce(add, [1, 2, 3, 4, 5])

def str_to_int(s):
	def str_to_chars(s):
		def str_to_char(s):
			return {'0':0, '1':1, '2':2, '3':3, '4':4, '5':5, '6':6, '7':7, '8':8, '9':9}[s]
		return map(str_to_char, s)
	def ints_to_int(s):
		def int_to_int(x, y):
			return x * 10 + y
		return reduce(int_to_int, s)
	return ints_to_int(str_to_chars(s))
print str_to_int('21435')
print '21435' + '12345'
print str_to_int('21435') + str_to_int('12345')

