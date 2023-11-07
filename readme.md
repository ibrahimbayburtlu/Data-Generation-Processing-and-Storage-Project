# Project Description
1. A source application will be written that generates random data: 
      In this data, the timestamp, a random integer between 0-100, and last 2 characters of the md5 hash of the top two data will generate 5 values per second. The generated values will be written to a socket.
2. The application on the listening socket side will perform filtering:
   If the value in the second field is above 90, it will be sent to a message queue along with the other fields. 
If not, it will write to append a file. 

3. Two applications will be written that listen to the Message Queue:
The first application will write the values it receives from MQ (RabbitMQ) to a database table. You don't need to code the Database side other than the insert. (Consumer) 
The second application will write the same message to a Mongo collection. Consecutive hash fields will be stored nested in the same record only if greater than "99". If not, a new record will be created. (Consumer) 

4. All of these operations will be logged, and the generated logs will be appended to a file.
