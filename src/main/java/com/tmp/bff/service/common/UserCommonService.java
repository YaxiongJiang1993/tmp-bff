package com.tmp.bff.service.common;


import com.tmp.bff.constant.UserRoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.tmp.bff.constant.UserRoleEnum.NEW_PURE;
import static com.tmp.bff.constant.UserRoleEnum.UN_REGISTER;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * 通用的用户工具服务
 *
 * @author jiangyaxiong
 */
@Slf4j
@Service
public class UserCommonService {

    public Long getUserId() {
        /*
         * 写死一个
         */
        String userId ="23079";
        if (isBlank(userId)) {
            return null;
        }
        return Long.valueOf(userId);
    }

    public UserRoleEnum getUserRole() {
        Long userId = getUserId();
        if (isNull(userId)) {
            return UN_REGISTER;
        }
        /*
         * 判断用户角色逻辑
         */
        return NEW_PURE;
    }

    public int getUserBabyAge(long userId) {
        /*
         * 判断用户角色逻辑
         */

        return 6;
    }

    public boolean isVip() {
        Long userId = getUserId();
        if (userId == null) {
            return false;
        }
        /*
         * vip 判断
         */

        return true;
    }

}
