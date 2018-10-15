#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import shutil

print os.name
print os.path.abspath('.')
print os.path.join('/Users/davywalker', 'testdir')
print os.path.split('/Users/davywalker/testfile.txt')
print os.path.splitext('Users/davywalker/testfile.txt')

shutil.copyfile('dummy_file.txt', 'dummy_file_bak.txt')
os.rename('dummy_file.txt', 'dummy_file.tmp')
os.remove('dummy_file.tmp')
os.rename('dummy_file_bak.txt', 'dummy_file.txt')

print [x for x in os.listdir('.') if os.path.isdir(x)]
print [x for x in os.listdir('.') if os.path.isfile(x) and os.path.splitext(x)[1] == '.py']
_py_files = [x for x in os.listdir('.') if os.path.isfile(x) and os.path.splitext(x)[1] == '.py']
print len(_py_files)
for _py_file in _py_files:
	if _py_file[0:1] == '.':
		os.rename(_py_file, _py_file[1:])
	else:
		os.rename(_py_file, '.' + _py_file)

