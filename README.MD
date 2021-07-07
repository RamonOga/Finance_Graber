<h1>Finance_Grabber</h1>

Данный проэкт делял как тех задание. 
Выполняет парсинг компаний с сайта:  
https://finviz.com/screener.ashx?v=111&f=idx_sp500&o=-marketcap
<br>
и цен из yahoo-finance:
<br>
https://query1.finance.yahoo.com/v8/finance/chart/{ticker}

Парсинг делается раз в десять минут. Данные записываются в базу Postgers.

В качестве клиентского интерфеса использовал jsp.


Проэкт сделал за два с половиной дня "чтобы работало". Много что можно-нужно доработать.
Добавить абстракций и использовать SOLID для упрощения кода, пересмотреть структуру данных в БД.
<br>

Знаний Spring и Hibernate на момент написания кода у меня не было. 

примеры пользовтельсого интерфейса
![Иллюстрация к проекту](https://github.com/RamonOga/Finance_Graber/blob/master/img/allPrices.PNG)
<br>
![Иллюстрация к проекту](https://github.com/RamonOga/Finance_Graber/blob/master/img/index.PNG)






