//Libraries used for Program
#include <iostream> //library for input and out
#include <iomanip> 
#include <istream>
#include <fstream> // library for saving stuff to a file
#include <stdlib.h> // library for exit function used in waitlist function

using namespace std;

const int numberOfRows = 15; // number of rows in deck
const int numberOfCols = 20; // number of col in deck

// all the function declarations. all functions are defined at the end
void displayWelcome();
int seatsQty();
void displayDeck(string deck[][20], int numberOfRows, int numberOfCols);
int menu();
void bookTickets(int choice, int seats, string deck[numberOfRows][numberOfCols], int row, int col, int purchases);
double calcAmt(int choice, int purchases);
void writeFile(int row, int col);
void createDeck(string deck[][20], int numberOfRows, int numberOfCols);
void loadPrevious(string deck[][20]);
void checkFull(string deck[][20], bool& another, int choice, int numberOfCols);
bool waitList(bool another, int& purchases, int seats);
void inputSeats(int purchases, int& row, int& col, int rowMax, int rowMin, string rowRange);
void askMore(bool& another, int& seats);
void takeDetails(string& firstName, string &lastName, int& day, int& mnth, int& year);
void paymentChoice(string card, bool& invalidPayment, double totalAmt, bool wait, int choice);

// program begins here

int main(){
	
	// variable are declared here
	char choice, proceed;
	int seats;
	int i, j, row,col,cvv;
	int purchases = 0;
	double totalAmt;
	bool another = false;
	string deck[numberOfRows][numberOfCols];
	
	
	displayWelcome(); // this displays the welcome message on the screen
	createDeck(deck, numberOfRows, numberOfCols); // this assigns the value to deck. "*" to premium seats, "o" to economy seats
	loadPrevious(deck); // loads booked seat from saved file
	displayDeck(deck, numberOfRows, numberOfCols); // prints the deck to the screen in table format

	seats = seatsQty(); // asks how many seats u want to buy
	choice = menu(); // displays menu and user enters the choice
	
	checkFull(deck, another,choice, numberOfCols); // after the user enter choice, this checks if the selected section is full or not
	bool wait = waitList(another, purchases, seats); // if the seats are fully booked, waitlist function starts working, otherwise it skips to next line.
	
	
	system("cls"); // clears the screen
	cout << "\n\nYou asked to book " << seats << " seats.\n\n"; // display the number of seats you entered
	while(another){
		
		// now the program runs according to the choice entered
		switch (choice){
			case 1 :{	
	           	for(int m = 0; m < seats; m++){
	           		cout<< "The cost of each seat with car is $150 per person\n\n"; // tells the price.
	           		displayDeck(deck, numberOfRows, numberOfCols); // again shows the table
	           		
	           		inputSeats(purchases, row, col, 14, 8, "8 - 14"); // this function tell u in which row u can book and let u enter the seat and seat number
					   			        
	        		if(deck[row][col] == " * |"){ // check if seats is already booked or not
	                    	deck[row][col] = " X |"; // if seat is not book, it changes to X (booked)
	                    	system("cls"); // clears screen
	                    	cout << "\n\nThe seat in " << row << "th Row and " << col << "th Column has been booked.\n\n\n";
	                    	purchases++; // when seat is booked, the purchases increase by one
	                    	writeFile(row, col); // saves the seat number in file
	               	}
	           		else{ 
	           			system("cls");
	              		cout << "Invalid seat choice"<<endl; // if u try to book already booked seat, it display this message
	               	}
				}
				askMore(another, seats); // ask you if u want to book more seats or not. if yes, then again u enter seat number, otherwise it skips to next step
				break;
			}
				// this is case that runs if user enters different choice in menu (with car or without car.
				// this case is same as the above one. there are only little difference in this
			case 2 :{	
		       	for(int m = 0; m < seats; m++){
		           	cout<< "The cost of each seat without car is $95 per person\n\n"; // the price is different
					displayDeck(deck, numberOfRows, numberOfCols);
					
					inputSeats(purchases, row, col, 7, 0, "0 - 7"); // range of row is different here
					
					// this is same as above 
					
			        if(deck[row][col] == " o |"){
		               	deck[row][col] = " X |";
		               	system("cls");
		               	cout << "\n\nThe seat in " << row << "th Row and " << col << "th Column has been booked.\n\n\n";
	                  	purchases++;
	                  	writeFile(row, col);
		           	}
		           	else{
		           		system("cls");
		          		cout << "Invalid seat choice"<<endl;
		           	}
		        }
		        askMore(another, seats);
				break;
			}
		}
	}
	system("cls");
	cout << purchases << " seats have been booked"; // when seats are booked, it displays how many seats are booked.
	//Payment for seats
	cout << "\n\n Please proceed to pay. (P) "; // now it tell you to enter p  to proceed
	cin >> proceed;
	bool abort = true; // if u enter p, it starts working, otherwise tell you to enter p correctly.
	while(abort){
		if(proceed == 'P' || proceed == 'p'){
		// these are the variables used in this function
			string firstName, lastName;
			int day, mnth, year;
			takeDetails(firstName, lastName, day, mnth, year);			
			totalAmt = calcAmt(choice, purchases);
			
			string cardNumber, expiry;
			int cvv, paymentMethod;
			bool invalidPayment = true;
			
			for(int i = 0; i < 100; i++){
			cout << "-"; // this is divider line ---------------------------- only.
			}
			// now it tells you mode of payment from which u can choose.
			cout << "\n\nHow would you like to make payment for the purchase?\n\n \t 1) Credit Card \t\t 2) Debit Card \t\t 3) Cash Payment\n\n\t\t\t";
			cin >> paymentMethod;
			
			while(invalidPayment){
				if(paymentMethod == 1 || paymentMethod == 2 || paymentMethod == 3){
					// here it see what u entered.
					
					switch(paymentMethod){
					
						case 1:{ // this case runs if u enter credit card
							paymentChoice("Credit", invalidPayment, totalAmt, wait, choice); // this function runs. see the function 
							
							break;
							
						}
					
				
						case 2:{// this runs if you select debit. amost everything is same. just word credit card is replaced with debit card
							paymentChoice("Debit", invalidPayment, totalAmt, wait, choice);
							break;
						}
						case 3: {
							cout << "Please make cash payment of $" <<totalAmt<<" by visiting our local branch \nwithin 5 days of this transaction to confirm your tickets.";
							cout << endl;
								for (int l = 0; l < 100; l++){
									cout << '*'; // this is a divider **********************
								}
								cout << "\n\n\t\t\tThank you for shopping with us! \n\n \t\t\t Enjoy your Journey!";
								ofstream file("customer.txt", ios::app); // this saves everything to file.
								file << "\nSTATUS: PENDING PAYMENT\n"; // this is written because payment is not made.
		
							invalidPayment = false;
							break;
						}
					}	
				}
				else if(isalpha(paymentMethod)){
					cout << "\nPlease enter the number from the menu.\n";
				}
				else{
					cout << "\nInvalid Choice. Please choose either 1, 2 or 3: \t"; // this appears if u enter any invalid option number
					cin >> paymentMethod;
				}
			
			}
			
			// this block saves this text at the end of the file. this is ur name and all personal details
			ofstream file("customer.txt", ios::app); 
			file << "\n-------------------------PURCHASE BY-------------------------\n";
			file << "Name: " << firstName << " " << lastName << " DOB: " << day<<"/"<< mnth << "/" << year << " Total Amount: " << totalAmt << "\n";
			file << "\n*************************************************************\n";
			
			
			
			abort = false;
		}
		
		else{
			cout << "Please enter 'P' to Proceed.\t";
			cin >> proceed;
		}

	}
	
	return 0;
}


