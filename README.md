# CsvDiff

This is a tool to compare the diff of two csv file. Before running the code, you need to config it in the file of application.properties:

- **cur-file:** the current file you have to be compared with(full path with file name).

- **target-file:** the file you want to compare with(full path with file name).

- **diff-file-delete:** the file to store for the result of the difference between two files(say some records only cur-file has but target-file doesnt).

- **diff-file-modification:** the file to store any modification between the records in two files.

- **diff-file-create:** the file to store any new records from the target file.

- **id:** the unique id for each record in the file.

- **time-window:** the time window you want to set up to run the application.

- **load-file-endpoint:** the end point to load data for comparing.

