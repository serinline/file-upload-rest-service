**file-upload-rest-service** 

This service: 
* It can upload single/multiple files as rest post call
* The files must be in the valid form 
* Get the updated data by primary key using a get call
* Delete data by primary key using delete call

The files should:
* it must be plain text file - .csv or .txt
* first line of file (header) must be "PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP"
* next 4 lines represents single record
* Primary Key must be non-blank string 