void displayWelcome(){ // this is a welcome fucntion
	for(int i = 0; i< 100; i++){
		cout << '*';
	}
	cout << endl;
	cout << "\n\n\t\t\t Welcome to BC Ferries! \n\n";
	cout << "\tThe sea is filled with timeless memories and we are here to help make \n\tnew ones. Experience Atlantic Canada the way it was meant to be:\n\t\t\t\t by sea.";
	cout << endl << endl << endl;
	cout << "We have two halls on our ferries. The price for seats in hall without car is $95 (Row 0 - 7) each \nand $150 (Row 8 - 14) for seats in hall for people travelling with car.\n\nYou can select from seats from below:-";
	cout << endl;
	for(int i = 0; i< 100; i++){
		cout << '*'; // this is divider **********************
	}
}
// this creates the seating arrangement
void createDeck(string deck[][20], int numberOfRows, int numberOfCols){
	for(int i = 0; i < numberOfRows; i++){
		for(int j = 0; j < numberOfCols; j++){
   			if(i <= 7){
   				deck[i][j] = " o |"; // this makes seat appear as O for economy seat
			   }
			else{
				deck[i][j] = " * |"; //this assign seat for premium class
			}
		}
	}
}
// this display the function in table format
void displayDeck(string deck[][20], int numberOfRows, int numberOfCols){
	cout << "\n\n \t * Premium Seats \t\t o Economy Seats";
	cout << "\t\t X Reserved Seats\n\n\n";
	cout << "Seats:   0   1   2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18  19" <<  endl; // this show the column headings

	for(int i = 0; i < numberOfRows; i++){
    	cout << "Row" << setw(3) << i<< " |"; // this show row number and puts a line, | in between every seat
    	for(int j=0; numberOfCols > j; j++){
    		cout << setw(3) << deck[i][j];
		}
    	cout << endl;
	}
}
// this is seat quantity function. you enter here how many seats u want to book
int seatsQty(){
	cout << "\n\nHow many seats would you like to book? ";
	int seats;
	bool invalidSeat = true;
	cin >> seats;
	while(invalidSeat){
		
		if( seats < 0 || seats > 300){
			cout << "\nInvalid Seat Quantity.\t Please enter a valid Seat Quantity: ";
			cin >> seats;
		}
		else{
			invalidSeat = false;
			return seats;
		}
	}
}
// this is menu function
int menu(){
		for(int i = 0; i < 100; i++){
		cout << "-"; // divider ----------------
		}
		cout <<"\n\n\t\t Menu:\n\n";
    	cout << "1)  Are you getting onboard with Car?\t\t"; // this display u options
	    cout << "2)  Or Without Car?\n\n";
   		cout << "Please enter your choice : ";
   		int choice;
    	cin >> choice;
    	bool invalidChoice = true;
    	while(invalidChoice){
    		if (choice == 1 || choice == 2){ // this checks if u entered the valid choice or not.
    			invalidChoice = false;
			}
			else{
				cout << "Invalid Menu Choice. Please select 1 or 2: \t";
				cin >> choice;
			}
		}
    	cout << endl << endl;
    	return choice;
}

