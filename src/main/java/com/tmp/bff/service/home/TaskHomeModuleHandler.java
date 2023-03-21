package com.tmp.bff.service.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * TaskHomeModuleHandler
 *
 * @author jiangyaxiong
 */

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskHomeModuleHandler {

    private HomeModuleHandler handler;

    private boolean fillResult;
}
