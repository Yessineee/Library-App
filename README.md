# Library-App
JAVA Library App (JDBC)


Setup Instructions

To set up the project, follow these steps:

Copy and execute the creation.sql file:

Locate the creation.sql file in the project directory.

Execute the SQL file in your database management system (DBMS) to create the necessary tables and structures.
You can execute the SQL file using either a MySQL or PostgreSQL client, or a GUI tool of your choice.

Update the database connection details:
After executing the SQL file, configure the database connection:

Open the src/conn.java file in your preferred code editor.

Update the following variables in the conn.java file with your database information:

String dbURL = "jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME";
String dbUsername = "YOUR_USERNAME";
String dbPassword = "YOUR_PASSWORD";


Replace:

YOUR_DATABASE_NAME with the name of the database you created.

YOUR_USERNAME with your database username.

YOUR_PASSWORD with your database password.

Create an Admin User (for Admin Page):
If you plan to access the admin page, you need to manually create an admin user in your database. To do this:

Open your DBMS and run the following SQL query to insert an admin user:

INSERT INTO admn VALUES('admin', 'admin_password');


Replace admin_password with the password you want for the admin user.

Execute login.class to start the project:
After updating the database connection details and creating the admin user (if necessary), you can run the project:

Locate the compiled login.class file (inside the src/ directory).

Execute the login.class file to start the application.


This will start the project, and you can begin interacting with it.

Note: Admin Page: To access the admin page, you must manually create an admin user in the database. Follow the instructions in step 3 to insert an admin user with the appropriate credentials.
