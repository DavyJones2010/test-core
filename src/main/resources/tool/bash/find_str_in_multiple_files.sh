# find <directory> -type -f -name "*.*" | xargs grep "<string>"
find . -type f -name "2015*" | xargs grep "1579407239"
pgm -A -b `autoget profilesynchost` 'fgrep 708654685 /home/admin/profilesync/logs/*'

pgm -A -b `autoget havanaservicehost` 'fgrep 2743027083 /home/admin/output/logs/*.log'

fgrep -n string file
# 列出string在file中的第几行 