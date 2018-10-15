#!/usr/bin/env python
import os

list = [x * x for x in range(1, 11)]
print list
# equivalent to for loops below
list = []
for x in range(1, 11):
	list.append(x * x)
print list

list = [ m + n for m in 'ABC' for n in 'XYZ']
print list
# equivalent to for loops below:
list = []
for m in 'ABC':
	for n in 'XYZ':
		list.append(m + n)
print list	

list = [d for d in os.listdir('.')]
print list
