#include <iostream>
#include <string>
#include <stdio.h>
#include <vector>
using namespace std;

int main(){
	
	int s, s2, j=0, foundValue;
	bool found = false;
	string cipher, ciphertext, plainText = "";
	string guessList[] = {"in", "on", "of", "to", "at", "or", "is", "it", "we", "am", "my", "he","as", "by", "do", "if", "go", "up", "no", "us", "my", "am" };
	
	
	cout << "Enter a two letter Cipher Text" <<endl;
	cin >> cipher;
	while(cipher.size() > 2){
		cout << "The Cipher Text cannot be longer than Two Characters!" << endl;
		cin >> cipher;
	}
	

	
	for (int i =0; i  < sizeof(guessList); i++){
		s = (char)cipher[0] - (char)guessList[i][0];
		s2 = (char)cipher[1] - (char)guessList[i][1];
		
		if(s2 < 0){
			s2 = 26 + s2;
		}
		
		if(s < 0)
			s = 26+s;
		
		if(s==s2){
			found = true;
			foundValue = i+1;
			break;
		}
		
	}
	
	if(found){
		
		cout << "***************FOUND*************"<<endl;
		cout << "The Key is\t" << foundValue << endl;
		cout << "The word matched is:\t" << guessList[foundValue-1] << endl;
		
	}
	else cout << "Error" << endl;
	cout << "Enter complete Cipher Message" <<endl;
	
	getline(cin, ciphertext);
	
	for(int k = 0; k < ciphertext.length(); k++){
		if((ciphertext[k] - s) < 'a')
			plainText += ciphertext[k] + 26 - s;
		else
			plainText += ciphertext[k] - s;
	}
	cout << plainText;
	/*for(int i = 0; i < 8; i++){
		if((11*i)%8 == 1)
		cout << i <<endl;
		else cout<< "error" << endl;
	}*/
}
