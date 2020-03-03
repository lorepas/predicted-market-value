# PredictedMarketValue
The aim of the application is to predict the player’s market value in base of the statistics obtained in the last season from about 10.000 players. We consider only moving player, for this reason in pre-processing we remove the goalkeeper from the data.
The statistics that interest our analysis from last season are:
-	Team
-	Nation
-	Born date
-	Role
-	Calls
-	Presences
-	Minutes Played
-	Goals
-	Assists
-	Penalty goals
-	Yellow cards
-	Double yellow cards
-	Red Cards
-	Market value

The market value is considered as double and it’s the class label of our dataset. The output is the expected market value of a new player inserted by the user.
There is only the generic user that works with the app. The user can download the file from the document database present in our cluster. For this reason, downloading data is possible only if the user is connected with the VPN. The user can download JSON or CSV file. If the user downloads JSON file, first to load it, he must to convert the file in CSV format.
The user, after adding the file, can insert new statistics about a new player. When he presses the PREDICT button, he can view the expected market value of that player and the intervals given by the error.
We can image as generic user a team’s manager who would like to know the expected market value of a player that want to buy or the suggested market value of a player that want to sell.
