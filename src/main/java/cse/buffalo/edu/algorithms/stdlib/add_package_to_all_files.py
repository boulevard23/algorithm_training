import os

lineToWrite = 'package cse.buffalo.edu.algorithms.stdlib;'
javaFiles = [f for f in os.listdir(".") if f.endswith(".java")]

for f in javaFiles:
  with open(f, 'r+') as file:
    content = file.read()
    file.seek(0, 0)
    file.write(lineToWrite + '\n\n' + content)
