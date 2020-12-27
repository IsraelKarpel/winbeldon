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

# CREATE TABLE rankings:
cursor.execute(
    "CREATE TABLE rankings"
    "(`rank_date` DATE NOT NULL,"
    " `rank` INT NOT NULL,"
    " `player_id` INT NOT NULL,"
    " `points` INT NOT NULL,"
    "PRIMARY KEY (rank_date,player_id));")

print('table was created successfully')

# prepare sql statement
SQL_INSERT_RANKINGS = 'INSERT IGNORE INTO rankings VALUES (%s, %s, %s, %s)'

rankings_file = open('rankings.csv', 'r')

# skip first line (should be columns names)
rankings_file.readline()

rows = rankings_file.readlines()
rows_count = len(rows)

i = 1
start = time.time()
for row in rows:
    cols = row.split(',')
    date = int(cols[0])
    rank = int(cols[1])
    player_id = int(cols[2])
    points = int(cols[3].strip())

    # create tuple of values
    values = (date, rank, player_id, points)
    cursor.execute(SQL_INSERT_RANKINGS, values)

    # commit every X rows, print status of uploading
    if i % 30000 == 0:
        db.commit()
        end = time.time()
        print("%.2f%% - %.0f sec" % ((i / rows_count * 100), (end - start)))
    i += 1

db.commit()

SQL_INDEX = 'CREATE INDEX rankings_rank_date_player_rank_index ON rankings (rank_date, player_rank);'
cursor.execute(SQL_INDEX)

end = time.time()
print("done uploading table to DB: %.0f sec" % (end - start))
