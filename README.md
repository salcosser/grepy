# grepy
a Grep like utility that utilizes NFAs (Nondeterministic Finite Automatas) and DFAs (Deterministic Finite Automatas).
This utility takes in a regex, and parses strings against the regex using a DFA which it creates on the fly. It also creates an NFA. (NOTE: NFAs use <code>~</code> to represent epsilon)
These DFAs are then used to test if a set of given strings match the original regex.
<hr>

## Instructions
### Setup
1. Clone or download the main branch of the grepy repository.
2. Inside of the downloaded files, you will find a folder titled "Production Version". This is where the working product is.
3. In this directory, add your regex file in a <code>.txt</code> format. On the first line, write the regex you would like to use wrapped in a ^ and $ on either side.
 (ex. <code>a+b</code> would look like <code> ^a+b$</code>). If you choose, you may also put strings to test on the next lines, one per line. For reference, refer to the other regex files which are in the folder already.
4. If you would like to specify the filenames of the outputted DFA and NFA, create the files using the extension <code>.DOT</code>.
5. Ensure that all of your files are in the "Production Version" directory before continuing.

### Execution
6. Before continuing, ensure that your computer is equipped with Java Runtime Environment and that it is accessible from your terminal of choice, as it will be necessary for continuing. 
      (hint: to check if you have it installed, type <code>java --version</code> to check.)
7. navigate to the "Production Version" directory within your terminal or command prompt.
8. Start by typing the command <code>java -jar grepy.jar</code>, but do not press enter yet.
9. If you have created your own DFA and NFA files, add the DFA  <code>.DOT</code> file name first followed by the NFA <code>.DOT</code> file name. Finally add the file name of your regex <code>.txt</code> file.
10. If you did not create DFA and NFA files, just add the regex <code>.txt</code> file.
11. So, your execution command will look like one of the two following commands:

#### <code>java -jar grepy.jar DFAFILE.DOT NFAFILE.DOT regex.txt</code>
#### Or
#### <code> java -jar grepy.jar regex.txt</code>
<hr>

12. Once you run the command, the program will generate the state machines, as well as attempting to parse any strings that were in the regex file.
13. you can choose to continue to try other strings by simply inputting them as specified by the interface, or typing <code>quit()</code> to end the program.
14. To view the state machines, the easiest way to create them would be to copy the contents of each file, and paste them into <a href="https://edotor.net/">this site</a>. 
