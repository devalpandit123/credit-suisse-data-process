package com.test.creditsuisse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class DataProcessEvent {
    private String id;
    private long duration;
    private boolean alert;
    private String type;
    private String host;
}
