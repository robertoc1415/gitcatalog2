package com.eshop.catalog.api.mappers;

public interface Mapper<A, B> {
    
    B mapTo(A a);

    A mapFrom(B b);
    
}
