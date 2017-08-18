Client<br>
Idea is to have a simple client for a multiplayer card game. Client should sets up a table which can lay cards at certain locations on the table. Cards can be flipped or turned. the client can only see the content of their own cards, opponent cards should be hidden unless opponent flips them. Client should allow registration of a user and allow users to create lobbies and start games. Lobbies will contain the two gamers and observers. Game information should be relayed to the users in lobbies. Observers should be able to see the contents of both gamer's cards, etc.

Currently:<br>Just a bunch of screens to register and login as a user
	with relevant soap handles

TODO:<br>
lobby system screens<br>
game system screens<br>
handle rpc timeouts<br>
post login menu (slideout?)<br>
better ui struture<br>
remove all the bulky soap beans and replace iwth json objects<br>