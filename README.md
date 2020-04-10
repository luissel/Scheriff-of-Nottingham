# [Tema 1 Programare Orientata pe Obiecte] Scheriff of Nottingham 2018

Implementare:
Am creat 2 pachete: Main si game.

In pachetul game, am creat clasele cu ajutorul carora am creat jocul: Player, 
PlayerComparator, Assets, BaseStrategy, BribedStrategy, GreedyStrategy, 
FinalScore. In clasa Assets am implementat structura unui bun si metodele 
pentru a ccesa si modifica membrii. Clasa Player implementeaza jucatorul si 
retine datele utile despre el. Clasele GreedyStrategy, BribedStrategy mostenesc
clasa BaseStrategy care la randul ei mosteneste clasa Player. In cele 3 clase 
am implementat strategiile de joc, fiecare avand metoda merchantAction si 
metoda scheriffAction care primeste ca parametru un jucator pe care trebuie sa
il inspecteze sau nu(daca accepta mita). Pentru calculul scorului final am 
retinut intr-un Hash pentru fiecare jucator numarul de bunuri din fiecare tip 
legal si le-am comparat intre ele, astfel acordand bonusurile.

In pachetul Main, am facut logica jocului. Cum fiecare jucator trebuie sa fie 
serif de 2 ori, numarul de runde e 2 * nr_jucatori. La fiecare runda se 
completeaza bunurile din mana fiecarui jucator, se alege jucatorul care va fi 
serif in functie de runda respectiva si va inspecta comerciantii. De fiecare 
data adaug bunurile confiscate in runda curenta la finalul pachetului de carti.

La finalul rundelor, afisez clasamentul final, ordonat descrescator in functie 
de punctaj.
