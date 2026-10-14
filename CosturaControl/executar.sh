#!/bin/sh
# Compila e executa o CosturaControl (Linux/Mac). Precisa do JDK 11+.
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name '*.java') && \
java -cp "out:lib/sqlite-jdbc-3.36.0.3.jar" Main
