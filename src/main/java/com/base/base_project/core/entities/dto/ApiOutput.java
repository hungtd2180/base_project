package com.base.base_project.core.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hungtd
 * Date: 10/10/2024
 * Time: 2:07 CH
 * for all issues, contact me: hungtd2180@gmail.com
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ApiOutput {
    private Integer status;
    private String message;
    private Object data;
}
