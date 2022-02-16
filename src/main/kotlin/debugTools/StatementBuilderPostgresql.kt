package com.example.debugTools

class StatementBuilderPostgresql {

    private var firstValue:Boolean = false

    fun createTableIfExists(tableName: String, values: String): String {
        return "CREATE TABLE IF NOT EXISTS $tableName ($values)"
    }

    fun insertIntoTablesThisMapping(tableName: String, mapping: Map<String, String>): String {
        var tableColumnNameString = ""
        var tableColumnValuesString = ""
        firstValue = true
        mapping.forEach {entry: Map.Entry<String, String> ->
            tableColumnNameString += addSeparator(", ")
            tableColumnNameString += addValueToString(entry.key)
            tableColumnValuesString += addSeparator(",")
            tableColumnValuesString += addValueToString(mapping.getValue(entry.key))
            if (firstValue) {firstValue = false}
        }
        return "INSERT INTO $tableName ($tableColumnNameString) values ($tableColumnValuesString)"
    }

    private fun addValueToString(value: String): String {
        return "$value"
    }

    private fun addSeparator(separator: String): String {
        if (firstValue) { return "" }
        return separator
    }
}