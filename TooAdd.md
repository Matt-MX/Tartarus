# TARTARUS 1

## To Add
- Guis
  - Login Screen # [Username], [Password], [Login], [Quit]
  - Title Screen # [Singleplayer], [Multiplayer], [Options], [Logout], [Quit]
  - Options Screen # [keyMap: Jump], [keyMap: Attack], [keyMap: Place], [keyMap: Left], [keyMap: Right], [keyMap: Crouch], [Back]
  - World Select # [{List Of Worlds}], [Load World], [Cancel], [New World]
  - New World # [input: name], [Create], [Cancel]
- Game
  - Player # Movement, Interaction, Events
  - World # Events, PlayerHandler, Block handler
  - World Generation # Generation through perlin noise seed
  - Items # Weapons, Tools, Consumables
  - World Interaction # Block Breaking, Block Placing
  - Enemies # Movement AI, Attacks
- Server
  - ? Multiplayer
  - Session authenticator
    - Login token
  - Login routine
    - Enters login details
    - Sends login details to server
    - Server checks if details are valid
    - If valid, return and save auth key
    - If invalid, return null
    - Client will disable multiplayer features if invalid

### Login packet format
```json
{"type":"LOGIN","username":"MattMX","password":"12u8jeqe29qd"}
```

### Login packet response
```json
[valid] {"type":"LOGIN","token":"183247y37yr7qh3qhvb9qhdqhuh"}
[invalid] {"type":"LOGIN","token":"NULL"}
```
