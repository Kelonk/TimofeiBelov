## Private
This repository is used for HW and etcetera student stuff\
Mostly used for Java + github learning
---
### Practice
This section is dedication for practice with ".md"

Also it's a try to answer additional question - essay

*Задачи, с которыми языки с динамической типизацией справляются лучше, чем со статической*\
Для начала общее пояснение:
- Динамическая типизация позволяет не объявлять требование на тип переменной, например для аргумента функции.
- Статическая же требует явного определения типа переменной при ее создании

По итогу преимущество динамические в том, что она позволяет работу с произвольными типами.

Это полезно для создания функций, принимающие произвольные значения без использования generic. Так функция:
`def sum(a, b): return a + b` (*Python*) сама приведет типы и попытается их сложить. В итоге можно будет передать int, int | str, str | int, float | double, int | str, Object | ...

Одну и ту же функцию можно будет использовать для разных типов и возварщать тоже разные. Python например позволяет еще и определить операцию сложения для классов.

Альтернатива, которую дают типизированные классы это generic и перегрузка функций. Но перегрузка заставляет в основном повторять код функции снова, делая последующее изменение сложнее. Generic же может иметь проблемы с приведением перед операцией или результата функции.

Еще есть преимущества при создании коллекций произвольных типов. 
Если например в **dictionary** заведомо известно, что некоторые ключи будут иметь значения определенного типа, 
то будет удобнее использовать динамическую типизацию и использовать соответственным образом значения с разными ключами. 
Статическая типизаия в этом случае же потребует приведения значений к чему-то наподобию **Object** 
(*В C# есть более удобный для таких случаев **dynamic**, но он может не поддерживаться. Например в случае Unity. 
Да и в основе своей этот костыль является симуляцией динамического типа/слабой типизации*) 
и попыткам обратной конвертации при получении значения по ключу.

В моей собственной практике это понадобилось при работе с json, куда я и сохранял такой словарь. Но оказалось, что **dynamic** в Unity
не поддерживается, из-за чего пришлось делать разные попытки приведения типов для разных полей (*т.к. ключами были **enum**, то такой подход мог быть реализован*). 
Стоить помнить, что этот конкретный пример работал в моем случае, потому что библиотека читающая **в** и **из** json файла интерпретировала типы обратно так, как они были сохранены.
(*Я использовал **Newtonsoft.Json** в С#*)

Ресурсы:

|                                                                                          Ссылка                                                                                           | Описание                                                                           |
|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|------------------------------------------------------------------------------------|
|                                                                                    Собственные знания                                                                                     | None                                                                               |
|                                                                   [Про типизацию](https://habr.com/ru/articles/308484/)                                                                   | Динамическая и статисечкая типизация как таковая в разных языках                   |
| [Про динамическую от wiki](https://ru.wikipedia.org/wiki/%D0%94%D0%B8%D0%BD%D0%B0%D0%BC%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B0%D1%8F_%D1%82%D0%B8%D0%BF%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F) | Страница от *wikipedia* с динамической типизацией                                  |
|              [Про определение операций для классов в Python](https://stackoverflow.com/questions/51036121/define-sum-for-a-class-using-non-associative-addition)                          | Способы определения операции сложения для произвольных (пользовательских) классов  |
|                                      [Про dynamic в C#](https://learn.microsoft.com/ru-ru/dotnet/csharp/advanced-topics/interop/using-type-dynamic)                                       | Описание **dynamic** типа в *C#* от *Microsoft*                                    |
