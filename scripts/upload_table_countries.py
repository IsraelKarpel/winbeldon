# import the connector from the mysql module that we downloaded
import mysql.connector
import time

# create a connection to the database()
db = mysql.connector.connect(
    host='localhost',
    user='root',
    passwd='db202020',
    database='winbeldon'
)
cursor = db.cursor()

# CREATE TABLE players:
cursor.execute(
    "CREATE TABLE countries"
    "(country_name VARCHAR(45) NOT NULL,"
    " country_code VARCHAR(5) NOT NULL,"
    "PRIMARY KEY (country_code));")

print('table was created successfully')

# prepare sql statement
SQL_INSERT_COUNTRIES = 'INSERT INTO countries VALUES (%s, %s)'

players_file = open('countries.csv', 'r')

# skip first line (should be columns names)
players_file.readline()

rows = players_file.readlines()
rows_count = len(rows)

i = 1
start = time.time()
for row in rows:
    cols = row.split(',')
    country_name = cols[0]
    country_code = cols[1].strip()

    # create tuple of values
    values = (country_name, country_code)
    cursor.execute(SQL_INSERT_COUNTRIES, values)

db.commit()
end = time.time()
print("done uploading table to DB: %.0f sec" % (end - start))
