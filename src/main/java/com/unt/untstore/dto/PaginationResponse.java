package com.unt.untstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginationResponse<T> {

    private int pageNumber;

    private int pageSize;

    private int totalPages;

    private int numberOfElements;

    private long totalElements;

    private List<T> body;

}