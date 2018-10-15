#!/usr/bin/env python
def my_add(a, b, f):
	return f(a) + f(b)
def my_simple_reverse(a):
	return -a
print my_add(-1, -2, abs)
print my_add(1, -2, my_simple_reverse)
my_abs = abs
print my_abs(-1)
your_abs = abs
print my_abs == your_abs
