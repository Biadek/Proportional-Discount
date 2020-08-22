# ProportionalDiscount

Główna logika obliczania zniżki umieszczona została w klasie ```ProportionalDiscount```. Dziedziczy ona po abstrakcyjnej klasie ```DiscountCalculator```. Dzięki takiemu zabiegowi kod staje się łatwiej rozszerzalny (w przyszłości można dodać np. LinearDiscount). 

```DiscountItem``` jest interfejsem, który implementują obiekty dla których ma być obliczana zniżka. Mogą to być konkretne klasy produktów lub usług. Interfejs wymaga zaimplementowania metody zwracającej cenę.

Klasa ```DiscountCalculator``` przyjmuje jako parametry konstruktora listę przedmiotów (dzięki interfejsowi ```DiscountItem``` odwracam zależność i uniezależniam się od konkretnej implementacji). Kolejnym parametrem jest walidator (również interfejs). Trzeci parametr mówi o całkowitej zniżce. 

Sprawdzanie poprawności danych wejściowych zostało podzielone. Stałe ograniczenia wynikające z domeny sprawdzane są w klasie ```DiscountCalculator```. Poprawność listy przedmiotów sprawdza walidator, który może przyjąć różną instancję w zależności od potrzeb.  By sprawdzić poprawność produktów wykorzystałem ```hiberante-validator``` oraz odpowiednie adnotacje w klasie ```Product```. W przypadku gdy dane wejściowe są niepoprawne, w miejscu wywołania funkcji obliczającej zniżkę zwracana jest wartość null.

Zgodnie z treścią zadania w przypadku, gdy nie da się rozdzielić rabatu w idealnych proporcjach, pozostałość należy dodać do ostatniego produktu. Istnieje jednak przypadek użycia w którym dodanie pozostałości do wcześniej obliczonego rabatu powodowałoby, że rabat jest większy niż pierwotna cena produktu (patrz: ```ProportionalDiscountTest.differenceGreaterThanBasePriceOfTheLastProduct()```). W takiej sytuacji nadaję maksymalny dozwolony rabat dla ostatniego przedmiotu i doliczam pozostałość do przedostatniego itd. Zachowanie to nie niszczy założeń przedstawionych w treści zadania, jednak takie zachowanie programu powinno być skonsultowane z klientem.

Do programu zostały napisane testy jednostkowe.

By zobaczyć działanie programu dla przykładowych danych należy uruchomić metodę main w klasie ```Main```. 
