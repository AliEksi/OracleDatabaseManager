# OracleDatabaseManager
With this script you can manage Oracle Database.

# Usage

```php
//Create instance
$conn = DatabaseManager::getInstance();

//Some queries
$conn->doAnyQuery("INSERT INTO TESTCOLUMNONE VALUES('alissd', 'fault')");
$conn->doAnyQuery("INSERT INTO TESTCOLUMNONE VALUES('little', 'talks')");
$conn->doAnyQuery("UPDATE TESTCOLUMNONE SET uname='littletalks' WHERE uname='alissd'");
$conn->doAnyQuery("DELETE FROM TESTCOLUMNONE WHERE uname = 'ali' ");

echo $conn->doUpdateQuery("UPDATE TESTCOLUMNONE SET uname='littletalks' WHERE uname='little'");

$result = $conn->doQuery('SELECT * FROM TESTCOLUMNONE');
echo "<pre>";
print_r($result);
echo "</pre>";
```
