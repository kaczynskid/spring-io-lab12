package io.spring.lab.store.item;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ItemsBinding {

    @Output("stockUpdate")
    MessageChannel stockUpdate();
}
