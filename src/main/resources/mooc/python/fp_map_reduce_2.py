#!/usr/bin/env python
def formatter(s):
	def format_single_name(s):
		return s[0].upper() + s[1:].lower()
	return map(format_single_name, s)
print formatter(['aDaM', 'LISA', 'barT'])

def prod(s):
	def my_prod_reduce(x, y):
		return x * y
	return reduce(my_prod_reduce, s)
print prod([2, 3, 4, 5])
