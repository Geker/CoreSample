package org.corejava.java8.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.map.HashedMap;

public class StreamTest {
    static public class Person {
        int age;
        String name;
        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person [age=" + age + ", name=" + name + "]";
        }

    }

    public static void main(String[] args) {
        HashMap m = new HashMap<String, String>();
        m.put("name", "sqq");
        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        persons.add(new Person(4, "name6"));
        Collections.shuffle(persons);
        List<Person> personList2 =
                persons.stream().sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).limit(3).skip(2).collect(Collectors.toList());
        System.out.println(personList2);
        
        Map<Integer, Set<String>> s =
                ( Map<Integer, Set<String>> ) persons.stream()
                    .collect(
                        Collectors.groupingBy(Person::getAge, HashedMap::new, Collectors.mapping(Person::getName, Collectors.toSet())));
        System.err.println(s);
        
    }
}
