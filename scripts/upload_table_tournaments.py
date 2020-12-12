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
    "CREATE TABLE tournaments"
    "(tourney_id VARCHAR(35) NOT NULL,"
    " tourney_name VARCHAR(35) NOT NULL,"
    " surface VARCHAR(10) NULL,"
    " draw_size INT NULL,"
    " tourney_level VARCHAR(2) NULL,"
    "PRIMARY KEY (tourney_id));")

print('table was created successfully')

# prepare sql statement
SQL_INSERT_TOURNAMENTS = 'INSERT INTO tournaments VALUES (%s, %s, %s, %s, %s)'

players_file = open('tournaments.csv', 'r')

# skip first line (should be columns names)
players_file.readline()

rows = players_file.readlines()
rows_count = len(rows)

i = 1
start = time.time()
for row in rows:
    cols = row.split(',')

    tourney_id = cols[0]
    tourney_name = cols[1]
    surface = cols[2]

    draw_size = cols[3]
    if draw_size == '':
        draw_size = None
    else:
        draw_size = int(draw_size)

    tourney_level = cols[4]

    # create tuple of values
    values = (tourney_id, tourney_name, surface, draw_size, tourney_level)
    cursor.execute(SQL_INSERT_TOURNAMENTS, values)

    # commit every X rows, print status of uploading
    if i % 5000 == 0:
        db.commit()
        end = time.time()
        print("%.2f%% - %.0f sec" % ((i / rows_count * 100), (end - start)))
    i += 1

db.commit()
end = time.time()
print("done uploading table to DB: %.0f sec" % (end - start))