double calcAmt(int choice, int purchases){ // this calculates the total amount you have to pay


	double totalAmt;
	cout << "\n\nCongratulations, You have successfully booked " << purchases << " tickets.";
	switch (choice){
		case 1:{ // this case calculates amount if you buy premium seats
			totalAmt = ((purchases*150)+((purchases*150*14.5)/100));
			cout << "\n\nYour total amount is $" << purchases*150 << " with \nservice tax of (14.5%) $" << (purchases*150*14.5)/100 <<".";
			cout << "Your net amount payable is $" <<totalAmt << endl << endl;
			break;
		}
		case 2:{ // this case run if u buy economy seat
			totalAmt = ((purchases*95)+((purchases*95*14.5)/100));
			cout << "Your total amount is $" << purchases*95 << " with service tax of (14.5%) $" << (purchases*95*14.5)/100 <<".";
			cout << "\n\nYour net amount payable is $" << totalAmt<< endl << endl;
			break;
		}
	}
	return totalAmt;	
}

// this function loads the previous booked seats
void loadPrevious(string deck[][20]){
	int a = 0;
	string word;
	string wordList[1000];
	ifstream fileOpen("customer.txt", ios::app);
	while(fileOpen >> word){
		wordList[a] = word;
		a++;
	}
	
	for(int b = 0; b < 100; b++){
	 	int r, c;
	 	if(wordList[b] == "Row:"){
	 		r = atoi(wordList[b+1].c_str());
		 	c = atoi(wordList[b+3].c_str());
		 	deck[r][c] = " X |";
		}
		 
	 }
}
// this function saves the seat number in file when you enter them
void writeFile(int row, int col){
	ofstream file("customer.txt", ios::app);
	file << "Row: "<< row <<"\t Seat: " << col << "\n";
}
// this function checks if the seats are available or not
void checkFull(string deck[][20], bool& another, int choice, int numberOfCols){
	switch(choice)	{
		case 1: { // this check for seat in premium section
			for(int q = 0; q < numberOfRows; q++){
				for(int w = 0; w < numberOfCols; w++){
					if(deck[q][w] == " * |" && another == false){
						another = true;	
					}
				}
			}
			break;
		}
		case 2:{ // this checks for seat in  economy section
			for(int q = 0; q < numberOfRows; q++){
				for(int w = 0; w < numberOfCols; w++){
					if(deck[q][w] == " o |" && another == false){
						another = true;	
					}
				}
			}
			break;
		}
	}
}
// if all seats are booked, this function runs
bool waitList(bool another, int& purchases, int seats){
		if(another == false){
		cout << "\n\n\t\tWe are fully booked.\n\tHowever, you can still book the tickets.\n\tBut you will be waitlisted.\n\n\nDo you want to continue? (Y/N): ";
		char wait; // the program tells that all seats are booked and let u decide if u want to get waitlisted or not
		cin >> wait; 
		if(wait == 'n' || wait == 'N'){// if you say no the program exits
			cout << "\n\nWe hope to provide you a better service next time.!";
			exit (EXIT_SUCCESS);
		}
		else{
			purchases = seats;
			return true;
		}
	}
}

