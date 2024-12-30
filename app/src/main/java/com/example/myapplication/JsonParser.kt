import org.json.JSONObject

data class SheetEntry(val column1: String, val column2: String)

fun parseSheetData(jsonResponse: String): List<SheetEntry> {
    val dataList = mutableListOf<SheetEntry>()
    val jsonObject = JSONObject(jsonResponse)
    val valuesArray = jsonObject.getJSONArray("values")

    // Skip the header row (index 0)
    for (i in 1 until valuesArray.length()) {
        val row = valuesArray.getJSONArray(i)
        val column1 = row.getString(0)
        val column2 = row.getString(1)
        dataList.add(SheetEntry(column1, column2))
    }
    return dataList
}