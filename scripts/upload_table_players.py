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
    "CREATE TABLE players"
    "(player_id INT NOT NULL,"
    " first_name VARCHAR(45) NOT NULL,"
    " last_name VARCHAR(45) NOT NULL,"
    " hand VARCHAR(1) NULL,"
    " birth_date DATE NULL,"
    " country_code VARCHAR(45) NOT NULL,"
    "PRIMARY KEY (player_id));")

print('table was created successfully')

# prepare sql statement
SQL_INSERT_PLAYERS = 'INSERT INTO players VALUES (%s, %s, %s, %s, %s, %s)'

players_file = open('players.csv', 'r')

# skip first line (should be columns names)
players_file.readline()

rows = players_file.readlines()
rows_count = len(rows)

i = 1
start = time.time()
for row in rows:
    cols = row.split(',')
    player_id = int(cols[0])
    first_name = cols[1]
    last_name = cols[2]
    hand = cols[3]

    birth_date = cols[4]
    if birth_date != '':
        birth_date = int(birth_date)
    else:
        birth_date = None

    country_code = cols[5]

    # create tuple of values
    values = (player_id, first_name, last_name, hand, birth_date, country_code)
    cursor.execute(SQL_INSERT_PLAYERS, values)

    # commit every X rows, print status of uploading
    if i % 1000 == 0:
        db.commit()
        end = time.time()
        print("%.2f%% - %.0f sec" % ((i / rows_count * 100), (end - start)))
    i += 1

db.commit()
end = time.time()
print("done uploading table to DB: %.0f sec" % (end - start))
