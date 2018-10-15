# http://askubuntu.com/questions/365877/copy-only-folders-not-files
find -type d -links 2 -exec mkdir -p "/path/to/backup/{}" \;