void inputSeats(int purchases, int& row, int& col, int rowMax, int rowMin, string rowRange){
	
	bool invalidRow = true;
	bool invalidCol = true;
	
	cout << "\n\nEnter the details for seat "<< purchases + 1 << ".\n\n";
	cout <<"\n\n" << "Please enter the Row Number (" << rowRange << "): "; // this tells you row range from which u can select seat
    cin >> row;
	while(invalidRow){
	if(row < rowMin || row > rowMax){ // if you enter invalid row number, it warns you
		cout << "This is an Invalid Row. Please select Row from " << rowRange << "\t";
		cin >> row;
	}
	else{ // if you enter valid row number it goes to next step
		invalidRow = false;
		}
	}
	
	cout << "\nPlease enter the Seat Number: "; // now you have to enter seat number
	cin >> col;
	while(invalidCol){// checks if you enter the correct seat number or not
		if(col < 0 || col > 19){
			cout << "This is an Invalid Seat. Please select Seat from 0 to 19\t";
			cin >> col;
		}
		else{
			invalidCol = false;
		}
	}
}

void askMore(bool& another, int& seats){ // when you book seats, this function ask you if you want to book more seat or no
	cout << "Would you like to book more tickets? (Y/N)";
	char more;
	cin >> more;
				
	if(more == 'Y' || more == 'y'){ // if you sdy yes, it asks you how much
		another = true;
		cout << "\n\nHow many more seats would you like to book?";
		cin >> seats; // then you enter how much more seat and then you again book all of them
	}
	else{
		another = false;
	}
}
 // this function is for taking your personal details
