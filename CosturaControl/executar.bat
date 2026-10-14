@echo off
rem Compila e executa o CosturaControl (Windows). Precisa do JDK 11+.
if not exist out mkdir out
dir /s /b src\*.java > fontes.txt
javac -encoding UTF-8 -d out @fontes.txt
del fontes.txt
java -cp "out;lib\sqlite-jdbc-3.36.0.3.jar" Main
pause
