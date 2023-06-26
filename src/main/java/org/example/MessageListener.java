package org.example;

import java.io.IOException;
import java.util.EventListener;

interface MessageListener extends EventListener {
    void onMessage(MessageEvent e) throws IOException;
}
