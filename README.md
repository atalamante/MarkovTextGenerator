# Markov Text Generator

Markov Text Generator creates a random yet readable set of text from existing text.

# Description
This program takes a text file, output length, and word length as input.
The word length is used to determine how to split up words in the text file and then stores these words accordingly. 
The key to this program is that it finds the next word after the current word and it is stored in a HashMap. 
When it comes time to output the text, it is randomized. 
After the first word is chosen, the program looks at the list of words that appeared after it, and then choses from that. 
This happens repeatedly until the output length is reached. The output of the program will look like normal English, but it will truly be scrambled words. 
This program was tested with the text files that are in this repository. 
