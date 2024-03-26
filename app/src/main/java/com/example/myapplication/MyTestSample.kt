package com.example.myapplication

import kotlinx.coroutines.NonCancellable

/**
 * The string below contains data in a comma-separated values format (CSV).
 * The data represents a user database or spreadsheet,
 * where each line is one user, and each cell is separated by a comma.
 * If you imported it into a spreadsheet, it would look like this:
 *
 * | Name             | Age | Address             | Phone Number | Email                 | Favorite Number | Employed |
 * +------------------+-----+---------------------+--------------+-----------------------+-----------------+----------+
 * | Burgess Greasley | 33  | 04 Ridge Oak Street | 649-893-5297 | bgreasley@4shared.com | 337             | true     |
 *
 * (just for illustration, you don't need to format the data like this)
 *
 * Your task is to:
 *  - read in all the values in the string below
 *  - sort them by the person's email address
 *  - write the sorted data back out as comma-separated-values (same format as input)
 */

val csv = """
Name,Age,Address,Phone Number,Email,Favorite Number,Employed
Burgess Greasley,33,04 Ridge Oak Street,649-893-5297,bgreasley1@4shared.com,337,true
Derwin Brunel,13,0843 Bunting Hill,790-611-6437,dbrunel2@discovery.com,961,true
Sheffie Spadotto,55,5298 Grover Court,265-791-1163,sspadotto3@salon.com,479,true
Courtney Fearnyhough,63,2102 Garrison Circle,502-971-1269,cfearnyhough4@wikipedia.org,876,true
Melloney Stickens,19,97934 Crownhardt Plaza,145-301-1842,mstickens5@facebook.com,852,false
Ellery Geere,53,7 Kedzie Center,515-764-9730,egeere6@adobe.com,516,false
Boone Malimoe,19,7259 Anhalt Court,776-410-0007,bmalimoe0@canalblog.com,142,false
Nikki Goodere,61,6 Canary Parkway,827-542-0107,ngoodere7@chicagotribune.com,0,true
Annabela Riddel,34,9132 Westridge Way,605-920-8468,ariddel8@w3.org,672,false
""".trimIndent()

fun main() {

    val list = csv.split("\n")
    /*val personList = mutableListOf<PersonTest>()
    list.forEachIndexed { index, value ->
        if (index != 0) {
            val plainPersonList = value.split(",")
            val personTest = PersonTest(plainPersonList[0], plainPersonList[1].toInt(), plainPersonList[2], plainPersonList[3], plainPersonList[4], plainPersonList[5], plainPersonList[6])
            personList.add(personTest)
        }
    }
    val result = personList.filter {
        it.phoneNo.startsWith("6") || it.phoneNo.startsWith("7")
    }*/
    higherOrderFn(list = list, lambda = { localPersonList: List<PersonTest> ->
        val result = localPersonList.filter {
            it.phoneNo.startsWith("6") || it.phoneNo.startsWith("7")
        }
        println("result is: $result")
        result
    })
}

fun higherOrderFn(list: List<String>, lambda: (List<PersonTest>) -> List<PersonTest>) {
    val personList = mutableListOf<PersonTest>()
    list.forEachIndexed { index, value ->
        if (index != 0) {
            val plainPersonList = value.split(",")
            val personTest = PersonTest(plainPersonList[0], plainPersonList[1].toInt(), plainPersonList[2], plainPersonList[3], plainPersonList[4], plainPersonList[5], plainPersonList[6])
            personList.add(personTest)
        }
    }
    lambda(personList)
}

data class PersonTest(val name: String, val age: Int, val address: String, val phoneNo: String, val email: String, val favNo: String, val isEmployed: String)
