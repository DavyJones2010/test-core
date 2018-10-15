#!/usr/bin/env python
print sorted([3, 1, 2, 5, 4, 0])
def reverse_order(x, y):
	if x > y:
		return -1
	else:
		return 1
print sorted([3, 1, 2, 5, 4, 0], reverse_order)
