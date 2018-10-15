# split -l <no_of_line_in_each_file> <orig_file_name> <new_file_prefix_>
# split -b <size_of_each_file> <orig_file_name> <new_file_prefix_>
split -l 1000 aaa.csv small_
split -b 100m aaa.txt small_
split -C 100m aaa.txt small_
