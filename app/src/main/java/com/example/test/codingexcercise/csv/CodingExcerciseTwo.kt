package com.example.test.codingexcercise.csv

val csvTwo = """
Name, Age, Address, Phone Number, Email, Favorite Number, Employed
Burgess Greasley,33,84 Ridge Oak Street, 649-893-5297, bgreasley1@4shared.com,337,true
Derwin Brunel,13,0843 Bunting Hill,790-611-6437,dbrunel2@discovery.com,961,true
Sheffie Spadotto,55,5298 Grover Court, 265-791-1163, sspadotto3@salon.com,479,true
Courtney Fearnyhough,63,2102 Garrison Circle, 502-971-1269,cfearnyhough4@wikipedia.org,876,true
Melloney Stickens,19,97934 Crownhardt Plaza,145-301-1842,mstickens5@facebook.com,852,false
Ellery Geere,53,7 Kedzie Center,515-764-9730,egeere6@adobe.com,516,false
Boone Malimoe,19,7259 Anhalt Court,776-410-0007,bmalimoee@canalblog.com,142,false
Nikki Goodere,61,6 Canary Parkway,827-542-0107,ngoodere7@chicagotribune.com,0,true
Annabela Riddel,34,9132 Westridge Way,605-920-8468,aridde18@w3.org,672,false
""".trimIndent()

fun main() {
    val data = csvTwo.lines()
    if (data.isNotEmpty()) {
        val header = data.first()
        val result = data
            .asSequence()
            .drop(1)
            .map { it.parseFromString() }
            .sortedBy { it.name }
            .map { it.joinToString() }
            .fold(header) { first, second ->
                buildString {
                    append(first)
                    append(System.lineSeparator())
                    append(second)
                }
            }
        println(result)
    }
}

fun String.parseFromString(): PersonInfo {
    val data = split(",")
    val properties = listOf("name", "age", "address", "phoneNo", "email", "favNo", "isEmployed")
    val mappedValues = properties.zip(data).toMap()
    return PersonInfo(
        name = mappedValues["name"] ?: "",
        age = mappedValues["age"] ?: "",
        address = mappedValues["address"] ?: "",
        phone = mappedValues["phoneNo"] ?: "",
        email = mappedValues["email"] ?: "",
        favNo = mappedValues["favNo"] ?: "",
        isEmployed = mappedValues["isEmployed"].toBoolean()
    )
}

data class PersonInfo(
    val name: String,
    val age: String,
    val address: String,
    val phone: String,
    val email: String,
    val favNo: String,
    val isEmployed: Boolean
) {
    fun joinToString(): String {
        return "$name,$age,$address,$phone,$email,$favNo,$isEmployed"
    }
}


