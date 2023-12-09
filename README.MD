# Project Title

Java file segregate app.

## Authors

- [Mateusz Oprawa](https://www.github.com/mawarop)

## Task (Polish)
1.	Opis i cel zadania
      Twoim zadaniem jest napisanie programu, który będzie umożliwiał segregowanie plików. Program powinien:  
      Stworzyć strukturę katalogów
      - HOME
      - DEV
      - TEST  
        
      W momencie pojawienia się w katalogu HOME pliku w zależności od rozszerzenia przeniesie go do folderu wg następujących reguł
      - plik z rozszerzeniem .jar, którego godzina utworzenia jest parzysta przenosimy do folderu DEV
      - plik z rozszerzeniem .jar, którego godzina utworzenia jest nieparzysta przenosimy do folderu TEST
      - plik z rozszerzeniem .xml, przenosimy do folderu DEV
      Dodatkowo w nowo stworzonym pliku /home/count.txt należy przechowywać liczbę przeniesionych plików (wszystkich i w podziale na katalogi), plik powinien w każdym momencie działania programu przechowywać aktualną liczbę przetworzonych plików.
2.	Wymagania techniczne
      - Można użyć dowolnych bibliotek i frameworków na licencjach otwartych (np. MIT, Apache, itp.).
      - Projekt powinien być budowany i uruchamiany przez wybrany system do budowania (np. maven, gradle, itp..).
      - Do projektu powinna być dołączona instrukcja budowania i uruchamiania

## Installation and Build

Load maven dependecies and run main method from any java IDE (java 17)

or Install latest version of maven: https://maven.apache.org/install.html and

java 17: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

Build project:
mvn dependency:resolve,
mvn package

Run created jar from target folder: java -jar <jar-file-name>.jar
    