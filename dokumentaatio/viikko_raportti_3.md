# Viikko 3

## Toteutukset

* Toimiva nonogram solveri joka pystyy ratkoa pieniä max 5x5 kokoisia nonogrammeja
* Tutkittu miten testejä voi lisätä 
* löydetty minkälaisen algoritmin projektiin voi luoda

## Ongelmat

* simppeli rekursiivinen solveri voi ratkaista helppoja nonorammeja mutta kun kooa vähänkin suurentaa huomaa ettei se pystykkään ratkaisemaan melkein mitään
* Testien kanssa suuria ongelmia kun projekti valittaa että junit dependencyt sun muut puuttuisivat, joten testejä ei olevielä
* Testien trial ja erroriin käytetty liikaa aikaa joten projekti ei muute päässyt etenemään suuremmin

## Ensi viikon suunnitelma

* Saada testit toimimaan ja vähintään 50% testikattavuus
* suunnitella seuraavaa parempaa algoritmia läpäisemään nonogrammit
* algoritmi joka on mielessä voidaan nähdä [wikipedia artikkelissakin](https://en.wikipedia.org/wiki/Nonogram#Solution_techniques) 
* algoritmi tulee siis menemään tarkistamaan ensin sarakkeet ja merkitsee sinne pakolliset saaret/pisteet ja myös mahdottomat sijainnit, jonka jälkeen tekee saman riveille, ja taas sarakkeille, ja jatkaa tätä kunnes on saanut kaiken ratkottua

![](https://en.wikipedia.org/wiki/Nonogram#/media/File:Paint_by_numbers_Animation.gif)

Aikaa käytetty: 12 tuntia
