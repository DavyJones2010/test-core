#!/usr/bin/env python
def is_odd(n):
	return n % 2 == 1
print filter(is_odd, [1, 2, 3, 4, 5, 6])
