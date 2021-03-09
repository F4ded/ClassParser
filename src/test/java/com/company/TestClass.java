package com.company;

import java.io.IOException;

interface TestInterface {
    void hello() throws IOException;
}

public class TestClass implements TestInterface {
    private int id;
    final static private String str = "test";

    public TestClass() {
        this.id = 3;
    }

    @Override
    public void hello() throws IOException {
        System.out.println("Hello" + id);
    }
}
