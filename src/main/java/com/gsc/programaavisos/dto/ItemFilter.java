package com.gsc.programaavisos.dto;


import lombok.*;

import java.sql.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemFilter {

    protected int itemType;
    protected int idBrand;
    protected String searchInput;
    protected Date dtEnd;

}
