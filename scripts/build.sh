#!/bin/bash
mkdir -p out
javac -d out src/rpn/*.java test/rpn/*.java
java -cp out rpn.PruebasRpn
