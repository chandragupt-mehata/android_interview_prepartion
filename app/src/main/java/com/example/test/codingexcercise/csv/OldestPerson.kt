package com.example.test.codingexcercise.csv

/**
 * val csvTwo = """
 * Name, Age, Address, Phone Number, Email, Favorite Number, Employed
 * Burgess Greasley,33,84 Ridge Oak Street, 649-893-5297, bgreasley1@4shared.com,337,true
 * Derwin Brunel,13,0843 Bunting Hill,790-611-6437,dbrunel2@discovery.com,961,true
 * Sheffie Spadotto,55,5298 Grover Court, 265-791-1163, sspadotto3@salon.com,479,true
 * Courtney Fearnyhough,63,2102 Garrison Circle, 502-971-1269,cfearnyhough4@wikipedia.org,876,true
 * Melloney Stickens,19,97934 Crownhardt Plaza,145-301-1842,mstickens5@facebook.com,852,false
 * Ellery Geere,53,7 Kedzie Center,515-764-9730,egeere6@adobe.com,516,false
 * Boone Malimoe,19,7259 Anhalt Court,776-410-0007,bmalimoee@canalblog.com,142,false
 * Nikki Goodere,61,6 Canary Parkway,827-542-0107,ngoodere7@chicagotribune.com,0,true
 * Annabela Riddel,34,9132 Westridge Way,605-920-8468,aridde18@w3.org,672,false
 * """.trimIndent()
 */

fun main() {
    findOldestEmployee(csvTwo)
    /*val data = csvTwo.lineSequence()
    val header = data.first()
    val newList= data.drop(1)
        .sortedBy { it.split(',')[1] }
        .fold(header){a,b-> "$a \n$b" }
    println(newList)*/
}

private fun findOldestPerson(csvTwo: String) {
    var maxAge = Int.MIN_VALUE
    var oldestPerson = ""
    csvTwo.lines().drop(1).map {
        val (name, age, _, _, _, _, _) = it.split(",")
        if (age.toInt() > maxAge) {
            maxAge = age.toInt()
            oldestPerson = name
        }
        it.split(",")
    }
    println("oldest person is: $oldestPerson")
}

private fun findOldestEmployee(csvTwo: String) {
    var maxAge = Int.MIN_VALUE
    var oldestPerson = ""
    csvTwo.lines().drop(1).map {
        val (name, age, _, _, _, _, isEmployed) = it.split(",")
        if (isEmployed.toBoolean() && age.toInt() > maxAge) {
            maxAge = age.toInt()
            oldestPerson = name
        }
        it.split(",")
    }
    println("oldest person is: $oldestPerson")
}

