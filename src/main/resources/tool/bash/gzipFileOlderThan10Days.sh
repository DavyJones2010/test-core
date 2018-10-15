#!/bin/bash
#find . -type f -name "*.log" -mtime +10 -print -exec gzip {} \;
find . -type f -name "*.log" -mtime +10 | xargs  tar -czvPf  older_log_$(date +%F).tar.gz
find . -type f -name "*.log" -mtime +10 -delete