package com.devops.app;

import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void testGetMessage() {
        String message = Main.getMessage();
        assertEquals("Bonjour c'est Ghita et bienvenue dans mon projet de DevOps version 2!", message);
    }
}
