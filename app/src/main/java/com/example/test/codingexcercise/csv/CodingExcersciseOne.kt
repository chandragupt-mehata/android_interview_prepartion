package com.example.test.codingexcercise.csv

val csvNew = """
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
    val csvList = csvNew.lines()
    if (csvList.isNotEmpty()) {
        val header = csvList.first().trim()
        val recordDetails = getSortedRecordDetails(csvList)
        val output = header + "\n" + recordDetails
        println(output)
    }
}

fun getSortedRecordDetails(csvList: List<String>): String {
    return csvList.drop(1).map {
        val(name, age, address, phoneNo, email, favNo, isEmployed)  = it.split(",")
        PersonDetails(name, age, address, phoneNo, email, favNo, isEmployed)
    }.sortedBy { it.name }.joinToString("\n") {
        "${it.name}, ${it.age}, ${it.address}, ${it.phoneNo}, ${it.email}, ${it.favNo}, ${it.isEmployed}"
    }
}

data class PersonDetails(val name: String, val age: String, val address: String, val phoneNo: String, val email: String, val favNo: String, val isEmployed: String) {

    // equals, toString, copy, componentN, hashCode

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other is PersonDetails) {
            return (this.name == other.name && this.age == other.age && this.address == other.address && this.phoneNo == other.phoneNo
                    && this.email == other.email && this.favNo == other.favNo && this.isEmployed == other.isEmployed)
        }
        return false
    }

    override fun hashCode(): Int {
        return (name.hashCode() + age.hashCode() + address.hashCode() + phoneNo.hashCode() +
                email.hashCode() + favNo.hashCode() + isEmployed.hashCode())
    }

}

operator fun <T> List<T>.component6() = this[5]
operator fun <T> List<T>.component7() = this[6]
