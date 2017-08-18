Client<br>
Idea is to have a simple client for a multiplayer card game. Client should sets up a table which can lay cards at certain locations on the table. Cards can be flipped or turned. the client can only see the content of their own cards, opponent cards should be hidden unless opponent flips them. Client should allow registration of a user and allow users to create lobbies and start games. Lobbies will contain the two gamers and observers. Game information should be relayed to the users in lobbies. Observers should be able to see the contents of both gamer's cards, etc.

Currently:<br>Just a bunch of screens to register and login as a user and look at current user activity
	with relevant soap handles

TODO:<br>
lobby system screens<br>
game system screens<br>
handle rpc timeouts<br>
post login menu (slideout?)<br>
better ui struture<br>
remove all the bulky soap beans and replace iwth json objects<br>

For now implement game with blank cards. Cards should load data from a json file following specs: https://mtgjson.com/
The idea here is that the client/server should handle any cards as long as the two clients playing each other have hte same pool of cards they made the deck from.
clients should be able to load a huge set of cards from a massive json, then make a deck to their own rules. games can follow any rules as long as the table allows them to arrange cards accordingly.
hash and compare the cards to make sure client/client have the same set of cards.
