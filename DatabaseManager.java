<?php

/**
 * Created by PhpStorm.
 * User: alieksi
 * Date: 2/15/2016
 * Time: 11:04 PM
 */
class DatabaseManager
{
    //We may change here.
    private static $username = "testuser";
    private static $password = "testpass";

    //Don't change this
    private static $localhost = "localhost/XE";
    private static $connection;
    private static $db;

    /*
     * Default constructor
     * Since we are doing singleton database,
     * We can't let them create object.
     */
    private function __construct()
    {
        //Since this is a singleton pattern.
        //This will be empty.
    }

    /*
     * Executes read only queries and returns data set.
     */
    public function doQuery($query)
    {
        $stid = oci_parse(self::$connection, $query);

        oci_execute($stid);
        oci_fetch_all($stid, $res);
        oci_free_statement($stid);

        return $res;
    }

    /*
     * Executes any query (Update, Delete, Select etc.)
     * Returns true, if success.
     */
    public function doAnyQuery($query)
    {
        $stid = oci_parse(self::$connection, $query);
        if(oci_execute($stid))
        {
            echo "Query executed successfully!";
            return true;
        }
        else
        {
            echo "Failed to execute query : " + $query;
            return false;
        }
    }

    /*
     * Executes a query and returns with number of affected rows.
     */
    public function doUpdateQuery($query)
    {
        $stid = oci_parse(self::$connection, $query);
        oci_execute($stid);
        $numRows = oci_num_rows($stid);

        return $numRows;
    }

    /**
     * Returns the instance of connection.
     */
    public static function getInstance()
    {
        if(!self::$connection)
        {
            //Make the connection
            self::$connection = oci_connect(self::$username, self::$password, self::$localhost);

            //Check if there is any error.
            if (!self::$connection)
            {
                $e = oci_error();
                trigger_error(htmlentities($e['message'], ENT_QUOTES), E_USER_ERROR);
            }
            else
            {
                self::$db = new DatabaseManager();
                echo "Connected!";
            }
        }

        return self::$db;
    }

    /**
     * Destructor Method
     * Closes the connection if exist.
     */
    public function __destruct()
    {
        if(self::$connection)
            oci_close(self::$connection);
    }
}

$conn = DatabaseManager::getInstance();
$conn->doAnyQuery("INSERT INTO TESTCOLUMNONE VALUES('alissd', 'fault')");
$conn->doAnyQuery("INSERT INTO TESTCOLUMNONE VALUES('little', 'talks')");
$conn->doAnyQuery("UPDATE TESTCOLUMNONE SET uname='littletalks' WHERE uname='alissd'");
$conn->doAnyQuery("DELETE FROM TESTCOLUMNONE WHERE uname = 'ali' ");
echo $conn->doUpdateQuery("UPDATE TESTCOLUMNONE SET uname='littletalks' WHERE uname='little'");

$result = $conn->doQuery('SELECT * FROM TESTCOLUMNONE');
echo "<pre>";
print_r($result);
echo "</pre>";
