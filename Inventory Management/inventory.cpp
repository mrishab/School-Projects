
#include <cstdlib>
#include <iostream>
#include <iomanip>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
using namespace std;

struct Bankaccount 
{
  string name, city, phone;  
};

void addData(vector <Bankaccount>& list, int& size);
void dispData(vector <Bankaccount> list, int size);
void saveFile(vector <Bankaccount> list, int size);
void openFile(vector <Bankaccount>& list, int& size);
char getMenuResponse();


int main(int argc, char *argv[])
{
	vector <Bankaccount> recList;
  int numOfRecs = 0;
  bool run = true;
  do
  {
    cout << "Total Accounts currently in the Bank List: "<< numOfRecs << endl << endl;
  	switch ( getMenuResponse() ) 
  	{
  		case 'A': addData(recList, numOfRecs); break;
  		case 'D': dispData(recList, numOfRecs); break;
  		case 'R': openFile(recList, numOfRecs); break;
  		case 'S': saveFile(recList, numOfRecs); break;
  		case 'Q': run = false; break; // ends 
  		default : cout << "Please enter correct response." << endl;
  	}
  }
  while (run);
  cout << endl << "Have a Good Day...!" << endl;
  return EXIT_SUCCESS;
}

// Task:     Allow data entry 
// Accepts:  References to the list
// Returns:  Nothing
void addData(vector <Bankaccount>& list, int& size)
{
  Bankaccount tmp; // declare a temp 
  char response;

  char str[256]; // needed for cin.getline
  
    system("cls");
    cout << "Enter the Details for the new Account: (\'quit\' to Quit)" << endl << endl;
    cout << "Name: ";

    cin.getline(str, 256, '\n'); // for char arrays
    tmp.name = str;
    cout << "Address: ";
    cin.getline(str, 256, '\n');
    tmp.city = str;
    cout << "Phone Number: ";
    
    cin >> tmp.phone;
    cout << endl;

    cout << " Save the record to the list? (y/n) ";
    cin >> response;
    if (toupper(response) == 'Y'){
    	size++;
    	list.push_back(tmp);

	} 
    
	else{
    	cout << "Information Discarded" << endl;
    	system("pause");
		}
  system("cls");
}
// Open file
void openFile(vector <Bankaccount>& list, int& size)
{
	Bankaccount tmp;
	string filename;
	cout << "Please enter the name of the File: ";
	cin >> filename;
  ifstream infi(filename.c_str());
  string str;
  stringstream strstrm;
  
  while(infi.fail()){
  	system("cls");
  	cout << "There was some error in accessing the file.\nPlease Try Again." << endl;
  	cin >> filename;
  	infi.open(filename.c_str());
  }


  if (!infi.fail()) { 
  
    system("cls");
    cout << "Reading List from the disc ";
    
    while(!infi.eof()) {
      getline(infi, str, ';'); 
      tmp.name = str;
      
      getline(infi, str, ';');
      strstrm.str(""); strstrm.clear(); 
      strstrm << str; 
      strstrm >> tmp.city;
      
      getline(infi, str); 
      strstrm.str(""); strstrm.clear();
      strstrm << str; 
      strstrm >> tmp.phone;
      
      list.push_back(tmp);
      size++;
    }
    cout << endl << endl << size << " records read from the disc." << endl;
  
    system("PAUSE");
    system("cls");
  }
  else {
    cout << "something wrong!" << endl;
    system("PAUSE");
    system("cls");
  }

}

void dispData(vector <Bankaccount> list, int size)
{
  system("cls");
  double phone = 0;
  
  if(size < 1) {
    cout << "No Records" << endl;
  } else {
    cout << "Bank Account List" << endl << endl;
    cout << fixed << setprecision(2);   
    cout << "Name                 City          Phone     " << endl;
    cout << "***********************************************" << endl;   
    cout << left;     
    for (int i = 0; i < size; i++) {
      cout << setw(21) << list[i].name << right
           << setw(14)  << list[i].city
           << setw(10) << list[i].phone << left << endl; 
    }
    
    cout << "************************************************" << endl;
    cout << right << setw(3) << size;
    cout << " items listed";
    cout << right << setw(19) << phone << endl << endl;
  } 
  system("PAUSE");
  system("cls");
}


void saveFile(vector <Bankaccount> list, int size) {
	string filename;
	cout << "Please enter the name of the File: ";
	cin >> filename;
  ofstream outfi(filename.c_str());
  
  if (!outfi.fail()) { 
    system("cls");  
    cout << "Saving List to the disc ";
    
    for(int i = 0; i < size; i++) {
      outfi << list[i].name << ';' 
            << list[i].city << ';'
            << list[i].phone;
      if (i < size-1) outfi << endl;
    }
    cout << endl << size << " records writen to the disc." << endl;
    outfi.close();
    system("PAUSE");
    system("cls");
  } 
  else {
    cout << "something wrong!" << endl;
    system("PAUSE");
    system("cls");
  }
}


char getMenuResponse()
// Task:     put menu
// Accepts:  
// Returns:  response by user
// Modifies: 
{
	char response;
	cout << endl << "\t\t\tMenu" << endl
		 << "(A)dd Record\n\n(D)isplay Records\n\n(R)ead Filen\n\n(S)ave File\n\n(Q)uit" << endl << endl << "> ";
	cin >> response;
	cin.ignore(256, '\n');	
	return toupper(response);
}
