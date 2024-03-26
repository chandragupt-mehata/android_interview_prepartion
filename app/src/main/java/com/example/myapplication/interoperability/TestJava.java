package com.example.myapplication.interoperability;

public class TestJava {

    public void soIt(String str) {

    }

    public static void main() {
        Person person = new Person();
        person.doSomething(null);
        //JvmFieldDataClass jvmFieldDataClass = new JvmFieldDataClass("cool", "notcool"); //compile error without jvmOverloads
        JvmFieldDataClass jvmFieldDataClass = new JvmFieldDataClass("cool", "notcool");
        jvmFieldDataClass.getDef();
        String x = jvmFieldDataClass.abc;
    }

    public String titleCanBeNull() {
        return null;
    }

}
