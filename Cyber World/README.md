CyberWorld Final Project for COMP-155 Course: Object Oriented Programming Instructor: Paul Rushton

CyberWorld is a text-based command game built using object-oriented approach in JAVA Programming language. It is a third person game in which user can move around the map, solve puzzle, collect hacker tools and use them to hack restricted areas.

Game Walkthrough
----------------------------------------------------------------------------------------------------------------------

Cyberworld is a city which is spread over a 13 x 13 grid. It is handy to keep pen and paper and draw a map about the cell, which makes it easy to visualise the structure of the map. Initially the player starts at location (5, 5) which is a 3 Cell wide road. The road is filled with HackBoxes which are MCQ based Puzzles. Move some blocks over the map and initiate FIND command to search for a Puzzle within a radius of 1 Block. These puzzles are randomly selected from given array of puzzles and randomly arranged on the Map.

When each of these puzzles is encountered, initiate OPEN command and say yes. Answer the question to unlock the puzzle. Every failed attempt brings the cop towards the player by one cell. If it reaches player’s position, the player dies at that spot and game overs. After unlocking the hackbox, initiate LOOK command to see which gadget is there. Pick that gadget. There are only 3 gadgets you can pick at a time. The first gadget needed is: Brute-forcer. When you have this gadget, go to location (5, 1) and initiate hack command. After getting the details about the system, use Brute-forcer with parameter help to get hint about the solution. Based on the hint, think of a common password which in this case is password. Initiate command, USE Brute-forcer [parameter] password [ACCESS GRANTED]

Entrance to the building is unlocked. But go back to the road and collect DECRYPTOR. Enter into the Office Building and find the vulnerable system to Kendall (our Ex boss’) office. Use the Scanner to get a hint about which gadget to use. When you know that it is an encryption-related vulnerability, use the decryptor to get hint about the solution. Manually try to break the Caesar cipher by hit and trial method. The decrypted message is cyberworld. Use DECRYPTOR cyberworld to gain access to the Cabin.

Once, you are into the cabin, go to his Desktop and access it by initiating ACCESS command. Try breaking his PC password by guessing common password. Gadget cannot help you break into the Fixed items. The password is 12345.

Make a note of the information that is displayed on his screen, especially the MAC-Address. Get back on the road and collect MAC-spoofer and a RFID card. Use the RFID card with parameter swipe to gain access to the Bank’s building. Once inside the Bank’s building, use the Scanner to find vulnerability about the entrance to bank’s secret location. It turns out to be to asking for connecting a device. Use the MAC-spoofer to spoof the stolen MAC-address from Kendall’s PC as parameter and then you will have access to the Bank’s cash storage area.

Once, inside the storage area, find the vault and the final task will be to manually break the password. Once, done, Game WON!

MCQ Puzzle Answers
---------------------------------------------------------------------------------------------------------------------

The answers for the MCQ Questions are: What is a Linux? OS Scanning is performed in which phase of a pen test? Pre-attack What will an open port return from an ACK scan? RST What is the preferred communications method used with systems on a bot-net? IRC Which of the following best describes a distributed denial-of-service attack? A DoS carried out by multiple systems What is the attack called evil twin? Rogue access point What is another term for turning off the SSID broadcast? SSID cloaking, What is the maximum length of an SSID? Thirty-two characters Which wireless mode connects machines directly to one another, without the use of an access point? Ad hoc Forensic Toolkit (FTK) is a? Software EXPLOITS DEPEND ON, ALL THE ABOVE FAT stands for, File allocation tables Registry key can be altered directly with? Registry Editor CDMA stand for, Code division multiple access At which layer of the OSI communication model dose bridge operate? Datalink Forensic Examiners are interested in following file because these contain portions of all documents and other materials a user produce while using the computer? Swap Power-On Passwords are function of Computer Hardware Digital certificates are used in the IPSec connection, What type of network infrastructure device issue digital certificate? CA

