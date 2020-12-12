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
    "CREATE TABLE matches_doubles"
    "(tourney_id VARCHAR(35) NOT NULL,"
    " tourney_date DATE NOT NULL,"
    " match_num INT NOT NULL,"
    " winner1_id INT NOT NULL,"
    " winner2_id INT NOT NULL,"
    " loser1_id INT NOT NULL,"
    " loser2_id INT NOT NULL,"
    " score VARCHAR(45) NULL,"
    " best_of INT NULL,"
    " round VARCHAR(10) NULL,"
    "PRIMARY KEY (tourney_id,match_num,winner1_id,winner2_id,loser1_id,loser2_id));")

print('table was created successfully')

# prepare sql statement
SQL_INSERT_MATCHES_DOUBLES = 'INSERT INTO matches_doubles VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)'

players_file = open('matches_doubles.csv', 'r')

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

    winner1_id = cols[3]
    if winner1_id == '':
        winner1_id = None
    else:
        winner1_id = int(winner1_id)

    winner2_id = cols[4]
    if winner2_id == '':
        winner2_id = None
    else:
        winner2_id = int(winner2_id)

    loser1_id = cols[5]
    if loser1_id == '':
        loser1_id = None
    else:
        loser1_id = int(loser1_id)

    loser2_id = cols[6]
    if loser2_id == '':
        loser2_id = None
    else:
        loser2_id = int(loser2_id)

    score = cols[7]

    best_of = cols[8]
    if best_of == '':
        best_of = None
    else:
        best_of = int(best_of)

    round_col = cols[9].strip()

    # create tuple of values
    values = (
        tourney_id, tourney_date, match_num, winner1_id, winner2_id, loser1_id, loser2_id, score, best_of, round_col)
    cursor.execute(SQL_INSERT_MATCHES_DOUBLES, values)

    # commit every X rows, print status of uploading
    if i % 10000 == 0:
        db.commit()
        end = time.time()
        print("%.2f%% - %.0f sec" % ((i / rows_count * 100), (end - start)))
    i += 1

db.commit()
end = time.time()
print("done uploading table to DB: %.0f sec" % (end - start))
