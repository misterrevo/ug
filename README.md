# Opis
<br>
Witam, ta aplikacja to zadanie rekrutacyjne, pozwalajaca przeliczyc wartosc komputerow z usd na pln.<br>
Aplikacja pozwala takze wyszukiwac komputery po nazwie i dacie ksiegowania.<br>

# Jak uruchomic?
<br>
Aby uruchomic projekt, nalezy wejsc w folder <i>target</i> i pobrac plik <i>ug-0.0.1-SNAPSHOT.jar</i>.<br>
Nastepenie otwieramy konsole w miesjcu gdzie jest plik i wpisujemy <i>java -jar ug-0.0.1-SNAPSHOT.jar</i>

# Opis endpointow
<br>
Wyszukiwanie po nazwie<br>
GET - localhost:8080/api/invoices/{name}/{sort} <br>
name - fragment nazwy komputera<br>
sort - do wyboru NAME/POSTING_DATE, okresla rodzaj sortowania<br>
<br>
Wyszukiwanie po dacie<br>
GET - localhost:8080/api/invoices/{start}/{end}/{sort} <br>
start - poczatek zakresu<br>
end- koniec zakresu<br>
sort - do wyboru NAME/POSTING_DATE, okresla rodzaj sortowania<br>
<br>
Zapis do bazy danych i pliku xml<br>
POST - localhost:8080/api/invoices <br>
BODY:<br>
<i>
{
    "computers":[
        {
            "name":"komputer 1",
            "costUSD": 120.00
        },
        {
            "name":"komputer 3",
            "costUSD": 150.00
        }
    ],
    "postingDate":"2023-01-04"
}
</i>

# Zadanie z meila
<br>
Przypadek opisany w meilu jest okodowany w przypadku testowym w klasie InvoiceServiceTest.<br>
Wygenerowany pliki xml znajduja sie w glownym folderze <i></i>(2023-01-03.xml/2023-01-10.xml)</i>