void takeDetails(string& firstName, string &lastName, int& day, int& mnth, int& year){
	bool invalidDOB = true;
	
	system("cls");
	cout << "\n\n Please enter your Full Name: \n\n"; // it ask for your name
	cout << "First Name: ";
	cin >> firstName; // takes firstname
	cout << "\nLast Name: ";
	cin >> lastName; // takes last name
	
	while(invalidDOB){ // u can guess what the following code is asking for. DOB
		cout << "\n\nPlease enter your Date of Birth (DD/MM/YYYY):\n";
		cout << "\nDay: ";
		cin >> day;
		cout << "\nMonth: ";
		cin >> mnth;
		cout << "\nYear: ";
		cin >> year;
		
		if(day > 0 && mnth > 0 && year > 1900 && day < 32 && mnth < 13 && year < 2016){
			invalidDOB = false;
		} //here it checks for invalid date. if u enter a day like 32 or month 13 or year 1852, it will ask you to submit them again
		
		else{
			cout << "\n\nInvalid Date of Birth.\n\n";
			cout << "\n\nPlease enter your Date of Birth (DD/MM/YYYY):\n";
			cout << "\nDay: ";
			cin >> day;
			cout << "\nMonth: ";
			cin >> mnth;
			cout << "\nYear: ";
			cin >> year;
		}
		system("cls");
	}
		
	for (int i=0; i < 100; i++){
		cout << "-"; // divider -----------------
	}
}

// now is the time for payment
void paymentChoice(string card, bool& invalidPayment, double totalAmt, bool wait, int choice){
	string cardNumber, expiry;
	int cvv;
	bool invalidCard = true;
	cout << "\n\nPlease enter your " << card << " Card details below:\n\n"; // here it ask you tu enter card details
	for(int i = 0; i < 100; i++){
		cout << "-";// divider
	}
	cout << "\n\n" << card << " Card Number: ";
	cin >> cardNumber;
	while(invalidCard){
		if(cardNumber.length() == 16){ // if you enter a number smaller than or greater than 16 digits it gives error and ask you to re enter them
			invalidCard = false;
		}
		else{
			cout << "Invalid Card Number. Please enter correct sixteen digit Card Number.\n\n";
			cin >> cardNumber;
		}
	}
	cout << "\n\n Expiry (MMYY): "; //now it ask for expiry date
	cin >> expiry;
	bool invalidExpiry = true;
	while(invalidExpiry){
		if(expiry.length() == 4){ //checks if the expiry date is smaller than 4 digits
			invalidExpiry = false;
		}
		else{
			cout << "Invalid Expiry Date. Please enter correct Expiry Date.\n\n";
			cin >> expiry;
		}
	}
					
	cout << "\t CVV: ";
	cin >> cvv;// enter cvv security code
	bool invalidCvv = true;
	while(invalidCvv){
		if(cvv < 1000 && cvv > 99){ // checks cvv
			invalidCvv = false;
		}
		else{
			cout << "Invalid Card Number. Please enter correct three digit CVV.\n\n";
			cin >> cvv;
		}
	}

	cout << "Do you authorise BC Ferries to charge $" << totalAmt <<" to your Credit Card Account? (Y/N)"; // ask for final confirmation
	char confirm;
	cin >> confirm;
					
	if (confirm == 'y' || confirm == 'Y'){ // if you say yes
		cout << endl;
		for (int l = 0; l < 100; l++){
			cout << '*';
	}
	
	// following are two different cases that will happen
		if(wait){ // if you are booking waitlisted tickets, then this will happen
			cout << "\n\n\t\t\tThank you for shopping with us! \n\n \t\t\t Enjoy your Journey!"; // it will show a thak u message
			ofstream file("customer.txt", ios::app);//and save the details in file.
			file << "\n****************************************\nChoice: " << choice <<"\nSTATUS: Waitlisted\n"; // but the status will appear as waitlisted
		}
		else{	//if you are making a normal booking
			
		cout << "\n\n\t\t\tThank you for shopping with us! \n\n \t\t\t Enjoy your Journey!"; // it shows same thank you message
		ofstream file("customer.txt", ios::app);
		file << "\nSTATUS: CONFIRMED\n"; // but status will be confirmed
		}
	}
	else{ // this will happen in case you say no to the final confirmation
		cout << endl;
		for (int l = 0; l < 100; l++){
			cout << '*';
		}
		cout << "\n\n\t\tTransaction was aborted.\n\nWe will be happy to see you again."; // it will say tansaction aborted 
		ofstream file("customer.txt", ios::app);
		file << "\nSTATUS: CANCELLED\n"; // and status of cancelled will appear on screen
		}
		invalidPayment = false;					
}
