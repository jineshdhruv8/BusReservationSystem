# BusReservationSystem
Designed &amp; hosted a Bus Reservation System web service using NetBeans IDE and Glassfish server with a backend SQL database. Incorporated coordination protocol using SOAP message handler to confront invalid sequences of user invocations

How to run the files:
1.	The folder consists of “BusReservationSystem.sql” file, “BusReservationSystem”, and “TestBRS” folders
2.	Use the “BusReservationSystem.sql” sql file to create the database schema with data entries by importing the SQL file to MySQL. Now our database is ready. This database consists of 4 tables which are bus, passenger, reserve and route.
3.	Import the “BusReservationSystem” and “TestBRS”in NetBeans. The BusReservationSystem is the Server which provides the Web Service and TestBRS is the client which will consume the webservice.
4.	Go to “\NetBeansProjects\BusReservationSystem\src\java\BusReservationSystem\” this path and open the DatabaseConnection.java file. Then search “connectMysql” function call and update the username and password for connecting to the database accordingly. (Default username is root and password is test)
5.	Now, deploy the BusReservationSystem. After successful deployment, run the client side (TestBRS)
