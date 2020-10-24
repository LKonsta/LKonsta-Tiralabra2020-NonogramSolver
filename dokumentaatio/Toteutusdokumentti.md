# Toteutus dokumentti

Alkuperäinen ideani oli luoda muutama eri algoritmi nonogrammien ratkomiseen mutta loppujen lopuksi ajan tuhlaamisen koodin toimivuuteen saamiseksi ja liian monimutkaisten koodien jatkamiseen, jätti minulle vain yhden (permutaatio solverin) algoritmin joka ratkaisee nonogrammeja.

## Aikajana

### 1-3 viikot

Alotin siis ohjelman suunnittelun ja kirjoittamisen. Sain ensimmäisen 3 viikon aikana toimimaan vain tällähetkelläkin koodista löytyvän simppelin ratkojan joka on erittäin hidas koska toimii rekursiivisesti. En saanut paljoa aikaan näillä viikoilla koska ensinnäkin en ollut koodannut mitään suurta projektia melkein puoleen vuoteen ja oli suuria ongelmia muutenkin testien toimivuuden ja pom tiedoston kanssa muutenkin.

### 2-4 viikot

aloitin rekursiivisen solverin yhteydessä myös suunnittelemaan omaa solveria logical_solver jossa käytin, silloin mielestäni hyvää ideaa jossa käytin alueen kartoittamiseen erittäin monimutkaista taulukkoa muotoa: ArrayList<Integer>[][][]

Tässä ideana oli että ensimmäiset 2 taulukkoa täsmää nonogrammin kokoa eli esim 10X15 kokiset nonogrammit olisivat muotoa ArrayList<Integer>[10][15][2]. Kolmannessa taulukossa on siis vian 2 vaihtoehtoa jotka täsmäävät sitä että katsotaanko kenttää sarakkeista vai riveistä jolloinka katsoen kentässä kohtaa x ja y olisi siellä tiedossa [[{rivien saarimahdollisuudet}],[{sarakkeen saarimahdollisuudet}]]. Eli syvimmässä kohdissa tätä oli 2 Arraylistaa joissa tiedossa mahdolliset saaret kohdissa.

Saaret olivat taas tiedossa siten että jossain tietyssä kohdassa voi olla vain tiettyjä saaria joten ne merkitään permutaation kaltaisesti kenttään siten että arraylistaan lisätään kaikki ne saarien indexit joita niissä kohdissa voisi olla. Saaria taas poistetaan sen mukaan jos esim jostain kohdasta löydetään pakollinen tyhäj niin sen viereiset saaret tarkistetaan ja katsotaan että onko haljenneet saaret enään mahdollisia olla siinä rivissä jne...

Tässä kohtaa koodistani alkoi tulla jopa minulle hieman liian monimutkaista joten päätin alkaa luomaan permutaatiolla toimivaa solveria.

### 5- viikot

Otin selvää enemmän muista nonogram solvereista ja sain idean permutaatio solveriin. Aika alkoi loppua joten testit ja muu toiminnallisuus kuten visuaalinen käyttöliittymä jäi tekemättä.

## Permutaatio solver

Permutaatio solveri ratkoo siis nonogrammit helpommat rivit ja sarakkeet kerrallaan kunnes pääsee loppuun tai ei löydä enään mitään muutettavaa ja alkaa bruteforceamaan nonogrammia läpi kunnes löyttää mahdollisen ratkaisun.

Kun permutaatio solverin koodauksessa menikin niin paljon aikaa niin mahdolliset nopeuttajat joita olisin voinut lisätä tähän aloritmiin jäivät vain yhteen. Suunnitelmissa oli siis muokata algoritmia ja verrata uutta saanutta aikaa vanhaan ja katsoa kuinka paljon parempi pieninkin lisäys nopeuttaisi tätä.

<img src="https://github.com/LKonsta/Tiralabra2020-NonogramSolver/blob/master/dokumentaatio/nonogrammi_versiot.png" width="600">

Nopeudet ovat millisekunneissa.

Version 1 nonogrammi ratkoi rivi ja sarake kerrallaan kunnes ei ollut enään muutosta, eikä ottanut huomioon että kauanko rivillä tai sarakkeella kestäisi mahdollisesti ratkaista. Muutosta on esim suuresti jos verrataan 10 levyistä riviä jossa on yksi saari kokoa 6. tässä tapauksessa permutaatioita on vain: 
[2222221111],[1222222111],[1122222211],[1112222221],[1111222222] eli listasta tulisi vain 5 pituinen.
kun taas jos rivin pituus olisi 10 ja siinnä olisi kolme 1 kokoista saarta olisi vaihtoehtoja:
[212121111],[212112111],[212111211],...,[111211212],[111121212] erittäin monta.

Mitä versio 2 tekee siis on ennen ratkomista asettaa kaikille riveille ja sarakkeille arvon sen mukaan että kuinka kauan mahdollisesti kestää että kaikki permutaatiot ovat saaneet listattua. Jonka jälkeen ohjelma ratkaisee rivit ja sarakkeet pikkuhiljaa pienemmät listan pituuden tuottavat ensin ja siitä eteenpäin suurempia kohti.

## Suunnitelmat

Suunnitelimssa oli seuraavaksi version 3 parantaa bruteforcen nopeutta. Tämän olisi saavuttanut sillä että ennen bruteforcausta listaa kaikki sen hetkiset rivit ja sarakkeet permutaatio funktion läpi ja katsoo millä rivillä on mahdollisimman vähän, eli mahdollisimman pieni listaus eri permutaatioista. Jos siis aloitamme siitä rivistä jossa vähiten variaatiota ja täyttää nonorammia sen rivin puuttuvien eli (0) kohtien täyttämisestä, olisi brute force toiminut paremmin ja mahdollisesti myös ratkaissut puuttuvat 2 nonogrammia joita minun algoritmini ei pysty ratkaista.
