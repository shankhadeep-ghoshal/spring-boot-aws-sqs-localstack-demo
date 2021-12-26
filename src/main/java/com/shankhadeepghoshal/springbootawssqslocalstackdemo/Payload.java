package com.shankhadeepghoshal.springbootawssqslocalstackdemo;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
@Payload1
public class Payload {
    @ToString.Include Integer id;
    @ToString.Include String message;
}
