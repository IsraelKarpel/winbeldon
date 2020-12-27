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
    "CREATE TABLE matches_singles"
    "(tourney_id VARCHAR(35) NOT NULL,"
    " tourney_date DATE NOT NULL,"
    " match_num INT NOT NULL,"
    " winner_id INT NOT NULL,"
    " loser_id INT NOT NULL,"
    " score VARCHAR(45) NULL,"
    " best_of INT NULL,"
    " round VARCHAR(10) NULL,"
    "PRIMARY KEY (tourney_id,match_num,winner_id,loser_id));")

print('table was created successfully')

# prepare sql statement
SQL_INSERT_MATCHES_SINGLES = 'INSERT INTO matches_singles VALUES (%s, %s, %s, %s, %s, %s, %s, %s)'

players_file = open('matches_singles.csv', 'r')

# skip first line (should be columns names)
players_file.readline()

rows = players_file.readlines()
rows_count = len(rows)

i = 1
start = time.time()
for row in rows:
    cols = row.split(',')

    tourney_id = cols[0]
    tourney_date = cols[1]
    match_num = int(cols[2])

    winner_id = cols[3]
    if winner_id == '':
        winner_id = None
    else:
        winner_id = int(winner_id)

    loser_id = cols[4]
    if loser_id == '':
        loser_id = None
    else:
        loser_id = int(loser_id)

    score = cols[5]

    best_of = cols[6]
    if best_of == '':
        best_of = None
    else:
        best_of = int(best_of)

    round_col = cols[7].strip()

    # create tuple of values
    values = (tourney_id, tourney_date, match_num, winner_id, loser_id, score, best_of, round_col)
    cursor.execute(SQL_INSERT_MATCHES_SINGLES, values)

    # commit every X rows, print status of uploading
    if i % 10000 == 0:
        db.commit()
        end = time.time()
        print("%.2f%% - %.0f sec" % ((i / rows_count * 100), (end - start)))
    i += 1

db.commit()

SQL_INDEX_BY_WINNER_ID = 'CREATE INDEX matches_singles_winner_id_index ON matches_singles (winner_id);'
cursor.execute(SQL_INDEX_BY_WINNER_ID)

end = time.time()
print("done uploading table to DB: %.0f sec" % (end - start))
