<?php

/**
 * Created by PhpStorm.
 * User: alieksi
 * Date: 2/15/2016
 * Time: 11:04 PM
 */
class DatabaseManager
{
    //Change here
    private static $username = "testuser";
    private static $password = "testpass";

    //Don't change this
    private static $localhost = "localhost/XE";
    private static $connection;
    private static $db;

    /*
     * Default constructor
     */
    private function __construct()
    {
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
