# Hack assembler

An assembler written in Java for the hack assembly language.

## To do:

[ ] - Read a file based on the command line argument passed to the program.
    [ ] -  Print the file name.
[ ] - 


## Steps:

### Parser:

[ ] - Read the file line by line.
    [ ] - Ignore white spaces at the beginning of each line.
    [ ] - If '//' encountered, ignore the rest of the line.
[ ] - Mark token as either A or C instruction.

### Instruction interpreter:

[ ] - If A instruction, convert decimal address to binary and return instruction.
[ ] - if C instruction, brek down instruction to constituents and return binary
encoded instruction.

Assembler (passes entire file to) -> Parser (emits one instruction after another)
-> Instruction interpreter (returns binary encoded instruction -> Assembler 
(concatenates instruction string and writes to file).
