# import the connector from the mysql module that we downloaded
import mysql.connector

# create a connection to the database()
db = mysql.connector.connect(
    host='localhost',
    user='root',
    passwd='db202020',
)
cursor = db.cursor()

# execute mysql level commend, for example create a new db:
cursor.execute("CREATE DATABASE winbeldon")

print('DATABASE winbeldon created successfully')
